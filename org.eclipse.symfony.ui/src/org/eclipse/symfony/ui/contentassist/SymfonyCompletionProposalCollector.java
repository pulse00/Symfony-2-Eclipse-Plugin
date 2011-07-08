package org.eclipse.symfony.ui.contentassist;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProposalCollector;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProposalLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.symfony.core.builder.SymfonyNature;
import org.eclipse.symfony.core.model.RouteSource;

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

		// creates a proposal for a route
		if (proposal.getModelElement().getClass() == RouteSource.class) {			
			return createRouteProposal(proposal);
		}
		
		return super.createScriptCompletionProposal(proposal);
	}
	
	
	
	private IScriptCompletionProposal createRouteProposal(
			final CompletionProposal typeProposal) {

		String completion = new String(typeProposal.getCompletion());
		int replaceStart = typeProposal.getReplaceStart();
		int length = getLength(typeProposal);
		Image image = getImage(((SymfonyCompletionProposalLabelProvider) getLabelProvider())
				.createTypeImageDescriptor(typeProposal));

		String displayString = ((SymfonyCompletionProposalLabelProvider) getLabelProvider())
				.createTypeProposalLabel(typeProposal);

		ScriptCompletionProposal scriptProposal = new RouteCompletionProposal(completion, replaceStart, length, image, displayString, 0);

		scriptProposal.setRelevance(computeRelevance(typeProposal));
		scriptProposal.setProposalInfo(new RouteProposalInfo(getSourceModule().getScriptProject(), typeProposal));
		return scriptProposal;		
	}


	@Override
	protected String getNatureId() { 
		return SymfonyNature.NATURE_ID;
	}
}