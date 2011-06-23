package org.eclipse.symfony.twig.codeassist;

import org.eclipse.symfony.twig.codeassist.strategies.TemplateVariableCompletionStrategy;
import org.eclipse.twig.core.codeassist.ITwigCompletionStrategy;
import org.eclipse.twig.core.codeassist.ITwigCompletionStrategyFactory;

/**
 * 
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
	public ITwigCompletionStrategy[] createStrategies() {

		return new ITwigCompletionStrategy[] {		
				new TemplateVariableCompletionStrategy()				
		};
		
	}

}
