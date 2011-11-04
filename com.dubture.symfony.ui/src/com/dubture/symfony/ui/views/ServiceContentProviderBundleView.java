package com.dubture.symfony.ui.views;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.SymfonyModelAccess;

public class ServiceContentProviderBundleView implements ITreeContentProvider {

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
		
		return ResourcesPlugin.getWorkspace().getRoot().getProjects();
		
	}

	@Override
	public Object[] getChildren(Object parentElement)
	{

		if (parentElement instanceof IProject) {			
			IProject project = (IProject) parentElement;
			IScriptProject sProject = DLTKCore.create(project);			
			List<Bundle> bundles = model.findBundles(sProject);			
			return bundles.toArray();
		} else if (parentElement instanceof Bundle) {
			
			Bundle bundle = (Bundle) parentElement;		
			
			List<Service> services = model.findServices(bundle.getPath());
			
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

		if (element instanceof IProject || element instanceof Bundle)
			return true;
		
		return false;
	}

}
