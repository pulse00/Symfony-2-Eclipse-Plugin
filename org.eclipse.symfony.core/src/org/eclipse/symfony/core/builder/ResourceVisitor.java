package org.eclipse.symfony.core.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;


/**
 * 
 * The {@link ResourceVisitor} is a standard buildvisitor to 
 * parse xml/yml config files from a Symfony2 project during
 * a full build.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class ResourceVisitor extends AbstractSymfonyVisitor 
	implements IResourceVisitor {
	
	
	@Override
	public boolean visit(IResource resource) throws CoreException {
					
		if (resource instanceof IFile && resource.getFileExtension() != null) {
			
			IFile file = (IFile) resource;
			
			if (resource.getFileExtension().equals("xml")) {
				getXmlParser().parse(file);				
			} else if (resource.getFileExtension().equals("yml")) {
								
				
				if (resource.getName().contains("routing")) {
					System.out.println("contains routing");
					try {
						getYmlRoutingParser().parse(file);
					} catch (Exception e) {

						System.err.println(e.getMessage());
					}
				}
			}
		}
		return true;
	}	
}