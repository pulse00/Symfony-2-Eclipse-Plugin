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

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;

import com.dubture.symfony.core.codeassist.contexts.RouteCompletionContext;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.index.model.Route;
import com.dubture.symfony.twig.codeassist.CompletionProposalFlag;
import com.dubture.twig.core.codeassist.ICompletionContext;
import com.dubture.twig.core.codeassist.ICompletionProposalFlag;
import com.dubture.twig.core.codeassist.ICompletionReporter;
import com.dubture.twig.core.codeassist.context.AbstractTwigCompletionContext;
import com.dubture.twig.core.codeassist.strategies.AbstractTwigCompletionStrategy;

/**
 * Completes route names inside a {@link RouteCompletionContext}
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
@SuppressWarnings({ "restriction" })
public class RouteCompletionStrategy extends AbstractTwigCompletionStrategy {

	public static int workaroundCount = 0;
	
	public RouteCompletionStrategy(ICompletionContext context) {
		super(context);
	}
	
	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		AbstractTwigCompletionContext context = (AbstractTwigCompletionContext) getContext();
		
		//TODO: this needs caching!!!
		List<Route> routes = SymfonyModelAccess.getDefault().findRoutes(context.getScriptProject());		
		ISourceRange range = getReplacementRange(context);
		
		SymfonyModelAccess model = SymfonyModelAccess.getDefault();
		
		String prefix = context.getPrefix();
		
		for (Route route : routes) {

			IType controller = model.findController(route.bundle, route.controller, context.getScriptProject());
			
			if (controller == null) {
				continue;
			}
			
			if (CodeAssistUtils.startsWithIgnoreCase(route.name, prefix)) {
				reporter.reportKeyword(route.name, range, new ICompletionProposalFlag[]{CompletionProposalFlag.ROUTE});
			}
		}	
	}
}
