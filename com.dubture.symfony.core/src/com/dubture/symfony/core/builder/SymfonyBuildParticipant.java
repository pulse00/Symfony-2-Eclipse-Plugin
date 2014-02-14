/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.parser.IModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPModuleDeclaration;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.visitor.AnnotationVisitor;

/**
 *
 * Not used yet.
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyBuildParticipant implements IBuildParticipant {
	private PHPModuleDeclaration getModuleDeclaration(IBuildContext context) {
		if (context.get(IBuildContext.ATTR_MODULE_DECLARATION) instanceof PHPModuleDeclaration) {
			return (PHPModuleDeclaration) context.get(IBuildContext.ATTR_MODULE_DECLARATION);
		}

		return null;
	}

	@Override
	public void build(IBuildContext context) throws CoreException {
		try {
			PHPModuleDeclaration module = getModuleDeclaration(context);

			if (module != null) {
				module.traverse(new AnnotationVisitor(context));
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
}
