package com.dubture.symfony.core.codeassist.strategies;

import java.util.List;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.MethodParameterKeywordStrategy;

import com.dubture.symfony.core.codeassist.contexts.TransUnitCompletionContext;
import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.Translation;
import com.dubture.symfony.index.dao.TransUnit;

/**
 * 
 * Strategy to complete Symfony translations. 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class TransUnitCompletionStrategy extends MethodParameterKeywordStrategy {

	public TransUnitCompletionStrategy(ICompletionContext context) {
		super(context);
		
	}
	
	
	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {

		System.err.println("apply translation");

		TransUnitCompletionContext context = (TransUnitCompletionContext) getContext();
		IScriptProject project = context.getSourceModule().getScriptProject();
		
		CompletionRequestor req = context.getCompletionRequestor();
		
		
		// FIXME: this is a VERY dirty hack to report the route completions
		// only to the SymfonyCompletionProposalCollector which
		// shows the correct popup information.
		// otherwise each route will shown twice.
		//
		// unfortunately there's no other way using the DLTK mechanism at the moment				
		if (!req.getClass().getName().contains("Symfony")) {
			return;			
		}
		
		SymfonyModelAccess model= SymfonyModelAccess.getDefault();
		List<TransUnit> units = model.findTranslations(project.getPath());		
		SourceRange range = getReplacementRange(context);		
		String prefix = context.getPrefix();
		
		if (units == null) {
			return;
		}
		
		List<Bundle> bundles = model.findBundles(context.getSourceModule().getScriptProject());
		
		for (TransUnit unit : units) {
			
			Bundle targetBundle = null;
			
			for (Bundle bundle : bundles) {			
				if (unit.path.startsWith(bundle.getTranslationPath())) {
					targetBundle = bundle;
					break;
				}
			}
			
			if (targetBundle.getScriptProject() == null) {
				targetBundle.setProject(project);
			}
			
			if (targetBundle != null && CodeAssistUtils.startsWithIgnoreCase(unit.name, prefix)) {
				System.err.println("report translation");
				Translation trans = new Translation(targetBundle,unit);				
				reporter.reportType(trans, "", range);	
			}
		}	
	}
}
