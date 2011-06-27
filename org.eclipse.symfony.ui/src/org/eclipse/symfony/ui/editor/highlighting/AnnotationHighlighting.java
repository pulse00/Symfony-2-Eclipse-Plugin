package org.eclipse.symfony.ui.editor.highlighting;

import java.io.BufferedReader;
import java.io.StringReader;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.eclipse.php.internal.core.ast.nodes.Comment;
import org.eclipse.php.internal.core.codeassist.strategies.PHPDocTagStrategy;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.symfony.core.parser.antlr.AnnotationCommonTree;
import org.eclipse.symfony.core.parser.antlr.AnnotationCommonTreeAdaptor;
import org.eclipse.symfony.core.parser.antlr.AnnotationLexer;
import org.eclipse.symfony.core.parser.antlr.AnnotationParser;
import org.eclipse.symfony.core.parser.antlr.IAnnotationNodeVisitor;

/**
 * 
 * Highlighting for Annotations.
 *
 */
@SuppressWarnings("restriction")
public class AnnotationHighlighting extends AbstractSemanticHighlighting {

	protected class AnnotationApply extends AbstractSemanticApply {
				
		private boolean isAnnotation;
		
		@Override
		public boolean visit(Comment comment) {

			try {				
				
				String source = getSourceModule().getSource();				
				source = source.substring(comment.getStart(), comment.getStart() + comment.getLength());				
				
				BufferedReader reader = new BufferedReader(new StringReader(source));
				String line = "";

				int currentOffset = comment.getStart();
				
				while((line = reader.readLine()) != null) {

					currentOffset += line.length() +1;
					
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
					
					if (isTag) {
						continue;
					}
					
					isAnnotation = false;
					String annotation = line.substring(start, end+1);					
															
					CharStream content = new ANTLRStringStream(annotation);

					AnnotationLexer lexer = new AnnotationLexer(content);
					AnnotationParser parser = new AnnotationParser(new CommonTokenStream(lexer));
					parser.setTreeAdaptor(new AnnotationCommonTreeAdaptor());
					AnnotationParser.annotation_return root = parser.annotation();
					AnnotationCommonTree tree = (AnnotationCommonTree) root.getTree();

					tree.accept(new IAnnotationNodeVisitor() {
						@Override
						public void endVisit(AnnotationCommonTree node) {
							if (node.getType() == AnnotationParser.ANNOTATION) {
								isAnnotation = true;								
							}
						}						
						@Override
						public void beginVisit(AnnotationCommonTree node) {

						}
					});					

					if (isAnnotation) {
											
						int annotationOffset = line.indexOf("@");
						int highlightStart = currentOffset - line.length() + annotationOffset - 1;
						int length = line.length() - annotationOffset + 1;
						highlight(highlightStart, length);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;
		}
	}
	
	public AnnotationHighlighting() {
		super();
	}
	
	@Override
	public String getDisplayName() {
		
		return "Annotations";

	}

	@Override
	public AbstractSemanticApply getSemanticApply() {

		return new AnnotationApply();
	}

	@Override
	public void initDefaultPreferences() {
		
		 getStyle().setUnderlineByDefault(true)
         .setDefaultTextColor(new RGB(64, 64, 64));
		
	}
}
