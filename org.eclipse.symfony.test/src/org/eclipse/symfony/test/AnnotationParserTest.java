package org.eclipse.symfony.test;

import junit.framework.TestCase;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.eclipse.symfony.core.parser.antlr.AnnotationCommonTree;
import org.eclipse.symfony.core.parser.antlr.AnnotationCommonTreeAdaptor;
import org.eclipse.symfony.core.parser.antlr.AnnotationNodeVisitor;
import org.eclipse.symfony.core.parser.antlr.SymfonyAnnotationLexer;
import org.eclipse.symfony.core.parser.antlr.SymfonyAnnotationParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AnnotationParserTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		
		
	}

	
	@Test
	public void testAnnotation() {
		
		AnnotationNodeVisitor root;
		
		root = getRootNode("* @Route()", false);
		
		assertNotNull(root);
		assertEquals("Route", root.getClassName());			
		assertTrue(root.getNamespace().length() == 0);
		
		root = getRootNode("* @ORM\\Foo()", false);
		
		assertEquals("Foo", root.getClassName());
		assertEquals("ORM\\", root.getNamespace());
		assertEquals("ORM\\Foo", root.getFullyQualifiedName());
		
		
	}
	
	
	@Test
	public void testSyntaxError() {

		AnnotationNodeVisitor root;		
		root = getRootNode("* @Ro+--ute(name='test\")", true);		
		assertNull(root);
		
		
	}
	
	private AnnotationNodeVisitor getRootNode(String line, boolean expectFail) {

		try {
			
			int start = line.indexOf('@');
			int end = line.length()-1;
			
			String annotation = line.substring(start, end+1);
			CharStream content = new ANTLRStringStream(annotation);
			SymfonyAnnotationLexer lexer = new SymfonyAnnotationLexer(content);
			SymfonyAnnotationParser parser = new SymfonyAnnotationParser(new CommonTokenStream(lexer));
			parser.setTreeAdaptor(new AnnotationCommonTreeAdaptor());
			SymfonyAnnotationParser.annotation_return root;
			
			root = parser.annotation();
			AnnotationCommonTree tree = (AnnotationCommonTree) root.getTree();
			AnnotationNodeVisitor visitor = new AnnotationNodeVisitor();
			tree.accept(visitor);
						
			if (expectFail)
				fail();
			
			return visitor;
			
			
		} catch (RecognitionException e) {
						
		}

		if (!expectFail)
			fail();
		
		return null;		
		
	}
}