package com.dubture.symfony.twig.codeassist;

import org.eclipse.php.core.codeassist.ICompletionContext;

import com.dubture.symfony.twig.codeassist.context.RouteCompletionContext;
import com.dubture.symfony.twig.codeassist.context.TemplateVariableCompletionContext;
import com.dubture.symfony.twig.codeassist.context.TemplateVariableFieldCompletionContext;
import com.dubture.symfony.twig.codeassist.context.TranslationCompletionContext;
import com.dubture.symfony.twig.codeassist.context.ViewPathArgumentContext;
import com.dubture.twig.core.codeassist.ITwigCompletionContextResolver;


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
				new TranslationCompletionContext(),
				
		};
	}
}
