package org.eclipse.symfony.core.model;

import java.util.ArrayList;
import java.util.List;

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

	
}