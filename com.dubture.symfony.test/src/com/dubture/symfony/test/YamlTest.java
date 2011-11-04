/**
 * 
 */
package com.dubture.symfony.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dubture.symfony.core.parser.YamlConfigParser;
import com.dubture.symfony.index.dao.Service;

/**
 * 
 * Tests for the {@link YamlConfigParser} class.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class YamlTest extends TestCase {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testServices() {

		try {
			
			Service service = null;
			String dir = System.getProperty("user.dir") + "/Resources/config/config.yml";
			FileInputStream input;

			input = new FileInputStream(new File(dir));
			
			YamlConfigParser parser = new YamlConfigParser(input);
			parser.parse();
			
			HashMap<String, Service> services = parser.getServices();
			
			assertNotNull(services);
			assertTrue(services.size() == 2);
			
			service = services.get("my_mailer");			
			assertTrue(service instanceof Service);
			assertEquals("my_mailer", service.id);
			assertEquals("Acme\\HelloBundle\\Mailer", service.phpClass);
			
			service = services.get("my_service");			
			assertTrue(service instanceof Service);
			assertEquals("my_service", service.id);
			assertEquals("Acme\\DemoBundle\\MyService", service.phpClass);
			
			
			
		} catch (Exception e) {

			e.printStackTrace();
			fail();
			
		}		
	}
}