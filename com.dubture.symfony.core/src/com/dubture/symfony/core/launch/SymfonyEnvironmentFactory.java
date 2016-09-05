package com.dubture.symfony.core.launch;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.composer.core.launch.environment.AbstractEnvironmentFactory;
import org.eclipse.php.composer.core.launch.environment.PrjPharEnvironment;

import com.dubture.symfony.core.SymfonyCorePlugin;
import com.dubture.symfony.core.preferences.CorePreferenceConstants.Keys;

/**
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public class SymfonyEnvironmentFactory extends AbstractEnvironmentFactory {

	public static final String FACTORY_ID = "com.dubture.symfony.core.launcherfactory";
	
	@Override
	protected IPreferenceStore getPreferenceStore() {
		return SymfonyCorePlugin.getDefault().getPreferenceStore();
	}

	@Override
	protected String getPluginId() {
		return SymfonyCorePlugin.ID;
	}

	@Override
	protected PrjPharEnvironment getProjectEnvironment(String executable) {
		return new SysPhpProjectConsole(executable);
	}

	@Override
	protected String getExecutableKey() {
		return Keys.PHP_EXECUTABLE;
	}

	@Override
	protected String getUseProjectKey() {
		return Keys.USE_PROJECT_PHAR;
	}

	@Override
	protected String getScriptKey() {
		return Keys.CONSOLE;
	}
}
