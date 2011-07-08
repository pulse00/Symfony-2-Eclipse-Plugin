package org.eclipse.symfony.ui;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposalFactory;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalCollector;

public class SymfonyCompletionProposalFactory implements
		IScriptCompletionProposalFactory {

	public SymfonyCompletionProposalFactory() {

	}

	@Override
	public IScriptCompletionProposal create(
			ScriptCompletionProposalCollector collector,
			CompletionProposal proposal) {

		return null;
	}

}
