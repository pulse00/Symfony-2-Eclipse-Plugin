package com.dubture.symfony.ui.wizards.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.ComboDialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.SelectionButtonDialogField;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.wizards.NameGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dubture.composer.ui.wizard.LocationGroup;

/**
 * Based on {@link org.eclipse.php.internal.ui.wizards.LocationGroup}
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyLocationGroup extends LocationGroup {

	private Text fVirtualHost;
	private SelectionButtonDialogField fCustomHost;
	protected String virtualHost;

	public SymfonyLocationGroup(Composite composite, NameGroup nameGroup, Shell shell) {
		super(composite, nameGroup, shell);
	}
	
	@Override
	protected void createExternalLocation(Group group, int numColumns) {
		
	}
	
	@Override
	protected void createLocalServersGroup(Group group, int numColumns) {
		Server[] servers = ServersManager.getServers();
		List<String> docRoots = new ArrayList<String>();
		for (int i = 0; i < servers.length; i++) {
			String docRoot = servers[i].getDocumentRoot();
			if (docRoot != null && !"".equals(docRoot.trim())) { //$NON-NLS-1$
				docRoots.add(docRoot);
			}
		}

		if (docRoots.size() > 0) {
			fLocalServerRadio = new SelectionButtonDialogField(SWT.RADIO);
			fLocalServerRadio.setDialogFieldListener(this);
			fLocalServerRadio.setLabelText(PHPUIMessages.PHPProjectWizardFirstPage_localServerLabel); //$NON-NLS-1$
			fLocalServerRadio.setSelection(false);
			fLocalServerRadio.doFillIntoGrid(group, numColumns);
			fSeverLocationList = new ComboDialogField(SWT.READ_ONLY);
			fSeverLocationList.setLabelText("Target directory");
			fSeverLocationList.doFillIntoGrid(group, numColumns );
			GridData data = (GridData) fSeverLocationList.getLabelControl(null).getLayoutData();
			data.widthHint = 120;
			fSeverLocationList.getLabelControl(null).setLayoutData(data);
			
			fSeverLocationList.setEnabled(false);
			docRootArray = new String[docRoots.size()];
			docRoots.toArray(docRootArray);
			fSeverLocationList.setItems(docRootArray);
			fSeverLocationList.selectItem(0);
			fLocalServerRadio.attachDialogField(fSeverLocationList);
			fWorkspaceRadio.setSelection(false);
			fLocalServerRadio.setSelection(true);
			LayoutUtil.setHorizontalGrabbing(fSeverLocationList.getComboControl(null));
			
			fCustomHost = new SelectionButtonDialogField(SWT.CHECK);
			fCustomHost.setDialogFieldListener(this);
			fCustomHost.setLabelText("Custom hostname:");
			fCustomHost.doFillIntoGrid(group, numColumns-1);
			
			fVirtualHost = new Text(group, SWT.SINGLE | SWT.BORDER);
			fVirtualHost.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					virtualHost = fVirtualHost.getText();
				}
			});
			
			GridDataFactory.fillDefaults().span(1, 1).grab(true, false).applyTo(fVirtualHost);
			fVirtualHost.setEnabled(false);
			
		} else {
			createNoLocalServersFound(group, numColumns);
		}
	}
	
	public boolean isExistingLocation() {
		return false;
	}
	
	@Override
	public IPath getLocation() {
		if (isInWorkspace()) {
			return Platform.getLocation();
		}
		return new Path(fSeverLocationList.getText());
	}
	
	public void changeControlPressed(DialogField field) {
		
	}
	
	public void update(Observable o, Object arg) {
		if (isInWorkspace()) {
			fLocation.setText(getDefaultPath(fNameGroup.getName()));
		}
		if (docRootArray != null && docRootArray.length > 0) {
			int index = fSeverLocationList.getSelectionIndex();
			String[] items = getDocItems(docRootArray);
			fSeverLocationList.setItems(items);
			fSeverLocationList.selectItem(index);
		}
		
		if (fCustomHost != null && fCustomHost.isSelected() == false && fNameGroup.getName().length() > 0) {
			fVirtualHost.setText("http://" + fNameGroup.getName() + ".dev");
			fVirtualHost.setEnabled(false);
		} else if (fCustomHost != null && fCustomHost.isSelected()) {
			fVirtualHost.setEnabled(true);
		}
		
		fireEvent();
	}

	public void dialogFieldChanged(DialogField field) {
		
		fireEvent();
	}

	public String getVirtualHost() {
		return virtualHost;
	}
}