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

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.SymfonyModelAccess;



/**
 * 
 * Resolves ${use_parent} variables in code templates.
 * 
 * 
 * @author Robert Gründler <r.gruendler@gmail.com>
 *
 */
public class UseStatementVariableResolver extends TemplateVariableResolver {

	private String[] phpdocs = {"string", "Boolean", "mixed"};			
	private List<String> internal = new ArrayList<String>(Arrays.asList(phpdocs));

	public UseStatementVariableResolver(String type, String description) {

		super(type, description);
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "restriction" })
	public void resolve(TemplateVariable variable, TemplateContext context) {


		if (context instanceof SymfonyTemplateContext) {

			try {

				List<String> statements = new ArrayList<String>();
				SymfonyTemplateContext symfonyContext = (SymfonyTemplateContext) context;
				String value = (String) symfonyContext.getTemplateVariable("use_parent");
				List<String> interfaces = (List<String>) symfonyContext.getTemplateVariable("interfaces");
				SymfonyModelAccess model = SymfonyModelAccess.getDefault();				
				IDLTKSearchScope scope = SearchEngine.createSearchScope(symfonyContext.getSourceModule().getScriptProject());

				if (interfaces!= null && interfaces.size() > 0) {					
					for (String _interface : interfaces) {

						String stmt = "use " + _interface.replaceFirst("/", "").replace("/", "\\") + ";";

						if (!statements.contains(stmt))
							statements.add(stmt);		

						IType[] types = model.findTypes(_interface, MatchRule.EXACT, 0, 0, scope, null);	

						for (IType type : types) {
							for (IMethod method : type.getMethods()) {

								for (IParameter param : method.getParameters()) {

									if (param.getType() != null && !internal.contains(param.getType())) {

										IType[] paramTypes = model.findTypes(param.getType(), MatchRule.EXACT, 0, 0, scope, null);

										if (paramTypes.length == 1) {

											IType paramType = paramTypes[0];											
											String statement = "use " + paramType.getTypeQualifiedName("\\") + ";";
											
											if (!statements.contains(statement))											
												statements.add(statement);
										}
									}
								}
							}
						}
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
				Logger.logException(e);
			}			
		}			
	}	
}