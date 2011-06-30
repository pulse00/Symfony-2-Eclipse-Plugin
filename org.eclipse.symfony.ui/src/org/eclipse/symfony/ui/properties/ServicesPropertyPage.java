package org.eclipse.symfony.ui.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.preferences.PHPVersionConfigurationBlock;
import org.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.symfony.ui.SymfonyUiPlugin;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

@SuppressWarnings("restriction")
public class ServicesPropertyPage extends PropertyAndPreferencePage {


	public static final String PREF_ID = "org.eclipse.symfony.ui.preferences.ServicesPreferencePage"; //$NON-NLS-1$
	public static final String PROP_ID = "org.eclipse.symfony.ui.propertyPages.ServiecsPreferencePage"; //$NON-NLS-1$

	private PHPVersionConfigurationBlock fConfigurationBlock;


	public ServicesPropertyPage() {

		setPreferenceStore(SymfonyUiPlugin.getDefault().getPreferenceStore());
		setTitle("Dependency Injection Services");

	}


	@Override
	public void createControl(Composite parent) {

		IWorkbenchPreferenceContainer container = (IWorkbenchPreferenceContainer) getContainer();
		fConfigurationBlock = new PHPVersionConfigurationBlock(
				getNewStatusChangedListener(), getProject(), container);

		super.createControl(parent);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IPHPHelpContextIds.PHP_INTERPRETER_PREFERENCES);		
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

}
