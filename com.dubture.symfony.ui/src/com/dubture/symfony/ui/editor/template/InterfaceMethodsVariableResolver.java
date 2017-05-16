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
import java.util.Map;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.php.internal.core.ast.rewrite.IndentManipulation;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.ui.CodeGeneration;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.SymfonyModelAccess;

/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class InterfaceMethodsVariableResolver extends TemplateVariableResolver {

	private char indentChar;
	private String indendation;
	private SymfonyModelAccess model;

	public InterfaceMethodsVariableResolver(String type, String description) {
		super(type, description);
		
		model = SymfonyModelAccess.getDefault();
		
	}


	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void resolve(TemplateVariable variable, TemplateContext context) {

		if (!(context instanceof SymfonyTemplateContext))
			return;

		SymfonyTemplateContext symfonyContext = (SymfonyTemplateContext) context;
		String delim = TextUtilities.getDefaultLineDelimiter(symfonyContext.getDocument());
		IScriptProject scriptProject =symfonyContext.getSourceModule().getScriptProject();
		IDLTKSearchScope scope = SearchEngine.createSearchScope(scriptProject);		

		indentChar = FormatterUtils
				.getFormatterCommonPreferences().getIndentationChar(symfonyContext.getDocument());
		indendation = String.valueOf(indentChar);
		
		try {

			List<String> interfaces = (List<String>) symfonyContext.getTemplateVariable("interfaces");

			if (interfaces!= null && interfaces.size() > 0) {

				String superMethods = "";
				Map options = scriptProject.getOptions(true);
				int tabWidth = IndentManipulation.getTabWidth(options);
				int indentWidth = IndentManipulation.getIndentWidth(options);
				Boolean generateComments = (Boolean) symfonyContext.getTemplateVariable("generate_comments");

				int i=0;
				
				for (String iface : interfaces) {

					IType[] types = model.findTypes(iface, MatchRule.EXACT, 0, 0, scope, null);

					for (IType type : types) {
						
						for (IMethod method : type.getMethods()) {
							
							String methodString = "";

							if (generateComments) {								
								String comment = CodeGeneration.getMethodComment(method, method, delim);
								comment = IndentManipulation.changeIndent(comment, 0, tabWidth, indentWidth, indendation, delim);
								
								// first line of first method docblock doesn't need indentation
								String prefix = i++ == 0 ? "" : indendation;
								methodString = prefix + comment + delim + indendation;
																
							} else {
								methodString = i++ <= 0 ? "" : indendation;
							}
							
							superMethods += methodString + "protected " + method.getSource().replace(";", " {" + delim + indendation + delim + delim + indendation + "}" + delim + delim);
							
						}
					}
				}

				variable.setValue(superMethods);

			} else {
				variable.setValue("");
			}

			variable.setResolved(true);				

		} catch (Exception e) {
			Logger.logException(e);
		}
	}
}
