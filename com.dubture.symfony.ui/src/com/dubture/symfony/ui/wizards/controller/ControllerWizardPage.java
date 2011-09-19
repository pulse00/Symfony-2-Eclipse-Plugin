package com.dubture.symfony.ui.wizards.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
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
public class ControllerWizardPage extends CodeTemplateWizardPage {


	protected Text fileText;
	protected ISelection selection;

	protected Label targetResourceLabel;
	private List<String> actions = new ArrayList<String>();
	private TableViewer viewer;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public ControllerWizardPage(final ISelection selection, String initialFileName) {
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
		targetResourceLabel.setText("Controller name:");

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

		gd = new GridData();
		gd.verticalAlignment = SWT.TOP;

		Label label = new Label(container, SWT.NULL);
		label.setText("Actions:");
		label.setLayoutData(gd);


		createActionTable(container);

		FillLayout buttonLayout = new FillLayout();
		buttonLayout.type = SWT.VERTICAL;

		gd = new GridData();
		gd.verticalAlignment = SWT.TOP;

		Composite buttonContainer = new Composite(container, SWT.NULL);
		buttonContainer.setLayout(buttonLayout);
		buttonContainer.setLayoutData(gd);


		Button addInterface = new Button(buttonContainer, SWT.NULL);
		addInterface.setText("Add...");
		//		addInterface.addSelectionListener(interfaceSelectionListener);

		Button removeInterface = new Button(buttonContainer, SWT.NULL);
		removeInterface.setText("Remove");

		//		removeInterface.addSelectionListener(interfaceRemoveListener);


		initialize();
		dialogChanged();
		setControl(container);
		PlatformUI
		.getWorkbench()
		.getHelpSystem()
		.setHelp(parent,
				IPHPHelpContextIds.CREATING_A_PHP_FILE_WITHIN_A_PROJECT);
	}
	

	public class ActionModel {
		
		public String name;

		public ActionModel(String name) {
			
			this.name = name;
			
		}

		public String toString() {
			return name;
		}
	}	

	private class ControllerContentProvider implements IStructuredContentProvider {

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object inputElement) {
						
			return (ActionModel[])inputElement;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {

		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		}

	}	


	private void createActionTable(Composite composite) {

		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;		

		
		viewer = new TableViewer(composite,SWT.BORDER|SWT.FULL_SELECTION);
		viewer.getControl().setLayoutData(gridData);
		
		viewer.setLabelProvider(new LabelProvider());
		viewer.setContentProvider(new ControllerContentProvider());
		viewer.setCellModifier(new ICellModifier() {

			public boolean canModify(Object element, String property) {
				return true;
			}

			public Object getValue(Object element, String property) {
				
				return ((ActionModel)element).name + "";
				
			}

			public void modify(Object element, String property, Object value) {
				
				TableItem item = (TableItem)element;
				((ActionModel)item.getData()).name = value.toString();
				viewer.update(item.getData(), null);
				
			}

		});
		viewer.setColumnProperties(new String[] { "name", "route" });
		viewer.setCellEditors(new CellEditor[] { new TextCellEditor(viewer.getTable()),new TextCellEditor(viewer.getTable()) });

		TableColumn column = new TableColumn(viewer.getTable(),SWT.NONE);
		column.setWidth(100);
		column.setText("Name");

		column = new TableColumn(viewer.getTable(),SWT.NONE);
		column.setWidth(100);
		column.setText("Route");

		ActionModel[] model = createModel();
		viewer.setInput(model);
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);

		viewer.getTable().addListener(SWT.EraseItem, new Listener() {

			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			public void handleEvent(Event event) {
				event.detail &= ~SWT.SELECTED;
			}
		});
	}

	private ActionModel[] createModel() {
		
		ActionModel[] elements = new ActionModel[1];		
		elements[0] = new ActionModel("index");		
		return elements;
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
	
	public List<String> getActions() {
		
		ActionModel[] model = (ActionModel[]) viewer.getInput();		
		List<String> actions = new ArrayList<String>();		
		
		for (ActionModel action : model) {			
			actions.add(action.name);
		}		
		
		return actions;
		
	}
}