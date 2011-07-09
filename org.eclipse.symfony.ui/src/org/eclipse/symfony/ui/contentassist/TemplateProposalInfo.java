package org.eclipse.symfony.ui.contentassist;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.text.completion.MemberProposalInfo;
import org.eclipse.symfony.core.model.Template;
import org.eclipse.symfony.ui.utils.HTMLUtils;

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
