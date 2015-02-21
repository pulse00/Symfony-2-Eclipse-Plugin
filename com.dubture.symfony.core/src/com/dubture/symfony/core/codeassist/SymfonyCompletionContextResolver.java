/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.internal.core.codeassist.contexts.CompletionContextResolver;

import com.dubture.symfony.core.codeassist.contexts.EntityCompletionContext;
import com.dubture.symfony.core.codeassist.contexts.RouteCompletionContext;
import com.dubture.symfony.core.codeassist.contexts.ServiceContainerContext;
import com.dubture.symfony.core.codeassist.contexts.ServiceReturnTypeContext;
import com.dubture.symfony.core.codeassist.contexts.TemplateVariableContext;
import com.dubture.symfony.core.codeassist.contexts.TransUnitCompletionContext;
import com.dubture.symfony.core.codeassist.contexts.ViewPathArgumentContext;

/**
 *
 * Context resolver for Symfony2.
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyCompletionContextResolver extends CompletionContextResolver
	implements ICompletionContextResolver {


	@Override
	public ICompletionContext[] createContexts() {

		return new ICompletionContext[] {
				new ServiceContainerContext(),
				new TemplateVariableContext(),
				new RouteCompletionContext(),
				new ViewPathArgumentContext(),
				new EntityCompletionContext(),
				new TransUnitCompletionContext(),
		};
	}
}
