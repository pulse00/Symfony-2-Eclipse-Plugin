/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.model;

import org.eclipse.php.core.compiler.ast.nodes.PHPMethodDeclaration;


/**
 * 
 * A {@link TemplateVariable} represents a variable
 * being passed from a {@link Controller} to a 
 * Symfony template.
 *  
 *  
 * TODO: there needs to be a mapping between the template for which
 * a Variable has been declared. Currently each variable declared
 * in an action of a controller is considered to be a template variable
 * of the controllers default template.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class TemplateVariable  {


	private String name;
	private String namespace = null;
	private String className = null;
	private PHPMethodDeclaration method;
	
	private int sourceStart;
	private int sourceEnd;
	

	public PHPMethodDeclaration getMethod() {
		return method;
	}


	public TemplateVariable(PHPMethodDeclaration method, String var,
			int sourceStart, int sourceEnd, String namespace, String className) {
		

		this.namespace = namespace;
		this.className = className;
		this.method = method;
		this.sourceStart = sourceStart;
		this.sourceEnd = sourceEnd;
		setName(var);
		
	}
	
	
	public void setName(String name) {

		if (name.startsWith("$")) {
			this.name = name;
		} else {
			this.name = "$" + name.replaceAll("['\"]", "");	
		}
	}

	
	public String getName(String prefix) {

		return prefix + name;

	}

	
	public String getNamespace() {
		
		return namespace;
	}
	
	public String getClassName() {
		
		return className;
		
	}


	public String getName() {

		return getName("");
	}


	public int sourceStart() {

		return sourceStart;
	}


	public int sourceEnd() {

		return sourceEnd;
	}


	public boolean isReference() {

		return namespace != null && className != null;
		
	}
	
	public boolean isScalar() {
		
		return namespace == null && className == null;
		
	}
	
	public String toString() {
				
		return getName() + " => " + getNamespace() + " - " + getClassName() + " " + getMethod();		
		
	}
}
