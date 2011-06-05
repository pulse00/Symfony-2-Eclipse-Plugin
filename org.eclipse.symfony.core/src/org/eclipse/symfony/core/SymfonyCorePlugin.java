package org.eclipse.symfony.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class SymfonyCorePlugin implements BundleActivator {

	private static BundleContext context;
	
	public static String ID = "org.eclipse.symfony.core";

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		SymfonyCorePlugin.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		SymfonyCorePlugin.context = null;
	}

}
