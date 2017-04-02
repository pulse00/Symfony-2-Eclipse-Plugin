/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.model.PhpModelAccess;

import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.Controller;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.Template;
import com.dubture.symfony.core.model.Translation;
import com.dubture.symfony.core.model.ViewPath;
import com.dubture.symfony.index.model.TransUnit;

@SuppressWarnings({ "restriction" })
public class CodeassistUtils {

	public static void reportTranslations(ICompletionReporter reporter, String prefix, ISourceRange range,
			IScriptProject project) {

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

			if (targetBundle != null && StringUtils.startsWithIgnoreCase(unit.name, prefix)) {
				Translation trans = new Translation(targetBundle, unit);
				reporter.reportType(trans, "", range);
			}
		}

	}

	/**
	 * Report the different parts of a ViewPath
	 * (Bundle:Controller(/Subpath):template) to the completion engine
	 */
	public static void reportViewpath(ICompletionReporter reporter, ViewPath viewPath, String prefix,
			ISourceRange range, IScriptProject project) {
		SymfonyModelAccess model = SymfonyModelAccess.getDefault();
		String bundle = viewPath.getBundle();
		String controller = viewPath.getController();
		String template = viewPath.getTemplate();
		IDLTKSearchScope projectScope = SearchEngine.createSearchScope(project);

		// complete the bundle part
		if (bundle == null && controller == null && template == null) {

			List<Bundle> bundles = model.findBundles(project);

			for (Bundle b : bundles) {

				IType[] bundleTypes = PhpModelAccess.getDefault().findTypes(b.getElementName(), MatchRule.EXACT, 0, 0,
						projectScope, null);

				if (bundleTypes.length == 1) {

					ModelElement bType = (ModelElement) bundleTypes[0];

					if (StringUtils.startsWithIgnoreCase(bType.getElementName(), prefix)) {
						Bundle bundleType = new Bundle(bType, b.getElementName());
						reporter.reportType(bundleType, ":", range);
					}
				}
			}
			// complete the controller part: "Bundle:|
		} else if (controller == null && template == null) {

			IType[] controllers = model.findBundleControllers(bundle, project);

			for (IPath path : model.findBundleViewPaths(bundle, project)) {

				IType t = null;
				for (IType type : controllers) {

					String pathString = path.removeLastSegments(path.segmentCount() - 1).toString();

					if (type.getElementName().contains(pathString)) {
						t = type;
						break;
					}
				}

				if (t == null) {
					continue;
				}

				Controller ctrl = new Controller((ModelElement) t, path.toString());
				reporter.reportType(ctrl, ":", range);
			}

			// complete template path: "Bundle:Controller:|
		} else if (bundle != null && controller != null) {

			IModelElement[] templates = model.findTemplates(bundle, controller, project);

			if (templates != null) {
				for (IModelElement tpl : templates) {

					if (StringUtils.startsWithIgnoreCase(tpl.getElementName(), prefix)) {
						Template t = new Template((ModelElement) tpl, tpl.getElementName());
						reporter.reportType(t, "", range);
					}

				}
			}

			// project root: "::|
		} else if (bundle == null && controller == null && template != null) {

			IModelElement[] templates = model.findRootTemplates(project);

			if (templates != null) {
				for (IModelElement tpl : templates) {

					if (StringUtils.startsWithIgnoreCase(tpl.getElementName(), prefix)) {
						Template t = new Template((ModelElement) tpl, tpl.getElementName());
						reporter.reportType(t, "", range);
					}

				}
			}

			// bundle root: "AcmeDemoBundle::|
		} else if (bundle != null && controller == null && template != null) {

			IModelElement[] templates = model.findBundleRootTemplates(bundle, project);

			if (templates != null) {
				for (IModelElement tpl : templates) {

					if (StringUtils.startsWithIgnoreCase(tpl.getElementName(), prefix)) {
						Template t = new Template((ModelElement) tpl, tpl.getElementName());
						reporter.reportType(t, "", range);
					}
				}
			}
		}
	}
}
