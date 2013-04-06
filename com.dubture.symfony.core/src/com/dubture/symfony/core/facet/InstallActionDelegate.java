package com.dubture.symfony.core.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.utils.ResourceUtil;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

import com.dubture.symfony.core.builder.SymfonyNature;

@SuppressWarnings("restriction")
public class InstallActionDelegate implements IDelegate {

	@Override
	public void execute(IProject project, IProjectFacetVersion version, Object object, IProgressMonitor monitor) throws CoreException {
		
		if (!project.hasNature(PHPNature.ID)) {
			return;
		}

		monitor.subTask("Installing Symfony nature");

		// add the composer nature
		ResourceUtil.addNature(project, monitor, SymfonyNature.NATURE_ID);
	}
}
