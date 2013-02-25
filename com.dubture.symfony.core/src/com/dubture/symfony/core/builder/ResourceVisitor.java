/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.builder;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

import com.dubture.symfony.core.log.Logger;

/**
 * 
 * The {@link ResourceVisitor} is a standard buildvisitor to parse xml/yml
 * config files from a Symfony2 project during a full build.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * 
 */
public class ResourceVisitor extends AbstractSymfonyVisitor implements IResourceVisitor {

	@Override
	public boolean visit(IResource resource) throws CoreException {
		try {
			return handleResource(resource);
		} catch (Exception e) {
			Logger.logException(e);
			return false;
		}
	}
}
