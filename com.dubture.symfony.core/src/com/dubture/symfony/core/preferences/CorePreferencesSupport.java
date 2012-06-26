/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;

import com.dubture.symfony.core.SymfonyCorePlugin;


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
                : DefaultScope.INSTANCE.getNode(SymfonyCorePlugin.ID));


    }

    public static CorePreferencesSupport getInstance() {
        if (corePreferencesSupport == null) {
            corePreferencesSupport = new CorePreferencesSupport();
        }

        return corePreferencesSupport;
    }

}
