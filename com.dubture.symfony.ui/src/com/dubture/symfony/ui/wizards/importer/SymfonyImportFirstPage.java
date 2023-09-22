package com.dubture.symfony.ui.wizards.importer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.internal.resources.ProjectDescription;
import org.eclipse.core.internal.resources.ProjectDescriptionReader;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.IStringButtonAdapter;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.StringButtonDialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.StringDialogField;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.VersionedPackage;
import org.eclipse.php.composer.api.collection.Dependencies;
import org.eclipse.php.composer.api.json.ParseException;
import com.dubture.symfony.core.SymfonyVersion;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.ui.SymfonyPluginImages;
import com.dubture.symfony.ui.SymfonyUiPlugin;

/**
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
@SuppressWarnings("restriction")
public class SymfonyImportFirstPage extends WizardPage {

	public class ValidationException extends Exception {
		private static final long serialVersionUID = 1L;
		public ValidationException(String string) {
			super(string);
		}
	}

	private StringButtonDialogField sourceButton;
	private StringButtonDialogField containerButton;
	private StringButtonDialogField consoleButton;

	protected IPath sourcePath;
	protected IPath consolePath;
	protected IPath containerPath;
	protected String projectName;
	protected PHPVersion phpVersion;
	protected SymfonyVersion symfonyVersion;

	private SymfonyProjectScanner scanner;
	private StringDialogField projectNameField;
	private IWorkspace workspace;

	protected SymfonyImportFirstPage(String pageName) {
		super(pageName);
		scanner = new SymfonyProjectScanner();
		workspace = ResourcesPlugin.getWorkspace();
		setTitle("Import Symfony project");
		setDescription("Import an existing Symfony project into your workspace");
		setImageDescriptor(SymfonyPluginImages.DESC_WIZBAN_IMPORT_PROJECT);
	}

	@Override
	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(container);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(container);

		projectNameField = new StringDialogField();
		projectNameField.setLabelText("Project name");
		projectNameField.doFillIntoGrid(container, 3);
		projectNameField.setDialogFieldListener(new IDialogFieldListener() {

			@Override
			public void dialogFieldChanged(DialogField field) {
				projectName = projectNameField.getText();
				validateSettings();
			}
		});

		sourceButton = new StringButtonDialogField(new IStringButtonAdapter() {
			@Override
			public void changeControlPressed(DialogField field) {
				try {
					DirectoryDialog dialog = new DirectoryDialog(getShell());
					String result = dialog.open();
					if (result != null) {
						sourceButton.setText(result);
						sourcePath = new Path(result);
						enableButtons();
						scanSourceFolder();
					}
				} catch (Exception e) {
					Logger.logException(e);
				}
			}
		});

		sourceButton.setButtonLabel("Browse");
		sourceButton.setLabelText("Source folder");
		sourceButton.doFillIntoGrid(container, 3);
		sourceButton.getTextControl(null).setEnabled(false);

		containerButton = new StringButtonDialogField(new IStringButtonAdapter() {
			@Override
			public void changeControlPressed(DialogField field) {

				try {
					FileDialog dialog = new FileDialog(getShell());
					//dialog.setFilterExtensions(new String[]{"xml"});
					String result = dialog.open();
					String relativePath = getRelativePath(result);
					if (result != null && relativePath != null) {
						containerButton.setText(relativePath);
						containerPath= new Path(relativePath);
						validateSettings();
					}
				} catch (Exception e) {
					Logger.logException(e);
				}

			}
		});

		LayoutUtil.setHorizontalGrabbing(sourceButton.getTextControl(null));

		containerButton.setButtonLabel("Browse");
		containerButton.setLabelText("Dumped container");
		containerButton.doFillIntoGrid(container, 3);

		consoleButton = new StringButtonDialogField(new IStringButtonAdapter() {
			@Override
			public void changeControlPressed(DialogField field) {
				FileDialog dialog = new FileDialog(getShell());
				String result = dialog.open();
				String relativePath = getRelativePath(result);
				if (result != null && relativePath != null) {
					consoleButton.setText(relativePath);
					consolePath = new Path(relativePath);
					validateSettings();
				}
			}
		});

		LayoutUtil.setHorizontalGrabbing(containerButton.getTextControl(null));

		consoleButton.setButtonLabel("Browse");
		consoleButton.setLabelText("Symfony console");
		consoleButton.doFillIntoGrid(container, 3);

		LayoutUtil.setHorizontalGrabbing(consoleButton.getTextControl(null));

		PlatformUI.getWorkbench().getHelpSystem().setHelp(container, SymfonyUiPlugin.PLUGIN_ID + "." + "import_firstpage");

		disableButtons();

		setControl(container);
	}

	protected String getRelativePath(String path) {

		if (path == null) {
			return null;
		}

		IPath container = new Path(path);
		if (!sourcePath.isPrefixOf(container)) {
			return null;
		}

		return container.setDevice(null).removeFirstSegments(sourcePath.segmentCount()).toOSString();
	}

	protected void enableButtons() {
		consoleButton.setEnabled(true);
		consoleButton.getTextControl(null).setEnabled(false);

		containerButton.setEnabled(true);
		containerButton.getTextControl(null).setEnabled(false);
	}

	protected void disableButtons() {
		consoleButton.setEnabled(false);
		containerButton.setEnabled(false);
	}

	protected void scanSourceFolder() throws InvocationTargetException, InterruptedException {

		if (sourcePath == null) {
			return;
		}

		getWizard().getContainer().run(true, true, scanner);

		if (scanner.getConsole() != null && scanner.getConsole().exists()) {
			IPath newConsolePath = new Path(scanner.getConsole().getAbsolutePath());
			newConsolePath = newConsolePath.removeFirstSegments(sourcePath.segmentCount());
			consoleButton.setText(newConsolePath.setDevice(null).toOSString());
			consolePath = newConsolePath;
		}

		if (scanner.getContainer() != null && scanner.getContainer().exists()) {
			IPath newContainerPath = new Path(scanner.getContainer().getAbsolutePath());
			newContainerPath = newContainerPath.setDevice(null).removeFirstSegments(sourcePath.segmentCount());
			containerButton.setText(newContainerPath.toOSString());
			containerPath = newContainerPath;
		}

		validateSettings();
	}

	protected class SymfonyProjectScanner implements IRunnableWithProgress {

		private File dumpedContainer;
		private File console;

		@Override
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			try {
				monitor.beginTask("Scanning Symfony project", 4);
				scanForDumpedContainer();
				monitor.worked(1);
				scanForConsole();
				monitor.worked(1);
				scanVersions();
				monitor.worked(1);
				scanForExistingProjectFile();
				monitor.worked(1);
			} catch (Exception e) {
				Logger.logException(e);
			} finally {
				monitor.done();
			}
		}

		protected void scanForDumpedContainer() {
			String sourcePath = sourceButton.getText();
			File sourceDirectory = new File(sourcePath);
			Collection<File> files = FileUtils.listFiles(sourceDirectory, null, true);
			for (File file : files) {
				if (file.getName().toLowerCase().endsWith("container.xml")) {
					dumpedContainer = file;
					break;
				}
			}
		}

		protected void scanForConsole() {
			String sourcePath = sourceButton.getText();
			File sourceDirectory = new File(sourcePath);
			Collection<File> files = FileUtils.listFiles(sourceDirectory, null, true);

			for (File file : files) {
				if (file.getName().toLowerCase().equals("console")) {
					console = file;
					break;
				}
			}
		}

		protected void scanVersions() throws IOException, ParseException {
			IPath composerPath = sourcePath.append("composer.json");
			ComposerPackage composer = new ComposerPackage(composerPath.toFile());
			Dependencies require = composer.getRequire();

			for (VersionedPackage versioned: require) {
				if ("symfony/symfony".equals(versioned.getName())) {
					if (versioned.getVersion().startsWith("2.1")) {
						symfonyVersion = SymfonyVersion.Symfony2_1_9;
					} else {
						symfonyVersion = SymfonyVersion.Symfony2_2_1;
					}
				}
				if ("php".equals(versioned.getName())) {
					if (versioned.getVersion().contains("5.3")) {
						phpVersion = PHPVersion.PHP5_3;
					} else {
						phpVersion = PHPVersion.PHP5_4;
					}
				}
			}
		}

		protected void scanForExistingProjectFile() throws IOException {

			IPath projectPath = sourcePath.append(".project");

			if (projectPath.toFile().exists()) {
				ProjectDescriptionReader reader = new ProjectDescriptionReader(workspace);
				final ProjectDescription projectDescription = reader.read(projectPath);

				if (projectDescription != null && projectDescription.getName() != null) {
					getShell().getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							projectName = projectDescription.getName();
							projectNameField.setTextWithoutUpdate(projectName);
						}
					});
				}
			}
		}

		public File getConsole() {
			return console;
		}

		public File getContainer() {
			return dumpedContainer;
		}
	}

	public IPath getSourcePath() {
		return sourcePath;
	}

	public IPath getContainerPath() {
		return containerPath;
	}

	public IPath getConsolePath() {
		return consolePath;
	}

	public String getProjectName() {
		return projectName;
	}

	public PHPVersion getPHPVersion() {
		return phpVersion;
	}

	public SymfonyVersion getSymfonyVersion() {
		return symfonyVersion;
	}

	public void validateProjectName() throws ValidationException {

		try {
			IProject project = workspace.getRoot().getProject(projectName);
			if (project != null && project.exists()) {
				throw new ValidationException("A project with the same name already exists in the workspace.");
			}
		} catch (Exception e) {
			throw new ValidationException(e.getMessage());
		}
	}

	public void validateContainerPath() throws ValidationException {
		try {
			validatePath(containerPath.toOSString());
		} catch (Exception e) {
			throw new ValidationException("The selected service container does not exist.");
		}
	}

	public void validateConsolePath() throws ValidationException {
		try {
			validatePath(consolePath.toOSString());
		} catch (Exception e) {
			throw new ValidationException("The selected Symfony console does not exist.");
		}
	}

	public void validatePath(String path) throws ValidationException {

		if (sourcePath == null) {
			throw new ValidationException("");
		}

		File absolute = new File(sourcePath.toOSString(), path);

		if (absolute.exists() == false) {
			throw new ValidationException("");
		}
	}

	public void validateSettings() {

		try {
			validateProjectName();
			validateContainerPath();
		} catch (ValidationException e) {
			setErrorMessage(e.getMessage());
			setPageComplete(false);
			return;
		}

		setPageComplete(true);
		setErrorMessage(null);
	}
}
