package com.dubture.symfony.debug.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.internal.debug.core.launching.XDebugWebLaunchConfigurationDelegate;
import org.eclipse.php.internal.server.core.Server;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.AppKernel;
import com.dubture.symfony.core.model.SymfonyKernelAccess;
import com.dubture.symfony.debug.server.SymfonyServer;
import com.dubture.symfony.debug.util.ServerUtils;
import com.dubture.symfony.index.dao.Route;
import com.dubture.symfony.ui.editor.EditorUtility;

/**
 * 
 * A ConfigurationDelegate for XDebug to inject the correct URL
 * depending on the current cursor position in the editor.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * 
 */
@SuppressWarnings("restriction")
public class SymfonyWebLaunchConfigurationDelegate extends
		XDebugWebLaunchConfigurationDelegate {
	
	
	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {

//		EditorUtility utility = new EditorUtility();		
//		Route route = utility.getRouteAtCursor();
//		IScriptProject project = utility.getProject();
//		
//		System.err.println(configuration.getAttribute(Server.BASE_URL, ""));
//		
//		String env = "";
//		try {
//			env = configuration.getAttribute(SymfonyServer.ENVIRONMENT, "");
//		} catch (CoreException e) {
//			Logger.logException(e);
//		}
//		
//		AppKernel kernel = null;
//		
//		if (env.length() != 0) {
//			kernel = SymfonyKernelAccess.getDefault().getKernel(project, env);
//		} else {
//			kernel = SymfonyKernelAccess.getDefault().getDevelopmentKernel(project);
//		}
//		
//		ServerUtils.injectRoutingURL(configuration, kernel, project, route);
		
		super.launch(configuration, mode, launch, monitor);
	}
}