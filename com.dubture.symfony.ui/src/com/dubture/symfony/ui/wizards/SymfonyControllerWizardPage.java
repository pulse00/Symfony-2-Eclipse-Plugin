package com.dubture.symfony.ui.wizards;

import org.eclipse.jface.viewers.ISelection;

import org.eclipse.php.internal.ui.wizards.PHPFileCreationWizardPage;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * The Wizard page for the {@link ControllerCreationWizard}
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyControllerWizardPage extends PHPFileCreationWizardPage {

	public SymfonyControllerWizardPage(ISelection selection) {
		super(selection);

	}
	
	public void createControl(final Composite parent) {
	
		super.createControl(parent);		
		//containerText.setVisible(false);
		fileText.setText("NewController.php");
		fileText.setFocus();
		fileText.setSelection(0, 3);
		
	}

}