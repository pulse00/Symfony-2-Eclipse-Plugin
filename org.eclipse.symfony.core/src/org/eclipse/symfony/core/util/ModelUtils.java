package org.eclipse.symfony.core.util;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.Service;

@SuppressWarnings("restriction")
public class ModelUtils {
	
	@SuppressWarnings({ "rawtypes" })
	public static Service extractServiceFromCall(PHPCallExpression call) {

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

						Service service = ModelManager.getInstance().findService(className);

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
