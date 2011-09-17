package com.dubture.symfony.ui.editor.template;

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.TemplateVariableResolver;

/**
 * 
 * Resolves ${use_parent} variables in code templates.
 * 
 * 
 * @author Robert Gründler <r.gruendler@gmail.com>
 *
 */
public class UseStatementVariableResolver extends TemplateVariableResolver {

	public UseStatementVariableResolver(String type, String description) {

		super(type, description);
	}
	
	@Override
	public void resolve(TemplateVariable variable, TemplateContext context) {
		
		
		if (context instanceof SymfonyTemplateContext) {
			
			SymfonyTemplateContext symfonyContext = (SymfonyTemplateContext) context;
			
			try {
				
				String value = symfonyContext.getVariable("use_parent");
				
				if (value != null && value.length() > 0) {
					
					String statement = "use " + value + ";";
					variable.setValue(statement);
				} else {
					variable.setValue("");
				}
				
				variable.setResolved(true);				
				
				
			} catch (Exception e) {

				e.printStackTrace();
			}			
		}			
	}	
}