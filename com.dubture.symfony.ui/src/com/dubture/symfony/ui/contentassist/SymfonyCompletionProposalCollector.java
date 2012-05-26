/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.contentassist;

import java.awt.color.CMMException;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.dltk.ui.text.completion.ProposalInfo;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProposalCollector;
import org.eclipse.swt.graphics.Image;

import com.dubture.symfony.core.builder.SymfonyNature;

/**
 * The {@link SymfonyCompletionProposalCollector} is 
 * responsible for creating custom proposals for
 * Symfony elements like Routes and Services.
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings("restriction")
public class SymfonyCompletionProposalCollector extends
		PHPCompletionProposalCollector {

	private SymfonyCompletionProposalLabelProvider labelProvider;
	
	public SymfonyCompletionProposalCollector(IDocument document,
			ISourceModule cu, boolean explicit) {
		super(document, cu, explicit);

	}
	
	
	@Override
	public CompletionProposalLabelProvider getLabelProvider() {

		if (labelProvider == null)
			labelProvider = new SymfonyCompletionProposalLabelProvider();
		
		return labelProvider;
		
	}
	
	
	@Override
	protected IScriptCompletionProposal createScriptCompletionProposal(
			CompletionProposal proposal) {

		IModelElement element = proposal.getModelElement();

		if (element == null) {
			return null;
		}
		
		ProposalInfo proposalInfo = CompletionProposalProvider.createScriptCompletionProposal(proposal, this);
		ImageDescriptor imageDescriptor = CompletionProposalProvider.createTypeImageDescriptor(proposal);
		
		if (proposalInfo != null) {
		    ScriptCompletionProposal symfonyProposal = generateSymfonyProposal(proposal, imageDescriptor);
		    symfonyProposal.setProposalInfo(proposalInfo);
		    symfonyProposal.setRelevance(computeRelevance(proposal));
		    return symfonyProposal;
		}
		
		// don't complete anything else or we'll get duplicate entries
		return null;
	}
	
	private ScriptCompletionProposal generateSymfonyProposal(CompletionProposal typeProposal, ImageDescriptor descriptor) {
		
		String completion = new String(typeProposal.getCompletion());
		int replaceStart = typeProposal.getReplaceStart();
		int length = getLength(typeProposal);
		Image image = getImage(descriptor);

		String displayString = ((SymfonyCompletionProposalLabelProvider) getLabelProvider())
				.createTypeProposalLabel(typeProposal);

		ScriptCompletionProposal scriptProposal = new EmptyCompletionProposal(completion, replaceStart, length, image, displayString, 0);

		return scriptProposal;
		
	}
	
	@Override
	protected String getNatureId() { 
		return SymfonyNature.NATURE_ID;
	}
}
