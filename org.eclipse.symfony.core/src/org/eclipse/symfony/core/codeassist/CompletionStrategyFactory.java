package org.eclipse.symfony.core.codeassist;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.core.codeassist.ICompletionStrategyFactory;
import org.eclipse.symfony.core.codeassist.contexts.AnnotationCompletionContext;
import org.eclipse.symfony.core.codeassist.contexts.ServiceContainerCompletionContext;
import org.eclipse.symfony.core.codeassist.strategies.AnnotationCompletionStrategy;
import org.eclipse.symfony.core.codeassist.strategies.ServiceContainerCompletionStrategy;

/**
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class CompletionStrategyFactory implements ICompletionStrategyFactory {


	@Override
	public ICompletionStrategy[] create(ICompletionContext[] contexts) {

		List<ICompletionStrategy> result = new LinkedList<ICompletionStrategy>();
		
		for (ICompletionContext context : contexts) {
			if (context.getClass() == AnnotationCompletionContext.class) {
				result.add(new AnnotationCompletionStrategy(context));
			} else if (context.getClass() == ServiceContainerCompletionContext.class) {				
				result.add(new ServiceContainerCompletionStrategy(context));
			}
		}
		
		return (ICompletionStrategy[]) result
		        .toArray(new ICompletionStrategy[result.size()]);

	}
}
