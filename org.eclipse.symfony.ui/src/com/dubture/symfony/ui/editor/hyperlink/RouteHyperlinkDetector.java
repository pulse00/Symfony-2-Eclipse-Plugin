package com.dubture.symfony.ui.editor.hyperlink;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;

/**
 * 
 * {@link RouteHyperlinkDetector} detects routes and links them
 * to the corresponding action.
 * 
 * 
 * @deprecated replaced by SymfonySelectionEngine
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings("restriction")
public class RouteHyperlinkDetector extends StringHyperlinkDetector {

	public RouteHyperlinkDetector() {

	}
	
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
//			String routeName = document.get(wordRegion.getOffset(), wordRegion.getLength());
//			Route route = SymfonyModelAccess.getDefault().findRoute(routeName, input.getScriptProject());
//			
//			if (route == null) {
//				return null;
//			}
//						
//			ViewPath path = new ViewPath(route.getViewPath());
//			IDLTKSearchScope scope = SearchEngine.createSearchScope(input.getScriptProject());
//			IType[] types = PhpModelAccess.getDefault().findTypes(null, path.getController() + "Controller", MatchRule.EXACT, 0, 0, scope, null);
//			
//			IType type = null;
//
//			if (types.length > 1) {
//				for (IType t : types) {
//					
//					// filter out test controllers
//					if (t.getFullyQualifiedName() != null && t.getFullyQualifiedName().contains("Tests"))
//						continue;
//					type = t;
//				}
//			} else if (types.length == 1) {
//				type = types[0];
//			}
//			
//			if (type == null) {
//				return null;
//			}
//			
//			IDLTKSearchScope controllerScope = SearchEngine.createSearchScope(type);
//			IMethod[] methods= PhpModelAccess.getDefault().findMethods(path.getTemplate(), MatchRule.PREFIX, 0, 0, controllerScope, null);
//
//			if (methods.length > 1 && canShowMultipleHyperlinks) {
//				
//				List<IHyperlink> links = new ArrayList<IHyperlink>();
//				
//				for (IMethod method : methods) {
//					
//					final IHyperlink link;
//					
//					link = new ModelElementHyperlink(wordRegion, method,
//							new OpenAction(editor));
//					
//					links.add(link);
//					
//					
//				}
//				
//				return (IHyperlink[]) links
//				        .toArray(new IHyperlink[links.size()]);
//				
//			} else if (methods.length == 1) {
//				
//				IMethod method = methods[0];
//				final IHyperlink link;
//								
//				link = new ModelElementHyperlink(wordRegion, method,
//						new OpenAction(editor));
//				
//				return new IHyperlink[] {link};				
//				
//			}
//			
//			
//		} catch (Exception e) {			
//			Logger.logException(e);
//		}
		
		return null;
	}
}
