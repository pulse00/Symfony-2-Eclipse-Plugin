/**
 * 
 */
package org.eclipse.symfony.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import junit.framework.TestCase;

import org.eclipse.symfony.core.parser.YamlConfigParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
			
			String dir = System.getProperty("user.dir") + "/Resources/config/config.yml";
			FileInputStream input;

			input = new FileInputStream(new File(dir));
			
			YamlConfigParser parser = new YamlConfigParser(input);
			parser.parse();
			
			HashMap<String, String> services = parser.getServices();
			
			assertNotNull(services);
			assertTrue(services.size() == 2);
			
			assertEquals(services.get("my_mailer"), "Acme\\HelloBundle\\Mailer");
			assertEquals(services.get("my_service"), "Acme\\DemoBundle\\MyService");			
			
			
		} catch (Exception e) {

			e.printStackTrace();
			fail();
			
		}		
	}
}