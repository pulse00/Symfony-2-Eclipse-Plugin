package com.dubture.symfony.twig.codeassist.context;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.php.internal.core.util.text.TextSequenceUtilities;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.text.XMLStructuredDocumentRegion;

import com.dubture.twig.core.documentModel.parser.TwigRegionContext;

@SuppressWarnings("restriction")
public class TranslationCompletionContext extends AbstractCompletionContext {
	
	private XMLStructuredDocumentRegion textRegion;
	
	private int offset;
	
	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		super.isValid(sourceModule, offset, requestor);
		
		IStructuredDocument doc = getDocument();		
		ITextRegion region = doc.getRegionAtCharacterOffset(offset);
		
		if (region == null || region.getType() != "XML_CONTENT")
			return false;
		
		ITextRegion previous = doc.getRegionAtCharacterOffset(region.getStart() -1);		

		if (previous != null && previous.getType().equals(TwigRegionContext.TWIG_CONTENT) && 
				previous instanceof XMLStructuredDocumentRegion && region instanceof XMLStructuredDocumentRegion) {
				
				textRegion = (XMLStructuredDocumentRegion) region;
				this.offset = offset;
				TextSequence seq = getStatementText();
				
				
				
				if (seq == null) {
					System.err.println("is null");
				}
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

		TextSequence textSequence = TextSequenceUtilities
				.createTextSequence(textRegion, offset, textRegion.getFullText().length());

		System.err.println(textSequence);
		return textSequence;
		
	}
	
	
}
