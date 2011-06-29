package org.eclipse.symfony.core.util;

import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.Service;

/**
 * 
 * A couple of Utility methods to extract 
 * Symfony2 model elements from PHP language structures.
 *  
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ModelUtils {
	
	
	/**
	 * 
	 * Parse a {@link PHPCallExpression} and generate
	 * a {@link Service} if one is found, ie:
	 * 
	 * 
	 * <pre> 
	 * 	$session = $this->get('session'); 
	 * </pre>
	 * 
	 * 
	 * @param call
	 * @return
	 */
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

						Service service = ModelManager.getInstance().findService(((Scalar)first).getValue());
						

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

	/**
	 * 
	 * Shortcut for extractBundleName(namespace.getName());
	 * 
	 * @param namespace
	 * @return
	 */
	public static String extractBundleName(NamespaceDeclaration namespace) {

		return extractBundleName(namespace.getName());
		
	}

	/**
	 * Get the BundleName from a FullyQualified ClassName / namespace
	 * 
	 * 
	 * @param fullyQualifiedName
	 * @return
	 */
	public static String extractBundleName(String fullyQualifiedName) {

		
		StringTokenizer tokenizer = new StringTokenizer(fullyQualifiedName, "\\");
		
		int i = 0;
		
		String prefix = "";
		
		while(tokenizer.hasMoreTokens()) {
			
			String token = tokenizer.nextToken();
			
			if (i++ == 0) {
				prefix = token;
			}
			
			if (token.endsWith("Bundle")) {
				return prefix + token;
			}
		}
		
		return null;
		
	}
}
