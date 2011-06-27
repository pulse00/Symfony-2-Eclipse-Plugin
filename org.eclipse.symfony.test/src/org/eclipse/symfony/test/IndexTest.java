package org.eclipse.symfony.test;


import java.sql.Connection;
import java.util.Stack;

import junit.framework.TestCase;

import org.eclipse.symfony.index.IServiceHandler;
import org.eclipse.symfony.index.SymfonyDbFactory;
import org.eclipse.symfony.index.dao.IServiceDao;
import org.eclipse.symfony.index.dao.Service;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * Tests for the SQL index.
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public class IndexTest extends TestCase {

	private Connection connection;
	private SymfonyDbFactory factory;
	private IServiceDao serviceDao;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		
		factory = SymfonyDbFactory.getInstance();		
		assertNotNull(factory);
		
		connection = factory.createConnection();		
		assertNotNull(connection);
		
		serviceDao = factory.getServiceDao();		
		assertNotNull(serviceDao);
		
		serviceDao.truncate(connection);
		
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
		
		connection.close();
		factory = null;
		connection = null;
		serviceDao = null;
	}

	@Test
	public void test() {

		try {
			
			serviceDao.insert(connection, "/foo/bar", "request", "Symfony\\Request\\Request", 0);
			serviceDao.insert(connection, "/moo/lar", "many", "Symfony\\Doctrine\\ORM\\ManyToOne", 0);
			serviceDao.insert(connection, "/sy/ror", "kernel", "Symfony\\Http\\Kernel", 0);
			serviceDao.commitInsertions();
			connection.commit();

			final Stack<Service> services = new Stack<Service>();
			
			serviceDao.findAll(connection, new IServiceHandler() {
				
				@Override
				public void handle(String id, String phpClass, String path) {					
					services.add(new Service(id, phpClass, path));
					
				}
			});
			
			assertEquals(3, services.size());
						
			Service service = serviceDao.find(connection, "request");
			
			assertNotNull(service);
			assertEquals("request", service.id);
			assertEquals("/foo/bar", service.path);
			assertEquals("Symfony\\Request\\Request", service.phpClass);
			
			
		} catch (Exception e) {

			e.printStackTrace();
			fail();
			
		}
	}
}