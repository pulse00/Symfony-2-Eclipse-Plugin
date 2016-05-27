/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.twig.codeassist.strategies;

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.jface.text.BadLocationException;

import com.dubture.symfony.twig.codeassist.context.ViewPathArgumentContext;
import com.dubture.twig.core.codeassist.ICompletionContext;
import com.dubture.twig.core.codeassist.ICompletionReporter;
import com.dubture.twig.core.codeassist.strategies.AbstractTwigCompletionStrategy;


/**
 * A viewpath completion strategy for Twig templates:
 * 
 * <pre> 
 * 	{% extends '|   <-- shows available Bundles etc. 
 * </pre>
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public class ViewPathCompletionStrategy extends AbstractTwigCompletionStrategy
{
	public ViewPathCompletionStrategy(ICompletionContext context) 
	{
		super(context);
	}
	
	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException
	{
        ViewPathArgumentContext context = (ViewPathArgumentContext) getContext();       
        ISourceRange range = getReplacementRange(context);
//        CodeassistUtils.reportViewpath(new ICompletionRepo, context.getViewPath(), 
//                context.getPrefix(), range, context.getScriptProject());

	}
}
