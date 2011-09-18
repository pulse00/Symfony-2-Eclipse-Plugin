package com.dubture.symfony.ui.editor.template;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IParameter;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.TemplateVariableResolver;

import com.dubture.symfony.core.model.SymfonyModelAccess;

/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class InterfaceMethodsVariableResolver extends TemplateVariableResolver {
	
	public InterfaceMethodsVariableResolver(String type, String description) {

		super(type, description);
	}
	
	
	@Override
	@SuppressWarnings({ "unchecked", "restriction" })
	public void resolve(TemplateVariable variable, TemplateContext context) {
				
		if (context instanceof SymfonyTemplateContext) {
			
			SymfonyTemplateContext symfonyContext = (SymfonyTemplateContext) context;
			
			try {
				
				List<String> interfaces = (List<String>) symfonyContext.getTemplateVariable("interfaces");
				
				if (interfaces!= null && interfaces.size() > 0) {
					
					String superMethods = "";					
					SymfonyModelAccess model = SymfonyModelAccess.getDefault();
					
					IDLTKSearchScope scope = SearchEngine.createSearchScope(symfonyContext.getSourceModule().getScriptProject());
					
					String[] array = {"string", "Boolean"};					
					List<String> internal = new ArrayList<String>(Arrays.asList(array));
					
					for (String iface : interfaces) {
						
						IType[] types = model.findTypes(iface, MatchRule.EXACT, 0, 0, scope, null);						
						
						for (IType type : types) {
							for (IMethod method : type.getMethods()) {
								
								String methodString = "public function ";
								methodString += method.getElementName() + "(";
																
								for (IParameter param : method.getParameters()) {
									
									if (param.getType() != null && !internal.contains(param.getType())) {
										methodString += param.getType() +  " ";										
									}
									
									methodString +=  param.getName();
									
									if (param.getDefaultValue() != null) {									
										methodString +=  " = " + param.getDefaultValue();										
									}
									
									methodString += ", ";
									
								}
								
								if (method.getParameters().length > 0) {
									methodString = methodString.substring(0, methodString.length() - 2);
								}
							
								methodString += ") {\n\t\t// TODO Auto-generated method stub\n\n\t}\n\n\t";
								superMethods += methodString;
								
							}
						}
					}
										
					variable.setValue(superMethods);
					
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