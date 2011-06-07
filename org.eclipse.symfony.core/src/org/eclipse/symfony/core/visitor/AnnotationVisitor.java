package org.eclipse.symfony.core.visitor;

import java.io.BufferedReader;
import java.io.StringReader;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.symfony.core.codeassist.strategies.AnnotationCompletionStrategy;
import org.eclipse.symfony.core.parser.antlr.SymfonyAnnotationLexer;
import org.eclipse.symfony.core.parser.antlr.SymfonyAnnotationParser;

/**
 * 
 * {@link AnnotationVisitor} parses annotations from
 * PHPDocBlocks.
 * 
 * This will mainly be used for error reporting purposes
 * and maybe syntax highlighting.
 * 
 * For code-assistance in annotations, see {@link AnnotationCompletionStrategy}
 * 
 * @see http://symfony.com/blog/symfony2-annotations-gets-better
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class AnnotationVisitor extends PHPASTVisitor {

	
	private ClassDeclaration currentClass = null;
	private PHPMethodDeclaration currentMethod = null;
	private boolean isAction = false;
	private char[] content;
	private IBuildContext context;
	
	
	public AnnotationVisitor(IBuildContext context) {
		
		this(context.getContents());
		this.context = context;
		
	}
	
	public AnnotationVisitor(char[] content) {
		
		this.content = content;
	}
	
	
	@Override
	public boolean visit(UseStatement s) throws Exception {
		
		//TODO: store the FQCN and the alias from the UseStatement
		return true;

	}
	
	@Override
	public boolean endvisit(UseStatement s) throws Exception {
	
		
		return true;
	
	}
	
	
	
	@Override
	public boolean visit(ClassDeclaration s) throws Exception {

		currentClass = s;
		
		
		for (String name : currentClass.getSuperClassNames()) {			
			if (name.equals("Controller")) {
			}
			
		}
		
		return true;
		
	}
	
	@Override
	public boolean endvisit(ClassDeclaration s) throws Exception {

		currentClass = null;
		return true;
	}
	
	
	
	@Override
	public boolean visit(PHPMethodDeclaration method) throws Exception {

		currentMethod = method;
		isAction = currentMethod.getName().endsWith("Action");
		
		if (currentClass == null || isAction == false)
			return true;
		
		
		PHPDocBlock comment = method.getPHPDoc();
		
		if (comment != null) {
			
			int start = comment.sourceStart();
			int end = comment.sourceEnd();
			
			String docBlock = String.valueOf(content).substring(start,end);
			parseAnnotations(docBlock, start);			
			
		}
		
		return true;
	}
	
	
	@Override
	public boolean endvisit(PHPMethodDeclaration s) throws Exception {
		
		currentMethod = null;
		return true;
	}
	
	/**
	 * 
	 * Extract Annotations from Comment string
	 * 
	 * @param comment
	 * @param sourceStart
	 */
	public void parseAnnotations(String comment, int sourceStart) {
		
		BufferedReader buffer = new BufferedReader(new StringReader(comment));
		
		try {

			String line;
			
			while((line = buffer.readLine()) != null) {
				
				sourceStart += line.toCharArray().length;
				
				int start = line.indexOf('@');
				int end = line.lastIndexOf(')');
				
				if ((start == -1 || end == -1)) continue;
				
				String annotation = line.substring(start, end+1);
				CharStream content = new ANTLRStringStream(annotation);             				
				
                SymfonyAnnotationLexer lexer = new SymfonyAnnotationLexer(content);
                CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                SymfonyAnnotationParser parser = new SymfonyAnnotationParser(tokenStream);
                
                try { 	
                     System.err.println("resolved annotation ..." + parser.name());
                } catch (RecognitionException e) {
                	                	
                	e.printStackTrace();
                	
                }				
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}		
	}
}