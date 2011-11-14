/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist.contexts;

import org.eclipse.core.resources.IProjectNature;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.QuotesContext;
import org.eclipse.php.internal.core.util.text.TextSequence;

import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.EntityAlias;
import com.dubture.symfony.core.model.ViewPath;
import com.dubture.symfony.core.util.text.SymfonyTextSequenceUtilities;

@SuppressWarnings("restriction")
public class EntityCompletionContext extends QuotesContext {

	
	private EntityAlias alias;

	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		
		if (super.isValid(sourceModule, offset, requestor)) {
			try {
				
				IProjectNature nature = sourceModule.getScriptProject().getProject().getNature(SymfonyNature.NATURE_ID);
				
				// wrong nature
				if(!(nature instanceof SymfonyNature)) {
					return false;	
				}
				
				
				TextSequence statementText = getStatementText();
				if (SymfonyTextSequenceUtilities.isInEntityFunctionParameter(statementText) > -1) {
					
					
					int startOffset = SymfonyTextSequenceUtilities.readViewPathStartIndex(statementText);

					String path = null;

					if (startOffset == -1) {
						path = "";
					} else {					 

						int original = statementText.getOriginalOffset(startOffset);
						int end = offset;

						if (original >= 0 &&  end > original) {
							path = statementText.getSource().getFullText().substring(original, end);
						}
						else path = "";
					}

					if (path != null) {								
						alias = new EntityAlias(path);
						return true;
					}
					
					//TODO: check if the containing class is implementing a ContainerAware Interface
					return true;
				}
				
			} catch (Exception e) {				
				Logger.logException(e);
			}
		}		
		
		return false;
	}
	
	
	public EntityAlias getAlias() {
		
		return alias;
		
	}
}
