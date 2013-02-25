/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
/**
 * 
 */
package com.dubture.symfony.core.builder;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.parser.XMLConfigParser;
import com.dubture.symfony.core.parser.YamlConfigParser;
import com.dubture.symfony.core.parser.YamlRoutingParser;
import com.dubture.symfony.core.parser.YamlTranslationParser;
import com.dubture.symfony.core.preferences.ProjectOptions;
import com.dubture.symfony.core.util.TranslationUtils;
import com.dubture.symfony.index.SymfonyIndexer;
import com.dubture.symfony.index.model.Route;
import com.dubture.symfony.index.model.RoutingResource;
import com.dubture.symfony.index.model.Service;
import com.dubture.symfony.index.model.TransUnit;

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
	
	protected boolean handleResource(IResource resource) throws Exception {

		boolean built = false;

		if (resource instanceof IProject || resource instanceof IFolder) {
			return resource.getProject().hasNature(SymfonyNature.NATURE_ID);
		} else if (!(resource instanceof IFile)) {
			return false;
		}
		
		IScriptProject scriptProject = DLTKCore.create(resource.getProject());
		boolean isOnBuildPath = scriptProject.isOnBuildpath(resource);
		indexer = SymfonyIndexer.getInstance();
		file = (IFile) resource;
		path = resource.getFullPath();
		timestamp = (int) resource.getLocalTimeStamp();

		if (resource.getName().toLowerCase().endsWith("devdebugprojectcontainer.xml")) {
			loadDumpedXmlContainer(resource);
			built = true;
		} else if ("xml".equals(resource.getFileExtension()) && isOnBuildPath) {
			loadXML(resource);
			built = true;
		} else if ("yml".equals(resource.getFileExtension()) && isOnBuildPath) {
			if (resource.getName().contains("routing")) {
				loadYamlRouting();
				built = true;
			} else if (resource.getFullPath().toString().contains("translations")) {
				loadYamlTranslation();
			}
		}

		return built;
	}

	@SuppressWarnings("rawtypes")
	protected void loadYamlTranslation() throws Exception {

		YamlTranslationParser parser = new YamlTranslationParser(file.getContents());
		parser.parse();
		String lang = TranslationUtils.getLanguageFromFilename(file.getName());
		Map<String, String> transUnits = parser.getTranslations();
		Iterator it = transUnits.keySet().iterator();
		List<TransUnit> translations = new ArrayList<TransUnit>();

		while (it.hasNext()) {
			String key = (String) it.next();
			String value = transUnits.get(key);
			TransUnit unit = new TransUnit(key, value, lang);
			translations.add(unit);
		}

		indexTranslations(translations);
	}

	protected JSONArray getSynthetics() {
		if (syntheticServices == null) {
			syntheticServices = ProjectOptions.getSyntheticServices(file.getProject());
		}

		return syntheticServices;
	}

	protected void loadYamlRouting() throws CoreException {
		YamlRoutingParser parser = new YamlRoutingParser(file.getContents());
		parser.parse();
		indexRoutes(parser.getRoutes());
		indexResources(parser.getResources());
	}

	protected void indexTranslations(List<TransUnit> translations) {
		for (TransUnit unit : translations) {
			indexer.addTranslation(unit, file.getFullPath().toString(), timestamp);
			Logger.debugMSG(String.format("indexing translational: %s", unit.toString()));
		}

		indexer.exitTranslations();
	}

	protected void indexRoutes(Stack<Route> routes) {
		// indexer.enterRoutes();
		for (Route route : routes) {
			indexer.addRoute(route, file.getProject().getFullPath());
		}

		indexer.exitRoutes();
	}

	protected void indexResources(Stack<RoutingResource> resources) {
		for (RoutingResource resource : resources) {
			Logger.debugMSG("indexing resource: " + resource.toString());
			indexer.addResource(resource, file.getProject().getFullPath());
		}

		indexer.exitResources();
	}

	protected void loadYaml() throws Exception {
		YamlConfigParser parser = new YamlConfigParser(file.getContents());
		parser.parse();
		indexServices(parser.getServices());
	}

	protected void loadDumpedXmlContainer(IResource resource) throws Exception {
		FileInputStream fis = new FileInputStream(file.getLocation().toFile());
		XMLConfigParser parser = new XMLConfigParser(fis);
		parser.parse();
		indexServices(parser.getServices());
		indexParameters(parser.getParameters());
	}

	@SuppressWarnings("rawtypes")
	private void indexParameters(HashMap<String, String> parameters) {
		Iterator it = parameters.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = parameters.get(key);
			if (key.isEmpty() || value.isEmpty()) {
				continue;
			}
			try {
				indexer.addParameter(key, value, path);
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
	}

	protected void loadXML(IResource resource) {
		try {
			FileInputStream fis = new FileInputStream(file.getLocation().toFile());
			XMLConfigParser parser = new XMLConfigParser(fis);
			parser.parse();
			if (parser.hasRoutes()) {
				indexRoutes(parser.getRoutes());
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
	

	@SuppressWarnings({ "rawtypes" })
	protected void indexServices(HashMap<String, Service> services) {
		indexer.enterServices(path.toString());
		Iterator it = services.keySet().iterator();
		JSONArray synths = getSynthetics();

		while (it.hasNext()) {

			String id = (String) it.next();
			Service service = services.get(id);

			if (service == null) {
				Logger.log(Logger.WARNING, "error parsing service " + id);
				continue;
			}

			if (service.phpClass != null && Service.SYNTHETIC.equals(service.phpClass)) {
				for (Object o : synths) {
					JSONObject _s = (JSONObject) o;
					if (_s.get(Service.NAME).equals(id)) {
						service.phpClass = (String) _s.get(Service.CLASS);
						break;
					}
				}
			}

			String _pub = service.isPublic() ? "true" : "false";
			indexer.addService(id, service.phpClass, _pub, service.getTags(), path.toString(), timestamp);
		}

		indexer.exitServices();
	}
}
