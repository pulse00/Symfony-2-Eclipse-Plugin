package org.eclipse.symfony.ui.contentassist;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ui.text.completion.MemberProposalInfo;
import org.eclipse.symfony.core.log.Logger;
import org.eclipse.symfony.core.model.Service;
import org.eclipse.symfony.ui.utils.HTMLUtils;

/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class ServiceProposalInfo extends MemberProposalInfo {

	public ServiceProposalInfo(IScriptProject project,
			CompletionProposal proposal) {
		super(project, proposal);

	}
	
	@Override
	public String getInfo(IProgressMonitor monitor) {
 
		try {
			return HTMLUtils.service2Html((Service) getModelElement());
		} catch (ModelException e) {
			Logger.logException(e);
			return "";
		}
	}	
}
