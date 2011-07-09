package org.eclipse.symfony.core.model;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;

/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class Annotation {

	
	private NamespaceDeclaration namespace;
	private ClassDeclaration classDeclaration;	
	
	private IType type;
	
	public Annotation(ISourceModule sourceModule, NamespaceDeclaration namespace, ClassDeclaration classDec) {
		
		
		this.namespace = namespace;
		this.classDeclaration =classDec;
		
		type = sourceModule.getType(classDec.getName());
		
		
		
	}
	

	public ClassDeclaration getClassDeclaration() {
		return classDeclaration;
	}

	public NamespaceDeclaration getNamespace() {
		return namespace;
	}

	public String getName() {
		
		return classDeclaration.getName();
		
	}
	
	public IType getType() {
		return type;
	}
}