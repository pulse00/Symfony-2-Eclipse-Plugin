package com.dubture.symfony.ui.preferences;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.preferences.IStatusChangeListener;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.wizards.fields.DialogField;
import org.eclipse.php.internal.ui.wizards.fields.IDialogFieldListener;
import org.eclipse.php.internal.ui.wizards.fields.IListAdapter;
import org.eclipse.php.internal.ui.wizards.fields.ListDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.preferences.CorePreferenceConstants.Keys;
import com.dubture.symfony.core.util.JsonUtils;
import com.dubture.symfony.ui.Messages;

/**
 * 
 *
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({"restriction", "rawtypes", "unchecked"})
public class ServiceConfigurationBlock extends
CoreOptionsConfigurationBlock {


	
	public class ServiceAdapter implements IListAdapter,
			IDialogFieldListener {

		private boolean canEdit(List selectedElements) {
			return selectedElements.size() == 1;
		}

		private boolean canSetToDefault(List selectedElements) {
			return selectedElements.size() == 1;
		}
		
		@Override
		public void dialogFieldChanged(DialogField field) {
			updateModel(field);

		}

		@Override
		public void customButtonPressed(ListDialogField field, int index) {
			addServiceButtonPressed(index);
			
		}

		@Override
		public void selectionChanged(ListDialogField field) {
			List selectedElements = field.getSelectedElements();
			field.enableButton(IDX_EDIT, canEdit(selectedElements));
			field.enableButton(IDX_DEFAULT, canSetToDefault(selectedElements));
			
		}

		@Override
		public void doubleClicked(ListDialogField field) {
			if (canEdit(field.getSelectedElements())) {
				addServiceButtonPressed(IDX_EDIT);
			}
		}

	}


	public class ServiceLabelProvider extends LabelProvider implements
	ITableLabelProvider, IFontProvider {

		@Override
		public String getText(Object element) {
			return getColumnText(element, 0);
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {

			SyntheticService service = (SyntheticService) element;
			if (columnIndex == 0) {				
				return service.name;
			} else return service.className;
		}


		@Override
		public Font getFont(Object element) {
			return null;
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
	}


	private static final Key SYNTHETIC_SERVICES= getSymfonyCoreKey(Keys.SYNTHETIC_SERVICES);

	private IStatus fTaskTagsStatus;
	
	private static final int IDX_ADD = 0;
	private static final int IDX_EDIT = 1;
	private static final int IDX_REMOVE = 2;
	private static final int IDX_DEFAULT = 4;	

	private ListDialogField serviceList;

	public ServiceConfigurationBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {

		super(newStatusChangedListener, project, getKeys(), container);		
		
		ServiceAdapter adapter = new ServiceAdapter();
		String[] buttons = new String[] {
				"Add service",
				"Edit service",
				"Remove service",
				null};
		serviceList = new ListDialogField(adapter, buttons,
				new ServiceLabelProvider());
		serviceList.setDialogFieldListener(adapter);
		serviceList.setRemoveButtonIndex(IDX_REMOVE);

		String[] columnsHeaders = new String[] {
				"Service name",
				"Service class", };

		serviceList.setTableColumns(new ListDialogField.ColumnsDescription(
				columnsHeaders, true));
//		serviceList.setViewerSorter(new TodoTaskSorter());

		
		unpackServices();
		if (serviceList.getSize() > 0) {
			serviceList.selectFirstElement();
		} else {
			serviceList.enableButton(IDX_EDIT, false);
			serviceList.enableButton(IDX_DEFAULT, false);
		}

		fTaskTagsStatus = new StatusInfo();		

	}

	

	private static Key[] getKeys() {
		return new Key[] { SYNTHETIC_SERVICES };
	}	
	
	
	private Composite createMarkersTabContent(Composite folder) {
		
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;

		PixelConverter conv = new PixelConverter(folder);

		Composite markersComposite = new Composite(folder, SWT.NULL);
		markersComposite.setLayout(layout);
		markersComposite.setFont(folder.getFont());

		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = conv.convertWidthInCharsToPixels(50);
		Control listControl = serviceList.getListControl(markersComposite);
		listControl.setLayoutData(data);

		Control buttonsControl = serviceList.getButtonBox(markersComposite);
		buttonsControl.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL
						| GridData.VERTICAL_ALIGN_BEGINNING));


		return markersComposite;
	}
	

	@Override
	protected Control createContents(Composite parent) {

		setShell(parent.getShell());

		
		GridData data = new GridData();
		data.horizontalSpan = 1;
		
		org.eclipse.swt.graphics.Rectangle rect = parent.getMonitor().getClientArea();
		data.widthHint = rect.width / 4;
		
		Label header = new Label(parent, SWT.WRAP | SWT.BORDER);
		header.setText(Messages.ServiceConfigurationBlock_6);
		header.setLayoutData(data);
		
		
		Composite markersComposite = createMarkersTabContent(parent);

		validateSettings(null, null, null);

		return markersComposite;

	}
	
	
	private void addServiceButtonPressed(int index) {
		
		SyntheticService edited = null;
		
		if (index != IDX_ADD) {
			edited = (SyntheticService) serviceList.getSelectedElements().get(0);
		}
		if (index == IDX_ADD || index == IDX_EDIT) {
			ServiceInputDialog dialog = new ServiceInputDialog(getShell(),
					edited, serviceList.getElements());
			if (dialog.open() == Window.OK) {
				if (edited != null) {
					serviceList.replaceElement(edited, dialog.getResult());
				} else {
					serviceList.addElement(dialog.getResult());
				}
			}
		} else if (index == IDX_DEFAULT) {
			
			Logger.debugMSG("set to default");
			//setToDefaultTask(edited);
		}		
		
	}

	
	private void unpackServices() {

		String currTags = getValue(SYNTHETIC_SERVICES);		
		List<SyntheticService> list = new ArrayList<SyntheticService>();
		
		JSONParser parser = new JSONParser();
		JSONArray data;
		
		try {
			
			data = (JSONArray) parser.parse(currTags);
			
			for (Object object: data) {
								
				String id = (String) ((JSONObject) object).get(Service.NAME);
				String className =  (String) ((JSONObject) object).get(Service.CLASS);
				list.add(new SyntheticService(id,className));
				
			}
			
		} catch (Exception e) {

			Logger.logException(e);
		}
		
		serviceList.setElements(list);
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
	
	protected final void updateModel(DialogField field) {
		
		if (field == serviceList) {
			
			List list = serviceList.getElements();
			
			JSONArray data = new JSONArray();
			
			for (int i = 0; i < list.size(); i++) {
				
				SyntheticService elem = (SyntheticService) list.get(i);				
				JSONObject jsonService = JsonUtils.createService(elem.name, elem.className);
				data.add(jsonService);
				
			}
			
			setValue(SYNTHETIC_SERVICES, data.toString());			
			validateSettings(SYNTHETIC_SERVICES, null, null);
			
		} 
	}	
	
	@Override
	public void performDefaults() {
		
		for (Key key : fAllKeys) {
		
			if (key.getName().equals(SYNTHETIC_SERVICES.getName())) {
		
				String defaults = key.getStoredValue(DefaultScope.INSTANCE, fManager);
				JSONArray data = JsonUtils.parseArray(defaults);
				List<SyntheticService> services = new ArrayList<SyntheticService>();
				
				for (Object element : data) {
					String name = (String) ((JSONObject) element).get(Service.NAME);
					String className = (String) ((JSONObject) element).get(Service.CLASS);					
					services.add(new SyntheticService(name, className));
				}
				
				serviceList.removeAllElements();
				serviceList.setElements(services);
				serviceList.refresh();				
				updateModel(serviceList);				
			}
		}		
	}
}