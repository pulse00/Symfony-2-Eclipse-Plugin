/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.index;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.eclipse.core.runtime.IPath;

import com.dubture.symfony.index.dao.IResourceDao;
import com.dubture.symfony.index.dao.IRouteDao;
import com.dubture.symfony.index.dao.IServiceDao;
import com.dubture.symfony.index.dao.ITransUnitDao;
import com.dubture.symfony.index.dao.Route;
import com.dubture.symfony.index.dao.RoutingResource;
import com.dubture.symfony.index.dao.TransUnit;
import com.dubture.symfony.index.log.Logger;


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
	private Connection connection;
	private IServiceDao serviceDao;
	private IRouteDao routeDao;
	private IResourceDao resourceDao;
	private ITransUnitDao transDao;
	
	private SymfonyIndexer() throws Exception {

		factory = SymfonyDbFactory.getInstance();
		connection = factory.createConnection();
		serviceDao = factory.getServiceDao();
		routeDao = factory.getRouteDao();
		transDao = factory.getTransDao();
		resourceDao = factory.getResourceDao();		
		
	}
	

	public static SymfonyIndexer getInstance() throws Exception {
		
		if (instance == null)
			instance = new SymfonyIndexer();
		
		return instance;		
		
	}
	

	public void addRoute(Route route, IPath path) {
				
		addRoute(route.name, route.pattern, route.controller, route.bundle, route.action, path);
		
	}
	
	
	
	public void addRoute(String name, String pattern, String controller, String bundle, String action, IPath path) {
				
		try {
			
			routeDao.deleteRoutesByPath(connection, name, path);
			routeDao.insert(connection, name, pattern, controller, bundle, action, path);
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
	
	
	public void addService(String id, String phpClass, String _public, List<String> tags, String path, int timestamp) {
		
		try {			
		    serviceDao.delete(connection, id, path);
			serviceDao.insert(connection, id, phpClass, _public, tags, path, timestamp);
		} catch (Exception e) {
			Logger.logException(e);
		}		
		
	}
		
	public void deleteServices(String path) {
		
		serviceDao.deleteServices(connection, path);
	}
	
	public void enterServices(String path) {

		serviceDao.deleteServices(connection, path);		
		
	}
	

	public void exitServices() {
		
		try {
			serviceDao.commitInsertions();
		} catch (Exception e) {
			Logger.logException(e);
		}		
	}
	
	public void findServices(String string, IServiceHandler iServiceHandler) {

		serviceDao.findServicesByPath(connection, string, iServiceHandler);		

	}

	public void findService(String id, String path,
			IServiceHandler iServiceHandler) {

		
		serviceDao.findService(connection, id, path, iServiceHandler);
		
	}
	
	
	public List<String> findTags(IPath path) {
		
		return serviceDao.findTags(connection, path);
	}


	public void exitRoutes() {

		try {
			routeDao.commitInsertions();
		} catch (SQLException e) {
			Logger.logException(e);
		}
		
	}


	public List<Route> findRoutes(IPath path) {

		try {
			return routeDao.findRoutes(connection, path);
		} catch (Exception e) {
			Logger.logException(e);
			return null;			
		}
	}
	
	public List<Route> findRoutes(IPath path, String prefix) {

		try {
			return routeDao.findRoutes(connection, prefix, path);
		} catch (Exception e) {
			Logger.logException(e);
			return null;			
		}
	}
	


	public Route findRoute(String route, IPath path) {

		try {
			return routeDao.findRoute(connection, route, path);
		} catch (Exception e) {
			Logger.logException(e);
			return null;
		}
	}


	public void addTranslation(TransUnit unit, String path, int timestamp) {


		try {
			transDao.deleteRoutesByPath(connection, unit.name, unit.language, path);
			transDao.insert(connection, path, unit.name, unit.value, unit.language, timestamp);
		} catch (SQLException e) {
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

		transDao.findTranslations(connection, path, iTranslationHandler);
		
	}
	
	
	public void findTranslations(String name, String path, ITranslationHandler handler) {

		transDao.findTranslations(connection, name, path, handler);
		
	}


	public List<Route> findRoutesByBundle(String bundleAlias, IPath path) {

		return routeDao.findRoutesByBundle(connection, bundleAlias, path);
		
	}
	
	public List<Route> findRoutesByController(String bundleAlias, String controller, IPath path) {
		
		return routeDao.findRoutesByController(connection, bundleAlias, controller, path);
		
	}


	public void addResource(RoutingResource resource, IPath fullPath) {

		try {
			
//			routeDao.deleteRoutesByPath(connection, name, path);
			resourceDao.insert(connection, resource.getPath(), resource.getType(), resource.getPrefix(), fullPath);
			
		} catch (Exception e) {
			Logger.logException(e);
		}
		
	}


	public void exitResources() {

		try {
			resourceDao.commitInsertions();
		} catch (SQLException e) {
			Logger.logException(e);
		}		
		
		
	}


	public void findResources(IPath path, IResourceHandler iResourceHandler) {

		resourceDao.findResource(connection, path, iResourceHandler);
		
	}
	
}
