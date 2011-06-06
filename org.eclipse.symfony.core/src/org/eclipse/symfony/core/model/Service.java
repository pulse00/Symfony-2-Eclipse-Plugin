package org.eclipse.symfony.core.model;

import org.eclipse.core.resources.IFile;


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
	private String clazz;
	private Bundle bundle;
	
	
	public Service(IFile resource, String name, String clazz) {
		
		file = resource;
		this.clazz = clazz;				
		
	}

	public IFile getFile() {
 
		return file;
	}

	public String getPHPClass() {

		return clazz;
		
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}	
}
