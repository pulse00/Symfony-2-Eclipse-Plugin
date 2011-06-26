package org.eclipse.symfony.test;


import junit.framework.TestCase;

import org.eclipse.dltk.core.index2.IIndexerParticipant;
import org.eclipse.dltk.core.index2.search.ModelAccess;
import org.eclipse.symfony.core.SymfonyLanguageToolkit;
import org.eclipse.symfony.core.index.SymfonyIndexerParticipant;
import org.eclipse.symfony.core.index.SymfonyIndexingParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("restriction")
public class ControllerTest extends TestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}


	@Test
	public void test() {
				
		try {
			
			IIndexerParticipant indexer = ModelAccess.getIndexerParticipant(SymfonyLanguageToolkit.getDefault());			
			assertTrue(indexer instanceof SymfonyIndexerParticipant);
			
			SymfonyIndexingParser parser = (SymfonyIndexingParser) indexer.getIndexingParser();			
			assertTrue(parser instanceof SymfonyIndexingParser);
			
			
			System.err.println("rerun");
			
		} catch (Exception e) {

			fail();
		}
	}

}
