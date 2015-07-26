/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.preferences;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.dubture.symfony.core.preferences.SymfonyCoreConstants;
import com.dubture.symfony.ui.Messages;
import com.dubture.symfony.ui.SymfonyUiPlugin;

/**
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public class SymfonyPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public SymfonyPreferencePage() {
		super();

		setPreferenceStore(SymfonyUiPlugin.getDefault().getPreferenceStore());
		setDescription(Messages.SymfonyPreferencePage_0);
	}

	public void init(IWorkbench workbench) {

	}

	@Override
	protected Control createContents(Composite parent) {
		initializeDialogUnits(parent);

		Composite result = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = 0;
		layout.verticalSpacing = convertVerticalDLUsToPixels(10);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		result.setLayout(layout);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, SymfonyUiPlugin.PLUGIN_ID + "." + "symfony_preferences");

		return super.createContents(parent);
	}

	@Override
	protected void createFieldEditors() {

		String[][] options;

	}
}
