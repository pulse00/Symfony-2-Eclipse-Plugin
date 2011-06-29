package org.eclipse.symfony.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.QuotesContext;
import org.eclipse.php.internal.core.util.text.TextSequence;


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
			
			TextSequence statementText = getStatementText();
			
						
			try {
				
				// TODO: this is just a quick test, we need more accurate helper
				// methods to detect various template contexts
				// like: Are we staying in a helper method? etc.
				if (statementText.toString().contains("generate("))
					return true;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}
}
