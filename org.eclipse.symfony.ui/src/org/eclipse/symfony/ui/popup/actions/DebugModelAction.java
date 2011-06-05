package org.eclipse.symfony.ui.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.Project;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class DebugModelAction implements IObjectActionDelegate {

	
	private ISelection selection;
	private IWorkbenchPart part;
	
	

	@Override
	public void run(IAction action) {
		
		System.out.println("run action");
			
		for (Project project : ModelManager.getInstance().getProjects()) {
			
			System.out.println(project.toDebugString());			
			
		}

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	
		this.selection = selection;

	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {

		this.part = targetPart;

	}

}
