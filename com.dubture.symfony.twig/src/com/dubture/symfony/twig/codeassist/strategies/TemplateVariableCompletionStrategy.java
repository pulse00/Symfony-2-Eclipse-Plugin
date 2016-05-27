/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.twig.codeassist.strategies;

import java.util.List;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.typeinference.FakeField;

import com.dubture.symfony.core.index.SymfonyElementResolver.TemplateField;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.util.PathUtils;
import com.dubture.symfony.twig.codeassist.context.TemplateVariableCompletionContext;
import com.dubture.twig.core.codeassist.ICompletionContext;
import com.dubture.twig.core.codeassist.ICompletionReporter;
import com.dubture.twig.core.codeassist.strategies.AbstractTwigCompletionStrategy;


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
			
//			SymfonyModelAccess model = SymfonyModelAccess.getDefault();
//			ISourceModule module = ctxt.getSourceModule();
//			IType controller = model.findControllerByTemplate(module);
//			List<TemplateField>variables = model.findTemplateVariables(controller);
//			String viewPath = PathUtils.createViewPathFromTemplate(ctxt.getSourceModule(), true);
//			
//			ISourceRange range = getReplacementRange(ctxt);
//			
//			// prepend the php dollar variable for the equals check
//			String prefix = "$" +ctxt.getPrefix();
//			
//			for(TemplateField element : variables) {
//
//				if (viewPath.equals(element.getViewPath()) && CodeAssistUtils.startsWithIgnoreCase(element.getElementName(), prefix)) {
//					reporter.reportField(new FakeField(element, element.getElementName(), Modifiers.AccPublic), "", range, true);
//				}
//			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
