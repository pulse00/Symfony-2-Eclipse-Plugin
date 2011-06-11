package org.eclipse.symfony.test;

import junit.framework.TestCase;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.eclipse.symfony.core.parser.antlr.AnnotationCommonTree;
import org.eclipse.symfony.core.parser.antlr.AnnotationCommonTreeAdaptor;
import org.eclipse.symfony.core.parser.antlr.AnnotationLexer;
import org.eclipse.symfony.core.parser.antlr.AnnotationNodeVisitor;
import org.eclipse.symfony.core.parser.antlr.AnnotationParser;
import org.eclipse.symfony.test.reporter.DebugErrorReporter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AnnotationParserTest extends TestCase {

	
	private DebugErrorReporter reporter = new DebugErrorReporter();
	
	@Before
	public void setUp() throws Exception {
		
		reporter.reset();
	}

	@After
	public void tearDown() throws Exception {
	
		reporter.reset();
		
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
		assertNotNull(root);
		assertTrue(reporter.hasErrors());
		
		reporter.reset();
		
		root = getRootNode("* @Route(name=)", true);
		assertNotNull(root);
		assertTrue(reporter.hasErrors());		

		
		reporter.reset();
		
		root = getRootNode("* @Template(name='foo' bar)", true);
		assertNotNull(root);
		assertTrue(reporter.hasErrors());		

		
		reporter.reset();
		
		root = getRootNode("* @Template(aho=')", true);
		assertNotNull(root);
		assertTrue(reporter.hasErrors());		
		
		
	}
	
	private AnnotationNodeVisitor getRootNode(String line, boolean expectFail) {

		
		try {
			
			int start = line.indexOf('@');
			int end = line.length()-1;
			
			String annotation = line.substring(start, end+1);
			CharStream content = new ANTLRStringStream(annotation);
			
			AnnotationLexer lexer = new AnnotationLexer(content, reporter);
			
			AnnotationParser parser = new AnnotationParser(new CommonTokenStream(lexer));
			parser.setErrorReporter(reporter);
			
			parser.setTreeAdaptor(new AnnotationCommonTreeAdaptor());
			AnnotationParser.annotation_return root;
			
			root = parser.annotation();
			AnnotationCommonTree tree = (AnnotationCommonTree) root.getTree();
			AnnotationNodeVisitor visitor = new AnnotationNodeVisitor();
			tree.accept(visitor);
				
			if (expectFail && reporter.hasErrors() == false) {
				
				System.err.println(line + " " + reporter.hasErrors());
				fail();
			}
				
			
			return visitor;
			
			
		} catch (RecognitionException e) {
						
		}

		if (!expectFail && reporter.hasErrors())
			fail();
		
		return null;		
		
	}
}