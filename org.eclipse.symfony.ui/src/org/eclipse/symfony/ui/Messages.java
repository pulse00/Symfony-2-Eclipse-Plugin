package org.eclipse.symfony.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.symfony.ui.messages"; //$NON-NLS-1$
	public static String ServicesPreferencePage_0;
	public static String SymfonyPreferencePage_0;
	public static String SymfonyServiceConfigurationBlock_0;
	public static String SymfonyServiceConfigurationBlock_1;
	public static String SymfonyServiceConfigurationBlock_2;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
