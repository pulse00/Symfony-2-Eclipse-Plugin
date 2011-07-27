package com.dubture.symfony.twig;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class SymfonyTwigPlugin extends Plugin {

	
	public static String ID = "com.dubture.symfony.twig"; //$NON-NLS-N$
	
	private static SymfonyTwigPlugin plugin;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		
		super.start(bundleContext);
		plugin = this;
		
	}
	
	public static SymfonyTwigPlugin getDefault() {
		
		return plugin;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
		plugin = null;
		
	}
	
	private static final String isDebugMode = "com.dubture.symfony.twig/debug";

	public static boolean debug() {
		
		String debugOption = Platform.getDebugOption(isDebugMode); //$NON-NLS-1$
		return getDefault().isDebugging() && "true".equalsIgnoreCase(debugOption); 
		
	}	

}
