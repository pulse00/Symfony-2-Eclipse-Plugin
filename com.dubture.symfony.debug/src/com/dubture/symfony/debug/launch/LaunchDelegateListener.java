/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.debug.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.debug.core.debugger.launching.ILaunchDelegateListener;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.AppKernel;
import com.dubture.symfony.core.model.SymfonyKernelAccess;
import com.dubture.symfony.debug.server.SymfonyServer;
import com.dubture.symfony.debug.util.ServerUtils;
import com.dubture.symfony.index.model.Route;
import com.dubture.symfony.ui.editor.EditorUtility;

/**
 * 
 * A PHP Debug launchDelegate that injects the correct Launch URL
 * for a corresponding Symfony route.
 *  
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class LaunchDelegateListener implements ILaunchDelegateListener {


	@Override
	public int preLaunch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) {
		
		EditorUtility utility = new EditorUtility();		
		Route route = utility.getRouteAtCursor();
		IScriptProject project = utility.getProject();
		
		String env = "";
		try {
			env = configuration.getAttribute(SymfonyServer.ENVIRONMENT, "");
		} catch (CoreException e) {
			Logger.logException(e);
		}
		
		AppKernel kernel = null;
		
		if (env.length() != 0) {
			kernel = SymfonyKernelAccess.getDefault().getKernel(project, env);
		} else {
			kernel = SymfonyKernelAccess.getDefault().getDevelopmentKernel(project);
		}
		
		if (kernel != null) {
		    ServerUtils.injectRoutingURL(configuration, kernel, project, route);
		}		
		
		return 0;
	}
}
