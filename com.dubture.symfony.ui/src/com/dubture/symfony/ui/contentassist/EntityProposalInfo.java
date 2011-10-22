package com.dubture.symfony.ui.contentassist;

import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.text.completion.MemberProposalInfo;

import com.dubture.symfony.core.model.Translation;

public class EntityProposalInfo extends MemberProposalInfo {

	public EntityProposalInfo(IScriptProject project,
			CompletionProposal proposal) {
		super(project, proposal);

	}
	
	@Override
	public String getInfo(IProgressMonitor monitor) {
 
		try {
			
			Translation translation = (Translation) getModelElement();			
			return translation.getElementName();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}	
	

}
