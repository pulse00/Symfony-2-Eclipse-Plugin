package org.eclipse.symfony.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.QuotesContext;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.symfony.core.util.text.SymfonyTextSequenceUtilities;


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
public class ServiceContainerContext extends
	QuotesContext {
	
	
	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		if (super.isValid(sourceModule, offset, requestor)) {
			try {
				
				TextSequence statementText = getStatementText();
				if (SymfonyTextSequenceUtilities.isInServiceContainerFunction(statementText) > -1) {
					
					//TODO: check if the containing class is implementing a ContainerAware Interface
					return true;
				}
				
			} catch (Exception e) {				
				e.printStackTrace();				
			}
		}		
		return false;
	}
}