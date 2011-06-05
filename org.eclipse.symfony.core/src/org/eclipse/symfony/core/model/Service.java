package org.eclipse.symfony.core.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;


/**
 * 
 * The Service class represents a Symfony2 Service 
 * retrievable from the DependencyInjection container.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class Service {
	
	private IFile file;
	private String name;
	private String clazz;
	
	
	public Service(IFile resource, String name, String clazz) {
		
		file = resource;
		this.name = name;
		this.clazz = clazz;				
		
	}

	public IFile getFile() {
 
		return file;
	}

	public String getPHPClass() {

		return clazz;
		
	}	
}
