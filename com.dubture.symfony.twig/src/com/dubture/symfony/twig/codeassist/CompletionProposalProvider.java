package com.dubture.symfony.twig.codeassist;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.ui.text.completion.ProposalInfo;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalCollector;
import org.eclipse.jface.resource.ImageDescriptor;

import com.dubture.twig.ui.editor.contentassist.ICompletionProposalProvider;

public class CompletionProposalProvider
{
    public ProposalInfo createScriptCompletionProposal(CompletionProposal proposal, ScriptCompletionProposalCollector collector)
    {
        return com.dubture.symfony.ui.contentassist.CompletionProposalProvider.createScriptCompletionProposal(proposal, collector);
    }

    public ImageDescriptor createTypeImageDescriptor(CompletionProposal proposal)
    {
        return com.dubture.symfony.ui.contentassist.CompletionProposalProvider.createTypeImageDescriptor(proposal);
    }

}
