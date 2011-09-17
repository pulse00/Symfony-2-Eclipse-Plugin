package com.dubture.symfony.ui.wizards.classes;

import com.dubture.symfony.ui.wizards.CodeTemplateWizard;

public class ClassCreationWizard extends CodeTemplateWizard {

	
	public ClassCreationWizard() {
		super();
		setWindowTitle("Create class"); 
		setNeedsProgressMonitor(true);
	}	
	
	public void addPages() {
		
		codeTemplateWizardPage = new ClassCreationWizardPage(selection, "NewClass");
		addPage(codeTemplateWizardPage);

	}
		

	@Override
	protected String getFileName() {
		
		return codeTemplateWizardPage.getFileName() + ".php";
		
	}
	
	@Override
	protected String getTemplateName() {

		return "symfonyclass";
	}


	@Override
	protected String getContextTypeID() {
		
		return "php_new_file_context";
		
	}	
}
