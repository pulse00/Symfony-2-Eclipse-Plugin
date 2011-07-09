package org.eclipse.symfony.core.codeassist.strategies;


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
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.MethodParameterKeywordStrategy;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProposalCollector;
import org.eclipse.symfony.core.codeassist.contexts.ViewPathArgumentContext;
import org.eclipse.symfony.core.model.Bundle;
import org.eclipse.symfony.core.model.Controller;
import org.eclipse.symfony.core.model.SymfonyModelAccess;
import org.eclipse.symfony.core.model.Template;
import org.eclipse.symfony.core.model.ViewPath;

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
		
		if (req.getClass() == PHPCompletionProposalCollector.class) {
			return;			
		}

//		if (workaroundCount == 0) {
//			workaroundCount++;
//			
//		} else {
//			workaroundCount = 0;
//			return;
//		}
		
		
		SymfonyModelAccess model = SymfonyModelAccess.getDefault();
		ISourceModule module = context.getSourceModule();
		ViewPath viewPath = context.getViewPath();
		
		String bundle = viewPath.getBundle();
		String controller = viewPath.getController();
		String template = viewPath.getTemplate();
		SourceRange range = getReplacementRange(context);
		IDLTKSearchScope projectScope = SearchEngine.createSearchScope(context.getSourceModule().getScriptProject());
		
		// complete the bundle part
		if (bundle == null) {

			List<String> bundles = model.findBundles(context.getSourceModule().getScriptProject());

			for (String b : bundles) {				
				
				
				IType[] bundleTypes = PhpModelAccess.getDefault().findTypes(b, MatchRule.EXACT, 0, 0, projectScope, null);
				
				if (bundleTypes.length == 1) {
					
					Bundle bundleType = new Bundle((ModelElement) bundleTypes[0], b);
					reporter.reportType(bundleType, ":", range);
					
				}
				
				//reporter.reportKeyword(b, ":", range);				
			}			
			
		// complete the controller part
		} else if (controller == null) {			
			
			
			
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

		} else if (template == null) {

			IModelElement[] templates = model.findTemplates(bundle, controller, module.getScriptProject());
			
			if (templates != null) {				
				for (IModelElement tpl : templates) {
					
					Template t = new Template((ModelElement) tpl, tpl.getElementName());
					reporter.reportType(t, "", range);
					
				}
			}
		}
	}
}
