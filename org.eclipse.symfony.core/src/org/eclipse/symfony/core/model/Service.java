package org.eclipse.symfony.core.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;


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

	private IPath path;
	
	
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

	public Service(String id, String phpClass, String path) {

		this.fqcn = phpClass;
		this.id = id;
		this.path = new Path(path);		
		
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

	public static Service fromIndex(org.eclipse.symfony.index.Service s) {
	
		Service service = new Service(s.id, s.phpClass, s.path);
		return service;

	}
	
}
