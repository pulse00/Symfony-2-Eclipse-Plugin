package org.eclipse.symfony.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.GlobalStatementContext;
import org.eclipse.php.internal.core.util.text.TextSequence;

@SuppressWarnings("restriction")
public class TemplateVariableContext extends GlobalStatementContext { 

	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
	
		if (super.isValid(sourceModule, offset, requestor)) {
			
			try {
				
				if (hasWhitespaceBeforeCursor()) {
					return true;
				}
				TextSequence statementText = getStatementText();
				
				if (statementText.toString().indexOf("->") > -1) {
					return false;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
			return true;
			
		}
		
		return false;
	}
}
