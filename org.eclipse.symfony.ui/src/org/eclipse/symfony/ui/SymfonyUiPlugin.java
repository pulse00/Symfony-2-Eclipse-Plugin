package org.eclipse.symfony.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.symfony.ui.listener.OpenDocumentListener;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class SymfonyUiPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.symfony.ui"; //$NON-NLS-1$

	// The shared instance
	private static SymfonyUiPlugin plugin;
	
	/**
	 * The constructor
	 */
	public SymfonyUiPlugin() {
		
		
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		
		//TODO: implement OpenDocumentListener for file links in 
		// symfony exception pages
		Display.getDefault().addListener(SWT.OpenDocument, new OpenDocumentListener());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;		
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static SymfonyUiPlugin getDefault() {
		return plugin;
	}

}
