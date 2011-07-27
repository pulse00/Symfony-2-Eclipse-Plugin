package com.dubture.symfony.ui;


import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.dubture.symfony.ui.viewsupport.ImagesOnFileSystemRegistry;

/**
 * The activator class controls the plug-in life cycle
 */
public class SymfonyUiPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.dubture.symfony.ui"; //$NON-NLS-1$

	// The shared instance
	private static SymfonyUiPlugin plugin;

	
	private ImagesOnFileSystemRegistry fImagesOnFSRegistry;
	
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
		// currently this is not used, as eclipse itself handles this
		// event and opens the file
		//Display.getDefault().addListener(SWT.OpenDocument, new OpenDocumentListener());
		

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
	
	public ImagesOnFileSystemRegistry getImagesOnFSRegistry() {
		if (fImagesOnFSRegistry == null) {
			fImagesOnFSRegistry = new ImagesOnFileSystemRegistry();
		}
		return fImagesOnFSRegistry;
	}
	

}
