package com.dubture.symfony.ui.wizards;

import org.eclipse.php.internal.ui.wizards.PHPFileCreationWizard;


/**
 * 
 * Wizard for creating Symfony controllers.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ControllerCreationWizard extends PHPFileCreationWizard {
	
	
	public ControllerCreationWizard() {
		super();
		
		
		//TODO: externalize string
		setWindowTitle("New Symfony Controller"); 
		setNeedsProgressMonitor(true);
	}
	
	
	@Override
	public void addPages() {

		phpFileCreationWizardPage = new SymfonyControllerWizardPage(selection);
		addPage(phpFileCreationWizardPage);

		newPhpTemplatesWizardPage = new NewControllerWizardPage();
		newPhpTemplatesWizardPage.setWizard(this);
		
	}

}
