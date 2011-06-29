package org.eclipse.symfony.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.QuotesContext;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.symfony.core.model.ViewPath;
import org.eclipse.symfony.core.util.text.SymfonyTextSequenceUtilities;

/**
 * 
 * A valid viewPath argument context is when staying
 * in a parameter of a method which accepts a viewPath,
 * ie $this->render('|  
 *
 * 
 * For a PHP method to be a valid context, it needs to have 
 * a PHPDocBlock declaring the viewPath parameter as a 
 * string with the variable name $view.
 * 
 * This convention is necessary, otherwise we would need
 * to hard-code the methods accepting view parameters or
 * the code-assist would pop up in every QuotesContext.
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
		
		 if (super.isValid(sourceModule, offset, requestor)) {
			 			 
			 try {
				 
				 TextSequence statement = getStatementText();
				 IScriptProject project = getSourceModule().getScriptProject();
				 
				 if (SymfonyTextSequenceUtilities.isInViewPathFunctionParameter(statement, project) == false)
					 return false;
				 
				 int startOffset = SymfonyTextSequenceUtilities.readViewPathStartIndex(statement);
				 String path = statement.getSource().getFullText().substring(statement.getOriginalOffset(startOffset), offset);
				 
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
