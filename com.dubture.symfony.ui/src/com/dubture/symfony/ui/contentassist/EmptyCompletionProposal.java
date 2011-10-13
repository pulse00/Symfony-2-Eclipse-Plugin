package com.dubture.symfony.ui.contentassist;

import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProposal;
import org.eclipse.swt.graphics.Image;

import com.dubture.symfony.core.model.Service;


/**
 * A {@link ScriptCompletionProposal} for Symfony routes.
 * 
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings("restriction")
public class EmptyCompletionProposal extends PHPCompletionProposal {

	public EmptyCompletionProposal(String replacementString,
			int replacementOffset, int replacementLength, Image image,
			String displayString, int relevance) {
		super(replacementString, replacementOffset, replacementLength, image,
				displayString, relevance);

	}
		
	
	@Override
	public String getAdditionalProposalInfo() {

		return "";	
	
	}
	
	
	@Override
	public String getReplacementString() {

		if (getModelElement() instanceof Service) {
			
			Service service = (Service) getModelElement();
			
			if (service.getId() != null)
				return service.getId();			
			
		}
		
		return super.getReplacementString();
	}
	
}
