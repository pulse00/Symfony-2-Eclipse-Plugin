package com.dubture.symfony.ui.wizards.controller;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.dubture.symfony.ui.SymfonyPluginImages;
import com.dubture.symfony.ui.wizards.CodeTemplateWizardPage;

/**
 * 
 * The Wizard page for the {@link ControllerCreationWizard}
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyControllerWizardPage extends CodeTemplateWizardPage {

	
	protected Text fileText;
	protected ISelection selection;

	protected Label targetResourceLabel;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public SymfonyControllerWizardPage(final ISelection selection, String initialFileName) {
		super("wizardPage", initialFileName); //$NON-NLS-1$
		setTitle("New Controller"); //$NON-NLS-1$
		setDescription("Create a new Symfony controller"); //$NON-NLS-1$
		setImageDescriptor(SymfonyPluginImages.DESC_WIZBAN_ADD_SYMFONY_FILE);
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(final Composite parent) {
		
		final Composite container = new Composite(parent, SWT.NULL);
		
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 400;

		targetResourceLabel = new Label(container, SWT.NULL);
		targetResourceLabel.setText("Controller name");

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		fileText.setFocus();
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		// gd.widthHint = 300;
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				dialogChanged();
			}
		});

		initialize();
		dialogChanged();
		setControl(container);
		PlatformUI
				.getWorkbench()
				.getHelpSystem()
				.setHelp(parent,
						IPHPHelpContextIds.CREATING_A_PHP_FILE_WITHIN_A_PROJECT);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */
	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1) {
				return;
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
				containerName = container.getFullPath().toString();
			}
		}
		setInitialFileName(initialFileName); //$NON-NLS-1$
	}

	protected void setInitialFileName(final String fileName) {
		
		fileText.setFocus();
		fileText.setText(fileName);
		fileText.setSelection(0, fileName.length());
	}


	/**
	 * Ensures that both text fields are set.
	 */
	protected void dialogChanged() {
		final String container = getContainerName();
		final String fileName = getFileName();

		if (container.length() == 0) {
			updateStatus(PHPUIMessages.PHPFileCreationWizardPage_10); //$NON-NLS-1$
			return;
		}
		final IContainer containerFolder = getContainer(container);
		if (containerFolder == null || !containerFolder.exists()) {
			updateStatus(PHPUIMessages.PHPFileCreationWizardPage_11); //$NON-NLS-1$
			return;
		}
		if (!containerFolder.getProject().isOpen()) {
			updateStatus(PHPUIMessages.PHPFileCreationWizardPage_12); //$NON-NLS-1$
			return;
		}
		if (fileName != null
				&& !fileName.equals("") && containerFolder.getFile(new Path(fileName)).exists()) { //$NON-NLS-1$
			updateStatus(PHPUIMessages.PHPFileCreationWizardPage_14); //$NON-NLS-1$
			return;
		}

		int dotIndex = fileName.lastIndexOf('.');
		if (fileName.length() == 0 || dotIndex == 0) {
			updateStatus(PHPUIMessages.PHPFileCreationWizardPage_15); //$NON-NLS-1$
			return;
		}

		if (dotIndex != -1) {
			String fileNameWithoutExtention = fileName.substring(0, dotIndex);
			for (int i = 0; i < fileNameWithoutExtention.length(); i++) {
				char ch = fileNameWithoutExtention.charAt(i);
				if (!(Character.isJavaIdentifierPart(ch) || ch == '.' || ch == '-')) {
					updateStatus(PHPUIMessages.PHPFileCreationWizardPage_16); //$NON-NLS-1$
					return;
				}
			}
		}

		final IContentType contentType = Platform.getContentTypeManager()
				.getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		
//		if (!contentType.isAssociatedWith(fileName)) {
//			// fixed bug 195274
//			// get the extensions from content type
//			final String[] fileExtensions = contentType
//					.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
//			StringBuffer buffer = new StringBuffer(
//					PHPUIMessages.PHPFileCreationWizardPage_17); //$NON-NLS-1$
//			buffer.append(fileExtensions[0]);
//			for (String extension : fileExtensions) {
//				buffer.append(", ").append(extension); //$NON-NLS-1$
//			}
//			buffer.append("]"); //$NON-NLS-1$
//			updateStatus(buffer.toString());
//			return;
//		}

		updateStatus(null);
	}

	protected void updateStatus(final String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}


	public String getFileName() {
		return fileText.getText();
	}


}