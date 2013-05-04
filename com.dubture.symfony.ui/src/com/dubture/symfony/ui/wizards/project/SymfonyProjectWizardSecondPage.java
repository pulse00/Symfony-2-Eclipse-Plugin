/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.wizards.project;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Observable;
import java.util.concurrent.CountDownLatch;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.SelectionButtonDialogField;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.BackingStoreException;
import org.pdtextensions.core.debug.LaunchConfigurationHelper;

import com.dubture.composer.core.buildpath.BuildPathManager;
import com.dubture.composer.ui.converter.String2KeywordsConverter;
import com.dubture.composer.ui.job.CreateProjectJob;
import com.dubture.composer.ui.job.CreateProjectJob.JobListener;
import com.dubture.composer.ui.wizard.AbstractWizardFirstPage;
import com.dubture.composer.ui.wizard.AbstractWizardSecondPage;
import com.dubture.composer.ui.wizard.project.BasicSettingsGroup;
import com.dubture.getcomposer.core.ComposerPackage;
import com.dubture.symfony.core.SymfonyCorePlugin;
import com.dubture.symfony.core.SymfonyVersion;
import com.dubture.symfony.core.facet.FacetManager;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.preferences.CorePreferenceConstants.Keys;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;
import com.dubture.symfony.ui.SymfonyUiPlugin;
import com.dubture.symfony.ui.job.NopJob;

/**
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * 
 */
@SuppressWarnings("restriction")
public class SymfonyProjectWizardSecondPage extends AbstractWizardSecondPage {

	private BasicSettingsGroup settingsGroup;
	private SelectionButtonDialogField overrideComposer;

	public SymfonyProjectWizardSecondPage(AbstractWizardFirstPage mainPage, String title) {
		super(mainPage, title);
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}
	
