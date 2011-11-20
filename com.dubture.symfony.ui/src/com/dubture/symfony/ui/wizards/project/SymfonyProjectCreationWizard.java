/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.wizards.project;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.wizards.PHPProjectCreationWizard;

import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.ui.SymfonyPluginImages;
import com.dubture.symfony.ui.wizards.ISymfonyProjectWizardExtension;

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

		setDefaultPageImageDescriptor(SymfonyPluginImages.DESC_WIZBAN_ADD_SYMFONY_FILE);
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
		fSecondPage = new SymfonyProjectWizardSecondPage(fFirstPage);
		fSecondPage.setTitle(PHPUIMessages.PHPProjectCreationWizard_Page2Title);
		fSecondPage
		.setDescription(PHPUIMessages.PHPProjectCreationWizard_Page2Description);
		addPage(fSecondPage);

		// Third page (Include Path)
		fThirdPage = new SymfonyProjectWizardThirdPage(fFirstPage);
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

			IProject project = fFirstPage.getProjectHandle();
			SymfonyProjectWizardFirstPage firstPage = (SymfonyProjectWizardFirstPage) fFirstPage;

			List<String> extensionNatures = new ArrayList<String>();
			
			// let extensions handle the project first
			for (ISymfonyProjectWizardExtension e : firstPage.getExtensions()) {

				ISymfonyProjectWizardExtension extension = (ISymfonyProjectWizardExtension) e;				
				String nature = extension.getNature();
				
				if (nature != null && extension.isActivated())
					extensionNatures.add(nature);

			}

			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();

			String[] newNatures = new String[natures.length + extensionNatures.size() + 1];
			System.arraycopy(natures, 0, newNatures, 1, natures.length);			
			
			newNatures[0] = SymfonyNature.NATURE_ID;
						
			for (int i=0; i < extensionNatures.size(); i++) {			
				newNatures[natures.length + 1 + i] = extensionNatures.get(i);				
			}
			
			description.setNatureIds(newNatures);
			project.setDescription(description, null);


		} catch (CoreException e) {
			Logger.logException(e);			
		}

		return res;

	}
}
