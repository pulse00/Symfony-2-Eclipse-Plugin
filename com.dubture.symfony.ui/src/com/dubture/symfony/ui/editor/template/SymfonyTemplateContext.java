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
import org.eclipse.dltk.ui.templates.ScriptTemplateContextType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateBuffer;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.php.internal.ui.editor.templates.PHPTemplateContext;


/**
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyTemplateContext extends PHPTemplateContext {

	
	private CodeTemplateVariableHolder varHolder;
	
	
	public SymfonyTemplateContext(
			ScriptTemplateContextType phpTemplateContextType,
			IDocument document, int offset, int length,
			ISourceModule sourceModule, CodeTemplateVariableHolder varHolder) {
		super(phpTemplateContextType, document, offset, length, sourceModule);
		
		this.varHolder = varHolder;
	
		
	}
	
	
	public Object getTemplateVariable(String key) {
		
		return varHolder.get(key);
		
	}
	
	

	@Override
	public TemplateBuffer evaluate(Template template)
			throws BadLocationException, TemplateException {

		return super.evaluate(template);

		
		
	}
	
	@Override
	public TemplateContextType getContextType() {
	
		return super.getContextType();
	}
	
}
