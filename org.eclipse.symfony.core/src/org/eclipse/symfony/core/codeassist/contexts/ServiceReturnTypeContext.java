package org.eclipse.symfony.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;
import org.eclipse.symfony.core.util.text.SymfonyTextSequenceUtilities;


/**
 * 
 * A context which is valid when completing services directly from
 * the DI container:
 * 
 * 
 * <pre>
 * 
 *   $em = $this->get('doctrine')-> |
 * 
 * </pre>
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ServiceReturnTypeContext extends ClassMemberContext {


	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		if (super.isValid(sourceModule, offset, requestor)) 
		{											
			return SymfonyTextSequenceUtilities.isInServiceContainerFunction(getStatementText()) > -1;

		}
		
		return false;
	}
}