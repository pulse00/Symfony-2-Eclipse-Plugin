package org.eclipse.symfony.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.QuotesContext;


/**
 * 
 * This context represents the state when staying in a function parameter to retrieve
 * a service from the DependencyInjection container. <br/>
 * Example:
 * 
 * <pre>
 *  $em = $this->get('|)
 * </pre>
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings("restriction")
public class ServiceContainerCompletionContext extends
	QuotesContext {
	
	
	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		
		if (super.isValid(sourceModule, offset, requestor)) {
		
			return true;
			
		}
		
		
		return false;
	}

}
