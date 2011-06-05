package org.eclipse.symfony.core.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.symfony.core.model.Project;

public class ResourceVisitor extends AbstractSymfonyVisitor 
	implements IResourceVisitor {
	
	
	

	@Override
	public boolean visit(IResource resource) throws CoreException {
					
		if (resource instanceof IProject) {

			System.out.println("is project!");
			Project project = new Project((IProject) resource);
			getModel().addProject(project);
			
		} else if (resource instanceof IFile) {
			
			IFile file = (IFile) resource;
			
			if (resource.getFileExtension().equals("xml")) {				
				getXmlParser().parse(file);				
			} else if (resource.getFileExtension().equals("yml")) {
				
				System.out.println("is yml file");
			}
			
		}
		
		return true;
		
	}	
}