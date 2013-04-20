package com.dubture.symfony.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

import com.dubture.symfony.core.SymfonyCorePlugin;
import com.dubture.symfony.ui.SymfonyUiPlugin;

@SuppressWarnings("restriction")
public class ConsolePreferencePage extends PropertyAndPreferencePage {

	public static final String PREF_ID = "com.dubture.symfony.ui.preferences.ConsolePreferencePage";
	public static final String PROP_ID = "com.dubture.symfony.ui.propertyPages.ConsolePreferencePage";
	
	private SymfonyLauncherConfigurationBlock configurationBlock;
	
	public ConsolePreferencePage() {
		setTitle("Console");
		setDescription(null);
		setPreferenceStore(SymfonyCorePlugin.getDefault().getPreferenceStore());
	}
	
	
	@Override
	public void createControl(Composite parent) {
		IWorkbenchPreferenceContainer container = (IWorkbenchPreferenceContainer) getContainer();
		configurationBlock = new SymfonyLauncherConfigurationBlock(getNewStatusChangedListener(), getProject(), container, new SymfonyLauncherBag());
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, SymfonyUiPlugin.PLUGIN_ID + "." + "symfony_launcher");
		super.createControl(parent);
	}
	
	protected void enableProjectSpecificSettings(
			boolean useProjectSpecificSettings) {
		if (configurationBlock != null) {
			configurationBlock
					.useProjectSpecificSettings(useProjectSpecificSettings);
		}
		super.enableProjectSpecificSettings(useProjectSpecificSettings);
	}
	
	
	@Override
	protected void performDefaults() {
		super.performDefaults();
		if (configurationBlock != null) {
			configurationBlock.performDefaults();
		}
	}

	@Override
	public boolean performOk() {
		if (configurationBlock != null && !configurationBlock.performOk()) {
			return false;
		}
		return super.performOk();
	}
	

	@Override
	protected Control createPreferenceContent(Composite composite) {
		return configurationBlock.createContents(composite);
	}

	@Override
	protected boolean hasProjectSpecificOptions(IProject project) {
		return configurationBlock.hasProjectSpecificOptions(getProject());
	}

	@Override
	protected String getPreferencePageID() {
		return PREF_ID;
	}

	@Override
	protected String getPropertyPageID() {
		return PROP_ID;
	}
	
	@Override
	public IPreferenceStore getPreferenceStore() {
		return SymfonyCorePlugin.getDefault().getPreferenceStore();
	}
}
