package org.eclipse.symfony.index;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SymfonyIndexer {
	
	private static SymfonyIndexer instance = null;
	
	private SymfonyDbFactory factory;
	private Connection connection;
	private IServiceDao serviceDao;
	private TemplateVarDao templateDao;
	
	private SymfonyIndexer() throws Exception {

		factory = SymfonyDbFactory.getInstance();
		connection = factory.createConnection();
		serviceDao = factory.getServiceDao();
		templateDao = factory.getTemplateDao();
		
	}
	

	public static SymfonyIndexer getInstance() throws Exception {
		
		if (instance == null)
			instance = new SymfonyIndexer();
		
		return instance;		
		
	}
	
	
	public void addService(String id, String phpClass, String path, int timestamp) {
		
		try {
			serviceDao.insert(connection, path, id, phpClass, timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	public void addTemplateVariable(String varName, String phpClass, String namespace, String path) {
		
		try {
			templateDao.insert(connection,varName, phpClass, namespace, path, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startIndexing(String path) {

		serviceDao.deleteServices(connection, path);		
		
	}

	public void commit() throws Exception {
		
		serviceDao.commitInsertions();
		
	}


	public void findServices(String string, IServiceHandler iServiceHandler) {

		serviceDao.findServicesByPath(connection, string, iServiceHandler);		

	}


	public void findService(String id, String path,
			IServiceHandler iServiceHandler) {

		
		serviceDao.findService(connection, id, path, iServiceHandler);
		
	}


	public void commitVariables() {

		try {
			templateDao.commitInsertions();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}