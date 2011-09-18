package com.dubture.symfony.ui.wizards.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;

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
		
		codeTemplateWizardPage = new SymfonyControllerWizardPage(selection, "NewController");
		addPage(codeTemplateWizardPage);

	}
	
	
	@Override
	public boolean performFinish() {
	
		boolean res = super.performFinish();
		
		try {
			
			IFolder folder = getProject().getFolder("/src/Acme/DemoBundle/Resources/views/ControllerName");			
			folder.create(true, false, null);
			
			IFile file  = folder.getFile("index.html.twig");
			
			String contents = "{% extends 'layout.html.twig'  %}";
			InputStream source = new ByteArrayInputStream(contents.getBytes());		
			
			file.create(source, false, null);
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
		
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