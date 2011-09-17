package com.dubture.symfony.ui.editor.template;

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.TemplateVariableResolver;

public class ExtendsStatementVariableResolver extends TemplateVariableResolver {

	public ExtendsStatementVariableResolver(String type, String description) {

		super(type, description);
	}
	
	
	@Override
	public void resolve(TemplateVariable variable, TemplateContext context) {
		
		
		System.err.println("resolve: " + variable.getValues());
		
//		if (context instanceof SymfonyTemplateContext) {
//			
//			SymfonyTemplateContext symfonyContext = (SymfonyTemplateContext) context;
//			
//			try {
//				
//				ISourceModule module = symfonyContext.getSourceModule();				
//				String path = module.getPath().removeFileExtension().segment(module.getPath().segmentCount()-1);
//				variable.setValue(path.toString());
//				variable.setResolved(true);
//				
//				
//			} catch (Exception e) {
//
//				e.printStackTrace();
//			}			
//		}			
	}	
	

}
