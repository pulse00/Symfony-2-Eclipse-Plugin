/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ 
	AnnotationParserTest.class, 
	XMLParserTest.class, 
	TextSequenceUtilityTest.class, 
	YamlTest.class, 
	PathUtilsTest.class,
	IndexTest.class,
	RoutingParserTest.class,
	ControllerTest.class,
	PathUtilsTest.class,
	ModelUtilsTest.class,	
	})
public class AllTests {

}
