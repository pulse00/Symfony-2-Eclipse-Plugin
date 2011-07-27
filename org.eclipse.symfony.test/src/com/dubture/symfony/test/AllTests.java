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
@SuiteClasses({ AnnotationParserTest.class, XMLParserTest.class, 
	TextSequenceUtilityTest.class, YamlTest.class, PathUtilsTest.class,
	IndexTest.class})
public class AllTests {

}
