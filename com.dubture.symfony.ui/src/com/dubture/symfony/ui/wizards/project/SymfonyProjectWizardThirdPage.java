package com.dubture.symfony.ui.wizards.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IAccessRule;
import org.eclipse.dltk.core.IBuildpathAttribute;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.buildpath.BuildPathUtils;
import org.eclipse.php.internal.ui.wizards.PHPProjectWizardFirstPage;
import org.eclipse.php.internal.ui.wizards.PHPProjectWizardThirdPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;

import com.dubture.symfony.core.SymfonyCorePlugin;
import com.dubture.symfony.core.log.Logger;

/**
 * 
 * Initializes the Symfony project structure and adds the folders to the buildpath.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyProjectWizardThirdPage extends PHPProjectWizardThirdPage {

	private int level;
	private String symfonyPath;	
	private boolean symfonyInstalled = false;


	public SymfonyProjectWizardThirdPage(PHPProjectWizardFirstPage mainPage) {
		super(mainPage);
		


	}

	private void importFile(File file, IProject project, List<IBuildpathEntry> entries) {

		try {

			level++;

			String path = file.getAbsolutePath().replace(symfonyPath, "");

			// import the directory
			if(file.isDirectory() && ! file.isHidden()) {

				IFolder folder = project.getFolder(path);

				if (!folder.exists()) {
					folder.create(true, false, null);	
				}

				// add root folders to buildpath
				if (level == 1 && ! folder.getFullPath().toString().endsWith("bin")) {

					IPath[] exclusion = {};

					if (folder.getName().equals("app")) {
						exclusion = new IPath[] { new Path("app/cache") };
					}

					IBuildpathEntry entry =  DLTKCore.newSourceEntry(folder.getFullPath(), exclusion);
					entries.add(entry);					
				}

				// now import recursively
				for (File f : file.listFiles()) {			
					importFile(f, project, entries);					
				}

				// create the project file
			} else if (file.isFile()) {				
				FileInputStream fis = new FileInputStream(file);				
				IFile iFile = project.getFile(path);				
				iFile.create(fis, true, null);
			}

			level--;

		} catch (CoreException e) {
			Logger.logException(e);
		} catch (FileNotFoundException e) {
			Logger.logException(e);
		}

	}

	private void installSymfony(final IProgressMonitor monitor) {

		monitor.beginTask("initializing symfony", 100);
		monitor.worked(10);

		Bundle bundle = Platform.getBundle(SymfonyCorePlugin.ID);
		URL fileURL = bundle.getEntry("Resources/symfony/standard");

		IProject projectHandle = fFirstPage.getProjectHandle();
		final IScriptProject scriptProject = DLTKCore.create(projectHandle);		

		File file = null;
		final List<IBuildpathEntry> entries = new ArrayList<IBuildpathEntry>();
		level = 0;

		try {
			
			file = new File(FileLocator.resolve(fileURL).toURI());
			symfonyPath = FileLocator.toFileURL(fileURL).getPath();

			if (file.isDirectory()) {

				final File[] files = file.listFiles();

				if(!scriptProject.isOpen()) {
					scriptProject.open(monitor);
				} 

				if (files != null && scriptProject != null && scriptProject.isOpen()) {

					
					IBuildpathEntry[] raw = scriptProject.getRawBuildpath();
					
					BuildPathUtils.removeEntryFromBuildPath(scriptProject, raw[0]);
					
					for (File f : files) {
						importFile(f, scriptProject.getProject(), entries);
					}
					
					
					
		            BuildPathUtils.addEntriesToBuildPath(scriptProject, entries);
					monitor.worked(90);

				}
			}

		} catch (URISyntaxException e) {
			Logger.logException(e);
			e.printStackTrace();
		} catch (IOException e) {
			Logger.logException(e);
			e.printStackTrace();
		} catch (ModelException e) {
			Logger.logException(e);
			e.printStackTrace();
		} finally {
			
			symfonyInstalled = true;
			monitor.worked(100);
			monitor.done();					
		}
	}
	
	
	
	@Override
	public void setVisible(boolean visible) {

		if (!symfonyInstalled)
			installSymfony(new NullProgressMonitor());				
		
		super.setVisible(visible);
	}
	
	


	
	@Override
	public void performFinish(final IProgressMonitor monitor) throws CoreException, InterruptedException {

		if (symfonyInstalled == true)
			return;
	
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				installSymfony(monitor);

			}
		});
		
		
		super.performFinish(monitor);
	}	
}