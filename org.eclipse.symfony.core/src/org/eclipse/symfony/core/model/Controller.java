package org.eclipse.symfony.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;

@SuppressWarnings("restriction")
public class Controller implements ISymfonyModelElement {


	private ISourceModule sourceModule;
	private ClassDeclaration classDeclaration;
	private List<Action> actions = new ArrayList<Action>();
	private IScriptProject project;
	
	
	public Controller(ISourceModule sourceModule,
			ClassDeclaration ctrlDeclaration) {
		
		this.sourceModule = sourceModule;
		this.classDeclaration = ctrlDeclaration;
		this.project = sourceModule.getScriptProject();

	}
	
	public ISourceModule getSourceModule() {
		return sourceModule;
	}


	public ClassDeclaration getClassDeclaration() {
		return classDeclaration;
	}

	public void addAction(Action action) {

		actions.add(action);
		
	}

	public IScriptProject getProject() {

		return project;
		
	}

	public IPath getPath() {

		return sourceModule.getPath();

	}

	public String getName() {

		return classDeclaration.getName();
	}
	
	@Override
	public boolean equals(Object obj) {
	
		if (!(obj instanceof Controller))
			return false;
		
		Controller other = (Controller) obj;
		
		//TODO: add a more sophisticted equals check...
		return this.getName() == other.getName() && 
				other.getActions().size() == actions.size();
		
	}

	public List<Action> getActions() {

		return actions;

	}

	public List<TemplateVariable> getTemplateVariables(String viewName) {
		
		for (Action action : actions) {
			if (action.getName().equals(viewName)) {				
				return action.getTemplateVariables();
			}			
		}

		return null;
	}

	public TemplateVariable getTemplateVariable(String viewName, String varName) {

		
		for (Action action :actions) {

			if (action.getName().equals(viewName)) {
				return action.getTemplateVariable(varName);
			}
		}
		return null;
	}
}