package org.eclipse.symfony.core.model;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.symfony.core.goals.ContainerAwareGoalEvaluatorFactory;
import org.eclipse.symfony.core.model.listener.IModelChangedListener;
import org.eclipse.symfony.core.model.listener.IModelClearListener;
import org.eclipse.symfony.index.IServiceHandler;
import org.eclipse.symfony.index.SymfonyIndexer;



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
@SuppressWarnings("restriction")
public class ModelManager {
	
	private static ModelManager instance;
	
	private List<Project> projects = new ArrayList<Project>();
	
	private List<IModelClearListener> modelClearListeners = new ArrayList<IModelClearListener>();
	private List<IModelChangedListener> modelChangeListeners = new ArrayList<IModelChangedListener>();

	private SymfonyIndexer index;
	
	

	
	private ModelManager() {	

		try {
			index = SymfonyIndexer.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		fireModelChangedEvent();
		
	}

	public void addBundle(Bundle bundle) {

		IScriptProject scriptProject = bundle.getProject();
				
		boolean found = false;
		
		for (Project project : projects) {
			
			if (project.equals(scriptProject)) {
				project.addBundle(bundle);				
				fireModelChangedEvent();
				found = true;
				break;
				
			} 
		}
		
		if (found == false) {			
			Project project = new Project(scriptProject);			
			project.addBundle(bundle);
			addProject(project);
			fireModelChangedEvent();
		}
	}
	
	
	public void addService(Service service) {
		
//		System.err.println("+++ adding service " + service.getClassName());
		for (Project project : projects) {
			for (Bundle bundle : project.getBundles()) {
				if (bundle.getBasePath().isPrefixOf(service.getFile().getFullPath())) {				
					bundle.addService(service);
					fireModelChangedEvent();
					return;
				}
			}
		}
		
		for (Project project : projects) {
			if (project.getPath().isPrefixOf(service.getFile().getFullPath())) {
				project.addService(service);				
				
			}
		}
		
		fireModelChangedEvent();
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
				fireModelChangedEvent();
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
	public Service findService(final String id, IPath path) {
		
		final List<Service> services = new ArrayList<Service>();		
		String pathString = path == null ? "" : path.toString();
		
		index.findService(id, pathString, new IServiceHandler() {
			
			@Override
			public void handle(String id, String phpClass, String path) {
				services.add(new Service(id, phpClass, path, null));				
			}
		});
		
		
		if (services.size() == 1)
			return services.get(0);
		
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
	 * @param path
	 * @return
	 */
	public List<Service> findServices(IPath path) {

		final List<Service> services = new ArrayList<Service>();
		
		index.findServices(path.toString(), new IServiceHandler() {
			
			@Override
			public void handle(String id, String phpClass, String path) {
				services.add(new Service(id, phpClass, path));
				
			}
		});
		
		return services;
		
	}

	public void addController(Controller controller) {
		
		IScriptProject project = controller.getProject();
		
		for (Project p : projects) {
			
			if (p.equals(project)) {
				p.addController(controller);
				fireModelChangedEvent();
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
	
	private void fireModelChangedEvent() {
		
		for (IModelChangedListener listener : modelChangeListeners) {
			listener.modelChanged();
		}
		
	}

	public void addModelChangeListener(IModelChangedListener listener) {

		modelChangeListeners.add(listener);
		
	}


	public Service findService(String className) {

		return findService(className, null);
	}
}