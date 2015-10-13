/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.test;


import java.sql.Connection;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dubture.symfony.index.SymfonyDbFactory;
import com.dubture.symfony.index.dao.IServiceDao;
import com.dubture.symfony.index.handler.IServiceHandler;
import com.dubture.symfony.index.model.Service;

import junit.framework.TestCase;

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
		
		serviceDao.truncate();
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
			Connection connection = SymfonyDbFactory.getInstance().createConnection();
			serviceDao.insert(connection, "request", "Symfony\\Request\\Request", "true", null, "/foo/bar", 0);
			serviceDao.insert(connection, "many", "Symfony\\Doctrine\\ORM\\ManyToOne", "true", null, "/moo/lar", 0);
			serviceDao.insert(connection, "kernel", "Symfony\\Http\\Kernel", "false", null, "/sy/ror", 0);
			serviceDao.commitInsertions();
			connection.commit();
			connection.close();

			final Stack<Service> services = new Stack<Service>();
			
			serviceDao.findAll(new IServiceHandler() {
				
				@Override
				public void handle(String id, String phpClass, String path, String _public, String tags) {
					Service s = new Service(id, phpClass, path);
					s.setPublic(_public);
					
					s.setTags(tags);
					services.add(s);
					
				}
			});
			
			assertEquals(3, services.size());
						
			Service service = serviceDao.find("request");
			
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
