package com.dubture.symfony.ui.editor.hyperlink;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;

/**
 * 
 * {@link ViewpathHyperlinkDetector} links viewPaths to the corresponding
 * template.
 * 
 * 
 * @deprecated replaced by SymfonySelectionEngine
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings("restriction")
public class ViewpathHyperlinkDetector extends StringHyperlinkDetector {


	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {


//		final PHPStructuredEditor editor = org.eclipse.php.internal.ui.util.EditorUtility
//				.getPHPEditor(textViewer);
//
//
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
//			IRegion wordRegion = findWord(document, offset);
//			
//			if (wordRegion == null)
//				return null;
//
//			
//
//			String path = document.get(wordRegion.getOffset(), wordRegion.getLength());			
//			ViewPath viewPath = new ViewPath(path);
//			
//						
//			
//			if (viewPath.isBundleBasePath() ) {
//				
//				IScriptFolder folder = SymfonyModelAccess.getDefault().findBundleFolder(viewPath.getBundle(), input.getScriptProject());				
//				ISourceModule module = folder.getSourceModule("Resources/views/"+viewPath.getTemplate());
//				
//				if (module != null) {
//					final IHyperlink link;	
//					link = new ModelElementHyperlink(wordRegion, module,
//							new OpenAction(editor));			
//	
//					return new IHyperlink[] { link };
//				
//				}
//				
//				
//			} else if (viewPath.isRoot()) {
//
//				IScriptProject project = input.getScriptProject();
//				IPath rootPath = project.getPath();
//				IPath vPath = rootPath.append("app/Resources/views");
//				ScriptFolder folder = (ScriptFolder) project.findScriptFolder(vPath);
//				
//				if (folder != null) {
//					
//					ISourceModule source = folder.getSourceModule(viewPath.getTemplate());
//					
//					if (source != null) {
//						final IHyperlink link;	
//						link = new ModelElementHyperlink(wordRegion, source,
//								new OpenAction(editor));			
//		
//						return new IHyperlink[] { link };
//						
//					}
//				}
//			} else {
//				
//				IScriptFolder folder = SymfonyModelAccess.getDefault().findBundleFolder(viewPath.getBundle(), input.getScriptProject());				
//				ISourceModule module = folder.getSourceModule("Resources/views/"+ viewPath.getController() + "/" + viewPath.getTemplate());
//				
//				if (module != null) {
//					final IHyperlink link;	
//					link = new ModelElementHyperlink(wordRegion, module,
//							new OpenAction(editor));			
//	
//					return new IHyperlink[] { link };
//				
//				}
//			}
//
//		} catch (Exception e) {			
//			Logger.logException(e);
//		}

		return null;
	}


}
