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

import com.dubture.twig.core.codeassist.context.VariableFieldContext;


/**
 * 
 * Is a valid completion context when staying after a twig 
 * field delimiter.
 * 
 * <pre>
 * 
 * 	{{ form.|  <-- a valid context
 * 
 * </pre> 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class TemplateVariableFieldCompletionContext extends
		VariableFieldContext {
	
	
	
	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		if (super.isValid(sourceModule, offset, requestor)) {
		    
            if (!requestor.getClass().getName().contains("Twig")) {
                return false;
            }
		    
			return true;			
		}
		
		return false;
	}
	
	

}
