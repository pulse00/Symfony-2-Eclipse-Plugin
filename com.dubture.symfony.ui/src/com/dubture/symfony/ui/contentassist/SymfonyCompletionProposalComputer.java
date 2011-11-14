/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.contentassist;

import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalCollector;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProposalComputer;


/**
 * 
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings("restriction")
public class SymfonyCompletionProposalComputer extends
		PHPCompletionProposalComputer {

	
	@Override
	protected ScriptCompletionProposalCollector createCollector(
			ScriptContentAssistInvocationContext context) {
		
		
		return new SymfonyCompletionProposalCollector(context.getDocument(), context.getSourceModule(), true);
		
//		try {
//			if (context.getSourceModule().getUnderlyingResource().getFileExtension().equals("php"))
//				return new SymfonyCompletionProposalCollector(context.getDocument(), context.getSourceModule(), true);
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//		
//		return super.createCollector(context);
	}
}
