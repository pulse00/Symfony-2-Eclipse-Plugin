/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.model;

import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceType;

import com.dubture.symfony.index.model.Route;

@SuppressWarnings("restriction")
public class Action extends SourceType {

	public Action(ModelElement parent, String name) {
		super(parent, name);

	}
	
	public boolean hasTemplate() {
		
		return true;
		
	}
	
	
	public Route getRoute() {
		
		
		Route route = new Route("home", "/foo", "AcmeDemoBundle:Demo:index");
		
		return route;
	}

}
