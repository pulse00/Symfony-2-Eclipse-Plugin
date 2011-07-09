package org.eclipse.symfony.ui.editor.hover;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.internal.ui.editor.hover.PHPDocumentationHover;
import org.eclipse.symfony.core.log.Logger;
import org.eclipse.symfony.core.model.SymfonyModelAccess;
import org.eclipse.symfony.index.dao.Route;


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
