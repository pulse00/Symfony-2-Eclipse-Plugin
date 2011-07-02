package org.eclipse.symfony.core.builder;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.symfony.core.SymfonyCorePlugin;
import org.eclipse.symfony.core.log.Logger;
import org.eclipse.symfony.core.model.ModelManager;

/**
 * 
 * (non-Javadoc)
 * 
 * @see IncrementalProjectBuilder 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class SymfonyBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = SymfonyCorePlugin.ID + ".symfonyBuilder";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
	 *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@SuppressWarnings("rawtypes")
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		
		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		return null;
	}

	protected void fullBuild(final IProgressMonitor monitor)
			throws CoreException {
		try {
			
			
			getProject().accept(new ResourceVisitor());
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
	
	protected void incrementalBuild(IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {

		delta.accept(new DeltaVisitor());
	}
}