package org.eclipse.symfony.core.model;

import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;

@SuppressWarnings("restriction")
public class TemplateVariable {

	private Scalar scalar;
	private VariableReference reference;
	
	private String name;
	private ISourceModule sourceModule;
	
	
	public TemplateVariable(ISourceModule iSourceModule, Scalar varName, VariableReference var) {
		
		scalar = varName;
		reference = var;
		name = scalar.getValue().replace("\"", "").replace("'", "");
		this.sourceModule = iSourceModule;

	}

	public String getName(String prefix) {

		return prefix + name;

	}

	public ISourceModule getSourceModule() {

		return  sourceModule;

	}
}