package com.dubture.symfony.core.facet;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

import com.dubture.composer.core.facet.ComposerFacetConstants;
import com.dubture.indexing.core.IndexingCorePlugin;
import com.dubture.symfony.core.SymfonyVersion;
import com.dubture.symfony.core.log.Logger;

@SuppressWarnings("restriction")
public class FacetManager {

	public static void installFacets(IProject project, PHPVersion version, SymfonyVersion symfonyVersion,
			IProgressMonitor monitor) {
		try {

			if (monitor == null) {
				monitor = new NullProgressMonitor();
			}

			IFacetedProject facetedProject = null;
			boolean hasComposerFacet = false;
			Set<IFacetedProject> facetedProjects = ProjectFacetsManager.getFacetedProjects();
			IProjectFacet composerFacet = ProjectFacetsManager.getProjectFacet(ComposerFacetConstants.COMPOSER_COMPONENT);
			
			for (IFacetedProject fp : facetedProjects) {
				if (project.getName().equals(fp.getProject().getName()) && fp.hasProjectFacet(composerFacet)) {
					hasComposerFacet = true;
					break;
				}
			}
			
			if (!hasComposerFacet) {
				facetedProject = com.dubture.composer.core.facet.FacetManager.installFacets(project, version, monitor);
			} else {
				facetedProject = ProjectFacetsManager.create(project);
			}
			
			if (facetedProject == null) {
				Logger.log(Logger.WARNING, "Could not retrieve a symfony faceted project to install the facet.");
				return;
			}
			
			IProjectFacet symfonyFacet = ProjectFacetsManager.getProjectFacet(SymfonyFacetConstants.SYMFONY_COMPONENT);

			// install the fixed facets
			switch (symfonyVersion) {
			case Symfony2_1_9:
				facetedProject.installProjectFacet(
						symfonyFacet.getVersion(SymfonyFacetConstants.SYMFONY_COMPONENT_VERSION_2_1), symfonyFacet,
						monitor);
				break;
			case Symfony2_2_0:
				facetedProject.installProjectFacet(
						symfonyFacet.getVersion(SymfonyFacetConstants.SYMFONY_COMPONENT_VERSION_2_2), symfonyFacet,
						monitor);
				break;
			default:
				break;
			}
			
			IndexingCorePlugin.getDefault().setupBuilder(project);

		} catch (CoreException ex) {
			Logger.logException(ex.getMessage(), ex);
		}
	}

	public static void uninstallFacets(IProject project, IProgressMonitor monitor) {
		try {
			if (project == null) {
				return;
			}
			
			if (monitor == null) {
				monitor = new NullProgressMonitor();
			}
			
			IProjectFacet symfonyFacet = ProjectFacetsManager.getProjectFacet(SymfonyFacetConstants.SYMFONY_COMPONENT);
			Set<IFacetedProject> facetedProjects = ProjectFacetsManager.getFacetedProjects();
			
			for (IFacetedProject fp: facetedProjects) {
				if (project.getName().equals(fp.getProject().getName()) && fp.hasProjectFacet(symfonyFacet)) {
					if (fp.hasProjectFacet(symfonyFacet.getVersion(SymfonyFacetConstants.SYMFONY_COMPONENT_VERSION_2_1))) {
						fp.uninstallProjectFacet(symfonyFacet.getVersion(SymfonyFacetConstants.SYMFONY_COMPONENT_VERSION_2_1), symfonyFacet, monitor);
					} else if (fp.hasProjectFacet(symfonyFacet.getVersion(SymfonyFacetConstants.SYMFONY_COMPONENT_VERSION_2_2))) {
						fp.uninstallProjectFacet(symfonyFacet.getVersion(SymfonyFacetConstants.SYMFONY_COMPONENT_VERSION_2_2), symfonyFacet, monitor);			
					}
					break;
				}
			}
		} catch (CoreException ex) {
			Logger.logException(ex.getMessage(), ex);
		}
	}
}
