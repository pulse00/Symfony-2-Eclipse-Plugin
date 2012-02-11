/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.views;

import java.text.MessageFormat;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.ui.SymfonyPluginImages;

@SuppressWarnings("restriction")
public class ServiceLabelProvider extends StyledCellLabelProvider implements ILabelProvider {
	
	
	@Override
	public void update(ViewerCell cell)
	{

		Object element = cell.getElement();
		
		Styler style = null;
		
		if (element instanceof IProject) {
			
			IProject project = (IProject) element;			
			StyledString styledString = new StyledString(project.getName(), style);			
			cell.setText(styledString.toString());
			cell.setImage(PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHP_PROJECT));
			
		} else if (element instanceof Service) {

			Service service = (Service) element;
			String name = service.getClassName() != null ? service.getClassName() : service.getId();			
			StyledString styledString = new StyledString(name, style);
			
			String decoration = MessageFormat.format(" [{0}]", new Object[] { service.getId() }); //$NON-NLS-1$
			styledString.append(decoration, StyledString.DECORATIONS_STYLER);
			
			
			cell.setText(styledString.toString());
			cell.setStyleRanges(styledString.getStyleRanges());
			
			if (service.isPublic()) {
				cell.setImage(SymfonyPluginImages.get(SymfonyPluginImages.IMG_OBJS_SERVICE_PUBLIC));	
			} else {
				cell.setImage(SymfonyPluginImages.get(SymfonyPluginImages.IMG_OBJS_SERVICE_PRIVATE));
			}
			
			
		} else if (element instanceof Bundle) {
			
			Bundle bundle = (Bundle) element;
			
			StyledString styledString = new StyledString(bundle.getElementName(), style);			
			cell.setText(styledString.toString());
			cell.setImage(SymfonyPluginImages.get(SymfonyPluginImages.IMG_OBJS_BUNDLE2));
			
			
		}
	}

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object element)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object element)
    {
        if (element instanceof Service) {
            Service service = (Service) element;
            return service.getElementName();
        }
        
        return "";
    }	
}
