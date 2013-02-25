/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.index;

import java.util.List;

import org.eclipse.core.runtime.IPath;

import com.dubture.symfony.index.dao.IParameterDao;
import com.dubture.symfony.index.dao.IResourceDao;
import com.dubture.symfony.index.dao.IRouteDao;
import com.dubture.symfony.index.dao.IServiceDao;
import com.dubture.symfony.index.dao.ITransUnitDao;
import com.dubture.symfony.index.handler.IResourceHandler;
import com.dubture.symfony.index.handler.IServiceHandler;
import com.dubture.symfony.index.handler.ITranslationHandler;
import com.dubture.symfony.index.log.Logger;
import com.dubture.symfony.index.model.Parameter;
import com.dubture.symfony.index.model.Route;
import com.dubture.symfony.index.model.RoutingResource;
import com.dubture.symfony.index.model.TransUnit;


/**
 * 
 * {@link SymfonyIndexer} is the main class to index
 * Symfony2 services and routes in a local H2 SQL 
 * database.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class SymfonyIndexer {
	
	private static SymfonyIndexer instance = null;
	private SymfonyDbFactory factory;
	private IServiceDao serviceDao;
	private IRouteDao routeDao;
	private IResourceDao resourceDao;
	private ITransUnitDao transDao;
	private IParameterDao paramDao;
	
	private SymfonyIndexer() throws Exception {
		factory = SymfonyDbFactory.getInstance();
		serviceDao = factory.getServiceDao();
		routeDao = factory.getRouteDao();
		transDao = factory.getTransDao();
		resourceDao = factory.getResourceDao();
		paramDao = factory.getParamDao();
	}
	
	public static SymfonyIndexer getInstance() throws Exception {
		if (instance == null) {
			instance = new SymfonyIndexer();
		}
		return instance;		
	}
	

	public void addRoute(Route route, IPath path) {
		addRoute(route.name, route.pattern, route.controller, route.bundle, route.action, path);
	}
	
	public void addRoute(String name, String pattern, String controller, String bundle, String action, IPath path) {
		try {
			routeDao.deleteRoutesByPath(name, path);
			routeDao.insert(name, pattern, controller, bundle, action, path);
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
	
	public void addService(String id, String phpClass, String _public, List<String> tags, String path, int timestamp) {
		try {			
		    serviceDao.delete(id, path);
			serviceDao.insert(id, phpClass, _public, tags, path, timestamp);
		} catch (Exception e) {
			Logger.logException(e);
		}		
	}
	
	public void deleteServices(String path) {
		serviceDao.deleteServices(path);
	}
	
	public void enterServices(String path) {
		serviceDao.deleteServices(path);
		paramDao.deleteParameters(path);
	}
	
	public void exitServices() {
		try {
			serviceDao.commitInsertions();
			paramDao.commitInsertions();
		} catch (Exception e) {
			Logger.logException(e);
		}		
	}
	
	public void findServices(String string, IServiceHandler iServiceHandler) {
		serviceDao.findServicesByPath(string, iServiceHandler);		
	}

	public void findService(String id, String path, IServiceHandler iServiceHandler) {
		serviceDao.findService(id, path, iServiceHandler);
	}
	
	public List<String> findTags(IPath path) {
		return serviceDao.findTags(path);
	}

	public void exitRoutes() {
		try {
			routeDao.commitInsertions();
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	public List<Route> findRoutes(IPath path) {
		try {
			return routeDao.findRoutes(path);
		} catch (Exception e) {
			Logger.logException(e);
			return null;			
		}
	}
	
	public List<Parameter> findParameters(IPath path) {
		try {
			return paramDao.findParameters(path);
		} catch (Exception e) {
			Logger.logException(e);
			return null;			
		}
	}
	
	public List<Route> findRoutes(IPath path, String prefix) {
		try {
			return routeDao.findRoutes(prefix, path);
		} catch (Exception e) {
			Logger.logException(e);
			return null;			
		}
	}
	
	public Route findRoute(String route, IPath path) {
		try {
			return routeDao.findRoute(route, path);
		} catch (Exception e) {
			Logger.logException(e);
			return null;
		}
	}

	public void addTranslation(TransUnit unit, String path, int timestamp) {
		try {
			transDao.deleteRoutesByPath(unit.name, unit.language, path);
			transDao.insert(path, unit.name, unit.value, unit.language, timestamp);
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
	
	public void addParameter(String key, String value, IPath path) {
		try {
			paramDao.insert(key, value, path);
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	public void exitTranslations() {
		try {
			transDao.commitInsertions();
		} catch (Exception e) {
			Logger.logException(e);
		}		
	}

	public void findTranslations(String path, ITranslationHandler iTranslationHandler) {
		transDao.findTranslations(path, iTranslationHandler);
	}
	
	public void findTranslations(String name, String path, ITranslationHandler handler) {
		transDao.findTranslations(name, path, handler);
	}

	public List<Route> findRoutesByBundle(String bundleAlias, IPath path) {
		return routeDao.findRoutesByBundle(bundleAlias, path);
	}
	
	public List<Route> findRoutesByController(String bundleAlias, String controller, IPath path) {
		return routeDao.findRoutesByController(bundleAlias, controller, path);
	}

	public void addResource(RoutingResource resource, IPath fullPath) {
		try {
//			routeDao.deleteRoutesByPath(name, path);
			resourceDao.insert(resource.getPath(), resource.getType(), resource.getPrefix(), fullPath);
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	public void exitResources() {
		try {
			resourceDao.commitInsertions();
		} catch (Exception e) {
			Logger.logException(e);
		}		
	}

	public void findResources(IPath path, IResourceHandler iResourceHandler) {
		resourceDao.findResource(path, iResourceHandler);
	}
}
