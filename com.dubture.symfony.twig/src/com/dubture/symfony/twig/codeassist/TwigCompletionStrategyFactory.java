/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.twig.codeassist;


import java.util.LinkedList;
import java.util.List;


import com.dubture.symfony.twig.codeassist.context.RouteCompletionContext;
import com.dubture.symfony.twig.codeassist.context.TemplateVariableCompletionContext;
import com.dubture.symfony.twig.codeassist.context.TemplateVariableFieldCompletionContext;
import com.dubture.symfony.twig.codeassist.context.TranslationCompletionContext;
import com.dubture.symfony.twig.codeassist.context.ViewPathArgumentContext;
import com.dubture.symfony.twig.codeassist.strategies.RouteCompletionStrategy;
import com.dubture.symfony.twig.codeassist.strategies.TemplateVariableCompletionStrategy;
import com.dubture.symfony.twig.codeassist.strategies.TemplateVariableFieldCompletionStrategy;
import com.dubture.symfony.twig.codeassist.strategies.TranslationCompletionStrategy;
import com.dubture.symfony.twig.codeassist.strategies.ViewPathCompletionStrategy;
import com.dubture.twig.core.codeassist.ICompletionContext;
import com.dubture.twig.core.codeassist.ICompletionStrategy;
import com.dubture.twig.core.codeassist.ITwigCompletionStrategyFactory;

/**
 * 
 * {@link TwigCompletionStrategyFactory} provides Symfony2 completion
 * strategies for the Twig plugin.
 *  
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class TwigCompletionStrategyFactory implements
		ITwigCompletionStrategyFactory {

	public TwigCompletionStrategyFactory() {

	}


	@Override
	public ICompletionStrategy[] create(ICompletionContext[] contexts) {


		List<ICompletionStrategy> result = new LinkedList<ICompletionStrategy>();
		
		for (ICompletionContext context : contexts) {
			if (context.getClass() == TemplateVariableCompletionContext.class) {
				
				result.add(new TemplateVariableCompletionStrategy(context));
				
			} else if (context.getClass() == TemplateVariableFieldCompletionContext.class) {
				
				result.add(new TemplateVariableFieldCompletionStrategy(context));
				
			} else if (context.getClass() == RouteCompletionContext.class) {
				
				result.add(new RouteCompletionStrategy(context));
				
			} else if (context.getClass() == ViewPathArgumentContext.class) {
				
				result.add(new ViewPathCompletionStrategy(context));
				
			} else if (context.getClass() == TranslationCompletionContext.class) {
				
				result.add(new TranslationCompletionStrategy(context));
				
			}
		}
		
		return (ICompletionStrategy[]) result
		        .toArray(new ICompletionStrategy[result.size()]);
				
	}
}
