package com.dubture.symfony.ui.editor.hyperlink;

import java.util.List;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.ui.editor.ModelElementHyperlink;
import org.eclipse.dltk.ui.actions.OpenAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.EditorUtility;

import com.dubture.symfony.core.index.SymfonyElementResolver.TemplateField;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.SymfonyModelAccess;

/**
 * 
 * Links variables in templates back to their declaration in the controller.
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class TemplateVariableHyperlinkDetector extends StringHyperlinkDetector {
	
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

			String literal = document.get(wordRegion.getOffset(), wordRegion.getLength());
			
			if (literal == null)
				return null;
			
			SymfonyModelAccess model = SymfonyModelAccess.getDefault();			
			List<TemplateField> tvars = model.findTemplateVariables(sourceModule, literal);			
			IHyperlink[] links = new IHyperlink[tvars.size()];
			
			int i =0;
			for (TemplateField field : tvars) {	
				
				IMethod method = field.getMethod();
				IDLTKSearchScope scope = SearchEngine.createSearchScope(field.getSourceModule());
				IMethod[] methods = model.findMethods(method.getElementName(), MatchRule.EXACT, 0, 0, scope, null);
				
				if (methods.length == 1) {										
					links[i++] = new ModelElementHyperlink(wordRegion, methods[0], new OpenAction(editor));;
				}
			}
			
			if (links.length > 0) {
				return links;
			}
			
			
		} catch (Exception e) {			
			Logger.logException(e);
		}
		
		return null;
	
	}

}
