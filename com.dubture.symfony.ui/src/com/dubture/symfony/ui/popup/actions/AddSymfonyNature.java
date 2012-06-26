/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.popup.actions;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.dubture.symfony.core.builder.SymfonyNature;

public class AddSymfonyNature implements IObjectActionDelegate {

    private ISelection selection;

    /**
    * Constructor for Action1.
    */
    public AddSymfonyNature() {
        super();
    }

    /**
    * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
    */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }

    /**
    * @see IActionDelegate#run(IAction)
    */
    @SuppressWarnings("rawtypes")
    public void run(IAction action) {
        if (selection instanceof IStructuredSelection) {
            for (Iterator it = ((IStructuredSelection) selection).iterator(); it
                    .hasNext();) {
                Object element = it.next();
                IProject project = null;
                if (element instanceof IProject) {
                    project = (IProject) element;
                } else if (element instanceof IAdaptable) {
                    project = (IProject) ((IAdaptable) element)
                            .getAdapter(IProject.class);
                }
                if (project != null) {
                    toggleNature(project);
                }
            }
        }

    }

    /**
    * @see IActionDelegate#selectionChanged(IAction, ISelection)
    */
    public void selectionChanged(IAction action, ISelection selection) {

        this.selection = selection;
    }

    private void toggleNature(IProject project) {
        try {
            IProjectDescription description = project.getDescription();
            String[] natures = description.getNatureIds();

            for (int i = 0; i < natures.length; ++i) {

                if (SymfonyNature.NATURE_ID.equals(natures[i])) {

                    // Remove the nature
                    String[] newNatures = new String[natures.length - 1];
                    System.arraycopy(natures, 0, newNatures, 0, i);
                    System.arraycopy(natures, i + 1, newNatures, i,
                            natures.length - i - 1);
                    description.setNatureIds(newNatures);
                    project.setDescription(description, null);
                    return;
                }
            }

            // Add the nature
            String[] newNatures = new String[natures.length + 1];
            System.arraycopy(natures, 0, newNatures, 1, natures.length);
            newNatures[0] = SymfonyNature.NATURE_ID;
            description.setNatureIds(newNatures);
            project.setDescription(description, null);
        } catch (CoreException e) {
        }
    }

}
