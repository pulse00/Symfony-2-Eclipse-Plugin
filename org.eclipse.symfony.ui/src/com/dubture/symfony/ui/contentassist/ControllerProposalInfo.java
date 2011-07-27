package com.dubture.symfony.ui.contentassist;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.text.completion.MemberProposalInfo;

import com.dubture.symfony.core.model.Controller;
import com.dubture.symfony.ui.utils.HTMLUtils;

/**
 * 
 * Proposalinfo for controllers.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class ControllerProposalInfo extends MemberProposalInfo {

	public ControllerProposalInfo(IScriptProject project,
			CompletionProposal proposal) {
		super(project, proposal);

	}
	
	@Override
	public String getInfo(IProgressMonitor monitor) {
 
		try {
			
			return HTMLUtils.controller2Html((Controller) getModelElement());			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}		

}
