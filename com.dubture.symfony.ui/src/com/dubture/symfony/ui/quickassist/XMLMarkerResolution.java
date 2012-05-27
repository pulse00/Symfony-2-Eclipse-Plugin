package com.dubture.symfony.ui.quickassist;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.wizards.NewElementWizard;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;

import com.dubture.composer.core.model.Autoload;
import com.dubture.composer.core.model.Composer;
import com.dubture.composer.core.model.ModelAccess;
import com.dubture.pdt.ui.wizards.classes.ClassCreationWizard;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.resources.SymfonyMarker;

public class XMLMarkerResolution implements IMarkerResolution2 {

	protected IMarker marker;
	
	public XMLMarkerResolution(IMarker marker)
	{
		this.marker = marker;
	}

	@Override
	public String getLabel()
	{
		try {
			String label = (String) marker.getAttribute(SymfonyMarker.RESOLUTION_TEXT);
			return label;
		} catch (CoreException e) {
			Logger.logException(e);
		}
		
		return "Create class ";
	}

	@Override
	public void run(IMarker marker)
	{
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		ISelection selection = selectionService.getSelection();
		
		ClassCreationWizard wizard= new ClassCreationWizard();
		String serviceClass = "";
		String className = "";
		String namespace = "";
		IScriptFolder folder = null;
		
		try {
			serviceClass = (String) marker.getAttribute(SymfonyMarker.SERVICE_CLASS);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		
		// TODO: refactor this logic to separate service
		if (serviceClass != null) {
			String[] parts = serviceClass.split("\\\\");
			className = parts[parts.length-1];
			
			int i = 0;
			
			while (i < parts.length -1 ) {
				namespace += parts[i++] + "\\";
			}
			
			namespace = namespace.substring(0, namespace.length() -1);
		}
		
		ModelAccess composer = ModelAccess.getInstance();
		IResource resource = marker.getResource();
		
		// TODO: refactor this to the composer plugin
		for (Composer pkg : composer.getPackages()) {
			
			Autoload autoload = pkg.getAutoload();
			if (autoload != null) {
				
				IPath path = pkg.getPath().append(autoload.getPSR0Path());
				
				if (path.isPrefixOf(resource.getFullPath())) {
					
					IPath targetPath = path.append(namespace);
					IScriptProject scriptProject = DLTKCore.create(resource.getProject());
					IPath fixedPath = new Path(targetPath.toString().replaceAll("\\\\", "/"));
					
					try {
						folder = scriptProject.findScriptFolder(fixedPath);
					} catch (Exception e) {
						Logger.logException(e);
						
					}
				}
			}
		}
		
		wizard.setClassName(className);
		wizard.setNamespace(namespace);
		wizard.setScriptFolder(folder);
		
		if (selection instanceof IStructuredSelection) {
			wizard.init(PlatformUI.getWorkbench(), (IStructuredSelection) selectionService.getSelection());
		}
		
		WizardDialog dialog= new WizardDialog(shell, wizard);
		dialog.create();
		int res= dialog.open();
		if (res == Window.OK && wizard instanceof NewElementWizard) {
			
		}		
	}

	@Override
	public String getDescription()
	{
		return "Open the 'New PHP Class' dialog";
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}
