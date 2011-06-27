package org.eclipse.symfony.ui.editor.highlighting;

import java.io.BufferedReader;
import java.io.StringReader;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Comment;
import org.eclipse.php.internal.core.ast.nodes.MethodDeclaration;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.symfony.core.parser.antlr.AnnotationCommonTree;
import org.eclipse.symfony.core.parser.antlr.AnnotationCommonTreeAdaptor;
import org.eclipse.symfony.core.parser.antlr.AnnotationLexer;
import org.eclipse.symfony.core.parser.antlr.AnnotationNodeVisitor;
import org.eclipse.symfony.core.parser.antlr.AnnotationParser;

/**
 * 
 * 
 * 
 * 
 *
 */
@SuppressWarnings("restriction")
public class AnnotationHighlighting extends AbstractSemanticHighlighting {

	
	protected class AnnotationApply extends AbstractSemanticApply {
		
		
		@Override
		public boolean visit(ASTNode node) {
		
			
			System.err.println(node.getClass().toString());
			return true;
		
		}
		
		@Override
		public boolean visit(Comment comment) {

			try {				
				
				
				String source = getSourceModule().getSource();				
				source = source.substring(comment.getStart(), comment.getStart() + comment.getLength());
				
				
				BufferedReader reader = new BufferedReader(new StringReader(source));
				String annotation = "";
				
				while((annotation = reader.readLine()) != null) {
										
					CharStream content = new ANTLRStringStream(annotation);

					AnnotationLexer lexer = new AnnotationLexer(content);
					AnnotationParser parser = new AnnotationParser(new CommonTokenStream(lexer));
					parser.setTreeAdaptor(new AnnotationCommonTreeAdaptor());
					AnnotationParser.annotation_return root = parser.annotation();
					AnnotationCommonTree tree = (AnnotationCommonTree) root.getTree();
					AnnotationNodeVisitor visitor = new AnnotationNodeVisitor();
					tree.accept(visitor);

					String className = visitor.getClassName();
					System.err.println(className);
					
				}
				
				
				// do not report errors on built in tag names
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
			return true;
		}
		
		@Override
		public boolean visit(MethodDeclaration classMethodDeclaration) {
			

			return true;
		}
		
		
	}
	
	public AnnotationHighlighting() {
		super();
	}
	
	@Override
	public String getDisplayName() {
		
		return "Symfony Annotations";

	}

	@Override
	public AbstractSemanticApply getSemanticApply() {

		System.err.println("semantic apply");
		return new AnnotationApply();
	}

	@Override
	public void initDefaultPreferences() {
		
		 getStyle().setUnderlineByDefault(true)
         .setDefaultTextColor(new RGB(102, 0, 0));
		
	}
}
