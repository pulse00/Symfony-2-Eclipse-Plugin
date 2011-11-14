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
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

/**
 * 
 * The {@link DeltaVisitor} is a standard delta buildvisitor to 
 * parse xml/yml config files from a Symfony2 project.
 *  
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class DeltaVisitor extends AbstractSymfonyVisitor 
	implements IResourceDeltaVisitor {


	@Override
	public boolean visit(IResourceDelta delta) throws CoreException {

		IResource resource = delta.getResource();
		boolean built = false;
		
		switch (delta.getKind()) {
		
		case IResourceDelta.ADDED:
		case IResourceDelta.CHANGED:
			
			built = handleResource(resource);
			break;

		case IResourceDelta.REMOVED:

			//TODO: find a way to remove the routes of a deleted yml/xml file
			
			if (path != null)
				indexer.deleteServices(path.toString());
			
			break;
		}

		return built;

	}

}
