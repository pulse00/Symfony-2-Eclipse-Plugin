package com.dubture.symfony.test.codeassist;

import org.junit.Test;

import com.dubture.symfony.test.AbstractCodeAssistTest;

/**
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public class AnnotationCodeAssistTest extends AbstractCodeAssistTest {

	public AnnotationCodeAssistTest() {
		super("annotation");
	}
	
	@Test
	public void testRouteAnnotationCompletion() throws Exception {
		runPdttTest("testRouteCompletion.pdtt");
	}
	
	@Test
	public void testDICompletion() throws Exception {
		runPdttTest("testDICompletion.pdtt");
	}
}
