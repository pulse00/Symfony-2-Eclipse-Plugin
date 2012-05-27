package com.dubture.symfony.ui.quickassist;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
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

import com.dubture.composer.core.model.ModelAccess;
import com.dubture.pdt.ui.wizards.classes.ClassCreationWizard;
import com.dubture.symfony.core.resources.SymfonyMarker;

public class XMLMarkerResolution implements IMarkerResolution2 {

	@Override
	public String getLabel() {
		return "Create class";
	}

	@Override
	public void run(IMarker marker) {
		
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		ISelection selection = selectionService.getSelection();
		
		ClassCreationWizard wizard= new ClassCreationWizard();
		String serviceClass = "";
		String className = "";
		String namespace = "";
		
		try {
			serviceClass = (String) marker.getAttribute(SymfonyMarker.SERVICE_CLASS);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (serviceClass != null) {
			String[] parts = serviceClass.split("\\\\");
			className = parts[parts.length-1];
			
			int i = 0;
			
			while (i < parts.length -1 ) {
				namespace += parts[i++] + "\\";
			}
			
			namespace = namespace.substring(0, namespace.length() -1);
		}
		
		wizard.setClassName(className);
		wizard.setNamespace(namespace);
		
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
