/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.editor.hyperlink;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.internal.ui.editor.ModelElementHyperlink;
import org.eclipse.dltk.ui.actions.OpenAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.EntityAlias;
import com.dubture.symfony.core.model.SymfonyModelAccess;

/**
 * Detects EntityHyperlinks such as 'AcmeDemoBundle:SomeEntityClass'
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class EntityHyperlinkDetector extends StringHyperlinkDetector {

	private ISourceModule input;
	private PHPStructuredEditor editor;
	
	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {

		editor = org.eclipse.php.internal.ui.util.EditorUtility
				.getPHPEditor(textViewer);

		if (editor == null || region == null) {
			return null;
		}

		input = EditorUtility.getEditorInputModelElement(editor, false);
		
		if (input == null) {
			return null;
		}

		IDocument document = textViewer.getDocument();
		int offset = region.getOffset();

		try {
			
			IRegion wordRegion = findWord(document, offset);
			
			if (wordRegion == null)
				return null;

			String entity = document.get(wordRegion.getOffset(), wordRegion.getLength());
			
			if (!entity.contains(":"))
				return null;
			
			String[] parts = entity.split(":");
			
			if (parts.length != 2) {
				return null;
			}
			
			EntityAlias alias = new EntityAlias(entity);
			
			if (!alias.hasBundle())
				return null;
			
			IType type = SymfonyModelAccess.getDefault().findEntity(alias, input.getScriptProject());
			
			if (type == null)
				return null;
			
			IHyperlink link = new ModelElementHyperlink(wordRegion, type, new OpenAction(editor));
			
			return new IHyperlink[] { link };
			
		} catch (Exception e) {		
			Logger.logException(e);			
		}
				
		return null;
		
	}
}
