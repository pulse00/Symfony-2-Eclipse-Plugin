package com.dubture.symfony.ui.editor.hyperlink;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.php.internal.ui.editor.hyperlink.PHPHyperlinkDetector;

@SuppressWarnings("restriction")
public abstract class StringHyperlinkDetector extends PHPHyperlinkDetector {
	
	public static IRegion findWord(IDocument document, int offset) {

		int start = -2;
		int end = -1;

		try {

			int pos = offset;
			char c;

			char separator = '?';
			int length = document.getLength();

			// search backwards until a string delimiter
			// to find the start position
			while (pos >= 0) {
				c = document.getChar(pos);

				if (c == '\'' || c == '"') {
					separator = c;
					break;
				}
				--pos;
			}

			start = pos;
			pos++;


			// search forward until a string delimiter
			// to find the end position
			while (pos < length) {
				c = document.getChar(pos);
				if (c == separator) {
					end = pos;
					break;
				}
				++pos;
			}


			if (separator != '?' && start >= 0 && end != 0) {				
				start++;
				int rlength = end - start;
				return new Region(start, rlength );
			}

		} catch (BadLocationException x) {
		}

		return null;
	}			

}
