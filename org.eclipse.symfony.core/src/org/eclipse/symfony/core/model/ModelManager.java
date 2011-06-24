package org.eclipse.symfony.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.symfony.core.goals.ContainerAwareGoalEvaluatorFactory;
import org.eclipse.symfony.core.model.listener.IModelClearListener;


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
		
		for (Project project : projects) {
			if (project.getPath().isPrefixOf(service.getFile().getFullPath())) {
				project.addService(service);				
				
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

	
	/**
	 * 
	 * @param Retrieve a single service by service id
	 * 
	 * 
	 * @return
	 */
	public Service getService(String id) {

		// TODO: pass in the ScriptProject so we can check
		// for the correct project scope.

		
		// check for a project-scoped service first
		for (Project project : projects) {			
			if(project.hasService(id)) {				
				return project.getService(id);
			}
		}
		
		for (Project project : projects) {
			for(Bundle bundle : project.getBundles()) {
				for(Service service : bundle.getServices()) {
					if (service.getId().equals(id))
						return service;
					
				}
			}
		}
		
		return null;
	}

	
	/**
	 * 
	 * @see ContainerAwareGoalEvaluatorFactory
	 * 
	 * @return
	 */
	public IEvaluatedType getControllerType() {
		
		
		//TOOD: find a way to get the IEvaluatedType of the Controller class,
		// so it can be checked against in goal evaluators

		return null;
	}

	
	/**
	 * Return all services of a {@link Project} or null if the
	 * project hasn't been found.
	 * 
	 * @param scriptProject
	 * @return
	 */
	public List<Service> getServices(IScriptProject scriptProject) {

		for (Project project : projects) {			
			if (project.equals(scriptProject)) {				
				return project.getServices();
			}
		}

		return null;
		
	}

	public void addController(Controller controller) {
		
		IScriptProject project = controller.getProject();
		
		for (Project p : projects) {
			
			if (p.equals(project)) {
				p.addController(controller);
				break;
			}			
		}		
	}

	/**
	 * 
	 * Search for all template variables that have been set in Controllers
	 * for this sourceModule.
	 * 
	 * 
	 * @param sourceModule
	 * @return
	 */
	public List<TemplateVariable> findTemplateVariables(ISourceModule sourceModule) {

		Bundle bundle = getBundle(sourceModule);
		
		if (bundle == null) {
			System.err.println("No matching bundle found for viewvariable fetching");
			return null;
		}
		
		return bundle.getTemplateVariables(sourceModule);
		
		
	}

	private Bundle getBundle(ISourceModule sourceModule) {

		for (Project project : projects) {
			
			for (Bundle bundle : project.getBundles()) {
				
				if (bundle.getBasePath().isPrefixOf(sourceModule.getPath()))
					return bundle;
			}
		}
		
		return null;
	}

	public TemplateVariable findTemplateVariable(ISourceModule sourceModule, String varName) {
		
		Bundle bundle = getBundle(sourceModule);
		
		if (bundle == null)
			return null;
		
		return bundle.getTemplateVariable(sourceModule, varName);
		
	}
}
