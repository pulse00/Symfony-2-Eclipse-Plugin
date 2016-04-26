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
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;

import com.dubture.symfony.twig.codeassist.context.TranslationCompletionContext;
import com.dubture.twig.core.codeassist.ICompletionContext;
import com.dubture.twig.core.codeassist.ICompletionReporter;
import com.dubture.twig.core.codeassist.strategies.AbstractTwigCompletionStrategy;

@SuppressWarnings({ "restriction" })
public class TranslationCompletionStrategy extends AbstractTwigCompletionStrategy {

	private TranslationCompletionContext tContext;
	
	public TranslationCompletionStrategy(ICompletionContext context) {
		super(context);

	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {

		tContext = (TranslationCompletionContext) getContext();
		IScriptProject project = tContext.getSourceModule().getScriptProject();
//		ISourceRange range = getReplacementRange(tContext);		
//		String prefix = tContext.getPrefix();
//		CodeassistUtils.reportTranslations(reporter, prefix, range, project );		

	}
	
	
	@Override
	public ISourceRange getReplacementRange(ICompletionContext context)
			throws BadLocationException {
		
		return new SourceRange(tContext.getOffset(), tContext.getStatementEnd());

	}

}
