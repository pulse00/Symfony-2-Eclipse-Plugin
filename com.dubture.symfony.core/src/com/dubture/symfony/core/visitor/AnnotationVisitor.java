/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.visitor;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Stack;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.php.internal.core.codeassist.strategies.PHPDocTagStrategy;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPFieldDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;

import com.dubture.symfony.annotation.parser.antlr.AnnotationCommonTree;
import com.dubture.symfony.annotation.parser.antlr.AnnotationCommonTreeAdaptor;
import com.dubture.symfony.annotation.parser.antlr.AnnotationLexer;
import com.dubture.symfony.annotation.parser.antlr.AnnotationNodeVisitor;
import com.dubture.symfony.annotation.parser.antlr.AnnotationParser;
import com.dubture.symfony.core.codeassist.strategies.AnnotationCompletionStrategy;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.Annotation;
import com.dubture.symfony.core.parser.antlr.error.AnnotationErrorReporter;
import com.dubture.symfony.core.preferences.SymfonyCorePreferences;

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
	private NamespaceDeclaration currentNamespace = null;

	private Annotation currentAnnotation = null;

	private boolean isAction = false;
	private char[] content;
	private IBuildContext context;
	


	private Stack<UseStatement> useStatements = new Stack<UseStatement>();


	public AnnotationVisitor(IBuildContext context) {

		this(context.getContents());
		this.context = context;

	}

	public AnnotationVisitor(char[] content) {

		this.content = content;
	}


	@Override
	public boolean visit(UseStatement s) throws Exception {

		useStatements.push(s);

		return true;

	}

	@Override
	public boolean visit(NamespaceDeclaration s) throws Exception {

		currentNamespace = s;
		
	
		
		return true;
	}

	@Override
	public boolean endvisit(NamespaceDeclaration s) throws Exception {

		currentNamespace = null;
		return true;
	}


	/**
	 * This could be used to parse Annotationclasses themselves
	 * to build up an internal model about the annotation.
	 * 
	 * However, there's no clean way at the moment as pretty much
	 * any class can be used as an annotation and there's no proper
	 * way to detect the semantics of the annotation from the php code.
	 * 
	 * @see http://www.doctrine-project.org/jira/browse/DDC-1198
	 */
	@Override
	public boolean visit(ClassDeclaration s) throws Exception {

		currentClass = s;
		return true;

	}

	@Override
	public boolean endvisit(ClassDeclaration s) throws Exception {

		currentClass = null;
		return true;
	}
	
	


	@Override
	public boolean visit(PHPFieldDeclaration s) throws Exception {


		if (currentAnnotation == null)
			return true;

//		currentAnnotation.addParameter(s);

		return true;

	}



	/**
	 * Parses annotations from method declarations.
	 */
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
				int end = line.length()-1;

				if ((start == -1 || end == -1)) continue;


				boolean isTag = false;				
				String aTag = line.substring(start +1);

				// check for built-int phpdoc tags and don't parse them
				// as annotations
				for(String tag : PHPDocTagStrategy.PHPDOC_TAGS) {					
					if (tag.equals(aTag)) {
						isTag = true;
						break;
					}					
				}

				if (isTag) continue;

				try { 	

					String annotation = line.substring(start, end+1);
					int sStart = sourceStart-line.toCharArray().length+line.indexOf('@');
					CharStream content = new ANTLRStringStream(annotation);

										
					AnnotationErrorReporter reporter = new AnnotationErrorReporter(context, sStart);
					AnnotationLexer lexer = new AnnotationLexer(content, reporter);
					AnnotationParser parser = new AnnotationParser(new CommonTokenStream(lexer), reporter);
					parser.setTreeAdaptor(new AnnotationCommonTreeAdaptor());
					AnnotationParser.annotation_return root = parser.annotation();
					AnnotationCommonTree tree = (AnnotationCommonTree) root.getTree();
					AnnotationNodeVisitor visitor = new AnnotationNodeVisitor(context);
					tree.accept(visitor);

					String className = visitor.getClassName();
					// do not report errors on built in tag names
					for (String tag : PHPDocTagStrategy.PHPDOC_TAGS) {
						if (tag.equals(className))
							return;						
					}
					
					reportUnresolvableAnnotation(visitor, sStart);

				} catch (Exception e) {

					Logger.logException(e);
				}				
			}

		} catch (Exception e) {
			Logger.logException(e);		}		
	}

	
	/**
	 * Checks if an annotation can be resolved via a {@link UseStatement}
	 * and adds an {@link IProblem} to the {@link IProblemReporter}
	 * if the annotation cannot be resolved.
	 * 
	 * 
	 * @param visitor
	 * @param sourceStart
	 */
	@SuppressWarnings("deprecation")
	private void reportUnresolvableAnnotation(AnnotationNodeVisitor visitor, int sourceStart) {

		String annotationClass = visitor.getClassName();
		String annotationNamespace = visitor.getNamespace();
		String fqcn = visitor.getFullyQualifiedName();

		boolean found = false;

		for (UseStatement statement : useStatements) {
			for (UsePart part : statement.getParts()) {
				SimpleReference alias = part.getAlias();				
				FullyQualifiedReference namespace = part.getNamespace();

				//statement has no alias and classname no namespace, simply
				// compare them to each other
				if (alias == null && annotationNamespace.length() == 0) {

					if (namespace.getName().equals(annotationClass)) {
						found = true;
					}

					/*
					 * something like
					 * 
					 * use use Doctrine\Common\Mapping as SomeMapping;
					 * 
					 * @SomeMapping
					 * 
					 */
				} else if (alias != null && annotationNamespace.length() == 0) {

					
					if (alias.getName().equals(annotationClass))
						found = true;
					/*
					 * something like
					 * 
					 * use use Doctrine\Common\Mapping as ORM;
					 * 
					 * @ORM\Table
					 * 
					 */

				} else if (alias != null && annotationNamespace.length() > 0) {

					if (alias.equals(visitor.getFirstNamespacePart())) {
						
						//TODO: search for matching classes using PDT SearchEngine
						
					}
				} else if (annotationNamespace != null && annotationClass != null) {
					
					String ns = annotationNamespace + annotationClass;
					
					if (fqcn.startsWith(annotationNamespace))
						found = true;
				}

				if (found == true)
					break;

			}

			if (found == true)
				break;		
		}

		if (found == false) {


			int start = sourceStart;
			int end = sourceStart + visitor.getFullyQualifiedName().length() + 1;
			String filename = context.getFile().getName();
			String message = "Unable to resolve annotation '" + fqcn + "'";
			int lineNo = context.getLineTracker().getLineInformationOfOffset(sourceStart).getOffset();

			/**
			 * this should be the way to create the problem without the deprecation
			 * warning, but then our QuickFixProcessor doesn't get called.
			 */
			//IProblem newProblem = new DefaultProblem(filename, message, DefaultProblemIdentifier.NULL, new String[0], ProblemSeverities.Error, start+1, end+1, lineNo, start);
			
			ProblemSeverity severity = SymfonyCorePreferences.getAnnotationSeverity();
			
			IProblem problem = new DefaultProblem(filename, message, IProblem.ImportRelated,
					new String[0], severity, start+1, end+1, lineNo);
			
			context.getProblemReporter().reportProblem(problem);


		}
	}
	
	

}
