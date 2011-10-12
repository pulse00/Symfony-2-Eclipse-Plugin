package com.dubture.symfony.ui.editor.hyperlink;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
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
import com.dubture.symfony.core.model.SymfonyModelAccess;


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
			
			// only resolve .js and .css files
			if (! (path.endsWith(".js") || path.endsWith(".css")) ) {
				return null;
			}

			
			IScriptProject scriptProject = sourceModule.getScriptProject();			
			IProject project = scriptProject.getProject();			

			IPath filePath = null;
			
			// the resource starts with a bundle alias
			if (path.startsWith("@")) {
				
				String[] parts = path.split("/");
				
				if (parts.length <= 0)
					return null;
				
				filePath = SymfonyModelAccess.getDefault().resolveBundleShortcut(parts[0], scriptProject);
				
				if (filePath != null) {
					
					// prepare the relative path
					path = path.replace(parts[0], "").replaceFirst("/", "");
				
					filePath = filePath.removeFirstSegments(1).append(path);
				}
				
			// it's a regular asset string, try to resolve it in the web folder
			} else {
				filePath = new Path("web/" + path);
			}
									
			if (filePath == null)
				return null;
			
			IFile file = project.getFile(filePath);			
			
			if (!file.exists()) {
				return null;
			}
			
			return new IHyperlink[] { new AssetHyperlink(wordRegion, file) };			
			
		} catch (Exception e) {			
			Logger.logException(e.getMessage(), e);			
		}
		
		return null;
	}	
}