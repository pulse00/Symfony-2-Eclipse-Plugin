package com.dubture.symfony.core.facet;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

import com.dubture.symfony.core.builder.SymfonyNature;


@SuppressWarnings("restriction")
public class UninstallActionDelegate implements IDelegate {

	@Override
	public void execute(IProject project, IProjectFacetVersion version, Object object, IProgressMonitor monitor)
			throws CoreException {
		
		if (!project.hasNature(PHPNature.ID)) {
			return;
		}

		monitor.subTask("Uninstalling Symfony nature");
		// remove the composer nature
		IProjectDescription desc = project.getDescription();
		ArrayList<String> natures =  new ArrayList<String>(Arrays.asList(desc.getNatureIds()));
		int index = natures.indexOf(SymfonyNature.NATURE_ID);
		if (index != -1) {
			natures.remove(index);
		}
		desc.setNatureIds(natures.toArray(new String[]{}));
		project.setDescription(desc, monitor);

	}
}
