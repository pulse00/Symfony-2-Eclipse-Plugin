package com.dubture.symfony.twig.codeassist.context;


import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.util.text.TextSequence;

import com.dubture.symfony.twig.log.Logger;
import com.dubture.twig.core.codeassist.context.QuotesContext;
import com.dubture.twig.core.util.text.TwigTextSequenceUtilities;

/**
 * 
 * Checks for a valid context to complete route names.
 * 
 * Right now the only check is to validate we're staying inside
 * a method call and not a single string literal.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class RouteCompletionContext extends QuotesContext {
	
	
	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		Logger.debugMSG("checking for valid twig route completion context");
		if (super.isValid(sourceModule, offset, requestor)) {
	
			TextSequence statement = getStatementText();
			
			 if (!TwigTextSequenceUtilities.isInFunction(statement)) {
				 Logger.debugMSG("no valid twig route context");
				 return false;
			 }			
			 
			Logger.debugMSG("twig route context is valid");
			return true;
			
		}
		
		Logger.debugMSG("super class detected invalid twig route context");
		return false;
		
	}
}
