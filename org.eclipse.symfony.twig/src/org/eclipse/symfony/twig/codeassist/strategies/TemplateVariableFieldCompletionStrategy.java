package org.eclipse.symfony.twig.codeassist.strategies;


import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.TemplateVariable;
import org.eclipse.twig.core.codeassist.context.VariableFieldContext;
import org.eclipse.twig.core.codeassist.strategies.VariableFieldStrategy;


/**
 * 
 * The {@link TemplateVariableFieldCompletionStrategy} completes
 * variable fields inside Symfony2 Twig templates:
 * 
 * 
 * <pre>
 * 
 * 	{{ form.|  <-- completes fields and methods of the Type "form" created in a controller
 * 
 * </pre>
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class TemplateVariableFieldCompletionStrategy extends
VariableFieldStrategy {

	public TemplateVariableFieldCompletionStrategy(ICompletionContext context) {
		super(context);
	}


	@Override
	public void apply(ICompletionReporter reporter) throws Exception {

		VariableFieldContext ctx = (VariableFieldContext) getContext();
		String varName = ctx.getVariable();

		ModelManager model = ModelManager.getInstance();

		System.err.println("get variable for " + varName);
		TemplateVariable var = model.findTemplateVariable(ctx.getSourceModule(), varName);

		if (var == null) {
			return;
		}

		String className = var.getClassName();
		IDLTKSearchScope scope = createSearchScope();

		if (className != null) {
			
			IType[] types =  PhpModelAccess.getDefault().findTypes(var.getNamespace(), 
					var.getClassName(), MatchRule.EXACT, 0, 0, scope, null);
							
			SourceRange range = getReplacementRange(getContext());
			
			if (types.length == 1) {
				IType type = types[0];
				for (IMethod method :type.getMethods()) {				
					reporter.reportMethod(method, "", range);
				}
				
				for (IField field : type.getFields()) {
					reporter.reportField(field, "", range, true);
				}
			}			
		}
	}
}
