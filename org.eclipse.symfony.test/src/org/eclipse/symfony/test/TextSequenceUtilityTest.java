package org.eclipse.symfony.test;

import junit.framework.TestCase;

import org.eclipse.symfony.core.util.text.SymfonyTextSequenceUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextSequenceUtilityTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		CharSequence sequence = "$this->get(";
		int contains = SymfonyTextSequenceUtilities.isInServiceContainerFunction(sequence);
		assertTrue(contains > -1);
		
		
		sequence = "$this->container->get('";
		contains = SymfonyTextSequenceUtilities.isInServiceContainerFunction(sequence);
		assertTrue(contains > -1);
		
		
		sequence = "$this->foo->get('";
		contains = SymfonyTextSequenceUtilities.isInServiceContainerFunction(sequence);
		assertTrue(contains == -1);
		

		sequence = "get('";
		contains = SymfonyTextSequenceUtilities.isInServiceContainerFunction(sequence);
		assertTrue(contains == -1);


		sequence = "$this->getLine('";
		contains = SymfonyTextSequenceUtilities.isInServiceContainerFunction(sequence);
		assertTrue(contains == -1);
		

	}

}
