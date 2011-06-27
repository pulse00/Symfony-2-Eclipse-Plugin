package org.eclipse.symfony.core.compiler;


import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.compiler.ISourceElementRequestor;
import org.eclipse.php.core.compiler.PHPSourceElementRequestorExtension;
import org.eclipse.php.internal.core.compiler.ast.nodes.Assignment;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.symfony.core.SymfonyCoreConstants;
import org.eclipse.symfony.core.model.Service;
import org.eclipse.symfony.core.util.ModelUtils;


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
	
	
	
	@Override
	public boolean visit(TypeDeclaration s) throws Exception {
		
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

		if (st instanceof Assignment) {

			Assignment s = (Assignment) st;

			Service service = null;
			
			if (s.getVariable().getClass() == VariableReference.class) {

				VariableReference var = (VariableReference) s.getVariable();		

				// A call expression like $foo = $this->get('bar');
				//
				if (s.getValue().getClass() == PHPCallExpression.class) {

					PHPCallExpression exp = (PHPCallExpression) s.getValue();

					// are we requesting a Service?
					if (exp.getName().equals("get")) {
						
						service = ModelUtils.extractServiceFromCall(exp);
						
						if (service != null) {

							try {

								ISourceElementRequestor.TypeInfo importInfo =
								        new ISourceElementRequestor.TypeInfo();
								
								Scalar scalar = (Scalar) exp.getArgs().getChilds().get(0);

								System.err.println(scalar.sourceStart() + " " + scalar.sourceEnd());
								importInfo.name = service.getId();
								importInfo.modifiers = Modifiers.AccPublic;
								importInfo.nameSourceStart = scalar.sourceStart();
								importInfo.nameSourceEnd = scalar.sourceEnd();
								
								fRequestor.enterType(importInfo);
								fRequestor.exitType(scalar.sourceEnd());
								
								System.err.println("in service " + importInfo.nameSourceStart + " " + importInfo.nameSourceEnd);	
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					// a more complex expression like
					// $form = $this->get('form.factory')->create(new ContactType());
					} else if (exp.getReceiver().getClass() == PHPCallExpression.class) {

						// try to extract a service if it's a Servicecontainer call
						service = ModelUtils.extractServiceFromCall((PHPCallExpression) exp.getReceiver());
						
						// nothing found, return
						if (service == null || exp.getCallName() == null) {
							
//							System.err.println("nothing found");
							return true;
						}
					}
				} 
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
