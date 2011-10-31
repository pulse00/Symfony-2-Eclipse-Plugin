package com.dubture.symfony.core.codeassist.contexts;

import java.util.List;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.core.codeassist.contexts.GlobalStatementContext;
import org.eclipse.php.internal.core.util.text.TextSequence;

import com.dubture.symfony.core.index.SymfonyElementResolver.TemplateField;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;
import com.dubture.symfony.core.util.ModelUtils;
import com.dubture.symfony.core.util.PathUtils;


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
				viewPath = PathUtils.createViewPathFromTemplate(getSourceModule(), false);
				
				return true;
				
				
			} catch (Exception e) {
				Logger.logException(e);
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
