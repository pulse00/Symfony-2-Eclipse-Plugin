package com.dubture.symfony.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.dubture.symfony.core.model.Service;

public class ServiceDetailView extends Composite {

	private Label name;
	private Label alias;
	
	public ServiceDetailView(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new GridLayout(1, true));
		 
		name = new Label(parent, SWT.NONE);
		alias = new Label(parent, SWT.NONE);
		
		
		

	}
	
	public void updateService(Service service) {
		
		name.setText(service.getElementName());
		alias.setText(service.getClassName());
		
	}
	
	
}
