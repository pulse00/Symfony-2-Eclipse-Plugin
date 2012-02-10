/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.model;


/**
 * 
 * Symfony model element IDs used to store
 * them in the DTLK H2 SQL Index.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public interface ISymfonyModelElement {
	
	
	// containers
	int BUNDLE = 200;	
	int NAMESPACE = 201;
	int CONTROLLER = 202;
	
	// method types
	int VIEW_METHOD = 300;
	int ROUTE_METHOD = 301;
	
	// misc
	int ANNOTATION = 400;	
	int ENVIRONMENT = 401;
	int TEMPLATE_VARIABLE = 402;
	int ROUTE = 403;
	


}
