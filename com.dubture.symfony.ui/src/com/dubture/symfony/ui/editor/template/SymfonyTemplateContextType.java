package com.dubture.symfony.ui.editor.template;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.templates.ScriptTemplateContext;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.ui.editor.templates.PhpTemplateContextType;

/**
 * 
 * Creates the {@link SymfonyTemplateContext}
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyTemplateContextType extends PhpTemplateContextType {

	public static final String SYMFONY_CONTEXT_TYPE_ID = "symfony"; //$NON-NLS-1$	
	
	@Override
	public ScriptTemplateContext createContext(IDocument document, int completionPosition, int length, ISourceModule sourceModule) {
		
		return new SymfonyTemplateContext(this, document, completionPosition, length,sourceModule);
		
	}
	
}