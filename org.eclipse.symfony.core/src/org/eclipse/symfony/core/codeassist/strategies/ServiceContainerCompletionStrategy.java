package org.eclipse.symfony.core.codeassist.strategies;

import java.util.List;

import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.MethodParameterKeywordStrategy;
import org.eclipse.symfony.core.codeassist.contexts.ServiceContainerContext;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.Service;


/**
 * CompletionStrategy to provide service names for DI::get calls like
 * 
 * 
 * <pre>
 * 
 *  // inside a ContainerAware interface
 * 	$this->get('| 
 *  $this->container->get('| 
 * 
 * </pre>
 * 
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class ServiceContainerCompletionStrategy extends
		MethodParameterKeywordStrategy {

	public ServiceContainerCompletionStrategy(ICompletionContext context) {
		super(context);

	}
		
	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {

		if (!(getContext() instanceof ServiceContainerContext)) {
			return;
		}
		
		ServiceContainerContext context = (ServiceContainerContext) getContext();
		ModelManager manager = ModelManager.getInstance();
		List<Service> services = manager.getServices(context.getSourceModule().getScriptProject());
		SourceRange range = getReplacementRange(context);
		
		if (services == null) {
			return;
		}

		for(Service service : services) {

			//TODO: filter by visibility |Êabstract services
			reporter.reportKeyword(service.getId(), "", range);
		}
	}
}