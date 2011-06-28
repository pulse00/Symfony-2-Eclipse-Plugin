package org.eclipse.symfony.twig.codeassist.context;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.twig.core.codeassist.context.QuotesContext;

public class RouteCompletionContext extends QuotesContext {
	
	
	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		if (super.isValid(sourceModule, offset, requestor)) {
	
			//TODO: check if we're staying inside a method that takes
			// routes as an argument
			return true;
			
		}
		
		
		return false;
	}
}
