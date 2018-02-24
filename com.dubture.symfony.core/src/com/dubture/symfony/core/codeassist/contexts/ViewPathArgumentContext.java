/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.util.text.TextSequence;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.ViewPath;
import com.dubture.symfony.core.util.text.SymfonyTextSequenceUtilities;

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
public class ViewPathArgumentContext extends QuoteIdentifierContext {

	private ViewPath viewPath;

	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		if (super.isValid(sourceModule, offset, requestor)) {

			try {

				if (requestor == null || !requestor.getClass().toString().contains("Symfony"))
				    return false;
				
				TextSequence statement = getStatementText();
				IScriptProject project = getCompanion().getSourceModule().getScriptProject();

				if (SymfonyTextSequenceUtilities.isInViewPathFunctionParameter(statement, project) == false)
					return false;

				int startOffset = SymfonyTextSequenceUtilities.readViewPathStartIndex(statement);

				String path = null;

				if (startOffset == -1) {
					path = "";
				} else {					 

					int original = statement.getOriginalOffset(startOffset);
					int end = offset;

					if (original >= 0 &&  end > original) {
						path = statement.getSource().getFullText().substring(original, end);
					}
					else path = "";
				}

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
