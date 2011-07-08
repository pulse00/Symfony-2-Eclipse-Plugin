package org.eclipse.symfony.ui.contentassist;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.text.completion.MemberProposalInfo;
import org.eclipse.symfony.core.model.RouteSource;
import org.eclipse.symfony.index.dao.Route;

/**
 * 
 * The ProposalInfo for Symfony routes. Displays
 * the Bundle/Controller/Action combination in the
 * completion popup of route names. 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public class RouteProposalInfo extends MemberProposalInfo {
	
	public RouteProposalInfo(IScriptProject project, CompletionProposal proposal) {
		super(project, proposal);
	}
	

	@Override
	public String getInfo(IProgressMonitor monitor) {
 
		try {
			
			RouteSource routeSource = (RouteSource) getModelElement();
			Route route = routeSource.getRoute();
			 
			StringBuilder info = new StringBuilder();
			
			info.append("<b>Bundle:</b> ");
			info.append(route.bundle);
			info.append("<br/><b>Controller:</b> ");
			info.append(route.controller);
			info.append("<br/><b>Action:</b> ");
			info.append(route.action);
			return info.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
