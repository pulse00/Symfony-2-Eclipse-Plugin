package org.eclipse.symfony.core.builder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.dltk.core.builder.IBuildParticipantFactory;

public class SymfonyBuildParticipantFactory implements IBuildParticipantFactory {


	@Override
	public IBuildParticipant createBuildParticipant(IScriptProject project)
			throws CoreException {

		System.out.println("create build participant");
		return new SymfonyBuildParticipant();
	}

}
