package org.eclipse.symfony.twig.codeassist;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.symfony.twig.codeassist.context.RouteCompletionContext;
import org.eclipse.symfony.twig.codeassist.context.TemplateVariableCompletionContext;
import org.eclipse.symfony.twig.codeassist.context.TemplateVariableFieldCompletionContext;
import org.eclipse.symfony.twig.codeassist.context.ViewPathArgumentContext;
import org.eclipse.twig.core.codeassist.ITwigCompletionContextResolver;


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
	public ICompletionContext[] createContexts() {
		
		return new ICompletionContext[] {
				new TemplateVariableCompletionContext(),
				new TemplateVariableFieldCompletionContext(),
				new RouteCompletionContext(),
				new ViewPathArgumentContext(),
				
		};
	}
}
