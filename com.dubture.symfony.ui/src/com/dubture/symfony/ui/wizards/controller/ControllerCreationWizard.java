package com.dubture.symfony.ui.wizards.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.ui.wizards.CodeTemplateWizard;


/**
 * 
 * Wizard for creating Symfony controllers.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class ControllerCreationWizard extends CodeTemplateWizard {
	
	public ControllerCreationWizard() {
		super();
		setWindowTitle("Create Controller"); 
		setNeedsProgressMonitor(true);
	}	
	

	/**
	 * Adding the page to the wizard.
	 */
	public void addPages() {
		
		codeTemplateWizardPage = new ControllerWizardPage(selection, "NewController");
		addPage(codeTemplateWizardPage);

	}
	
	
	
	@Override
	@SuppressWarnings("restriction")
	public boolean performFinish() {
	
		boolean res = super.performFinish();
		
		try {
			
			SymfonyModelAccess model = SymfonyModelAccess.getDefault();			
			String container = codeTemplateWizardPage.getContainerName();			
			IScriptProject project = DLTKCore.create(getProject());
			
			if (project == null)
				return res;
			
			List<Bundle> bundles = model.findBundles(project);			
			Bundle bundle = null;
			
			for (Bundle ns : bundles) {								
				if (container.startsWith(ns.getPath().toString())) {					
					bundle = ns;
					break;
				}
			}
			
			if (bundle == null) {			
				Logger.log(Logger.ERROR, "unable to resolve bundle for container: " + container);
				return res;
			}
						
			IPath viewPath = bundle.getPath().removeFirstSegments(1).append("Resources/views");
			IFolder folder = getProject().getFolder(viewPath);
			
			if (!folder.exists()) {				
				Logger.log(Logger.ERROR, "Unable to find bundle folder " + bundle.getPath().toString());
				return res;
			}
			
			IFolder viewFolder = getProject().getFolder(viewPath.append(getFileName().replace("Controller.php", "")));			
			viewFolder.create(true, false, null);
			
			for (String action : getActions()) {
				
				IFile file  = viewFolder.getFile(String.format("%s.html.twig", action));
				String contents = String.format("{%% extends '%s::layout.html.twig'  %%}", bundle.getElementName());
				InputStream source = new ByteArrayInputStream(contents.getBytes());			
				file.create(source, false, null);
				
			}
			
		} catch (Exception e) {
			Logger.logException(e);
		}
		
		return res;
		
	}

	private List<String> getActions() {

		return ( (ControllerWizardPage) codeTemplateWizardPage).getActions();
		
	}


	@Override
	protected String getFileName() {

		String name = codeTemplateWizardPage.getFileName();
		
		if (!name.endsWith("Controller"))
			name += "Controller";
		
		return name + ".php";

	}


	@Override
	protected String getTemplateName() {

		return "symfonycontroller";		
	}


	@Override
	protected String getContextTypeID() {
		
		return "php_new_file_context";
		
	}
}