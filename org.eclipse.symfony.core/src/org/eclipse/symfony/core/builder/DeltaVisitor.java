package org.eclipse.symfony.core.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.symfony.core.Logger;
import org.eclipse.symfony.core.parser.XMLConfigParser;

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
		
		switch (delta.getKind()) {
		case IResourceDelta.ADDED:
			
			// handle added resource

			if (resource instanceof IFile) {

				IFile file = (IFile) resource;

				if (resource.getFileExtension().equals("xml")) {

					try {
						XMLConfigParser parser;
						parser = new XMLConfigParser(file.getContents());
						parser.parse();					
					} catch (Exception e) {

						Logger.logException(e);

					}

				} else if (resource.getFileExtension().equals("yml")) {

//					System.out.println("is yml file");
				}

			}

			break;
		case IResourceDelta.REMOVED:
			// handle removed resource
			break;
		case IResourceDelta.CHANGED:
			// handle changed resource
			//			checkXML(resource);
			break;
		}
		//return true to continue visiting children.
		return true;

	}

}
