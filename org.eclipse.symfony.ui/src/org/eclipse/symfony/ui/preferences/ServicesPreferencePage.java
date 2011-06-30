package org.eclipse.symfony.ui.preferences;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.symfony.ui.Messages;
import org.eclipse.symfony.ui.SymfonyUiPlugin;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

@SuppressWarnings("restriction")
public class ServicesPreferencePage extends PropertyAndPreferencePage {


	public static final String PREF_ID = "org.eclipse.symfony.ui.preferences.ServicesPreferencePage"; //$NON-NLS-1$
	public static final String PROP_ID = "org.eclipse.symfony.ui.propertyPages.ServicesPreferencePage"; //$NON-NLS-1$

	private SymfonyServiceConfigurationBlock fConfigurationBlock;

	public ServicesPreferencePage() {

		setPreferenceStore(SymfonyUiPlugin.getDefault().getPreferenceStore());

		// only used when page is shown programatically
		setTitle(Messages.ServicesPreferencePage_0);


	}


	@Override
	public void createControl(Composite parent) {

		IWorkbenchPreferenceContainer container = (IWorkbenchPreferenceContainer) getContainer();
		fConfigurationBlock = new SymfonyServiceConfigurationBlock(
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
			return false;
		}
		return super.performOk();
	}

	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performApply()
	 */
	public void performApply() {
		
		
		if (fConfigurationBlock != null) {
			System.err.println("perform apply");
			fConfigurationBlock.performApply();
		} else System.err.println("null");
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
