/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.twig.codeassist;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.twig.codeassist.context.RouteCompletionContext;
import com.dubture.symfony.twig.codeassist.context.TemplateVariableCompletionContext;
import com.dubture.symfony.twig.codeassist.context.TemplateVariableFieldCompletionContext;
import com.dubture.symfony.twig.codeassist.context.ViewPathArgumentContext;
import com.dubture.symfony.twig.log.Logger;
import com.dubture.twig.core.codeassist.ICompletionContext;
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
	public ICompletionContext[] createContexts(IProject project) {
		
		try {
			if (project == null || !project.hasNature(SymfonyNature.NATURE_ID)) {
				return NO_CONTEXTS;
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return new ICompletionContext[] {
				new TemplateVariableCompletionContext(),
				new TemplateVariableFieldCompletionContext(),
				new RouteCompletionContext(),
				new ViewPathArgumentContext(),
				
		};
	}
}
