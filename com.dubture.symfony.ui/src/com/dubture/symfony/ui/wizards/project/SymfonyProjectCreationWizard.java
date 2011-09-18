package com.dubture.symfony.ui.wizards.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.internal.ui.wizards.PHPProjectCreationWizard;
import org.eclipse.php.internal.ui.wizards.PHPProjectWizardSecondPage;
import org.eclipse.php.internal.ui.wizards.PHPProjectWizardThirdPage;

import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.core.log.Logger;

/**
 *
 * Simple extension of the {@link PHPProjectCreationWizard} to add
 * the symfony nature after the project is created.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyProjectCreationWizard extends PHPProjectCreationWizard {
	
	
	public SymfonyProjectCreationWizard() {
		
		setDefaultPageImageDescriptor(PHPPluginImages.DESC_WIZBAN_ADD_PHP_PROJECT);
		setDialogSettings(DLTKUIPlugin.getDefault().getDialogSettings());
		setWindowTitle("New Symfony Project");
		
	}
	
	public void addPages() {
		
		fFirstPage = new SymfonyProjectWizardFirstPage();

		// First page
		fFirstPage.setTitle("New Symfony project");
		fFirstPage
				.setDescription("Create a Symfony project in the workspace or in an external location.");
		addPage(fFirstPage);

		// Second page (Include Path)
		fSecondPage = new PHPProjectWizardSecondPage(fFirstPage);
		fSecondPage.setTitle(PHPUIMessages.PHPProjectCreationWizard_Page2Title);
		fSecondPage
				.setDescription(PHPUIMessages.PHPProjectCreationWizard_Page2Description);
		addPage(fSecondPage);

		// Third page (Include Path)
		fThirdPage = new PHPProjectWizardThirdPage(fFirstPage);
		fThirdPage.setTitle(PHPUIMessages.PHPProjectCreationWizard_Page3Title);
		fThirdPage
				.setDescription(PHPUIMessages.PHPProjectCreationWizard_Page3Description);
		addPage(fThirdPage);

		fLastPage = fSecondPage;
	}
	
	
	
	@Override
	public boolean performFinish() {

		boolean res = super.performFinish();
		
		try {			
			
			IProject project = fLastPage.getScriptProject().getProject();
			
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 1, natures.length);
			newNatures[0] = SymfonyNature.NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
			
		} catch (CoreException e) {
			Logger.logException(e);			
		}
				
		return res;
		
	}
}