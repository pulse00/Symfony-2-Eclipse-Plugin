/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.editor.template;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.templates.ScriptTemplateContext;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.ui.editor.templates.PHPTemplateContextType;

/**
 * 
 * Creates the {@link SymfonyTemplateContext}
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyTemplateContextType extends PHPTemplateContextType {

	public static final String SYMFONY_CONTEXT_TYPE_ID = "symfony"; //$NON-NLS-1$	
	
	@Override
	public ScriptTemplateContext createContext(IDocument document, int completionPosition, int length, ISourceModule sourceModule) {
		
		return new SymfonyTemplateContext(this, document, completionPosition, length,sourceModule, null);
		
	}
	
}
