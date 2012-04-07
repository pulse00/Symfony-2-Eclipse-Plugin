/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist.strategies;


import java.util.List;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
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

import com.dubture.symfony.core.codeassist.contexts.ViewPathArgumentContext;
import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.Controller;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.Template;
import com.dubture.symfony.core.model.ViewPath;

/**
 * 
 * Completes ViewPath parts, ie: 
 * 
 * <pre>
 * 
 * 	$this->render('|  <-- will show all BundleNames
 * 
 *  $this->render('AcmeDemoBundle:|   <-- will show all Controllers
 *    
 *  $this->render('AcmeDemoBundle:Demo:| <--- will show all templates
 * 
 * </pre>
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class ViewPathCompletionStrategy extends MethodParameterKeywordStrategy {

	private static int workaroundCount = 0;

	public ViewPathCompletionStrategy(ICompletionContext context) {
		super(context);

	}	
	
	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {

		ViewPathArgumentContext context = (ViewPathArgumentContext) getContext();		
		CompletionRequestor req = context.getCompletionRequestor();
		
		SymfonyModelAccess model = SymfonyModelAccess.getDefault();
		ISourceModule module = context.getSourceModule();
		ViewPath viewPath = context.getViewPath();
		
		String bundle = viewPath.getBundle();
		String controller = viewPath.getController();
		String template = viewPath.getTemplate();
		SourceRange range = getReplacementRange(context);
		IDLTKSearchScope projectScope = SearchEngine.createSearchScope(context.getSourceModule().getScriptProject());
		
		String prefix = context.getPrefix();		

		// complete the bundle part
		if (bundle == null && controller == null && template == null) {

			List<Bundle> bundles = model.findBundles(context.getSourceModule().getScriptProject());

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
		// complete the controller part: "Bundle:| 
		} else if (controller == null && template == null) {			
			
			IType[] controllers = model.findBundleControllers(bundle, module.getScriptProject());
			
			if (controllers != null) {
				for (IType ctrl : controllers) {

					String name = ctrl.getElementName().replace("Controller", "");
					if (!name.endsWith("\\")) {						
						Controller con = new Controller((ModelElement) ctrl, name);
						reporter.reportType(con, ":", range);
					}
						
				}				
			}

		// complete template path: "Bundle:Controller:|
		} else if (bundle != null && controller != null) {

			IModelElement[] templates = model.findTemplates(bundle, controller, module.getScriptProject());
			
			if (templates != null) {				
				for (IModelElement tpl : templates) {

					if (CodeAssistUtils.startsWithIgnoreCase(tpl.getElementName(), prefix)) {
						Template t = new Template((ModelElement) tpl, tpl.getElementName());
						reporter.reportType(t, "", range);
					}
					
				}
			}
			
		// project root: "::| 
		} else if (bundle == null && controller == null && template != null) {

			IModelElement[] templates = model.findRootTemplates(module.getScriptProject());
			
			if (templates != null) {				
				for (IModelElement tpl : templates) {
					
					if (CodeAssistUtils.startsWithIgnoreCase(tpl.getElementName(), prefix)) {
						Template t = new Template((ModelElement) tpl, tpl.getElementName());
						reporter.reportType(t, "", range);
					}
					
				}
			}
			
		// bundle root: "AcmeDemoBundle::| 
		} else if (bundle != null && controller == null && template != null) {
			
			
			IModelElement[] templates = model.findBundleRootTemplates(bundle, module.getScriptProject());
			
			if (templates != null) {				
				for (IModelElement tpl : templates) {
					
					if (CodeAssistUtils.startsWithIgnoreCase(tpl.getElementName(), prefix)) {
						Template t = new Template((ModelElement) tpl, tpl.getElementName());
						reporter.reportType(t, "", range);
					}					
				}
			}
			
		}
	}
}
