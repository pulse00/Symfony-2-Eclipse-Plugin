package org.eclipse.symfony.core.visitor;

import java.util.Collection;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayCreation;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayElement;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassInstanceCreation;
import org.eclipse.php.internal.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.symfony.core.model.ModelManager;

@SuppressWarnings("restriction")
public class KernelVisitor extends PHPASTVisitor {
	
	
	private boolean inRegisterBundles;
	private IBuildContext context;
	
	public KernelVisitor(IBuildContext context) {

		this.context = context;
	}

	@Override
	public boolean visit(MethodDeclaration s) throws Exception {

		
		if (s.getName().equals("registerBundles")) {
			
			System.out.println("setting register bundles");
			return inRegisterBundles = true;
			
		}
		
		return false;
	}
	
	@Override
	public boolean endvisit(MethodDeclaration s) throws Exception {

		
		
		return inRegisterBundles = false;		

		
	}
	
	@Override
	public boolean visit(ClassInstanceCreation creation) throws Exception {

		
		if (inRegisterBundles == true) {
			
			if (creation.getClassName() instanceof FullyQualifiedReference) {
				
				FullyQualifiedReference reference = (FullyQualifiedReference) creation.getClassName();
				ModelManager.getInstance().activateBundle(reference.getFullyQualifiedName());
				
			}
		}

		return true;
	}
	
}