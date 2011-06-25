package org.eclipse.symfony.test;


import java.sql.Connection;

import junit.framework.TestCase;

import org.eclipse.symfony.index.IServiceDao;
import org.eclipse.symfony.index.SymfonyDbFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IndexTest extends TestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void test() {

		Connection connection = null;
		try {

			SymfonyDbFactory factory = SymfonyDbFactory.getInstance();
			connection = factory.createConnection();
			IServiceDao serviceDao = factory.getServiceDao();
			
			
			
		} catch (Exception e) {

			
			e.printStackTrace();
			fail();
			
		}
	}
}
