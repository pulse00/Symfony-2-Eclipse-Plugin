/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.twig.codeassist.context;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.util.text.TextSequence;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.ViewPath;
import com.dubture.symfony.core.util.text.SymfonyTextSequenceUtilities;
import com.dubture.symfony.twig.codeassist.strategies.ViewPathCompletionStrategy;
import com.dubture.twig.core.codeassist.context.QuotesContext;
import com.dubture.twig.core.util.text.TwigTextSequenceUtilities;

/**
 * 
 * Checks if a {@link ViewPathCompletionStrategy} can be applied.
 * 
 * This is true if the curser is inside a single string literal,
 * and not in a function parameter.
 *
 * 
 * I haven't found a way yet for a better validation mechanism here.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ViewPathArgumentContext extends QuotesContext {
	
	
	private ViewPath viewPath;
	
	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		if(super.isValid(sourceModule, offset, requestor)) {
			
			 try {				 
				 				 
				 TextSequence statement = getStatementText();


				 if (!requestor.getClass().getName().contains("Twig")) {
				     return false;
				 }
				 if (TwigTextSequenceUtilities.isInFunction(statement)) {
					 return false;
				 }
				 int startOffset = SymfonyTextSequenceUtilities.readViewPathStartIndex(statement);
				 String path = getDocument().getText().substring(statement.getOriginalOffset(startOffset), offset);
				 
				 if (path != null) {								
					 viewPath = new ViewPath(path);
					 return true;
				 }
				 
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
		
		return false;
	}
	
	public ViewPath getViewPath() {
		return viewPath;
	}	

}
