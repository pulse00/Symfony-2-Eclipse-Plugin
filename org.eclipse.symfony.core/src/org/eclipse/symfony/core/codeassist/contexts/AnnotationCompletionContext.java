package org.eclipse.symfony.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.PHPDocTagContext;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * 
 * {@link AnnotationCompletionContext} checks if we're
 * in a valid PHPDocTag completion context for annotations.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class AnnotationCompletionContext extends PHPDocTagContext {
	
	
	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		if (!super.isValid(sourceModule, offset, requestor) == true)
			return false;

		try {
			
			TextSequence sequence = getStatementText();
			int start = sequence.toString().lastIndexOf("@");
			int end = sequence.toString().length();
			
			String line = sequence.toString().substring(start, end);
			
			// we're inside an annotation's parameters
			// can't complete this so far.
			if (line.contains("(")) {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;			
		}
		
		return true;
	}
}