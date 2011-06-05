package org.eclipse.symfony.core.index;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.php.core.index.PhpIndexingVisitorExtension;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;


/**
 * 
 * {@link SymfonyIndexingVisitorExtension} contributes model elements
 * to the index.
 * 
 * Not used yet.
 *
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyIndexingVisitorExtension extends
		PhpIndexingVisitorExtension {
	
	private ClassDeclaration currentClass;
	private MethodDeclaration currentMethod;
	

	@Override
	public boolean visit(TypeDeclaration s) throws Exception {

		if (s instanceof ClassDeclaration) {
			currentClass = (ClassDeclaration) s;					
		}
		return super.visit(s);
	}
	
	@Override
	public boolean endvisit(TypeDeclaration s) throws Exception {
	
		if (currentClass != null) {
			
			
			
		}
		
		
		currentClass = null;
		return true;
	}
	
	@Override
	public boolean visit(MethodDeclaration s) throws Exception {
		
		if (currentClass != null && "build".equals(s.getName())) {

			currentMethod = s;
			
		}
		return true;
	}
	
	@Override
	public boolean endvisit(MethodDeclaration s) throws Exception {
		
		currentMethod = null;				
		return true;
	}	
}