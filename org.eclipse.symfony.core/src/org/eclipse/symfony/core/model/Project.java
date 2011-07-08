package org.eclipse.symfony.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IScriptProject;


/**
 * 
 * Represents a Symfony2 Project. You can retrieve
 * bundles, services, commands, etc. which declared
 * within a project instance.
 *  
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class Project extends AbstractSymfonyModel {

	
	public final static int YAML_CONFIG = 0;
	public final static int XML_CONFIG = 1;
	
	/**
	 * The underlying project in the eclipse workspace
	 */
	private IScriptProject project = null;
	
	
	//TODO: parse the publicPath (ie. "/web") from php sources
	private IPath publicPath;
	
	
	//TODO: parse the configuration type (xml/yml) from the php sources
	private int configType;
	

	/**
	 * Services declared in the project scope - not in bundles
	 * ie. app/config/config.yml
	 */
	private Stack<Service> services = new Stack<Service>();
	
	
	/**
	 * List of bundles this project contains
	 */
	private ArrayList<Bundle> bundles = new ArrayList<Bundle>();
	
	
	/**
	 * List of annotations declared in this project
	 */
	private List<Annotation> annotations = new ArrayList<Annotation>();
	
	
	public Project(IScriptProject project) {
		
		super(project);
		this.project = project;		
		
		
	}
	
	public IPath getPath() {
		
		return project.getPath();
		
	}
	
	public IScriptProject getRecource() {
		
		return project;
		
	}
	
	public String toDebugString() {
				
		String debug = project.getProject().getName();		
		return debug;
		
	}
	
	@Override
	public String toString() {

		return project.getProject().getName();
		
	}

	public void addBundle(Bundle bundle) {
		
//		for (Bundle existing : bundles) {			
//			if (existing.equals(bundle)) {
//				System.out.println("removing existing " + existing.getName());
//				bundles.remove(existing);
//			}
//		}
		
		
		bundles.add(bundle);
		
	}

	public List<Bundle> getBundles() {

		return bundles;
	}

	public String getName() {

		return project.getProject().getName();
	}
	
	@Override
	public boolean equals(Object project) {
		
		if (!(project instanceof IScriptProject))
			return false;
		
		return project.equals(this.project);
		
	}

	/**
	 * 
	 * Add an annotation to this project. 
	 * 
	 * If the annotation has been added already to this project, it will be
	 * replaced with the new one.
	 * 
	 * @param annotation
	 */
	public void addAnnotation(Annotation annotation) {

		annotations.remove(annotation);
		annotations.add(annotation);
		
	}

	/**
	 * Returns all annotations declared in this project
	 * 
	 * @return
	 */
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	/**
	 * Get all services declared in this project - 
	 * including inside bundles.
	 * 
	 * 
	 * @return the list of services
	 */
	public List<Service> getServices() {
		
		List<Service> _services = getBundleServices();
		_services.addAll(services);
		return _services;
	}
	
	
	/**
	 * Return a list of services declared only in the projects scope,
	 * ie. app/config/config.yml
	 * 
	 * 
	 * @return {@link List}
	 */
	public List<Service> getProjectScopedServices() {
		
		return services;		
		
	}
	
	/**
	 * Return a list of services declared in bundles only.
	 * 
	 * 
	 * @return {@link List}
	 */
	public List<Service> getBundleServices() {
	
		
		List<Service> _services = new ArrayList<Service>();
		
		for (Bundle bundle : bundles) {			
			for(Service service : bundle.getServices()) {				
				_services.add(service);
			}
		}
		
		return _services;
		
		 
	}


	/**
	 * Add a {@link Service} to the project scoped services.
	 * 
	 * 
	 * @param service
	 */
	public void addService(Service service) {

		services.add(service);
		
	}

	public boolean hasService(String id) {

		for(Service service : services) {			
			if (service.getId().equals(id))
				return true;
		}
		
		return false;
	}

	/**
	 * Get a specific service by id.
	 * 
	 * @param id
	 * @return
	 */
	public Service getService(String id) {

		for (Service service : services) {
			
			if(service.getId().equals(id)) {
				return service;
			}			
		}
		
		return null;
	}

	public void addController(Controller controller) {
		
		for (Bundle bundle : bundles) {
			if (bundle.getBasePath().isPrefixOf(controller.getPath())) {
				bundle.addController(controller);
				break;				
				
			}
		}
	}

}