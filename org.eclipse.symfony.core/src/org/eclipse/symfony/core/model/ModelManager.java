package org.eclipse.symfony.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

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
}
