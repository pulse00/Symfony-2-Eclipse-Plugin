package com.dubture.symfony.ui.editor.hyperlink;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;

public class XmlHyperlinkDetector extends AbstractHyperlinkDetector {

	public XmlHyperlinkDetector() {

	}

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {

		System.err.println("detect in xml");
		return null;
	}

}
