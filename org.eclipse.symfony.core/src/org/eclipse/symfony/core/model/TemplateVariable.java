package org.eclipse.symfony.core.model;

import org.eclipse.dltk.core.ISourceModule;


/**
 * 
 * A {@link TemplateVariable} represents a variable
 * being passed from a {@link Controller} to a 
 * Symfony template.
 *  
 *  
 * TODO: there needs to be a mapping between the template for which
 * a Variable has been declared. Currently each variable declared
 * in an action of a controller is considered to be a template variable
 * of the controllers default template.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class TemplateVariable implements ISymfonyModelElement {


	private String name;
	private ISourceModule sourceModule;
	private String namespace = null;
	private String className = null;
	
	

	public TemplateVariable(ISourceModule iSourceModule, String varName) {
		
		name = varName;
		this.sourceModule = iSourceModule;

	}


	public TemplateVariable(ISourceModule sourceModule2, String var,
			String namespace, String className) {
		
		this(sourceModule2, var);		
		this.namespace = namespace;
		this.className = className;		
		
	}

	
	public String getName(String prefix) {

		return prefix + name;

	}

	public ISourceModule getSourceModule() {

		return  sourceModule;

	}
	
	public String getNamespace() {
		
		return namespace;
	}
	
	public String getClassName() {
		
		return className;
		
	}


	@Override
	public String getName() {

		return getName("");
	}
}