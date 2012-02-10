/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.twig.codeassist.strategies;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.AbstractCompletionStrategy;

import com.dubture.symfony.core.codeassist.CodeassistUtils;
import com.dubture.symfony.twig.codeassist.context.TranslationCompletionContext;

@SuppressWarnings({ "restriction", "deprecation" })
public class TranslationCompletionStrategy extends AbstractCompletionStrategy {

	private TranslationCompletionContext tContext;
	
	public TranslationCompletionStrategy(ICompletionContext context) {
		super(context);

	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {

		tContext = (TranslationCompletionContext) getContext();
		IScriptProject project = tContext.getSourceModule().getScriptProject();
		SourceRange range = getReplacementRange(tContext);		
		String prefix = tContext.getPrefix();
		CodeassistUtils.reportTranslations(reporter, prefix, range, project );		

	}
	
	
	@Override
	public SourceRange getReplacementRange(ICompletionContext context)
			throws BadLocationException {
		
		return new SourceRange(tContext.getOffset(), tContext.getStatementEnd());

	}

}
