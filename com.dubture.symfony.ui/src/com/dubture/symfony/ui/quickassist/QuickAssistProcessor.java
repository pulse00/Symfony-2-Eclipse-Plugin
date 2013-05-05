/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.quickassist;

import java.util.Iterator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.quickassist.IQuickAssistInvocationContext;
import org.eclipse.jface.text.quickassist.IQuickAssistProcessor;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.wst.sse.ui.internal.StructuredMarkerAnnotation;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.resources.SymfonyMarker;

/**
 * 
 * Provides a "Ctrl+1" QuickAssist proposal in the XML SourceViewer.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * 
 */
@SuppressWarnings("restriction")
public class QuickAssistProcessor implements IQuickAssistProcessor {

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public boolean canFix(Annotation annotation) {
		return false;
	}

	@Override
	public boolean canAssist(IQuickAssistInvocationContext invocationContext) {
		return true;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ICompletionProposal[] computeQuickAssistProposals(IQuickAssistInvocationContext invocationContext) {

		if (!(invocationContext.getSourceViewer() instanceof StructuredTextViewer)) {
			return null;
		}
		
		StructuredTextViewer viewer = (StructuredTextViewer) invocationContext.getSourceViewer();

		int line = -1;

		try {
			line = viewer.getDocument().getLineOfOffset(invocationContext.getOffset());
			// the document counts 0 indexed, the marker 1 indexed
			line += 1;
		} catch (BadLocationException e1) {
			Logger.logException(e1);
		}

		if (viewer.getAnnotationModel() instanceof StructuredResourceMarkerAnnotationModel) {
			
			StructuredResourceMarkerAnnotationModel model = (StructuredResourceMarkerAnnotationModel) viewer.getAnnotationModel();
			Iterator iterator = model.getAnnotationIterator();

			while (iterator.hasNext()) {
				Object next = iterator.next();

				if (next instanceof StructuredMarkerAnnotation) {
					StructuredMarkerAnnotation annotation = (StructuredMarkerAnnotation) next;

					try {
						IMarker marker = annotation.getMarker();
						if (marker != null && SymfonyMarker.MISSING_SERVICE_CLASS.equals(marker.getType())) {
							int markerLine = MarkerUtilities.getLineNumber(marker);
							if (markerLine == line) {
								return new ICompletionProposal[] { new CreateClassCompletionProposal(marker) };
							}
						}
					} catch (CoreException e) {
						Logger.logException(e);
					}
				}
			}
		}
		
		return null;
	}
}
