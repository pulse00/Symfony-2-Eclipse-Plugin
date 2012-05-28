/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.resources;

/**
 * IResource markers specific to Symfony.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public interface SymfonyMarker {
	
	public static final String MISSING_SERVICE_CLASS = "com.dubture.symfony.core.serviceMarker";
	public static final String SERVICE_CLASS = "serviceClass";
	public static final String RESOLUTION_TEXT = "resolutionText";
}
