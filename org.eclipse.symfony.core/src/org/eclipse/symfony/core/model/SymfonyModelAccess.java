package org.eclipse.symfony.core.model;

import org.eclipse.php.internal.core.model.PhpModelAccess;

/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyModelAccess extends PhpModelAccess {

	
	private static SymfonyModelAccess modelInstance = null;
	
	
	public static SymfonyModelAccess getDefault() {
		
		if (modelInstance == null)
			modelInstance = new SymfonyModelAccess();
		
		return modelInstance;
	}
	

}
