/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.SymfonyModelAccess;

public class ServiceContentProviderFlatView implements ITreeContentProvider {

	private SymfonyModelAccess model = SymfonyModelAccess.getDefault();
	
	private static Object[] EMTPY = {};
	
	@Override
	public void dispose()
	{
		model = null;

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{

	}

	@Override
	public Object[] getElements(Object inputElement)
	{
		
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();		
		List<IProject> elements = new ArrayList<IProject>();
		
		for (IProject project : projects) {
			
			try {
				if (project.hasNature(SymfonyNature.NATURE_ID)) {
					elements.add(project);
				}
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
		
		return elements.toArray(new IProject[elements.size()]);
		
	}

	@Override
	public Object[] getChildren(Object parentElement)
	{

		if (parentElement instanceof IProject) {
			
			IProject project = (IProject) parentElement;			
			List<Service> services = model.findServices(project.getFullPath());			
			return services.toArray();
		}
		return EMTPY;
	}

	@Override
	public Object getParent(Object element)
	{
		return null;
	}

	@Override
	public boolean hasChildren(Object element)
	{

		if (element instanceof IProject)
			return true;
		
		return false;
	}

}
