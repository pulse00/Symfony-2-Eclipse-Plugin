package org.eclipse.symfony.core.preferences;

import org.eclipse.php.internal.core.preferences.PreferencesSupport;
import org.eclipse.symfony.core.SymfonyCorePlugin;


@SuppressWarnings("restriction")
public class CorePreferencesSupport extends PreferencesSupport {

	private static CorePreferencesSupport corePreferencesSupport;

	//TODO: check how to avoid deprecated usage of getPluginPreferences
	@SuppressWarnings("deprecation")
	public CorePreferencesSupport() {

		super(SymfonyCorePlugin.ID, SymfonyCorePlugin.getDefault() == null ? null
				: SymfonyCorePlugin.getDefault().getPluginPreferences());		


	}

	public static CorePreferencesSupport getInstance() {
		if (corePreferencesSupport == null) {
			corePreferencesSupport = new CorePreferencesSupport();
		}

		return corePreferencesSupport;
	}	

}
