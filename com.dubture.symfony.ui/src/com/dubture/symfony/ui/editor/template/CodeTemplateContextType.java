package com.dubture.symfony.ui.editor.template;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.templates.ScriptTemplateContext;
import org.eclipse.dltk.ui.templates.ScriptTemplateContextType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.ContextTypeRegistry;

/**
 * 
 * Add resolvers for plugin variables in code templates.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class CodeTemplateContextType extends ScriptTemplateContextType {

	private static final String CONTROLLER_CONTEXTTYPE = "php_new_file_context";

	@Override
	public ScriptTemplateContext createContext(IDocument document,
			int completionPosition, int length, ISourceModule sourceModule) {

		return new SymfonyTemplateContext(this, document, completionPosition, length, sourceModule);	
	}
	
	public CodeTemplateContextType(String contextName, String name) {
		super(contextName, name);


		//TODO: externalize Strings
		addResolver(new SymfonyNamespaceVariableResolver("namespace", "Symfony Namespace Resolver"));
		addResolver(new SymfonyClassNameVariableResolver("class_name", "Symfony Classname Resolver"));


	}	
	
	public CodeTemplateContextType(String contextName) {
		this(contextName, contextName);

	}

	
	public static void registerContextTypes(ContextTypeRegistry registry) {
		
		registry.addContextType(new CodeTemplateContextType(CodeTemplateContextType.CONTROLLER_CONTEXTTYPE));
		
		
	}
}