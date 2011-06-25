package org.eclipse.symfony.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.contexts.GlobalStatementContext;

@SuppressWarnings("restriction")
public class TemplateVariableContext extends GlobalStatementContext {

	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
	
		if (super.isValid(sourceModule, offset, requestor)) {

			
			try {
				//System.err.println("template var context " + getPreviousWord().toString());
			} catch (Exception e) {

				e.printStackTrace();
			}
			
			return true;
			
		}
		
		return false;
	}
}
