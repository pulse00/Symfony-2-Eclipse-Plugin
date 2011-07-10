package org.eclipse.symfony.core.codeassist;

import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.PHPSelectionEngine;
import org.eclipse.symfony.core.model.SymfonyModelAccess;
import org.eclipse.symfony.core.model.ViewPath;
import org.eclipse.symfony.core.util.text.SymfonyTextSequenceUtilities;


/**
 * 
 * 
 * The {@link SymfonySelectionEngine} helps DLTK to identify
 * symfony model elements for actions like "Open Declaration" - F3 and
 * Hyperlinking.
 * 
 * 
 * @see http://wiki.eclipse.org/DLTK_IDE_Guide:Step_3._Towards_an_IDE#Open_declaration_feature.
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonySelectionEngine extends PHPSelectionEngine {
	
	private static final IModelElement[] NONE = {};	
	
	@Override
	public IModelElement[] select(IModuleSource sourceUnit, int offset, int end) {

		IModelElement[] result = super.select(sourceUnit, offset, end);
		
		if (result.length > 0) {
			return result;
		}
		
		ISourceModule sourceModule = (ISourceModule) sourceUnit
				.getModelElement();		
		
		String content = sourceUnit.getSourceContents();
		
		int startOffset = SymfonyTextSequenceUtilities.readViewPathStartIndex(content, offset);
		int endOffset = SymfonyTextSequenceUtilities.readViewPathEndIndex(content, offset);
		
		if (startOffset >= 0 && endOffset != 0 && (endOffset > startOffset)) {
			
			// try to resolve a viewepath first
			String literal = content.substring(startOffset, endOffset);
			ViewPath viewPath = new ViewPath(literal);
			
			if (viewPath.isValid()) {
				
				IModelElement template = SymfonyModelAccess.getDefault().findTemplate(viewPath, sourceModule.getScriptProject());
				
				if (template != null) {
					return new IModelElement[] { template };
				}				
			}			
		}
		
		return NONE;
	}
}
