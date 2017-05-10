/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.debug.launch;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.util.FileUtils;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.server.ui.launching.Messages;
import org.eclipse.php.internal.server.ui.launching.PHPWebPageLaunchDialog;
import org.eclipse.php.internal.server.ui.launching.PHPWebPageLaunchShortcut;
import org.eclipse.php.internal.server.ui.launching.PHPWebPageURLLaunchDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.AppKernel;
import com.dubture.symfony.core.model.SymfonyKernelAccess;
import com.dubture.symfony.debug.server.SymfonyServer;
import com.dubture.symfony.debug.util.ServerUtils;
import com.dubture.symfony.index.model.Route;

@SuppressWarnings("restriction")
public class LaunchActionShortcut extends PHPWebPageLaunchShortcut {

	public void launch(ISelection selection, String mode) {
		if (selection instanceof IStructuredSelection) {

			ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
			ILaunchConfigurationType configType = lm.getLaunchConfigurationType(IPHPDebugConstants.PHPServerLaunchType);
			searchAndLaunch(((IStructuredSelection) selection).toArray(), mode, configType);
		}
	}

	public void launch(IEditorPart editor, String mode) {
		IEditorInput input = editor.getEditorInput();
		IFile file = (IFile) input.getAdapter(IFile.class);
		if (file != null) {

			ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
			ILaunchConfigurationType configType = lm.getLaunchConfigurationType(IPHPDebugConstants.PHPServerLaunchType);

			searchAndLaunch(new Object[] { file }, mode, configType);
		}
	}

	static ILaunchConfiguration findLaunchConfiguration(IProject project, String fileName, String selectedURL, Server server, String mode,
			ILaunchConfigurationType configType, boolean breakAtFirstLine, boolean showDebugDialog, IResource res, Route route, IScriptProject scriptProject) {
		ILaunchConfiguration config = null;

		Logger.debugMSG("trying to find launc configuration");
		try {
			ILaunchConfiguration[] configs = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations(configType);

			int numConfigs = configs == null ? 0 : configs.length;
			for (int i = 0; i < numConfigs; i++) {

				String configuredRouteName = configs[i].getAttribute(SymfonyServer.ROUTE, (String) null);

				if (configuredRouteName != null)
					if (configuredRouteName.equals(route.getName())) {
						config = configs[i].getWorkingCopy();
						Logger.debugMSG("found existing: " + configuredRouteName);
						break;
					}
			}

			if (config == null) {
				Logger.debugMSG("no launch config found; create new one");
				config = createConfiguration(project, fileName, selectedURL, server, configType, mode, breakAtFirstLine, showDebugDialog, res, route,
						scriptProject);
			}
		} catch (CoreException ce) {
			ce.printStackTrace();
		}
		return config;
	}

