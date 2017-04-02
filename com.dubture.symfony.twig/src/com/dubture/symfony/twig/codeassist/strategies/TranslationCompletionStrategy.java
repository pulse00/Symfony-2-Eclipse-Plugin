/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.twig.codeassist.strategies;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;

import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.Translation;
import com.dubture.symfony.index.model.TransUnit;
import com.dubture.symfony.twig.codeassist.CompletionProposalFlag;
import com.dubture.symfony.twig.codeassist.context.TranslationCompletionContext;
import com.dubture.twig.core.codeassist.ICompletionContext;
import com.dubture.twig.core.codeassist.ICompletionProposalFlag;
import com.dubture.twig.core.codeassist.ICompletionReporter;
import com.dubture.twig.core.codeassist.strategies.AbstractTwigCompletionStrategy;

public class TranslationCompletionStrategy extends AbstractTwigCompletionStrategy {

	private TranslationCompletionContext tContext;

	public TranslationCompletionStrategy(ICompletionContext context) {
		super(context);

	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {

		tContext = (TranslationCompletionContext) getContext();
		IScriptProject project = tContext.getScriptProject();
		ISourceRange range = getReplacementRange(tContext);
		SymfonyModelAccess model = SymfonyModelAccess.getDefault();
		String prefix = tContext.getPrefix();

		List<Bundle> bundles = model.findBundles(project);
		List<TransUnit> units = model.findTranslations(project.getPath());

		for (TransUnit unit : units) {

			Bundle targetBundle = null;

			for (Bundle bundle : bundles) {
				if (unit.path.startsWith(bundle.getTranslationPath())) {
					targetBundle = bundle;
					break;
				}
			}

			if (targetBundle.getScriptProject() == null) {
				targetBundle.setProject(project);
			}

			if (targetBundle != null && StringUtils.startsWithIgnoreCase(unit.name, prefix)) {
				Translation trans = new Translation(targetBundle, unit);
				reporter.reportKeyword(unit.name, range,
						new ICompletionProposalFlag[] { CompletionProposalFlag.TRANSLATION });
			}
		}

	}

	@Override
	public ISourceRange getReplacementRange(ICompletionContext context) throws BadLocationException {
		return new SourceRange(tContext.getOffset(), tContext.getStatementEnd());

	}

}
