package com.dubture.symfony.ui.explorer;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;

import com.dubture.symfony.core.model.Service;

public class ServiceTreeLabelProvider extends LabelProvider implements
		ILabelProvider {

    @Override
    public String getText(Object element)
    {
        if (element instanceof Service) {
            Service path = (Service) element;
            return path.getElementName();
        }
        
        return null;
    }
}