	public static void searchAndLaunch(Object[] search, String mode, ILaunchConfigurationType configType) {
		int entries = search == null ? 0 : search.length;

		// EditorUtility utility = new EditorUtility();
		Route route = new Route("foo", "bar");

		for (int i = 0; i < entries; i++) {
			try {
				String phpPathString = null;
				IProject project = null;
				Object obj = search[i];
				IResource res = null;
				if (obj instanceof IModelElement) {
					IModelElement elem = (IModelElement) obj;

					if (elem instanceof ISourceModule) {
						res = ((ISourceModule) elem).getCorrespondingResource();
					} else if (elem instanceof IType) {
						res = ((IType) elem).getUnderlyingResource();
					} else if (elem instanceof IMethod) {
						res = ((IMethod) elem).getUnderlyingResource();
					}
					if (res instanceof IFile) {
						obj = (IFile) res;
					}
				}

				if (obj instanceof IFile) {
					IFile file = (IFile) obj;
					res = file;
					project = file.getProject();
					IContentType contentType = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
					if (contentType.isAssociatedWith(file.getName())) {
						phpPathString = file.getFullPath().toString();
					}
				}

				Server defaultServer = ServersManager.getDefaultServer(project);
				if (defaultServer == null) {
					// Sould not happen
					throw new CoreException(new Status(IStatus.ERROR, PHPDebugUIPlugin.ID, IStatus.OK, Messages.PHPWebPageLaunchShortcut_0, null));
				}

				String basePath = PHPProjectPreferences.getDefaultBasePath(project);

				boolean breakAtFirstLine = PHPProjectPreferences.getStopAtFirstLine(project);
				String selectedURL = null;
				boolean showDebugDialog = true;
				if (obj instanceof IScriptProject) {
					final PHPWebPageLaunchDialog dialog = new PHPWebPageLaunchDialog(mode, (IScriptProject) obj);
					final int open = dialog.open();
					if (open == PHPWebPageLaunchDialog.OK) {
						defaultServer = dialog.getServer();
						selectedURL = dialog.getPHPPathString();
						phpPathString = dialog.getFilename();
						breakAtFirstLine = dialog.isBreakAtFirstLine();
						showDebugDialog = false;
					} else {
						continue;
					}
				}

				if (phpPathString == null) {
					// Could not find target to launch
					throw new CoreException(new Status(IStatus.ERROR, PHPDebugUIPlugin.ID, IStatus.OK, Messages.launch_failure_no_target, null));
				}

				// Launch the app
				/*
				 * ILaunchConfiguration config =
				 * findLaunchConfiguration(project, phpPathString, selectedURL,
				 * defaultServer, mode, configType, breakAtFirstLine,
				 * showDebugDialog, res, route, utility.getProject()); if
				 * (config != null) { DebugUITools.launch(config, mode); } else
				 * { // Could not find launch configuration or the user //
				 * cancelled. // throw new CoreException(new
				 * Status(IStatus.ERROR, // PHPDebugUIPlugin.ID, IStatus.OK, //
				 * PHPDebugUIMessages.launch_failure_no_config, null)); }
				 */
			} catch (CoreException ce) {
				final IStatus stat = ce.getStatus();
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						ErrorDialog.openError(PHPDebugUIPlugin.getActiveWorkbenchShell(), Messages.launch_failure_msg_title,
								Messages.launch_failure_server_msg_text, stat);
					}
				});
			}
		}
	}

	static ILaunchConfiguration createConfiguration(IProject project, String fileName, String selectedURL, Server server, ILaunchConfigurationType configType,
			String mode, boolean breakAtFirstLine, boolean showDebugDialog, IResource res, Route route, IScriptProject scriptProject) throws CoreException {

		ILaunchConfiguration config = null;
		if (!FileUtils.resourceExists(fileName)) {
			Logger.debugMSG("file not existing: " + fileName);
			return null;
		}

		ILaunchConfigurationWorkingCopy wc = configType.newInstance(null, getNewConfigurationName(route));

		// Set the debugger ID and the configuration delegate for this launch
		// configuration
		String debuggerID = PHPProjectPreferences.getDefaultDebuggerID(project);
		wc.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, debuggerID);
		IDebuggerConfiguration debuggerConfiguration = PHPDebuggersRegistry.getDebuggerConfiguration(debuggerID);
		wc.setAttribute(PHPDebugCorePreferenceNames.CONFIGURATION_DELEGATE_CLASS, debuggerConfiguration.getWebLaunchDelegateClass());

		wc.setAttribute(Server.NAME, server.getName());

		wc.setAttribute(Server.FILE_NAME, fileName);
		wc.setAttribute(Server.BASE_URL, "");
		wc.setAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO, PHPDebugPlugin.getDebugInfoOption());
		wc.setAttribute(IPHPDebugConstants.OPEN_IN_BROWSER, PHPDebugPlugin.getOpenInBrowserOption());
		wc.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, breakAtFirstLine);
		if (res != null) {
			wc.setMappedResources(new IResource[] { res });
		}

		config = wc.doSave();
		String URL = null;
		Logger.debugMSG("getting dev kernel from " + scriptProject.getElementName());
		AppKernel kernel = SymfonyKernelAccess.getDefault().getDevelopmentKernel(scriptProject);

		if (kernel == null) {
			Logger.debugMSG("No kernel found");
			return null;
		}
		Logger.debugMSG("kernel found: " + kernel.getScript());
		if (selectedURL != null) {
			Logger.debugMSG("found exisiting url: " + selectedURL);
			URL = selectedURL;
		} else {
			try {

				URL = ServerUtils.constructURL(config, scriptProject, route, kernel);
				Logger.debugMSG("Generated launch configuration url: " + URL);
				if (URL == null)
					URL = "";
			} catch (Exception e) {
				// safe as resolved URL is server.getBaseURL()
				e.printStackTrace();
				if (URL == null)
					URL = "";
			}
		}

		wc.setAttribute(Server.BASE_URL, URL);
		wc.setAttribute(SymfonyServer.URL, URL);

		// Display a dialog for selecting the route parameters.
		if (route.hasParameters()) {

			String title = "Please insert the required route parameters";
			SymfonyURLLaunchDialog launchDialog = new SymfonyURLLaunchDialog(wc, title, route);
			launchDialog.setBlockOnOpen(true);
			if (launchDialog.open() != PHPWebPageURLLaunchDialog.OK) {
				deleteLaunchconfig(config);
				return null;
			}
		}

		wc.setAttribute(SymfonyServer.ROUTE, route.getName());
		wc.setAttribute(SymfonyServer.ENVIRONMENT, kernel.getEnvironment());
		config = wc.doSave();
		return config;
	}

	private static void deleteLaunchconfig(final ILaunchConfiguration launchConfig) {

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				ILaunchConfiguration config = launchConfig;
				try {
					if (config instanceof ILaunchConfigurationWorkingCopy) {
						config = ((ILaunchConfigurationWorkingCopy) config).getOriginal();
					}
					if (config != null) {
						config.delete();
					}
				} catch (CoreException ce) {
					// Ignore
				}
			}
		});
	}

	protected static String getNewConfigurationName(Route route) {

		String configurationName = Messages.PHPWebPageLaunchShortcut_4;

		try {
			configurationName = route.getName();

		} catch (Exception e) {
			Logger.logException(e);
		}
		return DebugPlugin.getDefault().getLaunchManager().generateLaunchConfigurationName(configurationName);
	}

}
