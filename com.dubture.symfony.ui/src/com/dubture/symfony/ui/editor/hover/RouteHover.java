/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.editor.hover;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.internal.ui.editor.hover.PHPDocumentationHover;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.index.model.Route;


/**
 * 
 * Creates the hoverInfo when hovering over routes.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class RouteHover extends PHPDocumentationHover {

	
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		RouteBrowserInformationControlInput info = (RouteBrowserInformationControlInput) getHoverInfo2(
				textViewer, hoverRegion);
		return info != null ? info.getHtml() : null;
	}
	


	@Override
	public Object getHoverInfo2(ITextViewer textViewer, IRegion hoverRegion) {

		IModelElement[] elements = getElementsAt(textViewer, hoverRegion);

		if (elements == null || elements.length > 0)
			return null;

		ISourceModule sm = getEditorInputModelElement();

		try {

			String source = sm.getSource();			
			String selection = source.substring(hoverRegion.getOffset(), hoverRegion.getOffset() +  hoverRegion.getLength());

			if (selection != null && selection.length() > 0) {
				
				Route route = SymfonyModelAccess.getDefault().findRoute(selection, sm.getScriptProject());
				
				if (route != null) {
					
					RouteBrowserInformationControlInput info = new RouteBrowserInformationControlInput(null, route);
					return info;

				}
				
			}


		} catch (Exception e2) {
			Logger.logException(e2);			
		}

		return null;		

	}
}
