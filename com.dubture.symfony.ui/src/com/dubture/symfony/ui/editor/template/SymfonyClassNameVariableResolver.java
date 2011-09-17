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
 */public class SymfonyClassNameVariableResolver extends TemplateVariableResolver {

	protected SymfonyClassNameVariableResolver(String type, String description) {
		
		super(type, description);		

	}
	
	public SymfonyClassNameVariableResolver() {
	
		super();		
		
	}
	
	@Override
	protected String resolve(TemplateContext context) {

		return super.resolve(context);
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
