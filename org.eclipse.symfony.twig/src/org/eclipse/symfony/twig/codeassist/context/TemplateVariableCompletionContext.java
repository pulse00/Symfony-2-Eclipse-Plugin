package org.eclipse.symfony.twig.codeassist.context;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.twig.core.codeassist.context.TemplateVariablesContext;


/**
 * 
 * Simple context for staying inside Twig statements
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class TemplateVariableCompletionContext extends
	TemplateVariablesContext {
	
	
	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
	
		if (super.isValid(sourceModule, offset, requestor)) {
			
			return true;
			
		}
		
		return false;
	}
}
