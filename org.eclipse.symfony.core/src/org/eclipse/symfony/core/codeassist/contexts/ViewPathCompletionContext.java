package org.eclipse.symfony.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.QuotesContext;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.symfony.core.model.ViewPath;
import org.eclipse.symfony.core.util.text.SymfonyTextSequenceUtilities;

@SuppressWarnings("restriction")
public class ViewPathCompletionContext extends QuotesContext {
	
	private ViewPath viewPath;

	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		
		 if (super.isValid(sourceModule, offset, requestor)) {
			 			 
			 try {
				 
				 TextSequence statement = getStatementText();				 
				 int startOffset = SymfonyTextSequenceUtilities.readViewPathStartIndex(statement);				 
				 String path = getStatementText().getSource().getFullText().substring(statement.getOriginalOffset(startOffset), offset);
				 
				 if (path != null) {					 
					 viewPath = new ViewPath(path);
					 return true;
				 }
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		 		 
		 return false;
	}

	public ViewPath getViewPath() {
		return viewPath;
	}
}
