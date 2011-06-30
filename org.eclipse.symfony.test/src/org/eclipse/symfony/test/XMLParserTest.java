package org.eclipse.symfony.test;


import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Stack;

import junit.framework.TestCase;

import org.eclipse.symfony.core.parser.XMLConfigParser;
import org.eclipse.symfony.index.dao.Route;
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

	
	@Test
	public void testRoutes() {
		
		try {
			
			String dir = System.getProperty("user.dir") + "/Resources/config/routing.xml";
			FileInputStream input = new FileInputStream(new File(dir));
			XMLConfigParser parser = new XMLConfigParser(input);
			parser.parse();
			
			assertTrue(parser.hasRoutes());
			
			Stack<Route> routes = parser.getRoutes();			
			assertEquals(2, routes.size());
			
			Route route = routes.pop();
			
			assertEquals(route.name, "blog_index");
			assertEquals(route.pattern, "/blog");
			assertEquals(route.bundle, "AcmeBlogBundle");
			assertEquals(route.controller, "Blog");
			assertEquals(route.action, "index");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}


	/**
	 * Test parsing of service definitions.
	 */
	@Test	
	public void testServices() {

		try {
		
			String dir = System.getProperty("user.dir") + "/Resources/config/services1.xml";
			FileInputStream input = new FileInputStream(new File(dir));
			XMLConfigParser parser = new XMLConfigParser(input);
			parser.parse();
			
			HashMap<String, String>services = parser.getServices();
			
			assertTrue(services.size() == 4);
			
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
			
			
			assertTrue(services.containsKey("request"));
			assertEquals("synthetic", services.get("request"));
			
			
			
		} catch (Exception e) {
			
			fail();
			
		}		
	}
	
	@Test
	public void testORMServices() {
		
		
		try {
			
			String dir = System.getProperty("user.dir") + "/Resources/config/orm.xml";
			FileInputStream input = new FileInputStream(new File(dir));
			XMLConfigParser parser = new XMLConfigParser(input);
			parser.parse();
			
			HashMap<String, String>services = parser.getServices();			
			HashMap<String, String> parameters = parser.getParameters();
			
			
			assertTrue(services.size() == 7);
			assertTrue(parameters.size() == 19);
			
			String managerID = "doctrine.orm.entity_manager.abstract";
			String formID = "form.type.entity";
			String readerID= "doctrine.orm.metadata.annotation_reader";
			
			String managerClass = "Doctrine\\ORM\\EntityManager";
			String formClass = "Symfony\\Bridge\\Doctrine\\Form\\Type\\EntityType";
			String readerClass = "Symfony\\Bundle\\DoctrineBundle\\Annotations\\IndexedReader";
			
			assertTrue(services.containsKey(managerID));
			assertEquals(managerClass, services.get(managerID));
			
			assertTrue(services.containsKey(formID));
			assertEquals(formClass, services.get(formID));

			assertTrue(services.containsKey(readerID));
			assertEquals(readerClass, services.get(readerID));
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail();
			
		}		
		
		
	}
}
