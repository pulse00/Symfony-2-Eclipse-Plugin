package org.eclipse.symfony.twig.codeassist.context;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProposalCollector;
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

		if(super.isValid(sourceModule, offset, requestor)) {
			
			 try {				 
				 				 
//				 if (getCompletionRequestor().getClass() == PHPCompletionProposalCollector.class) {
//					 System.err.println("is so");
//					 return false;
//				 }
				 
				 TextSequence statement = getStatementText();
//				 IScriptProject project = getSourceModule().getScriptProject();
				 
//				 if (SymfonyTextSequenceUtilities.isInViewPathFunctionParameter(statement, project) == false)
//					 return false;
				 
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