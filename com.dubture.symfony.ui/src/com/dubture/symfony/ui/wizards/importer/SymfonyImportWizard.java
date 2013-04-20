package com.dubture.symfony.ui.wizards.importer;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.internal.resources.ProjectDescriptionReader;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.utils.ResourceUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.osgi.service.prefs.BackingStoreException;

import com.dubture.composer.core.ComposerNature;
import com.dubture.composer.core.buildpath.BuildPathManager;
import com.dubture.composer.core.log.Logger;
import com.dubture.symfony.core.SymfonyCorePlugin;
import com.dubture.symfony.core.SymfonyVersion;
import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.core.facet.FacetManager;
import com.dubture.symfony.core.preferences.CorePreferenceConstants.Keys;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;

/**
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
@SuppressWarnings("restriction")
public class SymfonyImportWizard extends Wizard implements IImportWizard {

	private SymfonyImportFirstPage firstPage;

	public SymfonyImportWizard() {
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		setWindowTitle(DataTransferMessages.DataTransfer_importTitle);
		setDefaultPageImageDescriptor(IDEWorkbenchPlugin.getIDEImageDescriptor("wizban/importzip_wiz.png"));//$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}
	
	@Override
	public void addPages() {
		addPage(firstPage = new SymfonyImportFirstPage("Import an existing Symfony project"));
	}

	@Override
	public boolean performFinish() {
		
		final IPath sourcePath = firstPage.getSourcePath();
		final IPath containerPath = firstPage.getContainerPath();
		final IPath consolePath = firstPage.getConsolePath();
		final String projectName = firstPage.getProjectName();
		final PHPVersion phpVersion = firstPage.getPHPVersion();
		final SymfonyVersion symfonyVersion = firstPage.getSymfonyVersion();
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IWorkspaceRoot root = workspace.getRoot();
				IProject project = root.getProject(projectName);
				monitor.beginTask("Importing Symfony project", 5);
				
				try {
					
					IProjectDescription description = null;
					
					if (sourcePath.append(".project").toFile().exists()) {
						ProjectDescriptionReader reader = new ProjectDescriptionReader(project);
						description = reader.read(sourcePath.append(".project"));
						description.setName(projectName);
					} else {
						description = workspace.newProjectDescription(projectName);
					}
					
					// If it is under the root use the default location
					if (Platform.getLocation().isPrefixOf(sourcePath)) {
						description.setLocation(null);
					} else {
						description.setLocation(sourcePath);
					}
					
					monitor.worked(1);
					project.create(description, monitor);
					project.open(monitor);
					monitor.worked(1);
					
					applyNatures(new String[]{PHPNature.ID, SymfonyNature.NATURE_ID, ComposerNature.NATURE_ID },  project, monitor);
					
					IScriptProject scriptProject = DLTKCore.create(project);
					IFile vendorPath = project.getFile(SymfonyCoreConstants.VENDOR_PATH);
					
					if (vendorPath != null && vendorPath.getRawLocation().toFile().exists()) {
						IBuildpathEntry sourceEntry = DLTKCore.newSourceEntry(vendorPath.getFullPath(), new IPath[] {
								new Path(SymfonyCoreConstants.SKELETON_PATTERN),
								new Path(SymfonyCoreConstants.TEST_PATTERN),
								new Path(SymfonyCoreConstants.CG_FIXTURE_PATTERN)
						});        
						BuildPathManager.setExclusionPattern(scriptProject, sourceEntry);
					}
					
					FacetManager.installFacets(project, phpVersion, symfonyVersion, monitor);
					monitor.worked(1);
					createProjectSettings(project, containerPath.toString(), consolePath.toString());
					
					project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
					monitor.worked(2);
				} catch (Exception e) {
					Logger.logException(e);
					// cleanup on failure
					if(project != null) {
						try {
							if (project.isOpen()) {
									project.close(monitor);
							}
							if (project.exists()) {
								project.delete(true, monitor);
							}
						} catch (CoreException e1) {
							Logger.logException(e1);
						}
						throw new InvocationTargetException(e);
					}
				} finally {
					monitor.done();
				}
			}
		};
		
		try {
			getContainer().run(false, true, op);
		} catch (InvocationTargetException e) {
			String message = e.getMessage();
			if (message == null || message.length() == 0) {
				message = "An error occurred during import. Please see the workspace logs for details.";
			}
			MessageDialog.openError(getShell(), "Error importing project", message);
			return false;
		} catch (InterruptedException e) {
			Logger.logException(e);
			return true;
		}
		
		return true;
	}
	
	protected void applyNatures(String[] natures, IProject project, IProgressMonitor monitor) throws CoreException {
		for (String nature : natures) {
			if (!project.hasNature(nature)) {
				ResourceUtil.addNature(project, monitor, nature);
			}
		}
	}
	
	protected void createProjectSettings(IProject project, String containerPath, String consolePath) throws BackingStoreException {
		
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(SymfonyCorePlugin.ID);
		String executable = preferences.get(Keys.PHP_EXECUTABLE, null);
		IEclipsePreferences node = new ProjectScope(project).getNode(SymfonyCorePlugin.ID);
		node.put(Keys.PHP_EXECUTABLE, executable);
		node.put(Keys.CONSOLE, consolePath);
		node.put(Keys.USE_PROJECT_PHAR, Boolean.FALSE.toString());
		node.put(Keys.DUMPED_CONTAINER, containerPath);
		node.flush();
	}
}
