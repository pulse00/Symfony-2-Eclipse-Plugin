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
	
	/**
	 * The fully qualified class name.
	 */
	private String fqcn;
	
	/***
	 * The namespace only.
	 */
	private String namespace;
	
	
	/**
	 * The name of the PHP class
	 */
	private String className;
	

	private String id;
	
	private Bundle bundle;
	
	
	public Service(IFile resource, String id, String clazz) {
		
		file = resource;
		this.fqcn = clazz;
		this.id = id;
		
		int lastPart = clazz.lastIndexOf("\\");
				
		if (lastPart == -1) {			
			namespace = "";
			className = clazz;
		} else {
			namespace = clazz.substring(0,lastPart);
			className = clazz.substring(lastPart + 1);
		}
	}

	public IFile getFile() {
 
		return file;
	}

	public String getFullyQualifiedName() {

		return fqcn;
		
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public String getId() {
		return id;
	}

	public String getNamespace() {
		
		return namespace;
	}
	
	public String getClassName() {
		return className;
	}
	
}
