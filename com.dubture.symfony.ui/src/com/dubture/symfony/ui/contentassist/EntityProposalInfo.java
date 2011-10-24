package com.dubture.symfony.ui.contentassist;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.text.completion.MemberProposalInfo;

public class EntityProposalInfo extends MemberProposalInfo {

	public EntityProposalInfo(IScriptProject project,
			CompletionProposal proposal) {
		super(project, proposal);

	}
	
	

}
