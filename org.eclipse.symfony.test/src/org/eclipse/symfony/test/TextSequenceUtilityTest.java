package org.eclipse.symfony.test;

import junit.framework.TestCase;

import org.eclipse.symfony.core.util.text.SymfonyTextSequenceUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * Tests for the {@link SymfonyTextSequenceUtilities}.
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class TextSequenceUtilityTest extends TestCase {

	/**
	 * The Sequence under test
	 */
	private CharSequence sequence;
	

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testIsInServiceContainerFunction() {
		
		sequence = "$this->get(";
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

		
		sequence = "$this->get('doctrine')->getConnection()->";
		contains = SymfonyTextSequenceUtilities.isInServiceContainerFunction(sequence);
		assertTrue(contains == -1);
		

	}

}
