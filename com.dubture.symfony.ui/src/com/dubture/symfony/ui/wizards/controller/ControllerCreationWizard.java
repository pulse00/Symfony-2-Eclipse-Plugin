package com.dubture.symfony.ui.wizards.controller;

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