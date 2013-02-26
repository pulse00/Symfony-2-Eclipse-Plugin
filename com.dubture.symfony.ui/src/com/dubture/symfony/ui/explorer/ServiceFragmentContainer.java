package com.dubture.symfony.ui.explorer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.ui.navigator.ProjectFragmentContainer;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.SymfonyModelAccess;

@SuppressWarnings("restriction")
public class ServiceFragmentContainer extends ProjectFragmentContainer {
	
	public ServiceFragmentContainer(IScriptProject project) {
		super(project);
	}

	@Override
	public IAdaptable[] getChildren() {
		List<Service> services = SymfonyModelAccess.getDefault().findServices(getScriptProject().getPath());
		for (Service service : services) {
			service.setProject(getScriptProject());
		}
		return (IAdaptable[]) services.toArray(new IAdaptable[services.size()]);
	}

	@Override
	public IProjectFragment[] getProjectFragments() {
		IScriptProject project = getScriptProject();
		List<Service> services = SymfonyModelAccess.getDefault().findServices(getScriptProject().getPath());
		List<IProjectFragment> fragments = new ArrayList<IProjectFragment>();
		
		for (Service service : services) {
			IProjectFragment fragment = project.getProjectFragment(service.getPath());
			service.setProject(project);
			if (fragment != null) {
				fragments.add(fragment);
			}
		}
		
		return (IProjectFragment[]) fragments.toArray(new IProjectFragment[fragments.size()]);
	}

	@Override
	public String getLabel() {
		return "Services";
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(IDE.SharedImages.IMG_OBJ_PROJECT);
	}
}
