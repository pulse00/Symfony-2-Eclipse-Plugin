package com.dubture.symfony.ui.wizards.project;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.internal.ui.wizards.PHPProjectWizardFirstPage;
import org.eclipse.php.internal.ui.wizards.PHPProjectWizardSecondPage;

@SuppressWarnings("restriction")
public class SymfonyProjectWizardSecondPage extends PHPProjectWizardSecondPage {

	public SymfonyProjectWizardSecondPage(PHPProjectWizardFirstPage mainPage) {
		super(mainPage);
	}
	
	
	@Override
	protected void updateProject(IProgressMonitor monitor)
			throws CoreException, InterruptedException {

		super.updateProject(monitor);
		

		IScriptProject project = getScriptProject();
		
		IFolder folder = project.getProject().getFolder("/foobar");
		
		folder.create(true, false, null);
		
		System.err.println("createed");
		
		
		
		
		
		
	}
}
