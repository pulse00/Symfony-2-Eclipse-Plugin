package org.eclipse.symfony.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;

@SuppressWarnings("restriction")
public class Action {

	
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
		
		templateVars.add(variable);
		
	}

	public String getName() {

		return name;
	}

	public List<TemplateVariable> getTemplateVariables() {

		return templateVars;
	}


}
