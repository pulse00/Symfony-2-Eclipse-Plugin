/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.contentassist;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.text.completion.MemberProposalInfo;

import com.dubture.symfony.core.model.RouteSource;
import com.dubture.symfony.index.model.Route;
import com.dubture.symfony.ui.utils.HTMLUtils;

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
			return HTMLUtils.route2Html(route);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
