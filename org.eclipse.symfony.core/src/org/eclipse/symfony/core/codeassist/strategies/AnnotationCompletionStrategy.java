package org.eclipse.symfony.core.codeassist.strategies;

import java.util.List;

import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.PHPDocTagStrategy;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.symfony.core.codeassist.contexts.AnnotationCompletionContext;
import org.eclipse.symfony.core.model.Annotation;
import org.eclipse.symfony.core.model.ModelManager;


/**
 * 
 * The {@link AnnotationCompletionStrategy} parses the UseStatements
 * of the current class and reports the aliases to 
 * the completion engine:
 * 
 *  <pre>
 * 
 *   use Doctrine\ORM\Mapping as ORM;
 *   
 *   ...
 *   
 *   /**
 *   @ | <-- add ORM\ to the code completion suggestions
 *  
 *  </pre>
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class AnnotationCompletionStrategy extends PHPDocTagStrategy
implements ICompletionStrategy {

	public AnnotationCompletionStrategy(ICompletionContext context) {
		super(context);

	}

	@Override
	public void apply(final ICompletionReporter reporter) throws BadLocationException {


		ICompletionContext ctx = getContext();

		if (!(ctx instanceof AnnotationCompletionContext)) {
			return;
		}		

		AnnotationCompletionContext context = (AnnotationCompletionContext) ctx;		
		final SourceRange range = getReplacementRange(context);
		List<Annotation> annotations = ModelManager.getInstance().getAnnotations(context.getSourceModule());
		
		for (Annotation annotation : annotations) {
			if (annotation.getType() != null) {				
				reporter.reportType(annotation.getType(), "()", range);
			} else {
				reporter.reportKeyword(annotation.getName(), "()", range);	
			}
		}

		super.apply(reporter);

	}
}