package org.eclipse.symfony.ui.editor.hyperlink;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.internal.ui.editor.ModelElementHyperlink;
import org.eclipse.dltk.ui.actions.OpenAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.hyperlink.PHPHyperlinkDetector;
import org.eclipse.symfony.core.Logger;
import org.eclipse.symfony.core.SymfonyCorePlugin;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.Service;


/**
 * {@link SymfonyHyperlinkDetector} currently detects
 * Services in PHP code. Ctrl+Click will open the 
 * Implementation.
 * 
 * Future Hyperlink-Detectors will provide:
 * 
 * 1. Open templates when clicking on @Template() annotations or render('...') functions
 * 2. Open Controllers when clicking on ViewVariables
 * 3. Open Definitions of routes when clicking on routes 
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings("restriction")
public class SymfonyHyperlinkDetector extends PHPHyperlinkDetector {

	public SymfonyHyperlinkDetector() {

	}


	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {

		
		final PHPStructuredEditor editor = org.eclipse.php.internal.ui.util.EditorUtility
				.getPHPEditor(textViewer);
		if (editor == null) {
			return null;
		}

		if (region == null) {
			return null;
		}

		IModelElement input = EditorUtility.getEditorInputModelElement(editor,
				false);
		if (input == null) {
			return null;
		}
		
		IDocument document = textViewer.getDocument();
		int offset = region.getOffset();
		
		try {
			IRegion wordRegion = findWord(document, offset);
			
			if (wordRegion == null)
				return null;

			String service = document.get(wordRegion.getOffset(), wordRegion.getLength());			
			Service s = ModelManager.getInstance().findService(service, input.getScriptProject().getPath());
			
			if (s == null) {
				return null;
			}
			
			IDLTKSearchScope scope = SearchEngine.createSearchScope(input.getScriptProject());
			
			IType[] types = PhpModelAccess.getDefault().findTypes(s.getNamespace(), s.getClassName(), MatchRule.EXACT, 0, 0, scope, null);
			
			if (types.length != 1) {
				Logger.debugMSG("No service found");
				return null;
			}
			
			IType type = types[0];			
			final IHyperlink link;
			
			link = new ModelElementHyperlink(wordRegion, type,
					new OpenAction(editor));			
			
			return new IHyperlink[] { link };
			
		} catch (Exception e) {
			
			Logger.logException(e);
		}
		
		return null;
	}
	
	
	public static IRegion findWord(IDocument document, int offset) {

		boolean namespacesSupported = true;
		
		int start = -2;
		int end = -1;

		try {
			int pos = offset;
			char c;
			

			int rightmostNsSeparator = -1;
			while (pos >= 0) {
				c = document.getChar(pos);
				
				if (!Character.isJavaIdentifierPart(c)
						&& (!namespacesSupported || c != '\'')) {
					break;
				}
				if (namespacesSupported && c == '\''
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
						&& (!namespacesSupported || c != '\'')) {
					break;
				}
				if (namespacesSupported && c == '\'') {
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
				
				return new Region(start + 2, end - start - 2);
			}
		}

		
		return null;
	}

}

