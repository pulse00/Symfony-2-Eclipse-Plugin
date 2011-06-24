package org.eclipse.symfony.twig.codeassist.strategies;



import java.util.List;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.typeinference.FakeField;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.TemplateVariable;
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
			
			AbstractTwigCompletionContext ctx = (AbstractTwigCompletionContext) getContext();
			ModelManager model = ModelManager.getInstance();
			List<TemplateVariable> variables = model.getTemplateVariables(ctx.getSourceModule());
			
			if (variables == null) {
				return;
			}
			
			SourceRange range = getReplacementRange(ctx);
			
			System.err.println("retrieved " + variables.size());			
			
			for (TemplateVariable variable : variables) {
				
				FakeField field = new FakeField((ModelElement) variable.getSourceModule(), variable.getName(""), Modifiers.AccPublic);
				reporter.reportField(field, "", range, true);								
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}