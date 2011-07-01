package org.eclipse.symfony.core;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class SymfonyCorePlugin extends Plugin {

	public static String ID = "org.eclipse.symfony.core";
	private static SymfonyCorePlugin plugin;
	
	public static boolean DEBUG = true;


	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);

		plugin = this;
		System.err.println("startup symfony");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
		plugin = null;
	}
	
	public static SymfonyCorePlugin getDefault() {
		
		return plugin;
	}

	@SuppressWarnings("rawtypes")
	public static void debug(Class clazz, String message) {

		if (DEBUG) {			
//			System.err.print(clazz.toString() + ": " );
			System.out.println(message);
		}	
	}

	public static void debug(String message) {

		if (DEBUG) {			
			System.out.println(message);
		}	
		
		
	}
}
