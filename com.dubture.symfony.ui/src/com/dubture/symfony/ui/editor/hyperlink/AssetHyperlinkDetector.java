package com.dubture.symfony.ui.editor.hyperlink;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.EditorUtility;

import com.dubture.symfony.core.log.Logger;


/**
 * 
 * Links js and css files.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class AssetHyperlinkDetector extends StringHyperlinkDetector {
	
	private PHPStructuredEditor editor;
	private ISourceModule sourceModule;	
		
	
	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {

		editor = EditorUtility.getPHPEditor(textViewer);
		
		if (editor == null)
			return null;
		
		sourceModule = org.eclipse.dltk.internal.ui.editor.EditorUtility.getEditorInputModelElement(editor, false);
		
		if (sourceModule == null)
			return null;
		

		IDocument document = textViewer.getDocument();
		int offset = region.getOffset();

		try {

			IRegion wordRegion = findWord(document, offset);
			
			if (wordRegion == null)
				return null;

			String path = document.get(wordRegion.getOffset(), wordRegion.getLength());
			
			if (! (path.endsWith(".js") || path.endsWith(".css")) ) {
				return null;
			}
						
			IScriptProject scriptProject = sourceModule.getScriptProject();			
			IProject project = scriptProject.getProject();			
			IFile file = project.getFile(new Path("web/" + path));			
			
			IHyperlink hyperlink = new AssetHyperlink(wordRegion, file);
			
			return new IHyperlink[] { hyperlink };			
			
		} catch (Exception e) {			
			Logger.logException(e.getMessage(), e);			
		}
		
		return null;
	}	
}