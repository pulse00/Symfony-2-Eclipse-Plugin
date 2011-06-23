package org.eclipse.symfony.twig.codeassist;

import org.eclipse.symfony.twig.codeassist.context.TemplateVariableCompletionContext;
import org.eclipse.twig.core.codeassist.ITwigCompletionContextResolver;
import org.eclipse.twig.core.codeassist.context.AbstractTwigCompletionContext;

public class TwigCompletionContextResolver implements
		ITwigCompletionContextResolver {

	public TwigCompletionContextResolver() {

	}

	@Override
	public AbstractTwigCompletionContext[] createContexts() {
		
		return new AbstractTwigCompletionContext[] {
				new TemplateVariableCompletionContext(),				
				
		};
	}
}
