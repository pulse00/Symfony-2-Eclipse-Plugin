package com.dubture.symfony.debug.launch;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationsDialog;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.ui.launching.PHPWebPageLaunchConfigurationTab;
import org.eclipse.php.internal.ui.preferences.ScrolledCompositeImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.AppKernel;
import com.dubture.symfony.core.model.SymfonyKernelAccess;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.debug.DebuggerConfiguration;
import com.dubture.symfony.debug.server.SymfonyServer;
import com.dubture.symfony.debug.util.ServerUtils;
import com.dubture.symfony.index.dao.Route;

/**
 *
 * A Symfony tab for the XDebug Debugger. Adds Environment
 * and Route selection to the debugger.
 *  
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class LaunchConfigurationTab extends AbstractLaunchConfigurationTab {

	private Combo kernelCombo;
	private Text route;
	private Route currentRoute;
	private AutoCompleteField ac;
	private SymfonyModelAccess model = SymfonyModelAccess.getDefault();
	private IScriptProject project;
	private List<AppKernel> kernels;
	private AppKernel kernel;
	
	private boolean isSymfonyDebugger = false;	
	private Composite comp;	
	private ILaunchConfiguration config;
	

	private KeyListener routeListener = new KeyListener() {
		
		@Override
		public void keyReleased(KeyEvent e) {

			if (project == null)
				return;
			
			String prefix = route.getText();
			
			if (prefix.length() < 1)
				return;
			
			List<Route> routes = model.findRoutes(project, prefix);			
			String[] names = new String[routes.size()];
			
			int i=0;
			for (Route route : routes) {
				names[i++] = route.getName();				
			}
			
			ac.setProposals(names);
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {

		}
	};
	private Text url;
	
	
	public LaunchConfigurationTab() {
				
		super();
		
	}
	
	public boolean isValid(ILaunchConfiguration launchConfig) {
		
		setErrorMessage(null);
		
		try {
			if (project == null || project.getProject().hasNature(SymfonyNature.NATURE_ID) == false)
				return true;
		} catch (CoreException e) {
			Logger.logException(e);
			return true;
		}
		
		if (isSymfonyDebugger == false)
			return true;
		
		String routeName = route.getText();		
		Route route = model.findRoute(routeName, project);
		
		if (route == null) {
			
			String message = "The route " + routeName + " does not exist in the project " + project.getElementName();
			if (routeName.length() == 0) {
				message = "You need to set a valid route name for the launch configuration";
			}
			setErrorMessage(message);
			return false;
		}
		
		return true;
		
	}
	
	
	
	@Override
	public void createControl(Composite parent) {

		Font font = parent.getFont();
		comp = new Composite(parent, SWT.NONE);
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
		
		data = new GridData();
		data.horizontalIndent = 0;
		kernelCombo.setLayoutData(data);		
		
		kernelCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				for (AppKernel k : kernels) {					
					String env = kernelCombo.getItem(kernelCombo.getSelectionIndex());
					if (env.equals(k.getEnvironment())) {
						kernel = k;
						break;
					}					
				}
				setDirty(true);
				updateLaunchConfigurationDialog();
				updateURL();				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
				
		Label routeLabel = new Label(group, SWT.NONE);
		routeLabel.setText("Route");
		
		route = new Text(group, SWT.SINGLE | SWT.BORDER);
		data = new GridData();
		data.widthHint = 200;
		route.addKeyListener(routeListener);
		route.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				
				currentRoute = model.findRoute(route.getText(), project);
				setDirty(true);
				updateLaunchConfigurationDialog();				
				updateURL();
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
		
		route.setLayoutData(data);
				
		ac = new AutoCompleteField(route, new TextContentAdapter(), new String[]{});
		
		Label urlLabel = new Label(group, SWT.NONE);
		urlLabel.setText("Generated URL");
		url = new Text(group, SWT.READ_ONLY | SWT.BORDER | SWT.SINGLE);
		data = new GridData(GridData.FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false);
		url.setLayoutData(data);
		
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
			Logger.logException(e);
		}
	}

		
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
			
		config = configuration;
		initializeProject(configuration);
		initializeEnvironment(configuration);
		initializeRoute(configuration);
		updateURL();				
		isValid(configuration);
		
	}
	
	private void updateURL() {
		
		try {						
			
			if (currentRoute == null) {
				url.setText("");
				return;				
			}
			
			String _url = ServerUtils.constructURL(config, project, currentRoute, kernel);
			url.setText(_url);
			config.getWorkingCopy().setAttribute(Server.BASE_URL, _url);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		
	}
	
	private void initializeProject(ILaunchConfiguration configuration) {
		
		comp.setEnabled(false);
		
		try {
			
			String debuggerID = configuration.getAttribute(
					PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, "");

			isSymfonyDebugger = DebuggerConfiguration.ID.equals(debuggerID);
			
			if (isSymfonyDebugger == false) {
				return;
			}

			String file = configuration.getAttribute(Server.FILE_NAME, "");			
			IPath path = new Path(file);
			kernels = new ArrayList<AppKernel>();
			
			if (path != null && path.segmentCount() > 0) {
				IProject rawProject = ResourcesPlugin.getWorkspace().getRoot().getProject(path.removeLastSegments(path.segmentCount()-1).toString());
				if (rawProject != null) {
					project = DLTKCore.create(rawProject);
					kernels = SymfonyKernelAccess.getDefault().getKernels(project);					
				}
			}
			
			if (project != null && project.getProject().hasNature(SymfonyNature.NATURE_ID))
				comp.setEnabled(true);
			
			
		} catch (CoreException e) {
			// ignore
		}
		
	}
	
	
	private void initializeEnvironment(ILaunchConfiguration configuration) {
		
		try {
			
			kernelCombo.removeAll();
			
			if (project == null) {
				comp.setEnabled(false);
				return;
			}
			
			String env = configuration.getAttribute(SymfonyServer.ENVIRONMENT, "");			
			comp.setEnabled(true);
			
			int i=0;
			
			for (AppKernel kernel : kernels) {				
				kernelCombo.add(kernel.getEnvironment());				
				if (kernel.getEnvironment().equals(env)) {
					kernelCombo.select(i);
					this.kernel = kernel;
				}
				i++;				
			}
			
		} catch (CoreException e) {
			// ignore
		}		
		
	}
	
	
	private void initializeRoute(ILaunchConfiguration configuration) {
		
		try {
			String name = configuration.getAttribute(SymfonyServer.ROUTE, "");
			currentRoute = model.findRoute(name, project);
			route.setText(name);
		} catch (CoreException e) {

		}		
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		
		try {
			
			int index = kernelCombo.getSelectionIndex();
			if (kernelCombo.getItemCount() > 0 && index >= 0 && index < kernelCombo.getItemCount()) {
				String env = kernelCombo.getItem(kernelCombo.getSelectionIndex());
				configuration.setAttribute(SymfonyServer.ENVIRONMENT, env);
			}
			
			configuration.setAttribute(Server.BASE_URL, url.getText());
			
			String routeName = route.getText();
			configuration.setAttribute(SymfonyServer.ROUTE, routeName);
			
		} catch (Exception e) {
			Logger.logException(e);
		}
		
	}

	@Override
	public String getName() {

		return "Symfony";
		
	}	
}
