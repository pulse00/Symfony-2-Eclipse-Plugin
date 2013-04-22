package com.dubture.symfony.test.codeassist;

import org.junit.Test;

import com.dubture.symfony.test.AbstractCodeAssistTest;

public class SymfonyCodeAssistTest2 extends AbstractCodeAssistTest {

	public SymfonyCodeAssistTest2() {
		super("codeassist2");
	}
	
	@Test
	public void testParameterizedServiceMethodCompletion() throws Exception {
		runPdttTest("testParameterizedServiceMethodCompletion.pdtt");
	}
}
