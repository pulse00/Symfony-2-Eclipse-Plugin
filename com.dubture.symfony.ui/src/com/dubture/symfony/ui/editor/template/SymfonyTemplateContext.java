package com.dubture.symfony.ui.editor.template;

import org.eclipse.dltk.core.ISourceModule;

import org.eclipse.dltk.ui.templates.ScriptTemplateContextType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateBuffer;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.php.internal.ui.editor.templates.PhpTemplateContext;


/**
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyTemplateContext extends PhpTemplateContext {

	public SymfonyTemplateContext(
			ScriptTemplateContextType phpTemplateContextType,
			IDocument document, int offset, int length,
			ISourceModule sourceModule) {
		super(phpTemplateContextType, document, offset, length, sourceModule);
	
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