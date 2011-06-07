package org.eclipse.symfony.core.codeassist;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.internal.core.codeassist.CompletionCompanion;
import org.eclipse.symfony.core.codeassist.contexts.AnnotationCompletionContext;

/**
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class CompletionContextResolver implements ICompletionContextResolver {

	
	@Override
	public ICompletionContext[] resolve(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor, CompletionCompanion companion) {

		return new ICompletionContext[] { new AnnotationCompletionContext() };
	}

	@Override
	public ICompletionContext[] createContexts() {
		// TODO Auto-generated method stub
		return new ICompletionContext[] { new AnnotationCompletionContext() };
	}

}
