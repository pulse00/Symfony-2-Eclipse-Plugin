package com.dubture.symfony.debug.launch;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.AppKernel;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.debug.server.SymfonyServer;

/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class LaunchConfigurationTab extends AbstractLaunchConfigurationTab {

	private Combo kernelCombo;
	private Text route;
	private AutoCompleteField ac;

	@Override
	public void createControl(Composite parent) {

		Font font = parent.getFont();
		Composite comp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		comp.setLayout(layout);
		comp.setFont(font);

		GridData gd = new GridData(GridData.FILL_BOTH);
		comp.setLayoutData(gd);
		setControl(comp);
		// setHelpContextId();

		Group group = new Group(comp, SWT.NONE);
		group.setFont(font);
		layout = new GridLayout();
		group.setLayout(layout);
		group.setLayoutData(new GridData(GridData.FILL_BOTH));

		String controlName = "Launch Settings";
		group.setText(controlName);
		
		
		Label label = new Label(group, SWT.NONE);
		label.setText("Environment");
		
		GridData data = new GridData(200, GridData.FILL, true, false);
		label.setLayoutData(data);
		
		kernelCombo = new Combo(group, SWT.READ_ONLY);
		
		data = new GridData(GridData.FILL, GridData.FILL, true, false);
		data.horizontalIndent = 0;
		kernelCombo.setLayoutData(data);		
		
		kernelCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDirty(true);
				updateLaunchConfigurationDialog();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		
		Label routeLabel = new Label(group, SWT.NONE);
		routeLabel.setText("Route");
		
		route = new Text(group, SWT.NONE);
		
		ac = new AutoCompleteField(route, new TextContentAdapter(), null);
		
		
		
		Dialog.applyDialogFont(comp);
		setControl(comp);
		
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		
		try {
			
			String env = configuration.getAttribute(SymfonyServer.ENVIRONMENT, "");
			
			if (env.length() == 0)
				return;
			
			for (int i=0; i<kernelCombo.getItemCount(); i++) {
				
				String item = kernelCombo.getItem(i);
				
				if (item.equals(env))
					kernelCombo.select(i);
			}
						
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

		
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {

		try {
			
			String file = configuration.getAttribute(Server.FILE_NAME, "");			
			IPath path = new Path(file);			
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(path.removeLastSegments(path.segmentCount()-1).toString());			
			IScriptProject scriptProject = DLTKCore.create(project);

			List<AppKernel> kernels = SymfonyModelAccess.getDefault().getKernels(scriptProject);

			kernelCombo.removeAll();
			
			for (AppKernel kernel : kernels) {				
				kernelCombo.add(kernel.getEnvironment());
			}
			
		} catch (CoreException e) {
			Logger.logException(e);
		}
		
		kernelCombo.select(0);		
		isValid(configuration);
		
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		
		try {
			
			if (kernelCombo.getItemCount() == 0)
				return;
			
			String env = kernelCombo.getItem(kernelCombo.getSelectionIndex());		
			configuration.setAttribute(SymfonyServer.ENVIRONMENT, env);			
			configuration.doSave();
			
		} catch (CoreException e) {

			e.printStackTrace();
		}
		
	}

	@Override
	public String getName() {

		return "Symfony";
		
	}
	
}
