package org.eclipse.symfony.twig.codeassist;

import org.eclipse.symfony.twig.codeassist.context.RouteCompletionContext;
import org.eclipse.symfony.twig.codeassist.context.TemplateVariableCompletionContext;
import org.eclipse.symfony.twig.codeassist.context.TemplateVariableFieldCompletionContext;
import org.eclipse.twig.core.codeassist.ITwigCompletionContextResolver;
import org.eclipse.twig.core.codeassist.context.AbstractTwigCompletionContext;


/**
 * 
 * Contributes Symfony2 completion contexts to the Twig 
 * plugin.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class TwigCompletionContextResolver implements
		ITwigCompletionContextResolver {

	public TwigCompletionContextResolver() {

	}

	@Override
	public AbstractTwigCompletionContext[] createContexts() {
		
		return new AbstractTwigCompletionContext[] {
				new TemplateVariableCompletionContext(),
				new TemplateVariableFieldCompletionContext(),
				new RouteCompletionContext(),
				
		};
	}
}
