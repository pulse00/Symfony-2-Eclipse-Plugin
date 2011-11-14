/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist.strategies;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.MethodParameterKeywordStrategy;

import com.dubture.symfony.core.codeassist.CodeassistUtils;
import com.dubture.symfony.core.codeassist.contexts.TransUnitCompletionContext;

/**
 * 
 * Strategy to complete Symfony translations. 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class TransUnitCompletionStrategy extends MethodParameterKeywordStrategy {

	public TransUnitCompletionStrategy(ICompletionContext context) {
		super(context);
		
	}
	
	
	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {

		TransUnitCompletionContext context = (TransUnitCompletionContext) getContext();
		CompletionRequestor req = context.getCompletionRequestor();
		
		
		if (!req.getClass().getName().contains("Symfony")) {
			return;			
		}
		
		IScriptProject project = context.getSourceModule().getScriptProject();
		SourceRange range = getReplacementRange(context);		
		String prefix = context.getPrefix();
		
		CodeassistUtils.reportTranslations(reporter, prefix, range, project );		

	}
}
