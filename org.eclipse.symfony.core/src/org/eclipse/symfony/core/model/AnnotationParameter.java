package org.eclipse.symfony.core.model;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPFieldDeclaration;


/**
 * 
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class AnnotationParameter extends AbstractSymfonyModel {

	
	private Annotation annotation;
	private PHPFieldDeclaration declaration;
	

	public AnnotationParameter(Annotation annotation, IModelElement sourceModule, PHPFieldDeclaration declaration) {
		super(sourceModule);
		
		this.declaration = declaration;

	}

	public String getName() {
	
		return declaration.getName();

	}

	public Annotation getAnnotation() {
		return annotation;
	}

}
