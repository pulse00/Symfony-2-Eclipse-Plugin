package org.eclipse.symfony.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.symfony.core.model.exception.InvalidBundleException;

/**
 * 
 * The Bunndle class represents a Symfony2 Bundle.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class Bundle {
	
	
	
	private IScriptProject project;
	private ClassDeclaration classDeclaration;
	private String name;
	private ISourceModule sourceModule;
	
	private List<Service> services = new ArrayList<Service>();
	private IPath basePath;

	public Bundle(ISourceModule iSourceModule, ClassDeclaration classDeclaration) throws InvalidBundleException {
				
		
		basePath = iSourceModule.getPath().removeLastSegments(1);
		
		if (iSourceModule.getPath().toString().contains("/Tests/"))
			throw new InvalidBundleException(iSourceModule.getPath().toString());
		
		
		this.sourceModule = iSourceModule;
		this.project = sourceModule.getScriptProject();
		this.classDeclaration = classDeclaration;
		
	}

	public IScriptProject getProject() {

		return project;
	}

	public String getName() {

		return classDeclaration.getName();
	
	}
	
	
	public IPath getBasePath() {
		
		
		return basePath;
		
	}

	public IResource getResource() {

		return sourceModule.getResource();
		
	}

	public void addService(Service service) {

		//System.out.println("creating service from " + service.getFile().getFullPath().toString());
		services.remove(service);
		services.add(service);
		
	}

	public List<Service> getServices() {

		return services;
	}
	

}
