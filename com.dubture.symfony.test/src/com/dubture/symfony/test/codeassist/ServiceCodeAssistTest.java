package com.dubture.symfony.test.codeassist;

import org.junit.Test;

import com.dubture.symfony.test.AbstractCodeAssistTest;

public class ServiceCodeAssistTest extends AbstractCodeAssistTest {

	public ServiceCodeAssistTest() {
		super("codeassist");
	}
	
	@Test
	public void testServiceIdCompletion() throws Exception {
		runPdttTest("testServiceIdCompletion.pdtt");
	}
	
	@Test
	public void testServiceMethodCompletion() throws Exception {
		runPdttTest("testServiceMethodCompletion.pdtt");
	}
	
	@Test
	public void testParameterizedServiceMethodCompletion() throws Exception {
		runPdttTest("testParameterizedServiceMethodCompletion.pdtt");
	}
}
