package com.dubture.symfony.ui.quickassist;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension2;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.resources.SymfonyMarker;
import com.dubture.symfony.ui.DialogUtils;

public class CreateClassCompletionProposal implements ICompletionProposal,
		ICompletionProposalExtension2 {

	private String className;
	private IMarker marker;
	
	public CreateClassCompletionProposal(IMarker marker) {
		try {
			className = (String) marker.getAttribute(SymfonyMarker.SERVICE_CLASS);
			this.marker = marker;
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}

	@Override
	public void apply(ITextViewer viewer, char trigger, int stateMask,
			int offset) {
		
		if (marker == null) {
			return;
		}
		
		DialogUtils.launchClassWizardFromMarker(marker);

	}

	@Override
	public void selected(ITextViewer viewer, boolean smartToggle) {

	}

	@Override
	public void unselected(ITextViewer viewer) {

	}

	@Override
	public boolean validate(IDocument document, int offset, DocumentEvent event) {
		return false;
	}

	@Override
	public void apply(IDocument document) {
		
	}

	@Override
	public Point getSelection(IDocument document) {
		return null;
	}

	@Override
	public String getAdditionalProposalInfo() {
		return "Launch the New Class Wizard and create a new class '" + className + "'";
	}

	@Override
	public String getDisplayString() {
		return "Create new class ";
	}

	@Override
	public Image getImage() {
		return DLTKUIPlugin.getImageDescriptorRegistry().get(DLTKPluginImages.DESC_OBJS_CLASS);
	}

	@Override
	public IContextInformation getContextInformation() {
		return null;
	}
}
