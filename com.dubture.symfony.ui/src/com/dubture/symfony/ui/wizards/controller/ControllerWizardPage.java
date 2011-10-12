package com.dubture.symfony.ui.wizards.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ModelProvider;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.wizards.fields.ComboDialogField;
import org.eclipse.php.internal.ui.wizards.fields.DialogField;
import org.eclipse.php.internal.ui.wizards.fields.IDialogFieldListener;
import org.eclipse.php.internal.ui.wizards.fields.SelectionButtonDialogField;
import org.eclipse.php.internal.ui.wizards.fields.StringDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.dubture.symfony.core.model.Action;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;
import com.dubture.symfony.index.dao.Route;
import com.dubture.symfony.ui.SymfonyPluginImages;
import com.dubture.symfony.ui.SymfonyUiPlugin;
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
	private Button addAction;
	private Button removeAction;
	private ComboDialogField routeType;
	private Link routeTypeLink;
	private StringDialogField routePrefix;
	
	private static final Image CHECKED = SymfonyPluginImages.DESC_OBJS_ROUTE.createImage();	
	private static final Image UNCHECKED = SymfonyPluginImages.DESC_OBJS_ROUTE.createImage();	
	
	
	private enum ModelProvider {
		
		INSTANCE;

		private List<Action> actions;

		private ModelProvider() {
			actions = new ArrayList<Action>();
			actions.add(new Action(null, "index", "/", "viewpath"));
		}

		public List<Action> getActions() {
			return actions;
		}		
	}


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
		layout.numColumns = 4;
		layout.verticalSpacing = 9;


		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		targetResourceLabel = new Label(container, SWT.NULL);
		targetResourceLabel.setText("Controller name:");

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		fileText.setFocus();
		gd = new GridData(GridData.FILL_HORIZONTAL);
		
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				dialogChanged();
			}
		});


		final SelectionButtonDialogField createActions = new SelectionButtonDialogField(SWT.CHECK);
		createActions.setLabelText("Generate actions");
		createActions.doFillIntoGrid(container, 2);
		
		createActions.setDialogFieldListener(new IDialogFieldListener() {
			
			@Override
			public void dialogFieldChanged(DialogField field) {

				boolean enabled = createActions.isSelected();
				
				
				viewer.getControl().setEnabled(enabled);
				addAction.setEnabled(enabled);
				removeAction.setEnabled(enabled);
				routeType.setEnabled(enabled);
//				routeTypeLink.setEnabled(enabled);
				routePrefix.setEnabled(enabled);
				
			}
		});
		
		

		routePrefix = new StringDialogField();
		routePrefix.setLabelText("Prefix");
		routePrefix.doFillIntoGrid(container, 2);
		routePrefix.setEnabled(false);

		
		String[] items = new String[] {"Annotation", "Yaml", "XML"};
		
		routeType = new ComboDialogField(SWT.READ_ONLY);
		routeType.setLabelText("Route type:");
		routeType.doFillIntoGrid(container, 2);
		routeType.setItems(items);
		routeType.selectItem(0);
		routeType.setEnabled(false);


		gd = new GridData();
		gd.horizontalSpan = 4;
		gd.horizontalAlignment = SWT.LEFT;
		
//		routeTypeLink = new Link(container, SWT.NONE);
//		routeTypeLink.setText("Configure default...");
//		routeTypeLink.setEnabled(false);
		
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

		createViewer(container);
		Composite buttonContainer = new Composite(container, SWT.NULL);
		buttonContainer.setLayout(buttonLayout);
		buttonContainer.setLayoutData(gd);

		viewer.getControl().setEnabled(false);
		
		addAction = new Button(buttonContainer, SWT.NULL);
		addAction.setText("Add...");
		addAction.setEnabled(false);
		//		addInterface.addSelectionListener(interfaceSelectionListener);

		removeAction = new Button(buttonContainer, SWT.NULL);
		removeAction.setText("Remove");
		removeAction.setEnabled(false);

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

	private void createViewer(Composite parent) {
		
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
		createColumns(viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
		// Get the content for the viewer, setInput will call getElements in the
		// contentProvider
		viewer.setInput(ModelProvider.INSTANCE.getActions());

		// Make the selection available to other views
		//		getSite().setSelectionProvider(viewer);
		// Set the sorter for the table

		// Layout the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
	}

	public TableViewer getViewer() {
		return viewer;
	}

	// This will create the columns for the table
	private void createColumns(final TableViewer viewer) {
		
		String[] titles = { "Name", "Route", "Template" };
		int[] bounds = { 100, 100, 100 };

		// First column is for the action name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Action p = (Action) element;
				return p.getElementName();
			}
		});

		// Second column is for the route
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			
			@Override
			public String getText(Object element) {
				Action p = (Action) element;
				
				Route route = p.getRoute();
				
				if (route != null)
					return route.getName();
				
				return null;

			}
		});

		// Second column is for the template creation
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}

			@Override
			public Image getImage(Object element) {
				if (((Action) element).hasTemplate()) {
					return CHECKED;
				} else {
					return UNCHECKED;
				}
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;

	}	


	private void createActionTable(Composite composite) {

		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;		



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

	public List<Action> getActions() {

		return ModelProvider.INSTANCE.actions;

	}

	public String getRouteType() {
	
		switch (routeType.getSelectionIndex()) {

		case 0:
			return SymfonyCoreConstants.ANNOTATION;
		case 1:
			return SymfonyCoreConstants.YAML;
		case 2:
			return SymfonyCoreConstants.XML;
		}
		
		return null;
		
	}
	
	public String getRoutePrefix() {
				
		return routePrefix.getText();
		
	}
}