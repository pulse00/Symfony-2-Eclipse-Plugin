package org.eclipse.symfony.core.compiler;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.expressions.StringLiteral;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.php.core.compiler.PHPSourceElementRequestorExtension;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.symfony.core.builder.SymfonyNature;
import org.eclipse.symfony.core.model.Service;
import org.eclipse.symfony.core.model.SymfonyModelAccess;
import org.eclipse.symfony.core.preferences.SymfonyCoreConstants;
import org.eclipse.symfony.core.util.PathUtils;


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
	private boolean inController;
	
	private SymfonyModelAccess model = SymfonyModelAccess.getDefault();
	private boolean isSymfonySource;
	
	
	@Override
	public void setSourceModule(IModuleSource sourceModule) {

		super.setSourceModule(sourceModule);
		
		try {
			isSymfonySource = sourceModule.getModelElement().getScriptProject().getProject().getNature(SymfonyNature.NATURE_ID) != null;
		} catch (CoreException e) {
			isSymfonySource = false;
			Logger.logException(e);
		}
	}
	
	@Override
	public boolean visit(TypeDeclaration s) throws Exception {
		
		
		if (!isSymfonySource)
			return false;
		
		if (s instanceof ClassDeclaration) {			
			currentClass = (ClassDeclaration) s;
			for (Object o : currentClass.getSuperClasses().getChilds()) {

				if (o instanceof FullyQualifiedReference) {

					FullyQualifiedReference superReference = (FullyQualifiedReference) o;					

					//TODO: find a way to check against the FQCN
					// via the UseStatement
					if (/*superReference.getNamespace().equals(SymfonyCoreConstants.CONTROLLER_NS) 
							&& */superReference.getName().equals(SymfonyCoreConstants.CONTROLLER_CLASS)) {
						
						// the ControllerIndexer does the actual work of parsing the
						// the relevant elements inside the controller
						// which are then being collected in the endVisit() method
						inController = true;
						
					}
				}
			}
		}		
	
		return true;
	}
	
	@Override
	public boolean visit(Statement st) throws Exception {

		return true;
	}
	
	
	@Override
	public boolean visit(Expression st) throws Exception {

		if (!isSymfonySource)
			return false;

		
		if (st instanceof StringLiteral) {
			
			StringLiteral literal = (StringLiteral) st;
			String literalValue = literal.getValue().replaceAll("['\"]", "");

			if (PathUtils.isViewPath(literalValue)) {
				//TODO: report viewpath reference
			}
			
			IPath path = getSourceModule().getModelElement().getScriptProject().getPath();
			Service service = model.findService(literalValue, path);
			
			if (service != null) {
				fRequestor.acceptTypeReference(service.getFullyQualifiedName(), literal.sourceStart());
			}
		}
	
		return true;
	}
	
	
	@Override
	public boolean endvisit(TypeDeclaration s) throws Exception {
				
		if (currentClass != null) {
						

		}
		
		inController = false;
		
		currentClass = null;
		return true;
	}
}