	@Override
	public void createControl(Composite parent) {
		
		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setFont(parent.getFont());

		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		
		overrideComposer = new SelectionButtonDialogField(SWT.CHECK);
		overrideComposer.setLabelText("Override composer.json values");
		overrideComposer.doFillIntoGrid(composite, 3);
		overrideComposer.setDialogFieldListener(new IDialogFieldListener() {
			@Override
			public void dialogFieldChanged(DialogField field) {
				settingsGroup.setEnabled(overrideComposer.isSelected());
			}
		});
		
		settingsGroup = new BasicSettingsGroup(composite, getShell());
		settingsGroup.addObserver(this);
		settingsGroup.setEnabled(false);
		
		setControl(composite);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, SymfonyUiPlugin.PLUGIN_ID + "." + "newproject_secondpage");
	}

	@Override
	protected String getPageTitle() {
		return "Override Composer values";
	}

	@Override
	protected String getPageDescription() {
		return "";
	}

	@Override
	protected void beforeFinish(IProgressMonitor monitor) throws Exception {

		final CountDownLatch latch = new CountDownLatch(1);
		
		SymfonyProjectWizardFirstPage symfonyPage = (SymfonyProjectWizardFirstPage) firstPage;
		monitor.beginTask("Initializing Symfony project", 2);
		monitor.setTaskName("Running create-project command...");
		monitor.worked(1);
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
				
				if (getProject() == null || getProject().getName() == null) {
					Logger.log(Logger.ERROR, "Unable to initialize symfony project, cannot retrieve project");
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
						//now that the container has been dumped...
						IProject project = getProject();
						if (project == null) {
							Logger.log(Logger.WARNING, "Could not retrieve project for saving the debug container");
							return;
						}
						
						IFile dumpedContainer = project.getFile(SymfonyCoreConstants.DEFAULT_CONTAINER);
						
						if (dumpedContainer == null || dumpedContainer.exists() == false) {
							Logger.log(Logger.WARNING, "Could not retrieve project for saving the debug container");
							return;
						}
						
						try {
							Logger.debugMSG("Setting dumped service container");
							IEclipsePreferences node = new ProjectScope(project).getNode(SymfonyCorePlugin.ID);
							node.put(Keys.DUMPED_CONTAINER, dumpedContainer.getFullPath().removeFirstSegments(1).toOSString());
							node.flush();
						} catch (BackingStoreException e) {
							Logger.logException(e);
						}
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
		});        
	        
        BuildPathManager.setExclusionPattern(getScriptProject(), sourceEntry);
        String version = ((SymfonyProjectWizardFirstPage)firstPage).getSymfonyVersion();
        SymfonyVersion symfonyVersion = SymfonyVersion.Symfony2_2_1;
        if (version.startsWith("v2.1")) {
        	symfonyVersion = SymfonyVersion.Symfony2_1_9;
        }
        
        try {
        	
        	FacetManager.installFacets(getProject(), firstPage.getPHPVersionValue(), symfonyVersion, monitor);
        	
        	if (overrideComposer.isSelected()) {
        		updateComposerJson(monitor);
        	}
        	
        	createProjectSettings();
        	
        	if (!firstPage.isInLocalServer()) {
        		return;
        	}
        	
        	Server server = createServer();
        	LaunchConfigurationHelper.createLaunchConfiguration(getProject(), server, getProject().getFile(new Path("web/app_dev.php")));
        	
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
	
	protected void createProjectSettings() throws BackingStoreException {
		
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(SymfonyCorePlugin.ID);
		String executable = preferences.get(Keys.PHP_EXECUTABLE, null);
		
		IEclipsePreferences node = new ProjectScope(getProject()).getNode(SymfonyCorePlugin.ID);
		node.put(Keys.PHP_EXECUTABLE, executable);
		node.put(Keys.CONSOLE, SymfonyCoreConstants.DEFAULT_CONSOLE);
		node.put(Keys.USE_PROJECT_PHAR, Boolean.FALSE.toString());
		node.flush();
		
	}
	
    private void updateComposerJson(IProgressMonitor monitor) throws IOException, CoreException {

    	IFile composerJson = getProject().getFile("composer.json");
    	ComposerPackage composerPackage = new ComposerPackage(composerJson.getRawLocation().makeAbsolute().toFile());
		String2KeywordsConverter keywordConverter = new String2KeywordsConverter(composerPackage);    	
    	
		if (settingsGroup.getVendor() != null && firstPage.nameGroup.getName() != null) {
			composerPackage.setName(String.format("%s/%s", settingsGroup.getVendor(), firstPage.nameGroup.getName()));
		}

		if (settingsGroup.getDescription().length() > 0) {
			composerPackage.setDescription(settingsGroup.getDescription());
		}

		if (settingsGroup.getLicense().length() > 0) {
			composerPackage.getLicense().clear();
			composerPackage.getLicense().add(settingsGroup.getLicense());
		}

		if (settingsGroup.getType().length() > 0) {
			composerPackage.setType(settingsGroup.getType());
		}

		if (settingsGroup.getKeywords().length() > 0) {
			keywordConverter.convert(settingsGroup.getKeywords());
		}

		String json = composerPackage.toJson();
		ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes());
		
		monitor.beginTask("Updating composer.json", 1);
		composerJson.setContents(bis, IResource.FORCE, monitor);
		monitor.worked(1);
    	
	}

	private IPath getSymfonyFolderPath(IProject project, String folderPath) {
        IFolder vendorFolder = project.getFolder(folderPath);
        if (vendorFolder.exists()) {
            return vendorFolder.getFullPath();
        }

        return null;
    }
    
    protected Server createServer() throws MalformedURLException {
    	Server server = ServersManager.createServer(getProject().getName(), ((SymfonyProjectWizardFirstPage)firstPage).getVirtualHost());
    	server.setDocumentRoot(getProject().getRawLocation().append("web").toOSString());
    	ServersManager.addServer(server);
    	ServersManager.save();
    	return server;
    }
}
