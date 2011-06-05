package org.eclipse.symfony.ui.popup.actions;

import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.symfony.core.model.Bundle;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.Project;
import org.eclipse.symfony.core.model.Service;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class DebugModelAction implements IObjectActionDelegate {

	
	private ISelection selection;
	private IWorkbenchPart part;
	
	

	@Override
	public void run(IAction action) {
		
		System.out.println("run action");
		
		List<Project> projects = ModelManager.getInstance().getProjects();
		
		System.out.println("The current workspace contains " + projects.size() + " projects");
			
		for (Project project : ModelManager.getInstance().getProjects()) {
			
			
			List<Bundle> bundles = project.getBundles();			
			System.out.println(project.toDebugString() + " contains " + bundles.size() + " bundles)");		
			
			for (Bundle bundle : bundles) {
				
				System.out.println("-- " + bundle.getName());
				List<Service> services = bundle.getServices();
				System.out.println("-- " + bundle.getName() + " contains " + services.size() + " services");
				
				for (Service service : services) {					
					System.out.println("----" + service.getPHPClass());					
				}				
			}
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
