package com.dubture.symfony.ui.contentassist;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.text.completion.MemberProposalInfo;

import com.dubture.symfony.core.model.Template;
import com.dubture.symfony.ui.utils.HTMLUtils;

public class TemplateProposalInfo extends MemberProposalInfo {

	
	public TemplateProposalInfo(IScriptProject project,
			CompletionProposal proposal) {
		super(project, proposal);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getInfo(IProgressMonitor monitor) {
 
		try {
			
			return HTMLUtils.template2Html((Template) getModelElement());			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}		

}
