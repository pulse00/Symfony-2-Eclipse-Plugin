/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.twig.codeassist.context;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.php.internal.core.util.text.TextSequenceUtilities;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.text.XMLStructuredDocumentRegion;

import com.dubture.twig.core.codeassist.context.AbstractTwigCompletionContext;
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
public class TranslationCompletionContext extends AbstractTwigCompletionContext{
	
	private XMLStructuredDocumentRegion textRegion;
	
	private int offset;
	
	@Override
	public boolean isValid(IDocument template, int offset, IProgressMonitor monitor) {
		
		if (!super.isValid(template, offset, monitor)) {
			return false;
		}
		
		IStructuredDocument doc = getDocument();
		
		ITextRegion region = doc.getRegionAtCharacterOffset(offset);
		
		if (region == null ) {
			return false;
		}
		
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
