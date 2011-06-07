package org.eclipse.symfony.ui.popup.actions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.symfony.core.model.Annotation;
import org.eclipse.symfony.core.model.AnnotationParameter;
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
			
			List<Annotation> annotations = project.getAnnotations();
			
			System.out.println(project.toDebugString() + " contains " + annotations.size() +  " annotations:");
			
			
			for (Iterator iterator = annotations.iterator(); iterator.hasNext();) {
				Annotation annotation = (Annotation) iterator.next();
				
				List<AnnotationParameter> params = annotation.getParameters();
				System.out.println("-- " + annotation.getName() + " (" + params.size() + " parameters)");
				
				for (Iterator iterator2 = params.iterator(); iterator2
						.hasNext();) {
					AnnotationParameter annotationParameter = (AnnotationParameter) iterator2
							.next();
					
					System.out.println("---- " + annotationParameter.getName());
					
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
