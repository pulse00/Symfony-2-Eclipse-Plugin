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
			return true;			
		}
		
		return false;
	}
	
	

}