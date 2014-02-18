/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.contentassist;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalCollector;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProposalComputer;


/**
 *
 */
@SuppressWarnings("restriction")
public class SymfonyCompletionProposalComputer extends
		PHPCompletionProposalComputer {


	@Override
	protected ScriptCompletionProposalCollector createCollector(
			ScriptContentAssistInvocationContext context) {

		return new SymfonyCompletionProposalCollector(context.getDocument(), context.getSourceModule(), true);

	}

	@Override
	protected List<ICompletionProposal> computeTemplateCompletionProposals(int offset, ScriptContentAssistInvocationContext context, IProgressMonitor monitor) {
		return Collections.emptyList();
	}
}
