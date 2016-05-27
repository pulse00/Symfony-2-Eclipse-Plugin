/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.twig.codeassist.context;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.IDocument;

import com.dubture.twig.core.codeassist.context.TemplateVariablesContext;


/**
 * 
 * Simple context for staying inside Twig statements
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class TemplateVariableCompletionContext extends
	TemplateVariablesContext {
	
	
	@Override
	public boolean isValid(IDocument template, int offset, IProgressMonitor monitor) {
		if (!super.isValid(template, offset, monitor)) {
			return false;
		}
	
		return true;
	}
}
