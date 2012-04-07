/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.index.SymfonyIndexer;

public class SymfonyCorePlugin extends Plugin {

	public static String ID = "com.dubture.symfony.core";
	private static SymfonyCorePlugin plugin;


	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
	    
		super.start(bundleContext);
		plugin = this;
		
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IResourceChangeListener listener = new IResourceChangeListener()
        {
            
            @Override
            public void resourceChanged(IResourceChangeEvent event)
            {
                final IWorkspace workspace = ResourcesPlugin.getWorkspace();
                SymfonyIndexer indexer;
                try {
                    indexer = SymfonyIndexer.getInstance();
                    for (IProject project : workspace.getRoot().getProjects()) {
                        indexer.deleteServices(project.getFullPath().toString());    
                    }
                } catch (Exception e) {
                    Logger.logException(e);
                }
            }
        };
        
        workspace.addResourceChangeListener(listener, IResourceChangeEvent.PRE_BUILD);

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


	private static final String isDebugMode = "com.dubture.symfony.core/debug";

	public static boolean debug() {
		
		String debugOption = Platform.getDebugOption(isDebugMode); //$NON-NLS-1$
		return getDefault().isDebugging() && "true".equalsIgnoreCase(debugOption); 
		
	}
}
