package com.dubture.symfony.ui.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.LabelProvider;

import com.dubture.symfony.core.model.Service;

public class ServiceLabelProvider extends LabelProvider {

	
	@Override
	public String getText(Object element)
	{
		
		if (element instanceof IProject) {
			
			IProject project = (IProject) element;			
			return project.getName();
		} else if (element instanceof Service) {
			
			Service service = (Service) element;			
			return service.getElementName();
		}
		return super.getText(element);
	}
	
}
