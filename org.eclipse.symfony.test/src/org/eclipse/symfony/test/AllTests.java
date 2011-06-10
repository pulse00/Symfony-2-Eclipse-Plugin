package org.eclipse.symfony.test;

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
@SuiteClasses({ AnnotationParserTest.class, XMLParserTest.class })
public class AllTests {

}
