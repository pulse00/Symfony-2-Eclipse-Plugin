package org.eclipse.symfony.ui;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.ui.IEditorPart;

public class ServiceTextHover implements IPHPTextHover {

	public ServiceTextHover() {
		super();
	}
	
	
	@Override
	public IHoverMessageDecorator getMessageDecorator() {

		return new IHoverMessageDecorator() {
			
			@Override
			public String getDecoratedMessage(String msg) {

				return "decorated " + msg + " message";

			}
		};
		
	}


	@Override
	public void setEditor(IEditorPart editor) {

	}


	@Override
	public void setPreferenceStore(IPreferenceStore store) {
		
	}


	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		
		System.err.println("get hover info " + hoverRegion.getOffset() + " " + hoverRegion.getLength());
		return null;

	}


	@Override
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {

		
		System.err.println("get hover region");
		return null;
	}
}