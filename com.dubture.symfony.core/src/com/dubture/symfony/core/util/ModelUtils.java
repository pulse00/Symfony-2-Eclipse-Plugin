/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.util;

import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.core.compiler.ast.nodes.Scalar;

import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;

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
	public static Service extractServiceFromCall(PHPCallExpression call, IPath path) {

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

						Service service = SymfonyModelAccess.getDefault().findService(((Scalar)first).getValue(), path);
						

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

		Assert.isNotNull(namespace);
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
			
			if (token.equals("Bundle"))
				continue;
			
			if (token.endsWith("Bundle")) {
				return prefix + token;
			}
			
			if (i > 1) 
				prefix += token;
		}
		
		return null;
		
	}

	public static String getControllerName(IType type) {

		if (!type.getElementName().endsWith(SymfonyCoreConstants.CONTROLLER_CLASS))
			return type.getElementName();
		
		return type.getElementName().replace(SymfonyCoreConstants.CONTROLLER_CLASS, "");
		
	}

	public static String resolveControllerShortcut(String path, IScriptProject project) {

		if (path.startsWith("@")) {
			
			String[] parts = path.split("\\/");
			
			if (parts.length > 0) {
				String bundle = parts[0];
				
				if (bundle.startsWith("@")) {					
					Bundle b = SymfonyModelAccess.getDefault().findBundle(bundle.replace("@", ""), project);					
					if (b != null) {						
						return b.getPath().toString() + path.replace(bundle, "");
					}
				}				
			}
		}
		
		return null;
	}
	
	public static IPath webToBundlePath(IPath webpath, IScriptProject project)
	{

	    SymfonyModelAccess modelAccess = SymfonyModelAccess.getDefault();
	    IPath truncated = webpath.removeFirstSegments(2);
	    String bundleName = truncated.segment(0) + "bundle";
	    Bundle bundle = modelAccess.findBundle(bundleName, project);
	    
	    if (bundle == null) {
	        return null;
	    }
	    
	    IPath bundlePath = bundle.getPath();
	    IPath resourcePath = bundlePath.append("Resources").append("public").append(truncated.removeFirstSegments(1));
	    return resourcePath.removeFirstSegments(1);
	    
	}
}
