package com.dubture.symfony.ui.preferences;

import org.eclipse.php.internal.ui.preferences.util.Key;
import org.pdtextensions.core.ui.preferences.launcher.LauncherKeyBag;

import com.dubture.symfony.core.SymfonyCorePlugin;
import com.dubture.symfony.core.preferences.CorePreferenceConstants;

/**
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyLauncherBag implements LauncherKeyBag {

	private final Key exeKey = new Key(SymfonyCorePlugin.ID, CorePreferenceConstants.Keys.PHP_EXECUTABLE); 
	private final Key pharKey = new Key(SymfonyCorePlugin.ID, CorePreferenceConstants.Keys.CONSOLE); 
	private final Key useKey = new Key(SymfonyCorePlugin.ID, CorePreferenceConstants.Keys.USE_PROJECT_PHAR); 
	
	@Override
	public Key[] getAllKeys() {
		return new Key[]{exeKey, pharKey, useKey};
	}

	@Override
	public Key getPHPExecutableKey() {
		return exeKey;
	}

	@Override
	public Key getScriptKey() {
		return pharKey;
	}

	@Override
	public Key getUseProjectKey() {
		return useKey;
	}
}
