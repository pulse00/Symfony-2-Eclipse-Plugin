package com.dubture.symfony.twig.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;

import com.dubture.symfony.ui.wizards.ISymfonyProjectWizardExtension;
import com.dubture.twig.core.TwigNature;

public class TwigProjectWizardExtension implements
		ISymfonyProjectWizardExtension {

	private Button enableTwigSupport;

	public TwigProjectWizardExtension() {

	}

	@Override
	public void addElements(Group fGroup) {

		enableTwigSupport = new Button(fGroup, SWT.CHECK | SWT.RIGHT);
		enableTwigSupport.setText("Enable Twig support");
		enableTwigSupport.setLayoutData(new GridData(SWT.BEGINNING,SWT.CENTER, false, false));
		
//		enableTwigSupport.setSelection(PHPUiPlugin.getDefault().getPreferenceStore().getBoolean((PreferenceConstants.JavaScriptSupportEnable)));
		
		
	}

	@Override
	public void performFinish(IProject project) {

		try {
			
			if (enableTwigSupport.getSelection() == false)
				return;
			
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 1, natures.length);
			newNatures[0] = TwigNature.NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
			
		} catch (CoreException e) {

			e.printStackTrace();
		}
				
	}
}
