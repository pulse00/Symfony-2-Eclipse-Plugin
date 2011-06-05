package org.eclipse.symfony.core.compiler;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.php.core.compiler.PHPSourceElementRequestorExtension;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;


/**
 * 
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SourceElementRequestor extends PHPSourceElementRequestorExtension {

	private ClassDeclaration currentClass;
	
	
	@Override
	public boolean visit(TypeDeclaration s) throws Exception {
		

		if (s instanceof ClassDeclaration) {			
			currentClass = (ClassDeclaration) s;			
		}
	
		return true;
	}
	
	@Override
	public boolean endvisit(TypeDeclaration s) throws Exception {
				
		if (currentClass != null) {
						
		}
		
		currentClass = null;
		return true;
	}
}
