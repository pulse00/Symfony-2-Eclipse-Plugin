package com.dubture.symfony.test.codeassist;

import org.junit.Test;

import com.dubture.symfony.test.AbstractCodeAssistTest;

public class SymfonyCodeAssistTest extends AbstractCodeAssistTest {

	public SymfonyCodeAssistTest() {
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
}
