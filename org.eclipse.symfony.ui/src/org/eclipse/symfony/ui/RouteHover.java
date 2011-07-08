package org.eclipse.symfony.ui;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.internal.ui.editor.hover.BestMatchHover;

@SuppressWarnings("restriction")
public class RouteHover extends BestMatchHover {

	
	
	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {

		
		System.err.println("get hover info");
		return super.getHoverInfo(textViewer, hoverRegion);
	}
	
	
	@Override
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {

		System.err.println("get hover region");
		return super.getHoverRegion(textViewer, offset);
	}
	
}
