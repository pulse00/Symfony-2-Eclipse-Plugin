/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist.strategies;


import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.GlobalElementStrategy;
import org.eclipse.php.internal.core.typeinference.FakeField;

import com.dubture.symfony.core.codeassist.contexts.TemplateVariableContext;
import com.dubture.symfony.core.index.SymfonyElementResolver.TemplateField;

/**
 * 
 * Completes variables in templates declared in 
 * Symfony2 controllers.
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings({ "restriction" })
public class TemplateVariableStrategy extends GlobalElementStrategy {

	public TemplateVariableStrategy(ICompletionContext context) {
		super(context);

	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {

		TemplateVariableContext ctxt = (TemplateVariableContext) getContext();
		ISourceRange range = getReplacementRange(getContext());
		String viewPath = ctxt.getViewPath();
		
		for(TemplateField element : ctxt.getVariables()) {

			if (viewPath.equals(element.getViewPath())) {
				reporter.reportField(new FakeField(element, element.getElementName(), Modifiers.AccPublic), "", range, false);
			}
		}
	}
}
