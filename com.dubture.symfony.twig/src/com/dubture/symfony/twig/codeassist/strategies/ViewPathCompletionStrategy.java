/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.twig.codeassist.strategies;

import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.MethodParameterKeywordStrategy;

import com.dubture.symfony.core.codeassist.CodeassistUtils;
import com.dubture.symfony.core.model.ViewPath;
import com.dubture.symfony.twig.codeassist.context.ViewPathArgumentContext;


/**
 * 
 * A viewpath completion strategy for Twig templates:
 * 
 * 
 * <pre> 
 * 	{% extends '|   <-- shows available Bundles etc. 
 * </pre>
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class ViewPathCompletionStrategy extends MethodParameterKeywordStrategy 
{
	public ViewPathCompletionStrategy(ICompletionContext context) 
	{
		super(context);
	}
	
	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException
	{
        ViewPathArgumentContext context = (ViewPathArgumentContext) getContext();       
        SourceRange range = getReplacementRange(context);
        CodeassistUtils.reportViewpath(reporter, (ViewPath) context.getViewPath(), 
                context.getPrefix(), range, context.getSourceModule().getScriptProject());

	}
}
