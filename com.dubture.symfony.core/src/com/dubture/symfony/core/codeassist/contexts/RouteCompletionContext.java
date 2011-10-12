package com.dubture.symfony.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.QuotesContext;
import org.eclipse.php.internal.core.util.text.TextSequence;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.util.text.SymfonyTextSequenceUtilities;


/**
 * 
 * Detects if we're staying in a route context:
 * 
 *  
 *  <pre>
 *  
 *  $view['router']->generate('| <-- valid route context
 *  
 *  </pre>
 * 
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

		
		if (super.isValid(sourceModule, offset, requestor)) {
			
			try {
				
				 TextSequence statement = getStatementText();
				 IScriptProject project = getSourceModule().getScriptProject();
				 
				 if (SymfonyTextSequenceUtilities.isInRouteFunctionParameter(statement, project) == false) {
					 return false;
				 }
				 
				 return true;
				 
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
		
		return false;
	}
}
