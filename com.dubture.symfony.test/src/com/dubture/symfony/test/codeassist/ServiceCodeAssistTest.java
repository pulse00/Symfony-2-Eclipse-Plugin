package com.dubture.symfony.test.codeassist;

import org.junit.Test;

import com.dubture.symfony.test.AbstractCodeAssistTest;

public class ServiceCodeAssistTest extends AbstractCodeAssistTest {

	public ServiceCodeAssistTest() {
		super("services");
	}
	
	@Test
	public void testServiceIdCompletion() throws Exception {
		runPdttTest("testServiceIdCompletion.pdtt");
	}
	
	@Test
	public void testServiceIdCompletion2() throws Exception {
		runPdttTest("testServiceIdCompletion2.pdtt");
	}
	
	@Test
	public void testServiceMethodCompletion() throws Exception {
		runPdttTest("testServiceMethodCompletion.pdtt");
	}
	
	@Test
	public void testParameterizedServiceMethodCompletion() throws Exception {
		runPdttTest("testParameterizedServiceMethodCompletion.pdtt");
	}
	
	@Test
	public void testChainedServiceCompletion() throws Exception {
		runPdttTest("testChainedServiceCompletion.pdtt");
	}	
}
