package com.dubture.symfony.ui.editor.hyperlink;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;

public class YamlHyperlinkDetector extends AbstractHyperlinkDetector {

	public YamlHyperlinkDetector() {

	}

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {

		
		// TODO: implement me
		
//		IDocument document = textViewer.getDocument();
//		int offset = region.getOffset();
//
//		IRegion wordRegion = findWord(document, offset, false);
//		
//
//		try {
//			String text = document.get(wordRegion.getOffset(),
//					wordRegion.getLength());
//			
//			System.err.println(text);
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
		

		return null;
	}
	
	public static IRegion findWord(IDocument document, int offset,
			boolean namespacesSupported) {

		int start = -2;
		int end = -1;

		try {
			int pos = offset;
			char c;

			int rightmostNsSeparator = -1;
			while (pos >= 0) {
				c = document.getChar(pos);
				if (!Character.isJavaIdentifierPart(c)
						&& (!namespacesSupported || c != '\\')) {
					break;
				}
				if (namespacesSupported && c == '\\'
						&& rightmostNsSeparator == -1) {
					rightmostNsSeparator = pos;
				}
				--pos;
			}
			start = pos;

			pos = offset;
			int length = document.getLength();

			while (pos < length) {
				c = document.getChar(pos);
				if (!Character.isJavaIdentifierPart(c)
						&& (!namespacesSupported || c != '\\')) {
					break;
				}
				if (namespacesSupported && c == '\\') {
					rightmostNsSeparator = pos;
				}
				++pos;
			}
			end = pos;

			if (rightmostNsSeparator != -1) {
				if (rightmostNsSeparator > offset) {
					end = rightmostNsSeparator;
				} else {
					start = rightmostNsSeparator;
				}
			}

		} catch (BadLocationException x) {
		}

		if (start >= -1 && end > -1) {
			if (start == offset && end == offset) {
				return new Region(offset, 0);
			} else if (start == offset) {
				return new Region(start, end - start);
			} else {
				return new Region(start + 1, end - start - 1);
			}
		}

		return null;
	}	

}
