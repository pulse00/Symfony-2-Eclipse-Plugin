package com.dubture.symfony.ui.contentassist;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.dltk.ui.text.completion.MemberProposalInfo;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProposalCollector;
import org.eclipse.swt.graphics.Image;

import com.dubture.doctrine.core.model.Entity;
import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.Controller;
import com.dubture.symfony.core.model.RouteSource;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.Template;
import com.dubture.symfony.core.model.Translation;

/**
 * The {@link SymfonyCompletionProposalCollector} is 
 * responsible for creating custom proposals for
 * Symfony elements like Routes and Services.
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings("restriction")
public class SymfonyCompletionProposalCollector extends
		PHPCompletionProposalCollector {

	private SymfonyCompletionProposalLabelProvider labelProvider;
	
	public SymfonyCompletionProposalCollector(IDocument document,
			ISourceModule cu, boolean explicit) {
		super(document, cu, explicit);

	}
	
	
	@Override
	public CompletionProposalLabelProvider getLabelProvider() {

		if (labelProvider == null)
			labelProvider = new SymfonyCompletionProposalLabelProvider();
		
		return labelProvider;
		
	}
	
	
	@Override
	protected IScriptCompletionProposal createScriptCompletionProposal(
			CompletionProposal proposal) {

		IModelElement element = proposal.getModelElement();

		if (element == null) {
			return null;
		}
		
		// creates a proposal for a route
		if (element.getClass() == RouteSource.class) {
			return createRouteProposal(proposal);
		} else if (element.getClass() == Service.class) {			
			return createServiceProposal(proposal);
		} else if (element.getClass() == Bundle.class) {
			return createBundleProposal(proposal);
		} else if (element.getClass() == Controller.class) {
			return createControllerProposal(proposal);
		} else if (element.getClass() == Template.class) {			
			return createTemplateProposal(proposal);			
		} else if (element.getClass() == Entity.class) {
			return createEntityProposal(proposal);
		} else if (element.getClass() == Translation.class) {
			return createTranslationProposal(proposal);
		}
		
		// don't complete anything else or we'll get duplicate entries
		return null;
	}
	
	private IScriptCompletionProposal createTranslationProposal(CompletionProposal proposal) {

		ScriptCompletionProposal scriptProposal = generateSymfonyProposal(proposal);
		scriptProposal.setRelevance(computeRelevance(proposal));
		
		scriptProposal.setProposalInfo(new TranslationProposalInfo(getSourceModule().getScriptProject(), proposal));
		return scriptProposal;								
	}


	private IScriptCompletionProposal createTemplateProposal(
			CompletionProposal proposal) {

		ScriptCompletionProposal scriptProposal = generateSymfonyProposal(proposal);
		scriptProposal.setRelevance(computeRelevance(proposal));
		scriptProposal.setProposalInfo(new TemplateProposalInfo(getSourceModule().getScriptProject(), proposal));
		return scriptProposal;								

	}

	private IScriptCompletionProposal createEntityProposal(
			CompletionProposal proposal) {
		
		ScriptCompletionProposal scriptProposal = generateSymfonyProposal(proposal);
		scriptProposal.setRelevance(computeRelevance(proposal));
		
		scriptProposal.setProposalInfo(new EntityProposalInfo(getSourceModule().getScriptProject(), proposal));
		return scriptProposal;								
		
	}

	private IScriptCompletionProposal createControllerProposal(
			CompletionProposal proposal) {
		
		ScriptCompletionProposal scriptProposal = generateSymfonyProposal(proposal);
		scriptProposal.setRelevance(computeRelevance(proposal));
		scriptProposal.setProposalInfo(new ControllerProposalInfo(getSourceModule().getScriptProject(), proposal));
		return scriptProposal;								
		
	}


	private ScriptCompletionProposal generateSymfonyProposal(CompletionProposal typeProposal) {
		
		String completion = new String(typeProposal.getCompletion());
		int replaceStart = typeProposal.getReplaceStart();
		int length = getLength(typeProposal);
		Image image = getImage(((SymfonyCompletionProposalLabelProvider) getLabelProvider())
				.createTypeImageDescriptor(typeProposal));

		String displayString = ((SymfonyCompletionProposalLabelProvider) getLabelProvider())
				.createTypeProposalLabel(typeProposal);

		ScriptCompletionProposal scriptProposal = new EmptyCompletionProposal(completion, replaceStart, length, image, displayString, 0);

		return scriptProposal;
		
	}
	
	
	
	private IScriptCompletionProposal createBundleProposal(
			CompletionProposal typeProposal) {

		ScriptCompletionProposal scriptProposal = generateSymfonyProposal(typeProposal);
		scriptProposal.setRelevance(computeRelevance(typeProposal));
		scriptProposal.setProposalInfo(new BundleProposalInfo(getSourceModule().getScriptProject(), typeProposal));
		return scriptProposal;				

	}


	private IScriptCompletionProposal createServiceProposal(
			CompletionProposal typeProposal) {

		ScriptCompletionProposal scriptProposal = generateSymfonyProposal(typeProposal);

		scriptProposal.setRelevance(computeRelevance(typeProposal));
		scriptProposal.setProposalInfo(new ServiceProposalInfo(getSourceModule().getScriptProject(), typeProposal));
		return scriptProposal;		
	}


	private IScriptCompletionProposal createRouteProposal(
			final CompletionProposal typeProposal) {

	
		ScriptCompletionProposal scriptProposal = generateSymfonyProposal(typeProposal);

		scriptProposal.setRelevance(computeRelevance(typeProposal));
		scriptProposal.setProposalInfo(new RouteProposalInfo(getSourceModule().getScriptProject(), typeProposal));
		return scriptProposal;		
	}


	@Override
	protected String getNatureId() { 
		return SymfonyNature.NATURE_ID;
	}
}