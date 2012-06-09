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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
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
import com.dubture.symfony.core.util.UncompressUtils;

/**
 *
 * Initializes the Symfony project structure and adds the folders to the buildpath.
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyProjectWizardSecondPage extends PHPProjectWizardSecondPage {

    public SymfonyProjectWizardSecondPage(PHPProjectWizardFirstPage mainPage) {
        super(mainPage);
    }

    /* (non-Javadoc)
     * @see org.eclipse.php.internal.ui.wizards.PHPProjectWizardSecondPage#performFinish(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    public void performFinish(IProgressMonitor monitor) throws CoreException,
            InterruptedException
    {
        super.performFinish(monitor);
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

                    List<IBuildpathEntry> entries = getBuildPathEntries(getProject());
                    if (entries.size() > 0) {
                        buildpathEntries = new IBuildpathEntry[entries.size()];
                        buildpathEntries = entries.toArray(buildpathEntries);
                    } else {
                        IDLTKLanguageToolkit toolkit = DLTKLanguageManager
                                .getLanguageToolkit(getScriptNature());
                        final BuildpathDetector detector = createBuildpathDetector(
                                monitor, toolkit);
                        buildpathEntries = detector.getBuildpath();
                    }
                } else {
                    monitor.worked(20);
                }
            } else if (firstPage.hasSymfonyStandardEdition()) {

                // flat project layout
//                IPath projectPath = getProject().getFullPath();
//                List cpEntries = new ArrayList();
//                cpEntries.add(DLTKCore.newSourceEntry(projectPath));

//                buildpathEntries = (IBuildpathEntry[]) cpEntries
//                        .toArray(new IBuildpathEntry[cpEntries.size()]);
//                includepathEntries = setProjectBaseIncludepath();

                buildpathEntries = new IBuildpathEntry[0];
                includepathEntries = new IncludePath[0];

                monitor.worked(20);

            } else {
                // flat project layout
//                IPath projectPath = getProject().getFullPath();
//                List cpEntries = new ArrayList();
//                cpEntries.add(DLTKCore.newSourceEntry(projectPath));

//                buildpathEntries = (IBuildpathEntry[]) cpEntries
//                        .toArray(new IBuildpathEntry[cpEntries.size()]);
//                includepathEntries = setProjectBaseIncludepath();

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

            if (installSymfony && p.hasSymfonyStandardEdition())
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

    /**
     * Returns the build path entries for this script project.
     *
     * @param scriptProject The script project for which the build path should be built
     *
     * @return A list of build path entries
     */
    private List<IBuildpathEntry> getBuildPathEntries(IProject project) {
        List<IBuildpathEntry> entries = new ArrayList<IBuildpathEntry>();

        // Setup app folder build path
        IPath appPath = getSymfonyFolderPath(project, SymfonyCoreConstants.APP_PATH);
        if (appPath != null) {
            entries.add(DLTKCore.newSourceEntry(appPath, new IPath[] {
                new Path(SymfonyCoreConstants.CACHE_PATH),
                new Path(SymfonyCoreConstants.LOG_PATH),
            }));
        }

        // Setup src folder build path
        IPath srcPath = getSymfonyFolderPath(project, SymfonyCoreConstants.SRC_PATH);
        if (srcPath != null) {
            entries.add(DLTKCore.newSourceEntry(srcPath));
        }

        // Setup vendor folder build path
        IPath vendorPath = getSymfonyFolderPath(project, SymfonyCoreConstants.VENDOR_PATH);
        if (vendorPath != null) {
            entries.add(DLTKCore.newSourceEntry(vendorPath, new IPath[] {
                new Path(SymfonyCoreConstants.SKELETON_PATH),
                new Path(SymfonyCoreConstants.TEST_PATH),
                new Path(SymfonyCoreConstants.TEST_PATH_LC),
                new Path(SymfonyCoreConstants.CG_FIXTURE_PATH)
            }));
        }

        return entries;
    }

    /**
     * Setup the build path for the project. Returns true if at least
     * one build path has been added, false otherwise.
     *
     * @param scriptProject The script project for which the build path should be built
     * @return True if the at least one entry has been added, false otherwise
     */
    private boolean setupBuildPath(IScriptProject scriptProject) {
        final List<IBuildpathEntry> entries = getBuildPathEntries(scriptProject.getProject());
        if (entries.size() <= 0) {
            return false;
        }

        // Adding entries to build path
        try {
            BuildPathUtils.addEntriesToBuildPath(scriptProject, entries);
            return true;
        } catch (ModelException exception) {
            exception.printStackTrace();
            Logger.logException(exception);
            return false;
        }
    }

    /**
     * Returns a Symfony folder path if it exists. It returns null if the
     * path doesn't exist.
     *
     * @param project The project where the folder should be located
     * @param folderPath The path relative to the project folder
     *
     * @return The {@link IPath} if it exists, null otherwise
     */
    private IPath getSymfonyFolderPath(IProject project, String folderPath) {
        IFolder vendorFolder = project.getFolder(folderPath);
        if (vendorFolder.exists()) {
            return vendorFolder.getFullPath();
        }

        return null;
    }

    public void installSymfony(IProgressMonitor monitor) {
        if (monitor == null) {
            monitor = new NullProgressMonitor();
        }

        SymfonyProjectWizardFirstPage firstPage = (SymfonyProjectWizardFirstPage) fFirstPage;
        monitor.subTask("Installing symfony...");
        monitor.beginTask("Installing symfony...", 100);
        monitor.worked(10);

        IProject projectHandle = fFirstPage.getProjectHandle();
        final IScriptProject scriptProject = DLTKCore.create(projectHandle);

        try {
            final IPath symfonyLibraryPath = new Path(firstPage.getLibraryPath());
            final File symfonyArchiveFile = symfonyLibraryPath.toFile();
            final File outputDirectory = scriptProject.getProject().getLocation().toFile();

            uncompressSymfonyLibrary(symfonyArchiveFile, outputDirectory);
            monitor.worked(70);

            if (!scriptProject.isOpen()) {
                scriptProject.open(monitor);
            }

            scriptProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
            setupBuildPath(scriptProject);
            monitor.worked(20);

        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.logException(exception);
        } finally {
            monitor.worked(100);
            monitor.done();
        }
    }

    /**
     * Uncompress the Symfony library to the specified output directory
     *
     * @param symfonyArchiveFile The Symfony library archive file
     * @param outputDirectory The output directory where to put the extracted files
     */
    public void uncompressSymfonyLibrary(File symfonyArchiveFile, File outputDirectory) {
        try {
            File tarArchiveFile = UncompressUtils.uncompressGzipArchive(symfonyArchiveFile, outputDirectory);
            UncompressUtils.uncompressTarArchive(tarArchiveFile, outputDirectory, new SymfonyEntryNameTranslator());

            // Delete the tar archive, it is not required anymore
            tarArchiveFile.delete();
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.logException("Error while extracting symfony library " + symfonyArchiveFile, exception);
        }
    }

    private static class SymfonyEntryNameTranslator implements UncompressUtils.EntryNameTranslator{
        private static final String SYMFONY_PREFIX = "Symfony";

        @Override
        public String translate(String entryName) {
            if (entryName.startsWith(SYMFONY_PREFIX)) {
                entryName = entryName.substring(SYMFONY_PREFIX.length() + 1);
            }

            return entryName;
        }
    }
}
