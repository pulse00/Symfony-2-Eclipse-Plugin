package org.eclipse.symfony.ui.editor.highlighting;

import org.eclipse.php.internal.core.ast.nodes.Comment;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.swt.graphics.RGB;

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
		public boolean visit(Comment comment) {

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

		return new AnnotationApply();
	}

	@Override
	public void initDefaultPreferences() {
		
		 getStyle().setUnderlineByDefault(true)
         .setDefaultTextColor(new RGB(102, 0, 0));
		
	}
}
