package com.dubture.symfony.core.codeassist.strategies;

import java.util.List;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.MethodParameterKeywordStrategy;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProposalCollector;

import com.dubture.doctrine.core.model.DoctrineModelAccess;
import com.dubture.symfony.core.codeassist.contexts.EntityCompletionContext;
import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.EntityAlias;
import com.dubture.symfony.core.model.SymfonyModelAccess;

@SuppressWarnings({ "restriction", "deprecation" })
public class EntityCompletionStrategy extends MethodParameterKeywordStrategy {

	public EntityCompletionStrategy(ICompletionContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {

		EntityCompletionContext context = (EntityCompletionContext) getContext();		
		CompletionRequestor req = context.getCompletionRequestor();

		if (req.getClass() == PHPCompletionProposalCollector.class) {
			return;			
		}

		SymfonyModelAccess model = SymfonyModelAccess.getDefault();
		IScriptProject project = context.getSourceModule().getScriptProject();

		SourceRange range = getReplacementRange(context);
		IDLTKSearchScope projectScope = SearchEngine.createSearchScope(context.getSourceModule().getScriptProject());

		DoctrineModelAccess doctrineModel =  DoctrineModelAccess.getDefault();
		
		EntityAlias alias = context.getAlias();
		String prefix = context.getPrefix();
		
		if (alias.hasBundle() == false) {
			
			List<Bundle> bundles = model.findBundles(project);

			for (Bundle b : bundles) {				

				IType[] bundleTypes = PhpModelAccess.getDefault().findTypes(b.getElementName(), MatchRule.EXACT, 0, 0, projectScope, null);

				if (bundleTypes.length == 1) {

					ModelElement bType = (ModelElement) bundleTypes[0];

					if (CodeAssistUtils.startsWithIgnoreCase(bType.getElementName(), prefix)) {
						Bundle bundleType = new Bundle(bType, b.getElementName());
						reporter.reportType(bundleType, ":", range);						
					}
				}
			}			
			
		} else {
						
			List<String> entities = doctrineModel.getEntities(project);			
			PhpModelAccess phpModel = PhpModelAccess.getDefault();			

			//TODO: cache the entities
			for (String entity : entities) {
				
				IType[] types = phpModel.findTypes(entity, MatchRule.EXACT, 0, 0, projectScope, null);
				
				for ( IType type : types) {
					reporter.reportType(type, "", range);
				}
			}
		}
	}		
}
