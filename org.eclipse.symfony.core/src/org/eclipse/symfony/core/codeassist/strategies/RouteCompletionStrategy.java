package org.eclipse.symfony.core.codeassist.strategies;

import java.util.List;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.strategies.MethodParameterKeywordStrategy;
import org.eclipse.symfony.core.codeassist.contexts.RouteCompletionContext;
import org.eclipse.symfony.core.model.SymfonyModelAccess;
import org.eclipse.symfony.index.dao.Route;


/**
 * 
 * Completes route names inside a {@link RouteCompletionContext}
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class RouteCompletionStrategy extends MethodParameterKeywordStrategy {

	public RouteCompletionStrategy(ICompletionContext context) {
		super(context);

	}
	
	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		
		AbstractCompletionContext context = (AbstractCompletionContext) getContext();		
		ISourceModule module = context.getSourceModule();		
		List<Route> routes = SymfonyModelAccess.getDefault().findRoutes(module.getScriptProject());		
		SourceRange range = getReplacementRange(context);
		
		for (Route route : routes) {
			reporter.reportKeyword(route.name, "", range);						
		}
	}
}