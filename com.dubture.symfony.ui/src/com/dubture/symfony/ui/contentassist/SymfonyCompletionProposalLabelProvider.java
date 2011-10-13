package com.dubture.symfony.ui.contentassist;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProposalLabelProvider;

import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.Controller;
import com.dubture.symfony.core.model.RouteSource;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.Template;
import com.dubture.symfony.ui.SymfonyPluginImages;


/**
 * 
 * A LabelProvider for Symfony completion proposals.
 * 
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings("restriction")
public class SymfonyCompletionProposalLabelProvider extends
		PHPCompletionProposalLabelProvider {

	
	@Override
	public ImageDescriptor createTypeImageDescriptor(CompletionProposal proposal) {

		IModelElement element = proposal.getModelElement();
		
		
		if (element.getClass() == RouteSource.class) {
			
			return SymfonyPluginImages.DESC_OBJS_ROUTE;

		} else if (element.getClass() == Service.class) {
			
			return SymfonyPluginImages.DESC_OBJS_SERVICE;
			
		} else if (element.getClass() == Bundle.class) {
			
			return SymfonyPluginImages.DESC_OBJS_BUNDLE;
			
		} else if (element.getClass() == Controller.class) {
			
			return SymfonyPluginImages.DESC_OBJS_CONTROLLER;
			
		} else if (element.getClass() == Template.class) {
			
			return SymfonyPluginImages.DESC_OBJS_TEMPLATE;
		}
		
		return super.createTypeImageDescriptor(proposal);
	}
	
	
	
	@Override
	public String createTypeProposalLabel(CompletionProposal typeProposal) {

		
		// show the id of the service in the proposal popup, so
		// the UsestatementInjector can inject to correct fully qualified name
		if (typeProposal.getModelElement() instanceof Service) {
			
			Service service = (Service) typeProposal.getModelElement();
						
			if (service.getId() != null)
				return service.getId();			
			
		}
		
		return super.createTypeProposalLabel(typeProposal);
		
	}
}