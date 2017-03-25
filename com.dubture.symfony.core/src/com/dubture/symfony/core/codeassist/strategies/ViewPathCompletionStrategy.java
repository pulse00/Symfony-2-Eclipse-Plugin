/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist.strategies;

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.MethodParameterKeywordStrategy;

import com.dubture.symfony.core.codeassist.CodeassistUtils;
import com.dubture.symfony.core.codeassist.contexts.ViewPathArgumentContext;

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
@SuppressWarnings({ "restriction" })
public class ViewPathCompletionStrategy extends MethodParameterKeywordStrategy {

	public ViewPathCompletionStrategy(ICompletionContext context) 
	{
		super(context);
	}	
	
	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException 
	{
		ViewPathArgumentContext context = (ViewPathArgumentContext) getContext();		
		ISourceRange range = getReplacementRange(context);
		CodeassistUtils.reportViewpath(reporter, context.getViewPath(), 
		        context.getPrefix(), range, context.getSourceModule().getScriptProject());

	}
}
