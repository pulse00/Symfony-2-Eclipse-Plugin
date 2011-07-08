package org.eclipse.symfony.ui.contentassist;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProposalLabelProvider;
import org.eclipse.symfony.core.model.RouteSource;
import org.eclipse.symfony.core.model.Service;
import org.eclipse.symfony.ui.SymfonyPluginImages;


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
		}
		
		return super.createTypeImageDescriptor(proposal);
	}
}