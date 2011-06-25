package org.eclipse.symfony.core.codeassist.strategies;


import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.strategies.GlobalElementStrategy;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.symfony.core.util.PathUtils;

/**
 * 
 * Completes variables in templates declared in 
 * Symfony2 controllers.
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class TemplateVariableStrategy extends GlobalElementStrategy {

	public TemplateVariableStrategy(ICompletionContext context) {
		super(context);

	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {

		AbstractCompletionContext ctxt = (AbstractCompletionContext) getContext();
		ISourceModule module = ctxt.getSourceModule();

		// get the name of the Controller to search for
		String controller = PathUtils.getControllerFromTemplatePath(module.getPath());

		if (controller == null) {
			return;
		}

		// find the type
		IType types[] = PhpModelAccess.getDefault().findTypes(controller, 
				MatchRule.EXACT, 0, 0, 
				SearchEngine.createSearchScope(module.getScriptProject()), null);

		if (types.length != 1)
			return;

		// create a searchscope for the Controller class only
		IDLTKSearchScope scope = SearchEngine.createSearchScope(types[0]);

		if(scope == null) {
			return;
		}

		// find all fields inside the controller
		IField[] fields = PhpModelAccess.getDefault().findFields(null, MatchRule.PREFIX, 0, 0, scope, null);
		SourceRange range = getReplacementRange(getContext());

		for(IField field : fields) {
			reporter.reportField(field, "", range, false);
		}
	}
}