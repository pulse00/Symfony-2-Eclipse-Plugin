/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.utils;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ui.wizards.NewElementWizard;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.getcomposer.core.collection.Psr0;
import org.getcomposer.core.objects.Namespace;
import org.pdtextensions.core.ui.wizards.NewClassWizard;

import com.dubture.composer.core.model.ModelAccess;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.resources.SymfonyMarker;

public class DialogUtils
{

    public static void launchClassWizardFromMarker(IMarker marker)
    {
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getShell();
        ISelectionService selectionService = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getSelectionService();
        ISelection selection = selectionService.getSelection();

        NewClassWizard wizard = new NewClassWizard();
        String serviceClass = "";
        String className = "";
        String namespace = "";
        IScriptFolder folder = null;

        try {
            serviceClass = (String) marker
                    .getAttribute(SymfonyMarker.SERVICE_CLASS);
        } catch (CoreException e) {
            Logger.logException(e);
        }

        // TODO: refactor this logic to separate service
        if (serviceClass != null) {
            String[] parts = serviceClass.split("\\\\");
            className = parts[parts.length - 1];

            int i = 0;

            while (i < parts.length - 1) {
                namespace += parts[i++] + "\\";
            }

            namespace = namespace.substring(0, namespace.length() - 1);
        }

        ModelAccess composer = ModelAccess.getInstance();
        IResource resource = marker.getResource();

        IPath resolvedNamespace = composer.resolve(resource);
        IScriptProject scriptProject = DLTKCore.create(resource
                .getProject());

        try {
	        if (resolvedNamespace != null && resolvedNamespace.isEmpty()) {
	        	IPath folderPath = new Path("/" + scriptProject.getElementName()).append("src").append(namespace.replace("\\", "/"));
					folder = scriptProject.findScriptFolder(folderPath);
	        	
	        } else {
	        	Psr0 namespaceMappings = composer.getNamespaceMappings(scriptProject.getProject());
	        	for (Namespace mapping : namespaceMappings) {
	        		if (mapping.getNamespace().equals(resolvedNamespace.toString())) {
	        			for (Object p : mapping.getPaths()) {
	        				if (!(p instanceof String)) {
	        					continue;
	        				}
	        				String path = (String) p;
	        				IPath folderPath = new Path("/" + scriptProject.getElementName()).append(path);
	        				folder = scriptProject.findScriptFolder(folderPath);
	        				break;
	        			}
	        		}
	        	}
	        }
        } catch (ModelException e) {
        	Logger.logException(e);
        }
        
        if (folder == null) {
        	MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error opening class wizard", "Could not open the New Class wizard. See the workspace log for details");
        	Logger.log(Logger.ERROR, "Unable to retrieve target folder from composer information");
        	return;
        }
        
        wizard.setClassName(className);
        wizard.setNamespace(namespace);
        wizard.setScriptFolder(folder);

        if (selection instanceof IStructuredSelection) {
            wizard.init(PlatformUI.getWorkbench(),
                    (IStructuredSelection) selectionService.getSelection());
        }

        WizardDialog dialog = new WizardDialog(shell, wizard);
        dialog.create();
        int res = dialog.open();
        if (res == Window.OK && wizard instanceof NewElementWizard) {

        }
    }
}
