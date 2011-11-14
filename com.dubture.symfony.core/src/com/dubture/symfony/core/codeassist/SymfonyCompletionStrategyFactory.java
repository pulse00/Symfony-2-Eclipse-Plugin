/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.core.codeassist.ICompletionStrategyFactory;

import com.dubture.symfony.core.codeassist.contexts.AnnotationCompletionContext;
import com.dubture.symfony.core.codeassist.contexts.EntityCompletionContext;
import com.dubture.symfony.core.codeassist.contexts.RouteCompletionContext;
import com.dubture.symfony.core.codeassist.contexts.ServiceContainerContext;
import com.dubture.symfony.core.codeassist.contexts.ServiceReturnTypeContext;
import com.dubture.symfony.core.codeassist.contexts.TemplateVariableContext;
import com.dubture.symfony.core.codeassist.contexts.TransUnitCompletionContext;
import com.dubture.symfony.core.codeassist.contexts.ViewPathArgumentContext;
import com.dubture.symfony.core.codeassist.strategies.AnnotationCompletionStrategy;
import com.dubture.symfony.core.codeassist.strategies.EntityCompletionStrategy;
import com.dubture.symfony.core.codeassist.strategies.RouteCompletionStrategy;
import com.dubture.symfony.core.codeassist.strategies.ServiceContainerCompletionStrategy;
import com.dubture.symfony.core.codeassist.strategies.ServiceReturnTypeCompletionStrategy;
import com.dubture.symfony.core.codeassist.strategies.TemplateVariableStrategy;
import com.dubture.symfony.core.codeassist.strategies.TransUnitCompletionStrategy;
import com.dubture.symfony.core.codeassist.strategies.ViewPathCompletionStrategy;

/**
 * Factory class for CompletionStrategies. 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class SymfonyCompletionStrategyFactory implements ICompletionStrategyFactory {


	@SuppressWarnings("rawtypes")
	@Override
	public ICompletionStrategy[] create(ICompletionContext[] contexts) {

		List<ICompletionStrategy> result = new LinkedList<ICompletionStrategy>();
		
		
		
		for (ICompletionContext context : contexts) {
			
			Class contextClass = context.getClass();
			
			if (contextClass == AnnotationCompletionContext.class) {
				
				result.add(new AnnotationCompletionStrategy(context));
				
			} else if (contextClass == ServiceContainerContext.class) {
				
				result.add(new ServiceContainerCompletionStrategy(context));
				
			} else if (contextClass == ServiceReturnTypeContext.class) {
				
				result.add(new ServiceReturnTypeCompletionStrategy(context));
				
			} else if (contextClass == TemplateVariableContext.class) {
				
				result.add(new TemplateVariableStrategy(context));
				
			} else if (contextClass == RouteCompletionContext.class) {
			
				result.add(new RouteCompletionStrategy(context));
				
			} else if (contextClass == ViewPathArgumentContext.class) {
				
				result.add(new ViewPathCompletionStrategy(context));
				
			} else if (contextClass == EntityCompletionContext.class) {
				
				result.add(new EntityCompletionStrategy(context));
			} else if (contextClass == TransUnitCompletionContext.class) {
				
				result.add(new TransUnitCompletionStrategy(context));
			}
		}
		
		return (ICompletionStrategy[]) result
		        .toArray(new ICompletionStrategy[result.size()]);

	}
}
