/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

import com.dubture.symfony.ui.PreferenceConstants;

public class PreferenceInitializer extends AbstractPreferenceInitializer {



	@Override
	public void initializeDefaultPreferences() {

		PreferenceConstants.initializeDefaultValues();

	}

}
