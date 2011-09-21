/**
 * 
 */
package com.dubture.symfony.core.builder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.yaml.snakeyaml.scanner.ScannerException;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.parser.XMLConfigParser;
import com.dubture.symfony.core.parser.YamlConfigParser;
import com.dubture.symfony.core.parser.YamlRoutingParser;
import com.dubture.symfony.core.preferences.ProjectOptions;
import com.dubture.symfony.index.SymfonyIndexer;
import com.dubture.symfony.index.dao.Route;


/**
 *
 * Abstract visitor to provide xml- and yml parsers.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public abstract class AbstractSymfonyVisitor {

	protected IFile file;
	protected IPath path;
	protected SymfonyIndexer indexer;
	protected int timestamp;
	protected JSONArray syntheticServices;
	

	protected boolean handleResource(IResource resource) {

		boolean built = false;
		
		try {
			
				if (resource instanceof IProject || resource instanceof IFolder) {
					
					return resource.getProject().hasNature(SymfonyNature.NATURE_ID);
					
				}
					
				if (resource instanceof IFile) {
					
					indexer = SymfonyIndexer.getInstance();
					file = (IFile) resource;
					path = resource.getFullPath();
					resource.getParent();
					timestamp = (int) resource.getLocalTimeStamp();
					
					if (resource.getFileExtension().equals("xml"))
					{				
						loadXML();
						built = true;

					} else if (resource.getFileExtension().equals("yml")) {
						
						if (resource.getName().equals("services.yml")) {					
							loadYaml();
							built = true;
						} else if (resource.getName().contains("routing")) {
							loadYamlRouting();
							built = true;
						}
					}
					
				}

			
		} catch (Exception e) {

			Logger.logException(e);
		}
		
		return built;
		
	}
	

	protected JSONArray getSynthetics() {
		
		if (syntheticServices == null)
			syntheticServices = ProjectOptions.getSyntheticServices(file.getProject());;
		
		return syntheticServices;
		
	}


	protected void loadYamlRouting() {

		try {			

			YamlRoutingParser parser = new YamlRoutingParser(file.getContents());
			parser.parse();
			
			indexRoutes(parser.getRoutes());		
			
		} catch (ScannerException se) {
			Logger.logException(se);
		} catch (Exception e) {		
			Logger.logException(e);
		}
	}


	protected void indexRoutes(Stack<Route> routes) {

		//indexer.enterRoutes();
		for (Route route : routes) {
			indexer.addRoute(route, file.getProject().getFullPath());
		}
		indexer.exitRoutes();		
		
	}


	protected void loadYaml() {

		try {
			
			YamlConfigParser parser = new YamlConfigParser(file.getContents());
			parser.parse();

			indexServices(parser.getServices());

		} catch (Exception e1) {

			Logger.logException(e1);

		}

	}

	protected void loadXML() {

		try {

			XMLConfigParser parser;					
			parser = new XMLConfigParser(file.getContents());
			parser.parse();

			if (parser.hasServices()) {
				indexServices(parser.getServices());
			}
			
			if (parser.hasRoutes()) {
				indexRoutes(parser.getRoutes());
			}

		} catch (Exception e) {

			Logger.logException(e);

		}
	}

	@SuppressWarnings({ "rawtypes" })
	protected void indexServices(HashMap<String, String> services) {

		try {

			indexer.enterServices(path.toString());
			Iterator it = services.keySet().iterator();
			
			JSONArray synths = getSynthetics();

			while(it.hasNext()) {

				String id = (String) it.next();
				String phpClass = services.get(id);				
				
				if(phpClass.equals(Service.SYNTHETIC)) {
					for (Object o : synths) {
						JSONObject service = (JSONObject) o;
						if (service.get(Service.NAME).equals(id)) {							
							phpClass = (String) service.get(Service.CLASS);
							break;
						}
					}
				}
				
				System.err.println("index service");
				indexer.addService(id, phpClass, path.toString(), timestamp);
			}
			
			indexer.exitServices();			

		} catch (Exception e) {
			Logger.logException(e);
		}
	}	
}
