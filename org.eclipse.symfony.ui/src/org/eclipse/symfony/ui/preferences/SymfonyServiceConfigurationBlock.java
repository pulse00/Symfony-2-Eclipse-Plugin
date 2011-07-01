package org.eclipse.symfony.ui.preferences;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.php.internal.ui.preferences.IStatusChangeListener;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.symfony.ui.Messages;
import org.eclipse.symfony.ui.PreferenceConstants.Keys;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * 
 *
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyServiceConfigurationBlock extends
SymfonyCoreOptionsConfigurationBlock {


	private class SyntheticService {

		private String name;
		private String className;

		public SyntheticService(String name, String className) {


			this.name = name;
			this.className = className;			
		}				

		public String getName() {

			return name;
		}

		public String getClassName() {

			return className;
		}
	}


	private static final Key SYNTHETIC_SERVICES= getSymfonyCoreKey(Keys.SYNTHETIC_SERVICES);

	private Button dummyButton; 

	private TableViewer viewer;

	private IStatus fTaskTagsStatus;

	private List<SyntheticService> serviceList = new ArrayList<SymfonyServiceConfigurationBlock.SyntheticService>();

	public SymfonyServiceConfigurationBlock(IStatusChangeListener context,
			IProject project, Key[] allKeys,
			IWorkbenchPreferenceContainer container) {
		super(context, project, allKeys, container);

	}

	public SymfonyServiceConfigurationBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {

		this(newStatusChangedListener, project, getKeys(), container);		

	}

	private static Key[] getKeys() {
		return new Key[] { SYNTHETIC_SERVICES };
	}	

	@Override
	protected Control createContents(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);

		serviceList.add(new SyntheticService("foo", "bar"));
		serviceList.add(new SyntheticService("mooo", "bfar"));
		createViewer(composite);
		return composite;

	}

	private void createViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
		// Get the content for the viewer, setInput will call getElements in the
		// contentProvider
		
		
		List<SyntheticService> services = unpackServices();
		viewer.setInput(services);
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


	private List<SyntheticService> unpackServices() {

		String currTags = getValue(SYNTHETIC_SERVICES);
		
		System.err.println(currTags);
		
		return new ArrayList<SymfonyServiceConfigurationBlock.SyntheticService>();
	}

	// This will create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Service name", "Service Class" };
		int[] bounds = { 100, 100 };

		// First column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((SyntheticService) element).getName();
				
			}
		});

		// Second column is for the last name
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((SyntheticService) element).getClassName();
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

	private void setDummyValue(String value) {

		System.err.println("set value");
		setValue(SYNTHETIC_SERVICES, value);
		validateSettings(SYNTHETIC_SERVICES, null, null);
	}


	public void setEnabled(boolean isEnabled) {

		System.err.println("set enabled");
		dummyButton.setEnabled(isEnabled);

	}



	@Override
	protected void validateSettings(Key changedKey, String oldValue,
			String newValue) {

		if (changedKey != null) {
			if (SYNTHETIC_SERVICES.equals(changedKey)) {
				fTaskTagsStatus = validateDummyValue();
			} else {
				return;
			}
		} else {
			fTaskTagsStatus = validateDummyValue();
		}
		fContext.statusChanged(fTaskTagsStatus);		


	}

	private IStatus validateDummyValue() {
		return new StatusInfo();
	}


	@Override
	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {

		String title = Messages.SymfonyServiceConfigurationBlock_0;

		String message;
		if (workspaceSettings) {
			message = Messages.SymfonyServiceConfigurationBlock_1;
		} else {
			message = Messages.SymfonyServiceConfigurationBlock_2;
		}
		return new String[] { title, message };

	}

}
