package org.eclipse.symfony.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;


/**
 * 
 * The {@link Action} class represents a Symfony2
 * action (aka controller) in a {@link Controller} (aka controller object).
 * 
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class Action implements ISymfonyModelElement {

	
	private List<TemplateVariable> templateVars = new ArrayList<TemplateVariable>();
	
	private String name;
	private PHPMethodDeclaration method;
	private Controller controller;
	
	
	public Action(Controller controller, PHPMethodDeclaration method) {
		
		this.controller = controller;
		this.method = method;
		this.name = method.getName().replace("Action", "");

	}

	public void addTemplateVariable(TemplateVariable variable) {
		
		
		System.err.println(getName() + " adding tvariable " + variable.getName("") + " " + variable.getClassName() + " " + variable.getNamespace());
		templateVars.add(variable);

		
	}

	public String getName() {

		return name;
	}

	public List<TemplateVariable> getTemplateVariables() {

		return templateVars;
	}

	public TemplateVariable getTemplateVariable(String varName) {

		System.err.println(getName() + " getting template variable " + varName + " searching in " + templateVars.size());
		// probably should store the TemplateVars in a <String, TemplateVariable> Map
		for (TemplateVariable var : templateVars) {			
			if (var.getName("").equals(varName))
				return var;
		}
		return null;
	}
}
