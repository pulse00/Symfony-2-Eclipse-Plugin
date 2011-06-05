package org.eclipse.symfony.core.builder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.dltk.core.builder.IBuildParticipantFactory;

/**
 * 
 * DLTK BuildParticipant extension point.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class SymfonyBuildParticipantFactory implements IBuildParticipantFactory {


	@Override
	public IBuildParticipant createBuildParticipant(IScriptProject project)
			throws CoreException {
		
		return new SymfonyBuildParticipant();
	}

}
