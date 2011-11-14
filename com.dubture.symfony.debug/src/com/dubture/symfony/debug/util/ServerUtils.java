/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.debug.util;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.AppKernel;
import com.dubture.symfony.core.util.RoutingUtil;
import com.dubture.symfony.debug.server.SymfonyServer;
import com.dubture.symfony.index.dao.Route;

/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ServerUtils {
	
	/**
	 * Returns the baseURL for a specific configuration + project combination
	 * 
	 * @param configuration
	 * @param project
	 * @return
	 * @throws CoreException
	 */
	public static String getBaseUrl(ILaunchConfiguration configuration, IScriptProject project, AppKernel kernel) 
			throws CoreException {
		
		ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();		
		Server server = ServersManager.getServer(configuration.getAttribute(
				Server.NAME, ""));
		
		if (kernel == null)
			return null;
		
		
		String env = kernel.getEnvironment();
		wc.setAttribute(SymfonyServer.ENVIRONMENT, env);
		wc.doSave();
		
		boolean isVhost = isVirtualHost(server, kernel);

		String base = "";
		
		if (isVhost) {
			base = String.format("%s/%s", server.getBaseURL(), kernel.getScript());
		} else {
			base = String.format("%s/%s/%s", server.getBaseURL(), project.getElementName(), kernel.getPath());			
		}
		
		return base;
		
	}
	
	/**
	 * Tries to evaluate if the Server is a virtual host.
	 * 
	 * 
	 * @param server
	 * @param kernel
	 * @return
	 */
	public static boolean isVirtualHost(Server server, AppKernel kernel) {
		
		return server.getDocumentRoot().endsWith(kernel.getRawPath().removeLastSegments(1).toString());		
		
	}
	
	/**
	 * 
	 * Inject a valid routing URL into the launchconfiguration
	 * 
	 * 
	 * @param configuration
	 */
	public static void injectRoutingURL(ILaunchConfiguration configuration, AppKernel kernel, IScriptProject project, Route route) {
		
		try {
			
			ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();			
			String url = constructURL(configuration, project, route, kernel);
			
			if (url != null) {
				
				wc.setAttribute(Server.BASE_URL, url);
				wc.doSave();					
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
	
	
	public static String constructURL(ILaunchConfiguration configuration, IScriptProject project, Route route, AppKernel kernel) {
		

		String base = null;
		try {
			 
			base = ServerUtils.getBaseUrl(configuration, project, kernel);
			
		} catch (CoreException e) {
			Logger.logException(e);
		}
		
		if (base == null)
			return null;
		
		return RoutingUtil.getURL(base, route, project);		
		
	}
}
