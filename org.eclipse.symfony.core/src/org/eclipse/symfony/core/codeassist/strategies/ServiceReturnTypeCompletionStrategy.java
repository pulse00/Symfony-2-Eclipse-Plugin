package org.eclipse.symfony.core.codeassist.strategies;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.strategies.ClassMembersStrategy;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.symfony.core.codeassist.contexts.ServiceReturnTypeContext;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.Service;
import org.eclipse.symfony.core.util.text.SymfonyTextSequenceUtilities;

/**
 * 
 * A completionstrategy that reports methods from a service returned
 * by the DI container.
 * 
 * 
 * @see ServiceReturnTypeContext
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class ServiceReturnTypeCompletionStrategy extends ClassMembersStrategy {

	private int trueFlag = 0;
	private int falseFlag = 0;

	public ServiceReturnTypeCompletionStrategy(ICompletionContext context) {
		super(context);

	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {

		ServiceReturnTypeContext context = (ServiceReturnTypeContext) getContext();
		String source = SymfonyTextSequenceUtilities.getServiceFromMethodParam(context.getStatementText());
		
		if (source == null)
			return;
		
		Service service = ModelManager.getInstance().getService(source);

		if (service == null)
			return;

		
		//TODO: the corresponding IType of a service should be searched for
		// at the point where the service is created during the build process
		// if that's possible.
		//
		// this way, we could avoid the SearchEngine call here
		IType[] types = getTypes(context, service.getClassName(), service.getNamespace());
		
		// ambigous types found, return nothing
		// any better ideas out there?
		if (types.length != 1)
			return;
				
		IType type = types[0];		
		SourceRange range = getReplacementRange(context);		
		String prefix = context.getPrefix();
		
		for(IMethod method : type.getMethods()) {						
			if (CodeAssistUtils.startsWithIgnoreCase(method.getElementName(), prefix)) {		
				reporter.reportMethod(method, "", range);	
			}			
		}
	}
	
	private IType[] getTypes(AbstractCompletionContext context, String prefix, String pkg) {
		
		IDLTKSearchScope scope = createSearchScope();

		List<IType> result = new LinkedList<IType>();
		if (prefix.length() > 1 && prefix.toUpperCase().equals(prefix)) {
			// Search by camel-case
			IType[] types = PhpModelAccess.getDefault().findTypes(pkg, prefix,
					MatchRule.CAMEL_CASE, trueFlag, falseFlag, scope, null);
			result.addAll(Arrays.asList(types));
		}
		
		IType[] types = PhpModelAccess.getDefault().findTypes(pkg, prefix,
				MatchRule.PREFIX, trueFlag, falseFlag, scope, null);
		result.addAll(Arrays.asList(types));

		return (IType[]) result.toArray(new IType[result.size()]);		
		
	}	
}