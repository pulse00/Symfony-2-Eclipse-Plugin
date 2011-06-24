package org.eclipse.symfony.core.model;

import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;

@SuppressWarnings("restriction")
public class TemplateVariable {


	private String name;
	private ISourceModule sourceModule;
	
	
	public TemplateVariable(ISourceModule iSourceModule, String varName) {
		
		name = varName;
		this.sourceModule = iSourceModule;

	}


	public String getName(String prefix) {

		return prefix + name;

	}

	public ISourceModule getSourceModule() {

		return  sourceModule;

	}
}