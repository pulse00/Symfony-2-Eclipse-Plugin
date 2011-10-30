package com.dubture.symfony.debug.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.AppKernel;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.util.RoutingUtil;
import com.dubture.symfony.debug.server.SymfonyServer;
import com.dubture.symfony.index.dao.Route;
import com.dubture.symfony.ui.editor.EditorUtility;

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
	@SuppressWarnings("unchecked")
	public static String getBaseUrl(ILaunchConfiguration configuration, IScriptProject project) 
			throws CoreException {
		
		ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
		
		Server server = ServersManager.getServer(configuration.getAttribute(
				Server.NAME, ""));
		
		Set<String> ks = configuration.getAttribute(SymfonyServer.KERNELS, new HashSet<String>());
		
		String environment = configuration.getAttribute(SymfonyServer.ENVIRONMENT, "");
		
		AppKernel appKernel = null;		
		Set<String> kernelConfig;
		
		if (ks.size() == 0) {

			SymfonyModelAccess model = SymfonyModelAccess.getDefault();				
			List<AppKernel> kernels = model.getKernels(project);
			kernelConfig = new HashSet<String>();
			
			for (AppKernel kernel : kernels) {				
				kernelConfig.add(kernel.getEnvironment());
				
				if (environment.length() > 0 && kernel.getEnvironment().equals(environment)) {
					appKernel = kernel;
				} else if (environment.length() == 0) {
					if (kernel.getEnvironment().contains("dev")) {
						appKernel = kernel;				
					}					
				}
				
			}
			
		} else {
			kernelConfig = ks;
		}
		
		if (appKernel == null)
			return null;
		
		wc.setAttribute(SymfonyServer.KERNELS, kernelConfig);
		wc.doSave();
		
		String base = String.format("%s/%s/%s", server.getBaseURL(), 
				project.getElementName(), appKernel.getPath());

		return base;
		
	}
	
	/**
	 * 
	 * Inject a valid routing URL into the launchconfiguration
	 * 
	 * 
	 * @param configuration
	 */
	public static void injectRoutingURL(ILaunchConfiguration configuration) {
		
		try {
			
			ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
			
			EditorUtility utility = new EditorUtility();		
			Route route = utility.getRouteAtCursor();
			IScriptProject project = utility.getProject();
			
			String url = constructURL(configuration, project, route);
			
			if (url != null) {
				
				wc.setAttribute(Server.BASE_URL, url);
				wc.doSave();					
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
	
	
	public static String constructURL(ILaunchConfiguration configuration, IScriptProject project, Route route) {
		

		String base = null;
		try {
			base = ServerUtils.getBaseUrl(configuration, project);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		
		if (base == null)
			return null;
		
		return RoutingUtil.getURL(base, route, project);		
		
	}
}
