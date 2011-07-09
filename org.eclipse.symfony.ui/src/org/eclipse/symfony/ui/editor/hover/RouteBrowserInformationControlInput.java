package org.eclipse.symfony.ui.editor.hover;

import org.eclipse.jface.internal.text.html.BrowserInformationControlInput;
import org.eclipse.symfony.index.dao.Route;
import org.eclipse.symfony.ui.utils.HTMLUtils;

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