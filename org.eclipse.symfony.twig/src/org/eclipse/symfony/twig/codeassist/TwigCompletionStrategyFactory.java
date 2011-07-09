package org.eclipse.symfony.twig.codeassist;


import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.strategies.AbstractCompletionStrategy;
import org.eclipse.symfony.core.codeassist.strategies.RouteCompletionStrategy;
import org.eclipse.symfony.twig.codeassist.context.RouteCompletionContext;
import org.eclipse.symfony.twig.codeassist.context.TemplateVariableCompletionContext;
import org.eclipse.symfony.twig.codeassist.context.TemplateVariableFieldCompletionContext;
import org.eclipse.symfony.twig.codeassist.context.ViewPathArgumentContext;
import org.eclipse.symfony.twig.codeassist.strategies.TemplateVariableCompletionStrategy;
import org.eclipse.symfony.twig.codeassist.strategies.TemplateVariableFieldCompletionStrategy;
import org.eclipse.symfony.twig.codeassist.strategies.ViewPathCompletionStrategy;
import org.eclipse.twig.core.codeassist.ITwigCompletionStrategyFactory;

/**
 * 
 * {@link TwigCompletionStrategyFactory} provides Symfony2 completion
 * strategies for the Twig plugin.
 *  
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class TwigCompletionStrategyFactory implements
		ITwigCompletionStrategyFactory {

	public TwigCompletionStrategyFactory() {

	}


	@Override
	public AbstractCompletionStrategy[] create(ICompletionContext[] contexts) {


		List<AbstractCompletionStrategy> result = new LinkedList<AbstractCompletionStrategy>();
		
		for (ICompletionContext context : contexts) {
			if (context.getClass() == TemplateVariableCompletionContext.class) {
				
				result.add(new TemplateVariableCompletionStrategy(context));
				
			} else if (context.getClass() == TemplateVariableFieldCompletionContext.class) {
				
				result.add(new TemplateVariableFieldCompletionStrategy(context));
				
			} else if (context.getClass() == RouteCompletionContext.class) {
				
				result.add(new RouteCompletionStrategy(context));
				
			} else if (context.getClass() == ViewPathArgumentContext.class) {
				
				result.add(new ViewPathCompletionStrategy(context));
			}
		}
		
		return (AbstractCompletionStrategy[]) result
		        .toArray(new AbstractCompletionStrategy[result.size()]);
				
	}
}
