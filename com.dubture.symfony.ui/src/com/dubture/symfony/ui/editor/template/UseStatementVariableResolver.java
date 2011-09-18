package com.dubture.symfony.ui.editor.template;

import java.util.ArrayList;
import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public void resolve(TemplateVariable variable, TemplateContext context) {
		
		
		if (context instanceof SymfonyTemplateContext) {
			
			try {
			
				List<String> statements = new ArrayList<String>();
				
				
				SymfonyTemplateContext symfonyContext = (SymfonyTemplateContext) context;
				String value = (String) symfonyContext.getTemplateVariable("use_parent");
				
				List<String> interfaces = (List<String>) symfonyContext.getTemplateVariable("interfaces");
				
				if (interfaces!= null && interfaces.size() > 0) {					
					for (String i : interfaces) {
						
						String stmt = "use " + i.replaceFirst("/", "").replace("/", "\\") + ";";
						
						if (!statements.contains(stmt))
							statements.add(stmt);						
						
					}					
				}
				
				if (value != null && value.length() > 0) {
					
					String statement = "use " + value + ";";
					
					if (!statements.contains(statement))
						statements.add(statement);
					
				}
				
				String s = "";
				
				for (String statement : statements) {				
					s += statement + "\n";					
				}				
				
				variable.setValue(s);
				variable.setResolved(true);				
				
				
			} catch (Exception e) {

				e.printStackTrace();
			}			
		}			
	}	
}