/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.utils;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.pdtextensions.core.ui.wizards.NewClassWizard;

import com.dubture.composer.core.model.ModelAccess;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.resources.SymfonyMarker;

public class DialogUtils
{

    public static void launchClassWizardFromMarker(IMarker marker)
    {
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
        ISelection selection = selectionService.getSelection();

        NewClassWizard wizard = new NewClassWizard();
        String serviceClass = "";
        String className = "";
        IScriptFolder folder = null;

        try {
            serviceClass = (String) marker.getAttribute(SymfonyMarker.SERVICE_CLASS);
        } catch (CoreException e) {
            Logger.logException(e);
        }
        
        if (serviceClass == null) {
        	Logger.log(Logger.ERROR, "Could not retrieve service class to open class dialog");
        	return;
        }

        ModelAccess composer = ModelAccess.getInstance();
        IResource resource = marker.getResource();
        className  = serviceClass.substring(serviceClass.lastIndexOf("\\"), serviceClass.length());
        String ns = serviceClass.replace(className, "");
		IPath folderPath = composer.reverseResolve(resource.getProject(), ns);
			
        if (folderPath == null) {
        	MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error opening class wizard", "Could not open the New Class wizard. See the workspace log for details");
        	Logger.log(Logger.ERROR, "Unable to retrieve target folder from composer information");
        	return;
        }
        
        IFolder targetFolder = resource.getProject().getFolder(folderPath);
        if (targetFolder.exists() == false) {
        	try {
				targetFolder.create(true, true, new NullProgressMonitor());
			} catch (CoreException e) {
				e.printStackTrace();
			}
        }
        
    	folder = (IScriptFolder) DLTKCore.create(targetFolder);
        
        wizard.setClassName(className.replace("\\", ""));
        wizard.setNamespace(ns);
        wizard.setScriptFolder(folder);

        if (selection instanceof IStructuredSelection) {
            wizard.init(PlatformUI.getWorkbench(), (IStructuredSelection) selectionService.getSelection());
        }

        WizardDialog dialog = new WizardDialog(shell, wizard);
        dialog.create();
        dialog.open();
    }
}
