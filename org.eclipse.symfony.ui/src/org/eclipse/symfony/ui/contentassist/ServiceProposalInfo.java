package org.eclipse.symfony.ui.contentassist;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.text.completion.MemberProposalInfo;
import org.eclipse.symfony.core.model.Service;

public class ServiceProposalInfo extends MemberProposalInfo {

	public ServiceProposalInfo(IScriptProject project,
			CompletionProposal proposal) {
		super(project, proposal);

	}
	
	@Override
	public String getInfo(IProgressMonitor monitor) {
 
		try {
			
			Service service = (Service) getModelElement();
			
			 
			StringBuilder info = new StringBuilder();
			
			
			if (service.getBundle() != null) {
				info.append("<b>Bundle:</b> ");
				info.append(service.getBundle().getFullyQualifiedName());
			}
			
			info.append("<br/><b>Controller:</b> ");
			info.append(service.getClassName());

			return info.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}	

}
