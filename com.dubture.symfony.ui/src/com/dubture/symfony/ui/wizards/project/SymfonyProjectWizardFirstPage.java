/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.wizards.project;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.php.internal.ui.wizards.CompositeData;
import org.eclipse.php.internal.ui.wizards.LocationGroup;
import org.eclipse.php.internal.ui.wizards.NameGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import com.dubture.composer.ui.converter.String2KeywordsConverter;
import com.dubture.composer.ui.wizard.project.BasicSettingsGroup;
import com.dubture.composer.ui.wizard.project.template.PackageProjectWizardFirstPage;
import com.dubture.composer.ui.wizard.project.template.Validator;
import com.dubture.getcomposer.core.ComposerPackage;

@SuppressWarnings("restriction")
public class SymfonyProjectWizardFirstPage extends PackageProjectWizardFirstPage implements IShellProvider {

	private Validator projectTemplateValidator;
	
	
	@Override
	public void createControl(Composite parent) {
		
		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setFont(parent.getFont());
		composite.setLayout(initGridLayout(new GridLayout(1, false), false));
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		initialName = "";
		nameGroup = new NameGroup(composite, initialName, getShell());
		nameGroup.addObserver(this);
		PHPLocationGroup = new LocationGroup(composite, nameGroup, getShell());
		
		final Group group = new Group(composite, SWT.None);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new GridLayout(3, false));
		group.setText("Optional information (composer.json)");
		
		settingsGroup = new BasicSettingsGroup(group, getShell());
		settingsGroup.addObserver(this);

		CompositeData data = new CompositeData();
		data.setParetnt(composite);
		data.setSettings(getDialogSettings());
		data.setObserver(PHPLocationGroup);

		versionGroup = new SymfonyVersionGroup(this, composite);
		nameGroup.addObserver(PHPLocationGroup);
		nameGroup.notifyObservers();
		projectTemplateValidator = new Validator(this);
		
		nameGroup.addObserver(projectTemplateValidator);
		PHPLocationGroup.addObserver(projectTemplateValidator);

		Dialog.applyDialogFont(composite);
		
		setControl(composite);
		composerPackage = new ComposerPackage();
		keywordConverter = new String2KeywordsConverter(composerPackage);
		
		setHelpContext(composite);
		
	}

	@Override
	protected void setHelpContext(Control container) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean doesOverrideComposer() {
		return true;
	}

	public String getSymfonyVersion() {
		return ((SymfonyVersionGroup)versionGroup).getSymfonyVersion();
	}
}
