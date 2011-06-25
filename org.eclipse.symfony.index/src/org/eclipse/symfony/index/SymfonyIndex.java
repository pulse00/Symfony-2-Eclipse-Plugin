package org.eclipse.symfony.index;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The {@link SymfonyIndex} plugin. Holds
 * the SQL index.
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public class SymfonyIndex extends Plugin {

	public static final String PLUGIN_ID = "org.eclipse.symfony.index"; //$NON-NLS-1$
	private static SymfonyIndex plugin;
	
	private static final ListenerList shutdownListeners = new ListenerList();
	
	

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		plugin = this;
	}
	
	public static void addShutdownListener(IShutdownListener listener) {
		shutdownListeners.add(listener);
	}	

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
		
		Object[] listeners = shutdownListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			((IShutdownListener) listeners[i]).shutdown();
		}
		shutdownListeners.clear();
		plugin = null;
	}
	
	public static SymfonyIndex getDefault() {
		
		return plugin;
		
	}
}
