package org.eclipse.symfony.core.codeassist.strategies;

import java.util.List;

import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.MethodParameterKeywordStrategy;
import org.eclipse.symfony.core.codeassist.contexts.ServiceContainerCompletionContext;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.Service;


/**
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

		if (!(getContext() instanceof ServiceContainerCompletionContext)) {
			System.err.println("no context");
			return;
		}
		
		ServiceContainerCompletionContext context = (ServiceContainerCompletionContext) getContext();
		List<Service> services = ModelManager.getInstance().getServices(context.getSourceModule().getScriptProject());
		SourceRange range = getReplacementRange(context);
		
		
		reporter.reportKeyword("doctrine", "", range);
		reporter.reportKeyword("twig", "", range);
		reporter.reportKeyword("assetic", "", range);
		
		
		if (services == null) {
			
			System.out.println("no services");
			return;
		}
		System.err.println(services.size());
		for(Service service : services) {
			System.err.println("reort " + service.getId());			
			reporter.reportKeyword(service.getId(), "", range);
		}
	}
}