package org.eclipse.symfony.core.model;

import org.eclipse.core.resources.IProject;

public class Project {

	private IProject project = null;
	
	public Project(IProject project) {
		
		this.project = project;		
		
	}
	
	public IProject getRecource() {
		
		return project;
		
	}
	
	public String toDebugString() {
				
		String debug = project.getName();		
		return debug;
		
	}
	
	@Override
	public String toString() {

		return project.getName();
		
	}
}