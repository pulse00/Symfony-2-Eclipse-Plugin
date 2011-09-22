package com.dubture.symfony.ui.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;

/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public abstract class CodeTemplateWizardPage extends WizardPage {

	protected String containerName;
	protected String initialFileName;

	protected CodeTemplateWizardPage(String pageName, String initialFileName) {
		super(pageName);
		
		this.initialFileName = initialFileName;

	}
	
	public String getContainerName() {
		return containerName;
	}
	
	public void setContainerName(String containerPath) {
		containerName = containerPath;
	}
	
	
	public IProject getProject() {
		String projectName = getContainerName();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(projectName));
		IProject project = null;
		if (resource instanceof IProject) {
			project = (IProject) resource;
		} else if (resource != null) {
			project = resource.getProject();
		}
		return project;
	}
	
	protected IContainer getContainer(final String text) {
		final Path path = new Path(text);

		final IResource resource = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(path);
		return resource instanceof IContainer ? (IContainer) resource : null;

	}
	
	public abstract String getFileName();


}
