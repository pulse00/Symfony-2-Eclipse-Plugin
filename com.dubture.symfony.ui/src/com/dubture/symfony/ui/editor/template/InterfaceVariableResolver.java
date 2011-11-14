/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.editor.template;

import java.util.List;

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.TemplateVariableResolver;


/**
 * 
 * Resolves ${interfaces} variables in code templates.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class InterfaceVariableResolver extends TemplateVariableResolver {
	
	public InterfaceVariableResolver(String type, String description) {

		super(type, description);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public void resolve(TemplateVariable variable, TemplateContext context) {
		
		
		if (context instanceof SymfonyTemplateContext) {
			
			SymfonyTemplateContext symfonyContext = (SymfonyTemplateContext) context;
			
			try {
				
				List<String> interfaces = (List<String>) symfonyContext.getTemplateVariable("interfaces");
				
				if (interfaces!= null && interfaces.size() > 0) {
					
					String interfaceStatement = "implements";
					
					for (String iface : interfaces) {
					
						String[] parts  = iface.split("\\\\");
						interfaceStatement += " " + parts[parts.length - 1]+ ", ";
						
					}
					
					interfaceStatement = interfaceStatement.substring(0, interfaceStatement.length() - 2 );					
					variable.setValue(interfaceStatement);
					
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
