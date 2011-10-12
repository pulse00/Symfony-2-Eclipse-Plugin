package com.dubture.symfony.core.codeassist.contexts;

import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;

import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.util.text.SymfonyTextSequenceUtilities;


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
			
			try {
				
				IProjectNature nature;				
				nature = sourceModule.getScriptProject().getProject().getNature(SymfonyNature.NATURE_ID);
				
				// wrong nature
				if(!(nature instanceof SymfonyNature)) {
					return false;	
				}
				
				return SymfonyTextSequenceUtilities.isInServiceContainerFunction(getStatementText()) > -1;
				
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
		
		return false;
	}
}