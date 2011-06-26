package org.eclipse.symfony.twig.codeassist.strategies;



import java.util.List;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.typeinference.FakeField;
import org.eclipse.symfony.core.index.SymfonyElementResolver.TemplateField;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.SymfonyModelAccess;
import org.eclipse.symfony.core.model.TemplateVariable;
import org.eclipse.symfony.core.util.PathUtils;
import org.eclipse.twig.core.codeassist.context.AbstractTwigCompletionContext;
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
			
			AbstractCompletionContext ctxt = (AbstractCompletionContext) getContext();
			ISourceModule module = ctxt.getSourceModule();
			SymfonyModelAccess model = SymfonyModelAccess.getDefault();
			
			String viewName = PathUtils.getViewFromTemplatePath(module.getPath());
			
			IType controller = model.findControllerByTemplate(module);		
			List<TemplateField> variables = model.findTemplateVariables(controller);
			
			SourceRange range = getReplacementRange(getContext());
			
			System.err.println(range.getOffset() + " " + range.getLength());			
			
			if (variables == null) {
				return;
			}
			
			for(TemplateField element : variables) {
				if (CodeAssistUtils.startsWithIgnoreCase(element.getMethod(), viewName)) {
					reporter.reportField(new FakeField(element, element.getElementName(), Modifiers.AccPublic), "", range, true);
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}