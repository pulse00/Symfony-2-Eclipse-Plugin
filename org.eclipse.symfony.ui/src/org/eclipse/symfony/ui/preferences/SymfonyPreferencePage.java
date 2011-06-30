package org.eclipse.symfony.ui.preferences;

import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.symfony.ui.Messages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class SymfonyPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	
	
	public SymfonyPreferencePage() {
		super();
		
		setPreferenceStore(DLTKUIPlugin.getDefault().getPreferenceStore());
		setDescription(Messages.SymfonyPreferencePage_0);
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
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

		Group doubleClickGroup = new Group(result, SWT.NONE);
		doubleClickGroup.setLayout(new GridLayout());
		doubleClickGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		doubleClickGroup
				.setText("foobar"); //$NON-NLS-1$

		Dialog.applyDialogFont(result);
		return result;		
	}
	
}