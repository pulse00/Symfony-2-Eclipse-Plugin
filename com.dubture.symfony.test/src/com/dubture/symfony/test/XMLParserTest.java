/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.test;


import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dubture.symfony.core.parser.XMLConfigParser;
import com.dubture.symfony.index.dao.Route;
import com.dubture.symfony.index.dao.Service;


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
			
			HashMap<String, Service>services = parser.getServices();
			
			assertTrue(services.size() == 5);
			
			String tplAnnotation = "view.template_annotation";
			String tplSomething = "view.template_something";
			String serviceAlias= "view.some_alias";
			String cacheReader = "annotations.file_cache_reader";
			
			String listenerClass = "Sensio\\Bundle\\FrameworkExtraBundle\\View\\AnnotationTemplateListener";
			String somethingClass = "Sensio\\Bundle\\FrameworkExtraBundle\\View\\Something";
			String cacheClass = "Doctrine\\Common\\Annotations\\FileCacheReader";
			
			assertTrue(services.containsKey(tplAnnotation));
			
			assertTrue(services.get(tplAnnotation) instanceof Service);			
			Service service = services.get(tplAnnotation);			
			assertEquals(tplAnnotation, service.id);
			assertEquals(listenerClass, service.phpClass);
			
			List<String> tags = service.getTags();
			
			assertEquals(2, tags.size());
			assertEquals("kernel.listener", tags.get(0));
			assertEquals("kernel.listener", tags.get(1));
			
			assertTrue(services.get(tplSomething) instanceof Service);	
			service = services.get(tplSomething);			
			assertEquals(tplSomething, service.id);
			assertEquals(somethingClass, service.phpClass);
			
			tags = service.getTags();
			assertEquals(1, tags.size());
			assertEquals("template", tags.get(0));
			
					
			assertTrue(services.get(serviceAlias) instanceof Service);
			service = services.get(serviceAlias);			
			assertEquals(tplAnnotation, service.id);
			assertEquals(listenerClass, service.phpClass);
			
			
			assertTrue(services.containsKey("request"));
			
			
			service = services.get(cacheReader);
			assertTrue(service instanceof Service);
			assertEquals(service.id, cacheReader);
			assertEquals(service.phpClass, cacheClass);
			
			
		} catch (Exception e) {
			
			fail();
			
		}		
	}
	
	@Test
	public void testORMServices() {
		
		
		try {
			
			Service service = null;
			
			String dir = System.getProperty("user.dir") + "/Resources/config/orm.xml";
			FileInputStream input = new FileInputStream(new File(dir));
			XMLConfigParser parser = new XMLConfigParser(input);
			parser.parse();
			
			HashMap<String, Service>services = parser.getServices();			
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
			service = services.get(managerID);			
			assertTrue(service instanceof Service);			
			assertEquals(service.id, managerID);
			assertEquals(service.phpClass, managerClass);

			assertTrue(services.containsKey(formID));			
			service = services.get(formID);			
			assertTrue(service instanceof Service);			
			assertEquals(service.id, formID);
			assertEquals(service.phpClass, formClass);
			
			assertTrue(services.containsKey(readerID));			
			service = services.get(readerID);			
			assertTrue(service instanceof Service);			
			assertEquals(service.id, readerID);
			assertEquals(service.phpClass, readerClass);
			
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail();
			
		}		
		
		
	}
}
