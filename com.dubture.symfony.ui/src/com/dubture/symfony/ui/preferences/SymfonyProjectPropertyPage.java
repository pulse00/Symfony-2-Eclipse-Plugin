package com.dubture.symfony.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.IStringButtonAdapter;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.StringButtonDialogField;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.osgi.service.prefs.BackingStoreException;

import com.dubture.symfony.core.SymfonyCorePlugin;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.preferences.CorePreferenceConstants.Keys;

@SuppressWarnings("restriction")
public class SymfonyProjectPropertyPage extends PropertyPage implements IWorkbenchPropertyPage {

	private StringButtonDialogField containerPath;
	private IProject project;
	private IEclipsePreferences node;

	public SymfonyProjectPropertyPage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Control createContents(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().equalWidth(false).numColumns(3).applyTo(container);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(container);
		
		containerPath = new StringButtonDialogField(new IStringButtonAdapter() {
			
			@Override
			public void changeControlPressed(DialogField field) {
				openDialog();
			}
		});
		
		containerPath.setLabelText("Dumped container");
		containerPath.setButtonLabel("Browse");
		containerPath.doFillIntoGrid(container, 3);
		containerPath.getTextControl(null).setEnabled(false);
		
		IAdaptable element2 = getElement();
		
		project = null;
		if (element2 instanceof IScriptProject) {
			project = ((IScriptProject)element2).getProject();
		} else if (element2 instanceof IProject) {
			project = (IProject) element2;
		}
		
		loadContainer();
		
		return container;
	}
	
	protected void loadContainer() {
		node = new ProjectScope(project).getNode(SymfonyCorePlugin.ID);
		String container = node.get(Keys.DUMPED_CONTAINER, "app/cache/dev/appDevDebugProjectContainer.xml");
		containerPath.setText(container);
	}
	
	protected void openDialog() {


		try {
			ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
			dialog.setInput(project);
			dialog.setAllowMultiple(false);
			dialog.setTitle("Select the dumped service container to use");
			
			if (dialog.open() == Window.OK) {
				IResource resource = (IResource) dialog.getFirstResult();
				containerPath.setText(resource.getFullPath().removeFirstSegments(1).toString());
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
	
	protected void saveSettings() {
		try {
			node.put(Keys.DUMPED_CONTAINER, containerPath.getText());
			node.flush();
		} catch (BackingStoreException e) {
			Logger.logException(e);
		}
	}

	@Override
	public boolean performOk() {
		saveSettings();
		return super.performOk();
	}
	
	@Override
	protected void performDefaults() {
		containerPath.setText("app/cache/dev/appDevDebugProjectContainer.xml");
		saveSettings();
		super.performDefaults();
	}
	
	@Override
	protected void performApply() {
		saveSettings();
		super.performApply();
	}
}
