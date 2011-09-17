package com.dubture.symfony.ui.editor.template;


import org.eclipse.core.runtime.IPath;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.jface.text.templates.TemplateVariableResolver;

import com.dubture.symfony.core.model.SymfonyModelAccess;

/**
 * 
 * Resolves "namespace" variables in new classes.
 * 
 * 
 * @author Robert Gründler <r.gruendler@gmail.com>
 *
 */
public class NamespaceVariableResolver extends TemplateVariableResolver {
	
	protected NamespaceVariableResolver(String type, String description) {
		
		super(type, description);		

	}
		

	@Override
	public void resolve(TemplateVariable variable, TemplateContext context) {
		
		
		System.err.println("resolve: " + variable.getValues());;
		
//		if (context instanceof SymfonyTemplateContext) {
//			
//			try {
//			
//				SymfonyTemplateContext symfonyContext = (SymfonyTemplateContext) context;
//				ISourceModule module = symfonyContext.getSourceModule();
//				IPath path = module.getPath().removeLastSegments(1);
//				String ns = SymfonyModelAccess.getDefault().findNameSpace(module.getScriptProject(), path);
//				
//				if (ns != null) {
//					variable.setValue(ns);
//					variable.setResolved(true);
//				}
//				
//				
//			} catch (Exception e) {
//
//				e.printStackTrace();
//			}			
//		}			
	}	
}