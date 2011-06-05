package org.eclipse.symfony.core.model;

import java.util.ArrayList;
import java.util.List;

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
public class Project  {

	/**
	 * The underlying project in the eclipse workspace
	 */
	private IScriptProject project = null;
	
	
	/**
	 * List of bundles this project contains
	 */
	private ArrayList<Bundle> bundles = new ArrayList<Bundle>();
	
	
	public Project(IScriptProject project) {
		
		this.project = project;		
		
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

		bundles.remove(bundle);
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
}