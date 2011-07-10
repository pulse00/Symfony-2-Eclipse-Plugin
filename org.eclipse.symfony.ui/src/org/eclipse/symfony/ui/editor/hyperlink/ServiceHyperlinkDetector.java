package org.eclipse.symfony.ui.editor.hyperlink;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.internal.ui.editor.ModelElementHyperlink;
import org.eclipse.dltk.ui.actions.OpenAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.symfony.core.log.Logger;
import org.eclipse.symfony.core.model.Service;
import org.eclipse.symfony.core.model.SymfonyModelAccess;


/**
 * {@link ServiceHyperlinkDetector} currently detects
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
 * @deprecated replaced by SymfonySelectionEngine
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings("restriction")
public class ServiceHyperlinkDetector extends StringHyperlinkDetector {

	public ServiceHyperlinkDetector() {

	}


	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {

		
//		final PHPStructuredEditor editor = org.eclipse.php.internal.ui.util.EditorUtility
//				.getPHPEditor(textViewer);
//		if (editor == null) {
//			return null;
//		}
//
//		if (region == null) {
//			return null;
//		}
//
//		IModelElement input = EditorUtility.getEditorInputModelElement(editor,
//				false);
//		if (input == null) {
//			return null;
//		}
//		
//		IDocument document = textViewer.getDocument();
//		int offset = region.getOffset();
//		
//		try {
//			
//			// TODO: fix the findWord() method for services like form.factory
//			IRegion wordRegion = findWord(document, offset);
//			
//			if (wordRegion == null)
//				return null;
//
//			String service = document.get(wordRegion.getOffset(), wordRegion.getLength());			
//			Service s = SymfonyModelAccess.getDefault().findService(service, input.getScriptProject().getPath());
//			
//			if (s == null) {
//				return null;
//			}
//
//			IType[] types = SymfonyModelAccess.getDefault().findServiceTypes(s, input.getScriptProject());
//			
//			// it should only exist 1 single service for each project with this service id
//			if (types.length > 1 && canShowMultipleHyperlinks) {
//
//				List<IHyperlink> links = new ArrayList<IHyperlink>();
//				
//				for (IType type : types) {
//					
//					final IHyperlink link;
//					
//					link = new ModelElementHyperlink(wordRegion, type,
//							new OpenAction(editor));			
//					
//					links.add(link);
//					
//				}
//				
//				return (IHyperlink[]) links
//				        .toArray(new IHyperlink[links.size()]);
//				
//				
//			} else if (types.length == 1) {
//				
//				IType type = types[0];			
//				final IHyperlink link;
//				
//				link = new ModelElementHyperlink(wordRegion, type,
//						new OpenAction(editor));			
//				
//				return new IHyperlink[] { link };
//				
//			}
//			
//			
//		} catch (Exception e) {
//			
//			Logger.logException(e);
//		}
		
		return null;
	}
}