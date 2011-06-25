package org.eclipse.symfony.core.codeassist.strategies;


import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.GlobalElementStrategy;
import org.eclipse.php.internal.core.model.PhpModelAccess;

@SuppressWarnings({ "restriction", "deprecation" })
public class TemplateVariableStrategy extends GlobalElementStrategy {

	public TemplateVariableStrategy(ICompletionContext context) {
		super(context);

	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {

		
		IDLTKSearchScope scope = createSearchScope();

		
		IField[] fields = PhpModelAccess.getDefault().findFields(null, MatchRule.PREFIX, 0, 0, scope, null);
		
		SourceRange range = getReplacementRange(getContext());
		
		for(IField field : fields) {
			
			if (field.getElementName().contains("forlong") ) {
				
				reporter.reportField(field, "", range, false);
				System.err.println(field.getElementName());
				
			}
		}
		
		System.err.println("apply template strategy, found " + fields.length + " fields");

	}

}
