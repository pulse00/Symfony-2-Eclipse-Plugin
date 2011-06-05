package org.eclipse.symfony.core.visitor;

import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.symfony.core.model.Bundle;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.exception.InvalidBundleException;

/**
 * 
 * {@link BundleVisitor} parses Bundle classes and
 * creates the corresponding model object.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class BundleVisitor extends PHPASTVisitor {
	

	private IBuildContext context;
	
	public BundleVisitor(IBuildContext context) {
		
		this.context = context;
		
	}
	
	
	@Override
	public boolean visit(ClassDeclaration declaration) throws Exception {
	
		
		if (declaration.getName().endsWith("Bundle")) {
					
			try {
				
				Bundle bundle = new Bundle(context.getSourceModule(), declaration);
				ModelManager.getInstance().addBundle(bundle);			
				return true;
				
			} catch (InvalidBundleException e) {
				
				System.err.println(e.getMessage());
			}
		} 
		
		return false;
	}
	
	

}
