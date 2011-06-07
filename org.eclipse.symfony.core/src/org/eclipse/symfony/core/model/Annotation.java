package org.eclipse.symfony.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPFieldDeclaration;

/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class Annotation extends AbstractSymfonyModel  {

	
	private NamespaceDeclaration namespace;
	private ClassDeclaration classDeclaration;
	private List<AnnotationParameter> parameters = new ArrayList<AnnotationParameter>();
	
	private IType type;
	
	public Annotation(ISourceModule sourceModule, NamespaceDeclaration namespace, ClassDeclaration classDec) {
		
		super(sourceModule);
		this.namespace = namespace;
		this.classDeclaration =classDec;
		
		type = sourceModule.getType(classDec.getName());
		
		
		
	}
	
	public void addParameter(PHPFieldDeclaration declaration) {

		AnnotationParameter param = new AnnotationParameter(this, sourceModule, declaration);
		parameters.add(param);		
		
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
	
	public List<AnnotationParameter> getParameters() {
		return parameters;
	}

	public IType getType() {
		return type;
	}
}