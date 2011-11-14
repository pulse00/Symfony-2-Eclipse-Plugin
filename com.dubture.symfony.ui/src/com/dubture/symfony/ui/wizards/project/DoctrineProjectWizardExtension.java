/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.wizards.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;

import com.dubture.doctrine.core.DoctrineNature;
import com.dubture.symfony.ui.wizards.ISymfonyProjectWizardExtension;

public class DoctrineProjectWizardExtension implements
		ISymfonyProjectWizardExtension {

	private Button enableDoctrineSupport;
	
	public DoctrineProjectWizardExtension() {

	}

	@Override
	public void addElements(Group fGroup) {

		enableDoctrineSupport = new Button(fGroup, SWT.CHECK | SWT.RIGHT);
		enableDoctrineSupport.setText("Enable Doctrine support");
		enableDoctrineSupport.setLayoutData(new GridData(SWT.BEGINNING,SWT.CENTER, false, false));
		enableDoctrineSupport.setSelection(true);
		

	}

	@Override
	public String getNature() {

		return DoctrineNature.NATURE_ID;
	}

	@Override
	public boolean isActivated() {

		return enableDoctrineSupport.getSelection();
	}

}
