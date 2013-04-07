/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.editor.hover;

import org.eclipse.jface.internal.text.html.BrowserInformationControlInput;

import com.dubture.symfony.index.model.Route;
import com.dubture.symfony.ui.utils.HTMLUtils;


/**
 * 
 * Creates the HTML for the route documentation popup when hovering over routes.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class RouteBrowserInformationControlInput extends
	BrowserInformationControlInput {

	
	private Route route;
	public RouteBrowserInformationControlInput(
			BrowserInformationControlInput previous, Route route) {
		super(previous);
		
		this.route = route;

	}

	@Override
	public String getHtml() {

		return HTMLUtils.route2Html(route);
		
	}
	
	@Override
	public int getLeadingImageWidth() {

		return 16;
	}

	@Override
	public Object getInputElement() {

		return route;
	}

	@Override
	public String getInputName() {

		return route.name;
		
	}
}
