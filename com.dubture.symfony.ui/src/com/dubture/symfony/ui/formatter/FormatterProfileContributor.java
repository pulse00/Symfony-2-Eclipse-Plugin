/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.formatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dubture.pdt.formatter.internal.ui.preferences.formatter.IProfileContributor;
import com.dubture.pdt.formatter.internal.ui.preferences.formatter.IProfileVersioner;
import com.dubture.pdt.formatter.internal.ui.preferences.formatter.ProfileManager.BuiltInProfile;
import com.dubture.pdt.formatter.internal.ui.preferences.formatter.ProfileVersioner;
import com.dubture.symfony.ui.SymfonyUiPlugin;

public class FormatterProfileContributor implements IProfileContributor {

	public final static String SYMFONY_PROFILE = SymfonyUiPlugin.PLUGIN_ID
			+ ".default.symfony_profile"; //$NON-NLS-1$
	
	@Override
	public List<BuiltInProfile> getBuiltinProfiles(IProfileVersioner versioner)
	{
		
		List<BuiltInProfile> profiles = new ArrayList<BuiltInProfile>();
		
		final BuiltInProfile symfonyProfile = new BuiltInProfile(SYMFONY_PROFILE,
				"Symfony [built in]",
				getSymfonySettings(), 2, versioner.getCurrentVersion(),
				versioner.getProfileKind());
		
		profiles.add(symfonyProfile);		
		
		return profiles;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map getSymfonySettings() {
		
		final Map options = SymfonyFormatterOptions.getDefaultSettings().getMap();
		ProfileVersioner.setLatestCompliance(options);
		return options;
	}
	

}
