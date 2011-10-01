package com.dubture.symfony.test;

import org.junit.Test;

import com.dubture.symfony.core.util.ModelUtils;

import junit.framework.TestCase;

public class ModelUtilsTest extends TestCase {

	@Test
	public void testAll() {
		
		String bundle = ModelUtils.extractBundleName("Acme\\DemoBundle\\Controller");
		assertEquals("AcmeDemoBundle", bundle);
		
		bundle = ModelUtils.extractBundleName("Acme\\Subunit\\DemoBundle\\Resources");
		assertEquals("AcmeSubunitDemoBundle", bundle);
		
		bundle = ModelUtils.extractBundleName("Acme\\Subunit\\FooBundle");
		assertEquals("AcmeSubunitFooBundle", bundle);
		
		
	}
}
