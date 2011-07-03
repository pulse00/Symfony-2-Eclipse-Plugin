package org.eclipse.symfony.index;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.symfony.index.dao.IRouteDao;
import org.eclipse.symfony.index.dao.IServiceDao;
import org.eclipse.symfony.index.dao.Route;
import org.eclipse.symfony.index.log.Logger;


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
	
	private SymfonyIndexer() throws Exception {

		factory = SymfonyDbFactory.getInstance();
		connection = factory.createConnection();
		serviceDao = factory.getServiceDao();
		routeDao = factory.getRouteDao();
		
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
	
	
	public void addService(String id, String phpClass, String path, int timestamp) {
		
		try {			
			serviceDao.insert(connection, path, id, phpClass, timestamp);
		} catch (Exception e) {
			Logger.logException(e);
		}		
		
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

		}
		return null;
	}
}