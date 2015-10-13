/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dubture.symfony.core.util.text.SymfonyTextSequenceUtilities;

import junit.framework.TestCase;


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


    @Test
    public void testMethodExtract() {


        sequence = "$this->get('translator')->trans(";
        String method = SymfonyTextSequenceUtilities.getMethodName(sequence);

        assertEquals("trans", method);


    }

}
