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
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.TemplateVariableResolver;

/**
 * 
 * Resolves "class_name" variables in new classes.
 * 
 * 
 * @author Robert Gründler <r.gruendler@gmail.com>
 *
 *
 */
 public class ClassNameVariableResolver extends TemplateVariableResolver {

	protected ClassNameVariableResolver(String type, String description) {
		
		super(type, description);		

	}
	
		
	@Override
	public void resolve(TemplateVariable variable, TemplateContext context) {
		
		if (context instanceof SymfonyTemplateContext) {
			
			SymfonyTemplateContext symfonyContext = (SymfonyTemplateContext) context;
			
			try {
				
				ISourceModule module = symfonyContext.getSourceModule();				
				String path = module.getPath().removeFileExtension().segment(module.getPath().segmentCount()-1);
				variable.setValue(path.toString());
				variable.setResolved(true);
				
				
			} catch (Exception e) {

				e.printStackTrace();
			}			
		}			
	}	

}
