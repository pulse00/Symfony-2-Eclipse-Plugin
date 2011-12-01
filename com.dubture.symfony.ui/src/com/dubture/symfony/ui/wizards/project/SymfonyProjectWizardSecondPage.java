/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.wizards.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
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
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.wizards.BuildpathDetector;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.buildpath.BuildPathUtils;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.ui.wizards.PHPProjectWizardFirstPage;
import org.eclipse.php.internal.ui.wizards.PHPProjectWizardSecondPage;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;

/**
 * 
 * Initializes the Symfony project structure and adds the folders to the buildpath.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com> 
 *
 */
@SuppressWarnings("restriction")
public class SymfonyProjectWizardSecondPage extends PHPProjectWizardSecondPage {

	private int level;
	private String symfonyPath;	
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
		
		boolean installSymfony = true;

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
			
			SymfonyProjectWizardFirstPage firstPage = (SymfonyProjectWizardFirstPage) fFirstPage;

			if (firstPage.getDetect()) {
				
				installSymfony = false;
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
			} else if (firstPage.hasSymfonyStandardEdition()) {

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
			
			if (installSymfony) 
				installSymfony(new SubProgressMonitor(monitor, 50));

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
	
	private void importFile(File file, IProject project, List<IBuildpathEntry> entries) {

		try {

			level++;
			

			// handle windows path separators
			String path = file.getAbsolutePath().replace("\\", "/").replace(symfonyPath, "");

			// import the directory
			if(file.isDirectory() && ! file.isHidden()) {

				IFolder folder = project.getFolder(path);

				if (!folder.exists()) {
					folder.create(true, true, null);	
				}

				// add root folders to buildpath
				if (level == 1 && ! folder.getFullPath().toString().endsWith("bin")) {

					IPath[] exclusion = {};

					if (folder.getName().equals(SymfonyCoreConstants.APP_PATH)) {
						exclusion = new IPath[] { 
								new Path(SymfonyCoreConstants.CACHE_PATH),
								new Path(SymfonyCoreConstants.LOG_PATH)
								};						
					} else if (folder.getName().equals(SymfonyCoreConstants.VENDOR_PATH)) {
						exclusion = new IPath[] { new Path(SymfonyCoreConstants.SKELETON_PATH) };
					}

					IBuildpathEntry entry =  DLTKCore.newSourceEntry(folder.getFullPath(), exclusion);
					entries.add(entry);					
				}

				// now import recursively
				for (File f : file.listFiles()) {			
					importFile(f, project, entries);					
				}

				// create the project file
			} else if (file.isFile() && ".gitkeep".equals(file.getName()) == false) {				
			    
			    FileInputStream fis = new FileInputStream(file);                
			    IFile iFile = project.getFile(path);
			    iFile.create(fis, true, null);
			    			        
			}

			level--;

		} catch (CoreException e) {
			e.printStackTrace();
			Logger.logException(e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Logger.logException(e);
		}

	}

	public void installSymfony(IProgressMonitor monitor) {

		if (monitor == null)
			monitor = new NullProgressMonitor();
		
		
		SymfonyProjectWizardFirstPage firstPage = (SymfonyProjectWizardFirstPage) fFirstPage;
		monitor.beginTask("Installing symfony...", 100);
		monitor.worked(10);

		IProject projectHandle = fFirstPage.getProjectHandle();
		final IScriptProject scriptProject = DLTKCore.create(projectHandle);		

		File file = null;
		final List<IBuildpathEntry> entries = new ArrayList<IBuildpathEntry>();
		
		level = 0;

		try {
			
			file = new File(firstPage.getLibraryPath());
			symfonyPath = new Path(firstPage.getLibraryPath()).toString();

			if (file.isDirectory()) {

				final File[] files = file.listFiles();

				if(!scriptProject.isOpen()) {
					scriptProject.open(monitor);
				} 

				if (files != null && scriptProject != null && scriptProject.isOpen()) {

					for (File f : files) {
						importFile(f, scriptProject.getProject(), entries);
					}
					
		            BuildPathUtils.addEntriesToBuildPath(scriptProject, entries);
					monitor.worked(90);

				}
			}
		} catch (ModelException e) {
			e.printStackTrace();
			Logger.logException(e);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.logException(e);
		} finally {
			
			monitor.worked(100);
			monitor.done();					
		}
	}	
}
