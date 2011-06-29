package org.eclipse.symfony.core.codeassist;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.internal.core.codeassist.contexts.CompletionContextResolver;
import org.eclipse.symfony.core.codeassist.contexts.AnnotationCompletionContext;
import org.eclipse.symfony.core.codeassist.contexts.RouteCompletionContext;
import org.eclipse.symfony.core.codeassist.contexts.ServiceContainerContext;
import org.eclipse.symfony.core.codeassist.contexts.ServiceReturnTypeContext;
import org.eclipse.symfony.core.codeassist.contexts.TemplateVariableContext;
import org.eclipse.symfony.core.codeassist.contexts.ViewPathArgumentContext;

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
				new AnnotationCompletionContext(),
				new ServiceContainerContext(),
				new ServiceReturnTypeContext(),
				new TemplateVariableContext(),
				new RouteCompletionContext(),
				new ViewPathArgumentContext(),
		};		
	}
}
