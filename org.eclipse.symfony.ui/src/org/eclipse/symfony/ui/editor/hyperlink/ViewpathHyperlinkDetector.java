package org.eclipse.symfony.ui.editor.hyperlink;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.internal.ui.editor.ModelElementHyperlink;
import org.eclipse.dltk.ui.actions.OpenAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.symfony.core.log.Logger;
import org.eclipse.symfony.core.model.SymfonyModelAccess;
import org.eclipse.symfony.core.model.ViewPath;

/**
 * 
 * {@link ViewpathHyperlinkDetector} links viewPaths to the corresponding
 * template.
 * 
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings("restriction")
public class ViewpathHyperlinkDetector extends StringHyperlinkDetector {


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

			String path = document.get(wordRegion.getOffset(), wordRegion.getLength());			
			ViewPath viewPath = new ViewPath(path);
			
			if (viewPath.isBasePath() ) {
				
				IScriptFolder folder = SymfonyModelAccess.getDefault().findBundleFolder(viewPath.getBundle(), input.getScriptProject());				
				ISourceModule module = folder.getSourceModule("Resources/views/"+viewPath.getTemplate());
				
				if (module != null) {
					final IHyperlink link;	
					link = new ModelElementHyperlink(wordRegion, module,
							new OpenAction(editor));			
	
					return new IHyperlink[] { link };
				
				}
				
				
			} else {

				IScriptFolder folder = SymfonyModelAccess.getDefault().findBundleFolder(viewPath.getBundle(), input.getScriptProject());				
				ISourceModule module = folder.getSourceModule("Resources/views/"+ viewPath.getController() + "/" + viewPath.getTemplate());
				
				if (module != null) {
					final IHyperlink link;	
					link = new ModelElementHyperlink(wordRegion, module,
							new OpenAction(editor));			
	
					return new IHyperlink[] { link };
				
				}
			}

		} catch (Exception e) {			
			Logger.logException(e);
		}

		return null;
	}


}
