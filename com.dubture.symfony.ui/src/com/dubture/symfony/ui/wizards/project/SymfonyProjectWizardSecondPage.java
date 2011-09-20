package com.dubture.symfony.ui.wizards.project;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.ui.util.CoreUtility;
import org.eclipse.dltk.internal.ui.wizards.BuildpathDetector;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.wizards.PHPProjectWizardFirstPage;
import org.eclipse.php.internal.ui.wizards.PHPProjectWizardSecondPage;

@SuppressWarnings("restriction")
public class SymfonyProjectWizardSecondPage extends PHPProjectWizardSecondPage {

	public SymfonyProjectWizardSecondPage(PHPProjectWizardFirstPage mainPage) {
		super(mainPage);
	}
	
	
	@Override
	protected void updateProject(IProgressMonitor monitor)
			throws CoreException, InterruptedException {

		
		IProject projectHandle = fFirstPage.getProjectHandle();
		IScriptProject create = DLTKCore.create(projectHandle);
		super.init(create, null, false);
		fCurrProjectLocation = getProjectLocationURI();

		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		try {
			monitor.beginTask(
					NewWizardMessages.ScriptProjectWizardSecondPage_operation_initialize,
					70);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			URI realLocation = fCurrProjectLocation;
			if (fCurrProjectLocation == null) { // inside workspace
				try {
					URI rootLocation = ResourcesPlugin.getWorkspace().getRoot()
							.getLocationURI();
					realLocation = new URI(rootLocation.getScheme(), null, Path
							.fromPortableString(rootLocation.getPath())
							.append(getProject().getName()).toString(), null);
				} catch (URISyntaxException e) {
					Assert.isTrue(false, "Can't happen"); //$NON-NLS-1$
				}
			}

			rememberExistingFiles(realLocation);

			createProject(getProject(), fCurrProjectLocation,
					new SubProgressMonitor(monitor, 20));

			IBuildpathEntry[] buildpathEntries = null;
			IncludePath[] includepathEntries = null;

			if (fFirstPage.getDetect()) {
				includepathEntries = setProjectBaseIncludepath();
				if (!getProject().getFile(FILENAME_BUILDPATH).exists()) {

					IDLTKLanguageToolkit toolkit = DLTKLanguageManager
							.getLanguageToolkit(getScriptNature());
					final BuildpathDetector detector = createBuildpathDetector(
							monitor, toolkit);
					buildpathEntries = detector.getBuildpath();

				} else {
					monitor.worked(20);
				}
			} else if (fFirstPage.hasPhpSourceFolder()) {
				// need to create sub-folders and set special build/include
				// paths
				IPreferenceStore store = getPreferenceStore();
				IPath srcPath = new Path(
						store.getString(PreferenceConstants.SRCBIN_SRCNAME));
				IPath binPath = new Path(
						store.getString(PreferenceConstants.SRCBIN_BINNAME));

				if (srcPath.segmentCount() > 0) {
					IFolder folder = getProject().getFolder(srcPath);
					CoreUtility.createFolder(folder, true, true,
							new SubProgressMonitor(monitor, 10));
				} else {
					monitor.worked(10);
				}

				if (binPath.segmentCount() > 0) {
					IFolder folder = getProject().getFolder(binPath);
					CoreUtility.createFolder(folder, true, true,
							new SubProgressMonitor(monitor, 10));
				} else {
					monitor.worked(10);
				}

				final IPath projectPath = getProject().getFullPath();

				// configure the buildpath entries, including the default
				// InterpreterEnvironment library.
				List cpEntries = new ArrayList();
				cpEntries.add(DLTKCore.newSourceEntry(projectPath
						.append(srcPath)));

				buildpathEntries = (IBuildpathEntry[]) cpEntries
						.toArray(new IBuildpathEntry[cpEntries.size()]);
				includepathEntries = new IncludePath[] { new IncludePath(
						getProject().getFolder(srcPath), getProject()) };
			} else {
				// flat project layout
				IPath projectPath = getProject().getFullPath();
				List cpEntries = new ArrayList();
				cpEntries.add(DLTKCore.newSourceEntry(projectPath));

//				buildpathEntries = (IBuildpathEntry[]) cpEntries
//						.toArray(new IBuildpathEntry[cpEntries.size()]);
//				includepathEntries = setProjectBaseIncludepath();
				
				buildpathEntries = new IBuildpathEntry[0];
				includepathEntries = new IncludePath[0];
				

				monitor.worked(20);
			}
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			init(DLTKCore.create(getProject()), buildpathEntries, false);

			// setting PHP4/5 and ASP-Tags :
			setPhpLangOptions();

			configureScriptProject(new SubProgressMonitor(monitor, 30));

			SymfonyProjectWizardFirstPage p = (SymfonyProjectWizardFirstPage) getFirstPage();
			
			// checking and adding JS nature,libs, include path if needed
			if (p.shouldSupportJavaScript()) {
				addJavaScriptNature(monitor);
			}

			// adding build paths, and language-Container:
			getScriptProject().setRawBuildpath(buildpathEntries,
					new NullProgressMonitor());
			LanguageModelInitializer.enableLanguageModelFor(getScriptProject());

			// init, and adding include paths:
			getBuildPathsBlock().init(getScriptProject(),
					new IBuildpathEntry[] {});
			IncludePathManager.getInstance().setIncludePath(getProject(),
					includepathEntries);

		} finally {
			monitor.done();
		}
	}
		
		
	private IProject getProject() {
		IScriptProject scriptProject = getScriptProject();
		if (scriptProject != null) {
			return scriptProject.getProject();
		}
		return null;
	}
	
	
	// Symfony2 requires > PHP 5.3
	protected void setPhpLangOptions() {
				
		ProjectOptions.setSupportingAspTags(false, getProject());
		ProjectOptions.setPhpVersion(PHPVersion.PHP5_3, getProject());
	}
}