/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.wizards.project;

import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.php.internal.ui.wizards.PHPProjectCreationWizard;

import org.eclipse.php.composer.ui.wizard.AbstractComposerWizard;
import org.eclipse.php.composer.ui.wizard.AbstractWizardFirstPage;
import org.eclipse.php.composer.ui.wizard.AbstractWizardSecondPage;
import com.dubture.symfony.ui.SymfonyPluginImages;

/**
 * Simple extension of the {@link PHPProjectCreationWizard} to add the symfony
 * nature after the project is created.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
@SuppressWarnings("restriction")
public class SymfonyProjectCreationWizard extends AbstractComposerWizard {

	public SymfonyProjectCreationWizard() {
		setDefaultPageImageDescriptor(SymfonyPluginImages.DESC_WIZBAN_ADD_SYMFONY_FILE);
		setDialogSettings(DLTKUIPlugin.getDefault().getDialogSettings());
		setWindowTitle("New Symfony Project");
	}

	@Override
	protected AbstractWizardFirstPage getFirstPage() {
		return new SymfonyProjectWizardFirstPage();
	}

	@Override
	protected AbstractWizardSecondPage getSecondPage() {
		return new SymfonyProjectWizardSecondPage(firstPage, "Configure your new Symfony project");
	}
}
