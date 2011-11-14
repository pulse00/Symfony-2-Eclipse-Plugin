/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.preferences;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.ui.Messages;
import com.dubture.symfony.ui.SymfonyUiPlugin;

/**
 * 
 * 
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ServicesPreferencePage extends PropertyAndPreferencePage {


	public static final String PREF_ID = "com.dubture.symfony.ui.preferences.ServicesPreferencePage"; //$NON-NLS-1$
	public static final String PROP_ID = "com.dubture.symfony.ui.propertyPages.ServicesPreferencePage"; //$NON-NLS-1$

	private ServiceConfigurationBlock fConfigurationBlock;
	
	public ServicesPreferencePage() {

		setPreferenceStore(SymfonyUiPlugin.getDefault().getPreferenceStore());

		// only used when page is shown programatically
		setTitle(Messages.ServicesPreferencePage_0);


	}


	@Override
	public void createControl(Composite parent) {

		IWorkbenchPreferenceContainer container = (IWorkbenchPreferenceContainer) getContainer();
		fConfigurationBlock = new ServiceConfigurationBlock(
				getNewStatusChangedListener(), getProject(), container);

		super.createControl(parent);

		//		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
		//				IPHPHelpContextIds.PHP_INTERPRETER_PREFERENCES);


	}

	@Override
	protected Control createPreferenceContent(Composite composite) {

		return fConfigurationBlock.createContents(composite);
	}

	@Override
	protected boolean hasProjectSpecificOptions(IProject project) {

		return fConfigurationBlock.hasProjectSpecificOptions(project);
	}

	@Override
	protected String getPreferencePageID() {

		return PREF_ID;
	}

	@Override
	protected String getPropertyPageID() {

		return PROP_ID;
	}

	protected void enableProjectSpecificSettings(
			boolean useProjectSpecificSettings) {
		if (fConfigurationBlock != null) {
			fConfigurationBlock
			.useProjectSpecificSettings(useProjectSpecificSettings);
		}
		super.enableProjectSpecificSettings(useProjectSpecificSettings);
	}

	protected void performDefaults() {
		super.performDefaults();
		if (fConfigurationBlock != null) {
			fConfigurationBlock.performDefaults();
		}
	}

	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		
		
		
		if (fConfigurationBlock != null && !fConfigurationBlock.performOk()) {
			Logger.debugMSG("return false");
			return false;
		}
		return super.performOk();
	}

	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performApply()
	 */
	public void performApply() {
		
		
		if (fConfigurationBlock != null) {
			fConfigurationBlock.performApply();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 */
	public void dispose() {
		if (fConfigurationBlock != null) {
			fConfigurationBlock.dispose();
		}
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#setElement
	 * (org.eclipse.core.runtime.IAdaptable)
	 */
	public void setElement(IAdaptable element) {
		super.setElement(element);
		setDescription(null); // no description for property page
	}	


}
