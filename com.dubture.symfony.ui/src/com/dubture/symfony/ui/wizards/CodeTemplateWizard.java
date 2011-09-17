package com.dubture.symfony.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore.CompiledTemplate;
import org.eclipse.php.internal.ui.viewsupport.ProjectTemplateStore;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;

import com.dubture.symfony.ui.SymfonyUiPlugin;
import com.dubture.symfony.ui.preferences.SymfonyTemplateStore;

/**
 * 
 * Generic code creation wizard.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
abstract public class CodeTemplateWizard extends Wizard implements INewWizard {

	protected ISelection selection;
	protected CodeTemplateWizardPage codeTemplateWizardPage;
	
	public CompiledTemplate compileTemplate(String containerName, String fileName, String templateName, String contextID) {

		Template template = getTemplateStore().findTemplate(templateName, contextID);

		if (template != null) {
			return SymfonyTemplateStore.compileTemplate(getTemplatesContextTypeRegistry(), template, containerName, fileName);	
		}

		return null;

	}


	public CompiledTemplate compileTemplate(String templateName, String contextID) {

		Template template = getTemplateStore().findTemplate(templateName, contextID);

		if (template != null) {
			return SymfonyTemplateStore.compileTemplate(getTemplatesContextTypeRegistry(), template);	
		}

		return null;

	}	

	protected ContextTypeRegistry getTemplatesContextTypeRegistry() {
		return SymfonyUiPlugin.getDefault().getCodeTemplateContextRegistry();
	}


	public String getContainerName() {
		
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1) {
				return null;
			}

			Object obj = ssel.getFirstElement();
			if (obj instanceof IAdaptable) {
				obj = ((IAdaptable) obj).getAdapter(IResource.class);
			}

			IContainer container = null;
			if (obj instanceof IResource) {
				if (obj instanceof IContainer) {
					container = (IContainer) obj;
				} else {
					container = ((IResource) obj).getParent();
				}
			}

			if (container != null) {
				return container.getFullPath().toString();
			}
		}
		
		return null;
		
	}

	public IProject getProject() {
		
		String projectName = getContainerName();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(projectName));
		IProject project = null;
		if (resource instanceof IProject) {
			project = (IProject) resource;
		} else if (resource != null) {
			project = resource.getProject();
		}
		return project;
	}	



	protected ProjectTemplateStore getTemplateStore() {

		IProject project = getProject();

		ProjectTemplateStore templateStore;
		if (ProjectTemplateStore.hasProjectSpecificTempates(project)) {
			templateStore = new ProjectTemplateStore(project);
		} else {
			templateStore = new ProjectTemplateStore(null);
		}

		try {
			templateStore.load();
		} catch (IOException e) {
			// Ignore the error.
		}
		return templateStore;

	}
	
	@Override
	public boolean performFinish() {

		final String containerName = codeTemplateWizardPage
				.getContainerName();
		
		final String fileName = getFileName();
				
		final PHPTemplateStore.CompiledTemplate template = compileTemplate(containerName, fileName, getTemplateName(), getContextTypeID());

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					new FileCreator().createFile(CodeTemplateWizard.this,
							containerName, fileName, monitor, template.string,
							template.offset);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(),
					PHPUIMessages.PHPFileCreationWizard_0,
					realException.getMessage()); //$NON-NLS-1$
			return false;
		}
		return true;
		

	}
	
	/**
	 * A static nested class for the creation of a new PHP File.
	 * 
	 * @author yaronm
	 * 
	 */
	public static class FileCreator {

		/**
		 * The worker method. It will find the container, create the file if
		 * missing or just replace its contents, and open the editor on the
		 * newly created file. This method does not take an editor id to use
		 * when opening the file.
		 * 
		 * @param wizard
		 * @param containerName
		 * @param fileName
		 * @param monitor
		 * @param contents
		 * @throws CoreException
		 * @see {@link #createFile(Wizard, String, String, IProgressMonitor, String, String)}
		 */
		public void createFile(Wizard wizard, String containerName,
				String fileName, IProgressMonitor monitor, String contents)
				throws CoreException {
			createFile(wizard, containerName, fileName, monitor, contents, 0,
					null);
		}

		public void createFile(Wizard wizard, String containerName,
				String fileName, IProgressMonitor monitor, String contents,
				int offset) throws CoreException {
			createFile(wizard, containerName, fileName, monitor, contents,
					offset, null);
		}

		/**
		 * The worker method. It will find the container, create the file if
		 * missing or just replace its contents, and open the editor on the
		 * newly created file.
		 * 
		 * @param wizard
		 * @param containerName
		 * @param fileName
		 * @param monitor
		 * @param contents
		 * @param editorID
		 *            An optional editor ID to use when opening the file (can be
		 *            null).
		 * @throws CoreException
		 */
		public void createFile(Wizard wizard, String containerName,
				String fileName, IProgressMonitor monitor, String contents,
				final int offset, final String editorID) throws CoreException {
			// create a sample file
			monitor.beginTask(
					NLS.bind(PHPUIMessages.newPhpFile_create, fileName), 2);
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IResource resource = root.findMember(new Path(containerName));
			if (!resource.exists() || !(resource instanceof IContainer)) {
				throwCoreException(PHPUIMessages.PHPFileCreationWizard_1
						+ containerName + PHPUIMessages.PHPFileCreationWizard_2); //$NON-NLS-1$ //$NON-NLS-2$
			}
			IContainer container = (IContainer) resource;
			final IFile file = container.getFile(new Path(fileName));

			// adopt project's/workspace's line delimiter (separator)
			String lineSeparator = Platform.getPreferencesService().getString(
					Platform.PI_RUNTIME,
					Platform.PREF_LINE_SEPARATOR,
					null,
					new IScopeContext[] { new ProjectScope(container
							.getProject()) });
			if (lineSeparator == null)
				lineSeparator = Platform.getPreferencesService().getString(
						Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR,
						null, new IScopeContext[] { new InstanceScope() });
			if (lineSeparator == null)
				lineSeparator = System
						.getProperty(Platform.PREF_LINE_SEPARATOR);
			if (contents != null) {
				contents = contents.replaceAll("(\n\r?|\r\n?)", lineSeparator); //$NON-NLS-1$
			}

			try {
				InputStream stream = openContentStream(contents);
				if (file.exists()) {
					file.setContents(stream, true, true, monitor);
				} else {
					file.create(stream, true, monitor);
				}
				stream.close();
			} catch (IOException e) {
				Logger.logException(e);
				return;
			}

			// Change file encoding:
			/*
			 * if (container instanceof IProject) { PHPProjectOptions options =
			 * PHPProjectOptions.forProject((IProject) container); if (options
			 * != null) { String defaultEncoding = (String)
			 * options.getOption(PHPCoreConstants.PHPOPTION_DEFAULT_ENCODING);
			 * if (defaultEncoding == null || defaultEncoding.length() == 0) {
			 * defaultEncoding = container.getDefaultCharset(); }
			 * file.setCharset(defaultEncoding, monitor); } }
			 */

			monitor.worked(1);
			monitor.setTaskName(NLS.bind(PHPUIMessages.newPhpFile_openning,
					fileName));
			wizard.getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage page = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					try {
						normalizeFile(file);
						IEditorPart editor;
						if (editorID == null) {
							editor = IDE.openEditor(page, file, true);
						} else {
							editor = IDE.openEditor(page, file, editorID, true);
						}
						if (editor instanceof PHPStructuredEditor) {
							StructuredTextViewer textViewer = ((PHPStructuredEditor) editor)
									.getTextViewer();
							textViewer.setSelectedRange(offset, 0);
						}
					} catch (PartInitException e) {
					}
				}
			});
			monitor.worked(1);
		}

		/**
		 * We will initialize file contents with a sample text.
		 */
		private static InputStream openContentStream(String contents) {
			if (contents == null) {
				contents = ""; //$NON-NLS-1$
			}

			return new ByteArrayInputStream(contents.getBytes());
		}

		private static void throwCoreException(String message)
				throws CoreException {
			IStatus status = new Status(IStatus.ERROR,
					PHPUIMessages.PHPFileCreationWizard_4, IStatus.OK, message,
					null); //$NON-NLS-1$
			throw new CoreException(status);
		}

		/**
		 * @param file
		 */
		protected void normalizeFile(IFile file) {
		}

	}	
	
	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
	
	
	protected abstract String getFileName();
	
	protected abstract String getTemplateName();
	
	protected abstract String getContextTypeID();

}