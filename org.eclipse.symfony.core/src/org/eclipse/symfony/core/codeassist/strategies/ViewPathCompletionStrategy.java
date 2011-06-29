package org.eclipse.symfony.core.codeassist.strategies;


import java.util.List;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.MethodParameterKeywordStrategy;
import org.eclipse.symfony.core.codeassist.contexts.ViewPathArgumentContext;
import org.eclipse.symfony.core.model.SymfonyModelAccess;
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

	public ViewPathCompletionStrategy(ICompletionContext context) {
		super(context);

	}	
	
	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {

		ViewPathArgumentContext context = (ViewPathArgumentContext) getContext();
		SymfonyModelAccess model = SymfonyModelAccess.getDefault();
		ISourceModule module = context.getSourceModule();
		ViewPath viewPath = context.getViewPath();
		
		
		String bundle = viewPath.getBundle();
		String controller = viewPath.getController();
		String template = viewPath.getTemplate();
		SourceRange range = getReplacementRange(context);
		
		// complete the bundle part
		if (bundle == null) {

			List<String> bundles = model.findBundles(context.getSourceModule().getScriptProject());

			for (String b : bundles) {				
				reporter.reportKeyword(b, ":", range);				
			}			
		// complete the controller part
		} else if (controller == null) {			
			IType[] controllers = model.findBundleControllers(bundle, module.getScriptProject());
			
			if (controllers != null) {
				for (IType ctrl : controllers) {
					reporter.reportKeyword(ctrl.getElementName().replace("Controller", ""), ":", range);
				}				
			}

		} else if (template == null) {

			IModelElement[] templates = model.findTemplates(bundle, controller, module.getScriptProject());
			
			if (templates != null) {				
				for (IModelElement tpl : templates) {					
					reporter.reportKeyword(tpl.getElementName(), "", range);
				}
			}
		}
	}
}
