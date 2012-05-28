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

import com.dubture.composer.core.builder.ComposerNature;
import com.dubture.symfony.ui.wizards.ISymfonyProjectWizardExtension;

/**
 *
 */
public class ComposerProjectWizardExtension implements
        ISymfonyProjectWizardExtension
{

    private Button enableComposerSupport;
    
    @Override
    public void addElements(Group fGroup) {

        enableComposerSupport = new Button(fGroup, SWT.CHECK | SWT.RIGHT);
        enableComposerSupport.setText("Enable Composer support");
        enableComposerSupport.setLayoutData(new GridData(SWT.BEGINNING,SWT.CENTER, false, false));
        enableComposerSupport.setSelection(true);

    }

    @Override
    public String getNature() {

        return ComposerNature.NATURE_ID;
    }

    @Override
    public boolean isActivated() {

        return enableComposerSupport.getSelection();
    }
}
