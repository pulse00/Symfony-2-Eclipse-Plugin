package com.dubture.symfony.ui.preferences;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.SelectionButtonDialogField;
import org.eclipse.php.internal.ui.preferences.IStatusChangeListener;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.wizards.fields.DialogField;
import org.eclipse.php.internal.ui.wizards.fields.IStringButtonAdapter;
import org.eclipse.php.internal.ui.wizards.fields.StringButtonDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.pdtextensions.core.ui.preferences.launcher.LauncherConfigurationBlock;
import org.pdtextensions.core.ui.preferences.launcher.LauncherKeyBag;

import com.dubture.symfony.core.SymfonyCorePlugin;

@SuppressWarnings("restriction")
public class SymfonyLauncherConfigurationBlock extends LauncherConfigurationBlock {

	private SelectionButtonDialogField customConsole;

	public SymfonyLauncherConfigurationBlock(IStatusChangeListener context, IProject project,
			IWorkbenchPreferenceContainer container, LauncherKeyBag keyBag) {
		super(context, project, container, keyBag);
	}

	@Override
	protected String getPluginId() {
		return SymfonyCorePlugin.ID;
	}

	@Override
	protected void afterSave() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beforeSave() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createScriptGroup(Composite result) {
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.minimumHeight = 60;
		gd.heightHint = 60;
		
		scriptGroup = new Group(result, SWT.NONE);
		scriptGroup.setLayout(new GridLayout(3, false));
		scriptGroup.setLayoutData(gd);
		scriptGroup.setText(getScriptLabel());

		customConsole = new SelectionButtonDialogField(SWT.CHECK);
		customConsole.setLabelText("Use custom console location");
		customConsole.doFillIntoGrid(scriptGroup, 3);
		customConsole.setDialogFieldListener(new org.eclipse.dltk.internal.ui.wizards.dialogfields.IDialogFieldListener() {
			@Override
			public void dialogFieldChanged(org.eclipse.dltk.internal.ui.wizards.dialogfields.DialogField field) {
				scriptField.setEnabled(customConsole.isSelected());
			}
		});

		scriptField = new StringButtonDialogField(new IStringButtonAdapter() {
			@Override
			public void changeControlPressed(DialogField field) {
				FileDialog dialog = new FileDialog(getShell());
				String path = dialog.open();
				if (path != null) {
					scriptField.setText(path);
				}
			}
		});

		scriptField.setButtonLabel("Browse");

		boolean useProjectPhar = getBooleanValue(useScriptInsideProject);

		if (useProjectPhar) {
			customConsole.setSelection(true);
		} else {
			scriptField.setEnabled(false);
			customConsole.setSelection(false);
		}

		scriptField.setDialogFieldListener(this);
		scriptField.setLabelText(getScriptFieldLabel());
		scriptField.doFillIntoGrid(scriptGroup, 3);
		
		scriptField.getTextControl(null).addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				if (!validateScript(scriptField.getText())) {
					StatusInfo info = new StatusInfo(StatusInfo.WARNING, "The selected file is no valid symfony console script.");
					fContext.statusChanged(info);
				}
			}
		});
		
	}
	
	protected boolean doUseScriptInsideProject() {
		return customConsole.isSelected();
	}
	

	@Override
	protected String getHeaderLabel() {
		return "Select the PHP executable to be used for running the symfony console.";
	}

	@Override
	protected String getProjectChoiceLabel() {
		return "1";
	}

	@Override
	protected String getGlobalChoiceLabel() {
		return "2";
	}

	@Override
	protected String getScriptLabel() {
		return "Console script";
	}

	@Override
	protected String getButtonGroupLabel() {
		return "4";
	}

	@Override
	protected String getScriptFieldLabel() {
		return "";
	}

	@Override
	protected boolean validateScript(String text) {
		
		if (project == null) {
			return false;
		}
		
		IFile file = project.getFile(new Path(text));
		
		//TODO: validate for an actual console script ;)
		return (file != null && file.exists());
	}
}
