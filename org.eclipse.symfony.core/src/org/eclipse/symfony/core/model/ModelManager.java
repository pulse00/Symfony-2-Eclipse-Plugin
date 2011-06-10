package org.eclipse.symfony.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;


/**
 * 
 * The ModelManager is the central singleton which
 * holds references to all projects in the workspace.
 * 
 * You can add listeners to modelevents, ie. when the
 * model is cleared completely by a full-build.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class ModelManager {
	
	private static ModelManager instance;
	
	private List<Project> projects = new ArrayList<Project>();
	
	private List<IModelClearListener> modelClearListeners = new ArrayList<IModelClearListener>();
	
	private ModelManager() {			
		
	}
	
	public static ModelManager getInstance() {
				
		if (instance == null)
			instance = new ModelManager();
		
		return instance;
	}
	
	
	public void clear() {

		projects = new ArrayList<Project>();

		System.out.println("clearing model");
		for (IModelClearListener listener : modelClearListeners) {
			listener.modelCleared();			
		}
	}
	
	public void addModelClearListener(IModelClearListener listener) {
				
		modelClearListeners.add(listener);
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void addProject(Project project) {

		System.out.println("Creating project: " + project.getName());
		
		for (Project proj : projects) {			
			if (proj == project) {
				projects.remove(proj);
				break;
			}
		}
		
		projects.add(project);
		
	}

	public void addBundle(Bundle bundle) {

		IScriptProject scriptProject = bundle.getProject();
				
		boolean found = false;
		
		for (Project project : projects) {
			
			if (project.equals(scriptProject)) {
				project.addBundle(bundle);				
				dispatchChangeListener();
				found = true;
				break;
				
			} 
		}
		
		if (found == false) {			
			Project project = new Project(scriptProject);			
			project.addBundle(bundle);
			addProject(project);
			dispatchChangeListener();
		}
	}
	
	
	public void addService(Service service) {
		
		for (Project project : projects) {
			for (Bundle bundle : project.getBundles()) {
				

				if (bundle.getBasePath().isPrefixOf(service.getFile().getFullPath())) {				
					bundle.addService(service);
					return;
				}
			}
		}
	}
	
	private void dispatchChangeListener() {
		
		//TODO: implement model change listener logic
		
	}

	public void activateBundle(String fullyQualifiedName) {

		
		for (Project project : projects) {
			for (Bundle bundle : project.getBundles()) {				
				if (bundle.getFullyQualifiedName().equals(fullyQualifiedName)) {
					bundle.activate();
				}
			}
		}
	}

	public void addRoute(Route route) {
		
		//TODO: add route to corresponding bundle / app
		
	}

	public void addAnnotation(Annotation annotation) {

		for (Project project : projects) {
			if (project.getPath().isPrefixOf(annotation.getSourceModule().getPath())) {
				project.addAnnotation(annotation);
				break;
			}
		}
	}

	public List<Annotation> getAnnotations(ISourceModule sourceModule) {

		for (Project project : projects) {			
			if (project.equals(sourceModule.getScriptProject())) {				
				return project.getAnnotations();
			}
		}
		
		return new ArrayList<Annotation>();
	}

	public Service getService(String className) {

		
		for (Project project : projects) {
			for(Bundle bundle : project.getBundles()) {
				for(Service service : bundle.getServices()) {

					System.out.println(service.getId());
					
					if (service.getId().equals(className))
						return service;
					
				}
			}
		}
		
		return null;
	}
}
