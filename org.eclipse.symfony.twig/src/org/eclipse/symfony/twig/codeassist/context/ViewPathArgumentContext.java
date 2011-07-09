package org.eclipse.symfony.twig.codeassist.context;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.symfony.core.log.Logger;
import org.eclipse.symfony.core.model.ViewPath;
import org.eclipse.symfony.core.util.text.SymfonyTextSequenceUtilities;
import org.eclipse.twig.core.codeassist.context.QuotesContext;

@SuppressWarnings("restriction")
public class ViewPathArgumentContext extends QuotesContext {
	
	
	private ViewPath viewPath;
	
	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		// TODO Auto-generated method stub
		if(super.isValid(sourceModule, offset, requestor)) {
			
			 try {
				 
				 TextSequence statement = getStatementText();
				 IScriptProject project = getSourceModule().getScriptProject();
				 
				 if (SymfonyTextSequenceUtilities.isInViewPathFunctionParameter(statement, project) == false)
					 return false;
				 
				 int startOffset = SymfonyTextSequenceUtilities.readViewPathStartIndex(statement);
				 String path = statement.getSource().getFullText().substring(statement.getOriginalOffset(startOffset), offset-1);
				 
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