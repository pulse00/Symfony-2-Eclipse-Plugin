package org.eclipse.symfony.core.codeassist.strategies;

import java.util.List;

import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.AbstractCompletionStrategy;
import org.eclipse.php.internal.core.typeinference.FakeField;
import org.eclipse.symfony.core.codeassist.contexts.AnnotationParamCompletionContext;
import org.eclipse.symfony.core.model.Annotation;
import org.eclipse.symfony.core.model.AnnotationParameter;
import org.eclipse.symfony.core.model.ModelManager;


/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class AnnotationParamCompletionStrategy extends
	AbstractCompletionStrategy {

	public AnnotationParamCompletionStrategy(ICompletionContext context) {
		super(context);

	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		
		
		ICompletionContext context = getContext();
		if (!(context instanceof AnnotationParamCompletionContext)) {
			return;
		}
		AnnotationParamCompletionContext tagContext = (AnnotationParamCompletionContext) context;
		SourceRange range = getReplacementRange(tagContext);
		List<Annotation> annotations = ModelManager.getInstance().getAnnotations(tagContext.getSourceModule());
		
		for (Annotation annotation : annotations) {
			if (annotation.getName().equals(tagContext.getPreviousWord())) {
				for (AnnotationParameter param : annotation.getParameters()) {
					FakeField field = new FakeField((ModelElement) tagContext.getSourceModule(), param.getName(), 0, 0);					
					reporter.reportField(field, "", range, true);
				}
			}
		}
	}	
}