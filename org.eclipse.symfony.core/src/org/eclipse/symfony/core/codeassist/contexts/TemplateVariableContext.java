package org.eclipse.symfony.core.codeassist.contexts;

import java.util.List;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.core.codeassist.contexts.GlobalStatementContext;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.symfony.core.SymfonyCoreConstants;
import org.eclipse.symfony.core.index.SymfonyElementResolver.TemplateField;
import org.eclipse.symfony.core.model.SymfonyModelAccess;
import org.eclipse.symfony.core.util.ModelUtils;
import org.eclipse.symfony.core.util.PathUtils;


/**
 * 
 * A context for template variables, ie
 * 
 *  
 *  <pre>
 *  
 *  // ../Resources/views/Demo/index.php
 *  
 *  <?php | <-- valid context 
 *  
 *  </pre>
 *  
 *  
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class TemplateVariableContext extends GlobalStatementContext { 

	private List<TemplateField> variables = null;	
	private String viewPath = null;
	
	
	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
	
		if (super.isValid(sourceModule, offset, requestor)) {
			
			try {
				
//				if (!hasWhitespaceBeforeCursor()) {
//					System.err.println("no");
//					return false;
//				}
				
				TextSequence statementText = getStatementText();
				
				if (statementText.toString().indexOf("->") > -1) {
					return false;
				}

				SymfonyModelAccess model = SymfonyModelAccess.getDefault();
				ISourceModule module = getSourceModule();
				IType controller = model.findControllerByTemplate(module);
				variables = model.findTemplateVariables(controller);
				viewPath = PathUtils.createViewPathFromTemplate(getSourceModule());
				
				return true;
				
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * Return the variables available in this view.
	 * 
	 * 
	 * @return
	 */
	public List<TemplateField> getVariables() {
		
		return variables;
		
	}

	/**
	 * Return the path of this view in the form 
	 * 
	 * BundleName:Controller:View
	 * 
	 * @return
	 */
	public String getViewPath() {
		
		return viewPath;
	}
}
