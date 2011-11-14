/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.QuotesContext;
import org.eclipse.php.internal.core.util.text.TextSequence;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.util.text.SymfonyTextSequenceUtilities;

/**
 * 
 * Checks if we're in a translation context, ie:
 * 
 * <pre>
 * 
 * 	$this->get('translator')->translate('| <--- completes translational units
 * 
 * </pre>
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class TransUnitCompletionContext extends QuotesContext {
	
	
	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		
		if (super.isValid(sourceModule, offset, requestor)) {
			
			try {
				
				 TextSequence statement = getStatementText();				 
				  
				 if (SymfonyTextSequenceUtilities.isInTranslationFunctionParameter(statement) > -1) {
					 return true;
				 }
				 
				 return false;
				 
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
		
		return false;
	}	

}
