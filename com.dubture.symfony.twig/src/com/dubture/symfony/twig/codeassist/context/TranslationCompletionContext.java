/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.twig.codeassist.context;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.php.internal.core.util.text.TextSequenceUtilities;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.text.XMLStructuredDocumentRegion;

import com.dubture.symfony.twig.log.Logger;
import com.dubture.twig.core.documentModel.parser.TwigRegionContext;
import com.dubture.twig.core.util.TwigModelUtils;

/**
 * 
 * Translation context for twig templates.
 * 
 * <pre>
 * 
 *   {% trans %} | <--- completes translations  
 * 
 * </pre>
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class TranslationCompletionContext extends AbstractCompletionContext {
	
	private XMLStructuredDocumentRegion textRegion;
	
	private int offset;
	
	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

//		try {
//		    if (TwigModelUtils.isTwigTemplate(sourceModule.getUnderlyingResource().getName()) == false) {
//		        return false;
//		    }
//		} catch (ModelException e) {
//			Logger.logException(e);
//			return false;
//		}

		super.isValid(sourceModule, offset, requestor);
		
        if (!requestor.getClass().getName().contains("Twig")) {
            return false;
        }
		
		IStructuredDocument doc = getDocument();		
		ITextRegion region = doc.getRegionAtCharacterOffset(offset);
		
		if (region == null || region.getType() != "XML_CONTENT")
			return false;
		
		ITextRegion previous = doc.getRegionAtCharacterOffset(region.getStart() -1);		

		if (previous != null && previous.getType().equals(TwigRegionContext.TWIG_CONTENT) && 
				previous instanceof XMLStructuredDocumentRegion && region instanceof XMLStructuredDocumentRegion) {
				
				textRegion = (XMLStructuredDocumentRegion) region;
				this.offset = offset;
				XMLStructuredDocumentRegion xmlRegion = (XMLStructuredDocumentRegion) previous;
				
				if (xmlRegion.getText().contains("trans")) {
					return true;
				}				
			
		}
		return false;
	}
	
	public int getStatementEnd() {
		
		return textRegion.getEnd();
	}
	
	
	@Override
	public TextSequence getStatementText() {

		if (textRegion == null)
			return null;
		
		TextSequence textSequence = TextSequenceUtilities
				.createTextSequence(textRegion, offset, textRegion.getFullText().length());

		return textSequence;
		
	}	
}
