package org.eclipse.symfony.core.visitor;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassInstanceCreation;
import org.eclipse.php.internal.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.symfony.core.model.ModelManager;

/**
 * 
 * The {@link KernelVisitor} parses AppKernel classes to
 * detect active bundles in registerBundles().
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class KernelVisitor extends PHPASTVisitor {
	
	
	private boolean inRegisterBundles;
	
	
	public KernelVisitor(IBuildContext context) {
	}

	@Override
	public boolean visit(MethodDeclaration s) throws Exception {

		
		if (s.getName().equals("registerBundles")) {			
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
				try {
					FullyQualifiedReference reference = (FullyQualifiedReference) creation.getClassName();
					ModelManager.getInstance().activateBundle(reference.getFullyQualifiedName());									
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
}