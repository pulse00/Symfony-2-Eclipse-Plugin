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

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.strategies.AbstractCompletionStrategy;

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
				
			} else if (context.getClass() == TranslationCompletionContext.class) {
				
				result.add(new TranslationCompletionStrategy(context));
				
			}
		}
		
		return (AbstractCompletionStrategy[]) result
		        .toArray(new AbstractCompletionStrategy[result.size()]);
				
	}
}
