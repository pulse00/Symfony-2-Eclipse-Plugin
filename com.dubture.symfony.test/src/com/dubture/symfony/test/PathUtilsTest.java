/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.test;


import junit.framework.TestCase;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dubture.symfony.core.util.PathUtils;

public class PathUtilsTest extends TestCase {

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

		IPath fieldPath = new Path("/Symfony2/src/Acme/DemoBundle/Controller/DemoController.php");		
		String controller = PathUtils.getControllerFromFieldPath(fieldPath);
		
		assertEquals("DemoController", controller);
		
		
		IPath templatePath = new Path("/Symfony2/src/Acme/DemoBundle/Resources/views/layout.html.twig");		
		String viewPath = PathUtils.getViewFromTemplatePath(templatePath);
		assertEquals("layout", viewPath);
		
	}
}
