package org.eclipse.symfony.core.builder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.symfony.core.log.Logger;
import org.eclipse.symfony.core.parser.XMLConfigParser;
import org.eclipse.symfony.core.parser.YamlConfigParser;
import org.eclipse.symfony.core.parser.YamlRoutingParser;
import org.eclipse.symfony.core.preferences.ProjectOptions;
import org.eclipse.symfony.index.SymfonyIndexer;
import org.eclipse.symfony.index.dao.Route;
import org.json.simple.JSONObject;
import org.yaml.snakeyaml.scanner.ScannerException;


/**
 * 
 * The {@link ResourceVisitor} is a standard buildvisitor to 
 * parse xml/yml config files from a Symfony2 project during
 * a full build.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class ResourceVisitor extends AbstractSymfonyVisitor 
implements IResourceVisitor {

	private IFile file;
	private IPath path;
	private SymfonyIndexer indexer;
	private int timestamp;

	@Override
	public boolean visit(IResource resource) throws CoreException {
		
		try {

			if (resource instanceof IFile && resource.getFileExtension() != null) {

				indexer = SymfonyIndexer.getInstance();
				file = (IFile) resource;
				path = resource.getFullPath();
				resource.getParent();
				timestamp = (int) resource.getLocalTimeStamp();
				

				if (resource.getFileExtension().equals("xml"))
				{				
					loadXML();

				} else if (resource.getFileExtension().equals("yml")) 
				{
					
					if (resource.getName().equals("services.yml")) {					
						loadYaml();
					} else if (resource.getName().contains("routing")) {
						loadYamlRouting();
					}
				}
			}
		} catch (Exception e) {

			Logger.logException(e);
		}

		return true;
	}


	private void loadYamlRouting() {

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


	private void indexRoutes(Stack<Route> routes) {

		//indexer.enterRoutes();
		for (Route route : routes) {
			indexer.addRoute(route, file.getProject().getFullPath());
		}
		indexer.exitRoutes();		
		
	}


	private void loadYaml() {

		try {
			
			YamlConfigParser parser = new YamlConfigParser(file.getContents());
			parser.parse();

			indexServices(parser.getServices());

		} catch (Exception e1) {

			Logger.log(Logger.INFO, e1.getMessage());

		}

	}

	private void loadXML() {

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

			Logger.log(Logger.INFO_DEBUG, e.getMessage());

		}
	}

	@SuppressWarnings({ "rawtypes" })
	private void indexServices(HashMap<String, String> services) {

		try {

			JSONObject syntheticServices = ProjectOptions.getSyntheticServices(file.getProject());
			indexer.enterServices(path.toString());
			Iterator it = services.keySet().iterator();

			while(it.hasNext()) {

				String id = (String) it.next();
				String phpClass = services.get(id);				
				
				if(phpClass.equals("synthetic")) {
					
					
					if (syntheticServices.containsKey(id)) {

						String pc = (String) syntheticServices.get(id);
						
						if (pc != null ) {
							phpClass = pc;
						} else {
							phpClass = (String) syntheticServices.get(id);	
						}

						
					} else phpClass = "";
				}
				
				indexer.addService(id, phpClass, path.toString(), timestamp);

			}
			
			indexer.exitServices();			

		} catch (Exception e) {
			Logger.logException(e);
		}
	}
}