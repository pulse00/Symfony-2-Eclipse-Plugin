package org.eclipse.symfony.core.codeassist;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.internal.core.codeassist.contexts.CompletionContextResolver;
import org.eclipse.symfony.core.codeassist.contexts.AnnotationCompletionContext;

/**
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class AnnotationCompletionContextResolver extends CompletionContextResolver 
	implements ICompletionContextResolver {


	@Override
	public ICompletionContext[] createContexts() {
		
		return new ICompletionContext[] { 
				new AnnotationCompletionContext()
		};
		
	}

}
