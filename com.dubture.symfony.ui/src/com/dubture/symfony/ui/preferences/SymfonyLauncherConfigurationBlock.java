package com.dubture.symfony.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.ui.preferences.IStatusChangeListener;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.pdtextensions.core.ui.preferences.launcher.LauncherConfigurationBlock;
import org.pdtextensions.core.ui.preferences.launcher.LauncherKeyBag;

import com.dubture.symfony.core.SymfonyCorePlugin;

@SuppressWarnings("restriction")
public class SymfonyLauncherConfigurationBlock extends LauncherConfigurationBlock {

	public SymfonyLauncherConfigurationBlock(IStatusChangeListener context, IProject project,
			IWorkbenchPreferenceContainer container, LauncherKeyBag keyBag) {
		super(context, project, container, keyBag);
	}

	@Override
	protected String getPluginId() {
		return SymfonyCorePlugin.ID;
	}

	@Override
	protected void afterSave() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beforeSave() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getHeaderLabel() {
		return "Select the PHP executable to be used for running the symfony console.";
	}

	@Override
	protected String getProjectChoiceLabel() {
		return "fahht";
	}

	@Override
	protected String getGlobalChoiceLabel() {
		return "fahh2";
	}

	@Override
	protected String getScriptLabel() {
		return "Console script";
	}

	@Override
	protected String getButtonGroupLabel() {
		return "Console selection";
	}

	@Override
	protected String getScriptFieldLabel() {
		return "Custom console script";
	}

	@Override
	protected boolean validateScript(String text) {
		return false;
	}
}
