package org.eclipse.symfony.core.visitor;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.Assignment;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.Service;
import org.eclipse.symfony.core.model.SymfonyModelAccess;
import org.eclipse.symfony.core.model.TemplateVariable;



/**
 * 
 * The {@link TemplateVariableVisitor} parses {@link Assignment}
 * statements for object instantiations which could be potentially
 * be passed to templates as variables.
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings("restriction")
public class TemplateVariableVisitor extends PHPASTVisitor {

	protected Map<String, TemplateVariable> templateVariables = new HashMap<String, TemplateVariable>();
	protected IBuildContext context = null;

	public TemplateVariableVisitor(IBuildContext context) {
		
		this.context = context;
		
	}
	

	@Override
	public boolean visit(Assignment s) throws Exception {

		Service service = null;
		String varName = null;

		if (s.getVariable().getClass() == VariableReference.class) {

			VariableReference var = (VariableReference) s.getVariable();			
			varName = var.getName().replace("$", "");

			if (s.getValue().getClass() == PHPCallExpression.class) {

				PHPCallExpression exp = (PHPCallExpression) s.getValue();

				// are we calling a method named "get" ?
				if (exp.getName().equals("get")) {
					
					service = extractServiceFromCall(exp);
					
					if (service != null && service.getClassName() != null && service.getNamespace() != null) {					
						TemplateVariable tempVar = new TemplateVariable(context.getSourceModule(), service.getClassName(), service.getNamespace(), varName);
						templateVariables.put(varName, tempVar);
					}

				} else {

					if (exp.getReceiver().getClass() == PHPCallExpression.class) {

						service = extractServiceFromCall((PHPCallExpression) exp.getReceiver());
						
						if (service == null || context == null || exp.getCallName() == null) {
							return true;
						}
						
						SimpleReference callName = exp.getCallName();
						TemplateVariable tempVar = SymfonyModelAccess.getDefault().createTemplateVariableByReturnType(context.getSourceModule(), callName.toString(), service.getClassName(), service.getNamespace(), varName);
						
						if (tempVar != null) {
							templateVariables.put(varName, tempVar);
						}
						
					}
				}
			}
		}
		return true;
	}
	

	@SuppressWarnings("rawtypes")
	private Service extractServiceFromCall(PHPCallExpression call) {

		ASTNode receiver = call.getReceiver();

		if (receiver instanceof VariableReference) {

			VariableReference ref = (VariableReference) receiver;

			// is the receiver an object instance ?
			if (ref.getName().equals("$this")) {

				List args = call.getArgs().getChilds();

				// does the get() method have exact one argument?

				if (args.size() == 1) {

					Object first = args.get(0);

					if (first instanceof Scalar && ((Scalar)first).getScalarType() == Scalar.TYPE_STRING) {

						//TODO: check if there are PDT utils for stripping away quotes from
						// string literals.
						String className = ((Scalar)first).getValue().replace("'", "").replace("\"", "");

						//System.err.println(className);
						Service service = ModelManager.getInstance().getService(className);

						// we got a service match, return the goalevaluator.
						if (service != null) {							
							return service;
						}
					}							
				}
			}			
		}		
		return null;
	}
}
