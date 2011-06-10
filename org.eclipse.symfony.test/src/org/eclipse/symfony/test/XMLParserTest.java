package org.eclipse.symfony.test;


import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import junit.framework.TestCase;

import org.eclipse.symfony.core.parser.XMLConfigParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * TestCase for the {@link XMLConfigParser}
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class XMLParserTest extends TestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}


	/**
	 * Test parsing of service definitions.
	 */
	@Test	
	public void testServices() {

		String dir = System.getProperty("user.dir") + "/Resources/config/services1.xml";
		
		try {
		
			FileInputStream input = new FileInputStream(new File(dir));
			XMLConfigParser parser = new XMLConfigParser(input);
			parser.parse();
			
			HashMap<String, String>services = parser.getServices();
			
			assertTrue(services.size() == 3);
			
			String tplAnnotation = "view.template_annotation";
			String tplSomething = "view.template_something";
			String serviceAlias= "view.some_alias";
			
			String listenerClass = "Sensio\\Bundle\\FrameworkExtraBundle\\View\\AnnotationTemplateListener";
			String somethingClass = "Sensio\\Bundle\\FrameworkExtraBundle\\View\\Something";
			
			assertTrue(services.containsKey(tplAnnotation));
			assertEquals(listenerClass, services.get(tplAnnotation));
			
			assertTrue(services.containsKey(tplSomething));
			assertEquals(somethingClass, services.get(tplSomething));

			assertTrue(services.containsKey(serviceAlias));
			assertEquals(listenerClass, services.get(serviceAlias));
			
			
			
		} catch (Exception e) {
			
			fail();
			
		}		
	}
}
