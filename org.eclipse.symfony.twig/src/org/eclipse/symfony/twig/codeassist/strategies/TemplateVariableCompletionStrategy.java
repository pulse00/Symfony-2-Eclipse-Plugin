package org.eclipse.symfony.twig.codeassist.strategies;



import java.util.List;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.typeinference.FakeField;
import org.eclipse.symfony.core.index.SymfonyElementResolver.TemplateField;
import org.eclipse.symfony.core.model.SymfonyModelAccess;
import org.eclipse.symfony.core.util.PathUtils;
import org.eclipse.symfony.twig.codeassist.context.TemplateVariableCompletionContext;
import org.eclipse.twig.core.codeassist.strategies.AbstractTwigCompletionStrategy;


/**
 * Passes variables declared in Symfony controllers to the
 * twig completion engine:
 * 
 *  
 *  <pre>
 *  
 *  
 *  ../../SomeController.php
 *  
 *  public function indexAction() {
 *    
 *     $foo = new Bar();     
 *     return array('foo' => $foo);
 *  
 *  
 *  }
 *  
 *  ../../index.html.twig:
 *  
 *  
 *  {{ | <-- get the foo variable completed
 *    
 *  
 *  </pre>
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class TemplateVariableCompletionStrategy extends AbstractTwigCompletionStrategy {
	
	
	public TemplateVariableCompletionStrategy(ICompletionContext context) {
		super(context);

	}

	@Override
	public void apply(ICompletionReporter reporter) {

		try {
			
			TemplateVariableCompletionContext ctxt = (TemplateVariableCompletionContext) getContext();
			
			SymfonyModelAccess model = SymfonyModelAccess.getDefault();
			ISourceModule module = ctxt.getSourceModule();
			IType controller = model.findControllerByTemplate(module);
			List<TemplateField>variables = model.findTemplateVariables(controller);
			String viewPath = PathUtils.createViewPathFromTemplate(ctxt.getSourceModule());
			
			SourceRange range = getReplacementRange(ctxt);
			
			for(TemplateField element : variables) {

				if (viewPath.equals(element.getViewPath())) {
					reporter.reportField(new FakeField(element, element.getElementName(), Modifiers.AccPublic), "", range, true);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}