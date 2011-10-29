package com.dubture.symfony.debug.util;

import java.util.List;

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
	public static String getBaseUrl(ILaunchConfiguration configuration, IScriptProject project) 
			throws CoreException {
		
		Server server = ServersManager.getServer(configuration.getAttribute(
				Server.NAME, ""));
		
		SymfonyModelAccess model = SymfonyModelAccess.getDefault();				
		List<AppKernel> kernels = model.getKernels(project);
		
		AppKernel appKernel = null;
		for (AppKernel kernel : kernels) {					
			if (kernel.getEnvironment().contains("dev")) {
				appKernel = kernel;
				break;
			}
		}
		
		if (appKernel == null && kernels.size() > 0)
			appKernel = kernels.get(0);
		
		if (appKernel == null)
			return null;
		
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
			
			EditorUtility utility = new EditorUtility();		
			Route route = utility.getRouteAtCursor();
			IScriptProject project = utility.getProject();
			
			String base = ServerUtils.getBaseUrl(configuration, project);
			
			if (base == null)
				return;
			
			String url = RoutingUtil.getURL(base, route, project);
			
			if (url != null) {
				ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
				wc.setAttribute(Server.BASE_URL, url);
				wc.doSave();					
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
}
