/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.wizards.project;

import java.util.Observable;
import java.util.concurrent.CountDownLatch;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.server.ui.ServerLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.dubture.composer.core.buildpath.BuildPathManager;
import com.dubture.composer.ui.job.CreateProjectJob;
import com.dubture.composer.ui.job.CreateProjectJob.JobListener;
import com.dubture.composer.ui.wizard.AbstractWizardFirstPage;
import com.dubture.composer.ui.wizard.AbstractWizardSecondPage;
import com.dubture.symfony.core.SymfonyVersion;
import com.dubture.symfony.core.facet.FacetManager;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;
import com.dubture.symfony.ui.job.NopJob;

/**
 *
 * Initializes the Symfony project structure and adds the folders to the buildpath.
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyProjectWizardSecondPage extends AbstractWizardSecondPage {

	public SymfonyProjectWizardSecondPage(AbstractWizardFirstPage mainPage, String title) {
		super(mainPage, title);
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}
	
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);
	}

	@Override
	protected String getPageTitle() {
		return "Finish your new Symfony project configuration";
	}

	@Override
	protected String getPageDescription() {
		return "";
	}

	@Override
	protected void beforeFinish(IProgressMonitor monitor) throws Exception {

		final CountDownLatch latch = new CountDownLatch(1);
		
		SymfonyProjectWizardFirstPage symfonyPage = (SymfonyProjectWizardFirstPage) firstPage;
		monitor.beginTask("Initializing Symfony project", 1);
		IPath path = null;
		
		if (symfonyPage.isInLocalServer()) {
			path = symfonyPage.getPath().removeLastSegments(1);
		} else {
			path = Platform.getLocation();
		}
		CreateProjectJob projectJob = new CreateProjectJob(path, firstPage.nameGroup.getName(), SymfonyCoreConstants.SYMFONY_STANDARD_EDITION, symfonyPage.getSymfonyVersion());
		projectJob.setJobListener(new JobListener() {
			@Override
			public void jobStarted() {
				latch.countDown();
			}

			@Override
			public void jobFinished(final String projectName) {
				latch.countDown();
			}

			@Override
			public void jobFailed() {
				latch.countDown();
			}
		});
		
		projectJob.addJobChangeListener(new JobChangeAdapter() {
			
			@Override
			public void done(IJobChangeEvent event) {
				
				if (event.getResult() == null || event.getResult().getCode() == Status.ERROR) {
					Logger.log(Logger.ERROR, "Could not run composer create-project");
					return;
				}
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(getProject().getName());
				if (project == null) {
					Logger.log(Logger.WARNING, "Unable to retrieve project for running the console after project initialization");
					return;
				}
				
				refreshProject(getProject().getName());
				NopJob job = new NopJob();
				job.setProject(project);
				job.addJobChangeListener(new JobChangeAdapter() {
					@Override
					public void done(IJobChangeEvent event) {
						
					}
				});
				
				job.schedule();
			}
		});
		
		projectJob.schedule();
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			
		}
		
		monitor.worked(1);
	}

	@Override
	protected void finishPage(IProgressMonitor monitor) throws Exception {
		
		IPath vendorPath = getSymfonyFolderPath(getProject(), SymfonyCoreConstants.VENDOR_PATH);
			IBuildpathEntry sourceEntry = DLTKCore.newSourceEntry(vendorPath, new IPath[] {
				new Path(SymfonyCoreConstants.SKELETON_PATTERN),
				new Path(SymfonyCoreConstants.TEST_PATTERN),
				new Path(SymfonyCoreConstants.CG_FIXTURE_PATTERN)
			}
		);        
	        
        BuildPathManager.setExclusionPattern(getScriptProject(), sourceEntry);
        String version = ((SymfonyProjectWizardFirstPage)firstPage).getSymfonyVersion();
        SymfonyVersion symfonyVersion = SymfonyVersion.Symfony2_2_1;
        if (version.startsWith("v2.1")) {
        	symfonyVersion = SymfonyVersion.Symfony2_1_9;
        }
        FacetManager.installFacets(getProject(), firstPage.getPHPVersionValue(), symfonyVersion, monitor);
        
        if (!firstPage.isInLocalServer()) {
        	return;
        }
        
        try {
        	Server server = ServersManager.createServer(getProject().getName(), ((SymfonyProjectWizardFirstPage)firstPage).getVirtualHost());
        	server.setDocumentRoot(getProject().getRawLocation().append("web").toOSString());
        	ServersManager.addServer(server);
        	ServersManager.save();
        	
        	createLaunchConfiguration(server, getProject().getFile(new Path("web/app_dev.php")));
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
	
    private IPath getSymfonyFolderPath(IProject project, String folderPath) {
        IFolder vendorFolder = project.getFolder(folderPath);
        if (vendorFolder.exists()) {
            return vendorFolder.getFullPath();
        }

        return null;
    }	
    
    private ILaunchConfiguration createLaunchConfiguration(Server server, IFile frontController) throws CoreException {
    	
        ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
        ILaunchConfigurationType  configType = lm.getLaunchConfigurationType(IPHPDebugConstants.PHPServerLaunchType);
    	
    	if (!frontController.exists()) {
            Logger.debugMSG("Front controller does not exist, cannot create launch configuration: " + frontController.toString());
            return null;
        }
        
        IProject project = getProject();
        ILaunchConfigurationWorkingCopy wc = configType.newInstance(null, getNewConfigurationName(getProject().getName()));
        String debuggerID = PHPProjectPreferences.getDefaultDebuggerID(project);
        String URL = server.getBaseURL() + "/app_dev.php";
        AbstractDebuggerConfiguration debuggerConfiguration = PHPDebuggersRegistry.getDebuggerConfiguration(debuggerID);
        
        wc.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, debuggerID);
        wc.setAttribute(PHPDebugCorePreferenceNames.CONFIGURATION_DELEGATE_CLASS, debuggerConfiguration.getWebLaunchDelegateClass());
        wc.setAttribute(Server.NAME, server.getName());
        wc.setAttribute(Server.FILE_NAME, frontController.getFullPath().toOSString());
        wc.setAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO, PHPDebugPlugin.getDebugInfoOption());
        wc.setAttribute(IPHPDebugConstants.OPEN_IN_BROWSER, PHPDebugPlugin.getOpenInBrowserOption());
        wc.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, false);
        wc.setAttribute(Server.BASE_URL, URL);
        wc.setAttribute(ServerLaunchConfigurationTab.AUTO_GENERATED_URL, false);
        
        return wc.doSave();
    }
    
    protected static String getNewConfigurationName(String name) {

        String configurationName = "New_configuration";

        try {
            configurationName = name;

        } catch (Exception e) {
            Logger.logException(e);
        }
        return DebugPlugin.getDefault().getLaunchManager()
                .generateLaunchConfigurationName(configurationName);
    }
    
}
