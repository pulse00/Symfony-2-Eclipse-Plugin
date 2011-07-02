package org.eclipse.symfony.core.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.symfony.core.SymfonyCorePlugin;


/**
 * 
 * The PreferenceSupport of the Symfony core plugin.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class CorePreferencesSupport extends PreferencesSupport {

	private static CorePreferencesSupport corePreferencesSupport;

	public CorePreferencesSupport() {

		super(SymfonyCorePlugin.ID, SymfonyCorePlugin.getDefault() == null ? null
				: InstanceScope.INSTANCE.getNode(SymfonyCorePlugin.ID));		


	}

	public static CorePreferencesSupport getInstance() {
		if (corePreferencesSupport == null) {
			corePreferencesSupport = new CorePreferencesSupport();
		}

		return corePreferencesSupport;
	}	

}
