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

import com.dubture.symfony.index.dao.Route;

@SuppressWarnings("restriction")
public class Action extends SourceType {

	
	private String routePattern;
	private String viewPath;
	
	public Action(ModelElement parent, String name, String routePattern, String viewPath) {
		super(parent, name);
		
		this.routePattern = routePattern;
		this.viewPath = viewPath;

	}

	public Route getRoute() {

		Route route = new Route(getElementName(), routePattern, viewPath);
		return route;
	}

	public boolean hasTemplate() {

		return true;
	}

}
