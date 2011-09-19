package com.dubture.symfony.twig.ui;

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
	public String getNature() {
		
		return TwigNature.NATURE_ID;
		
	}


}
