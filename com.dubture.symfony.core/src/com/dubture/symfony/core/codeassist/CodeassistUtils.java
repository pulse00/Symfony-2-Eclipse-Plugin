package com.dubture.symfony.core.codeassist;

import java.util.List;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;

import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.Translation;
import com.dubture.symfony.index.dao.TransUnit;

@SuppressWarnings({ "restriction", "deprecation" })
public class CodeassistUtils {

	public static void reportTranslations(ICompletionReporter reporter, String prefix, SourceRange range, IScriptProject project) {

		SymfonyModelAccess model = SymfonyModelAccess.getDefault();
		
		List<Bundle> bundles = model.findBundles(project);
		List<TransUnit> units = model.findTranslations(project.getPath());		
		
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
				Translation trans = new Translation(targetBundle,unit);				
				reporter.reportType(trans, "", range);	
			}
		}			

	}

}
