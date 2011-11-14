/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.builder;

import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.dltk.core.builder.IBuildParticipantFactory;

/**
 * 
 * DLTK BuildParticipant extension point. 
 * 
 * Creates a {@link SymfonyBuildParticipant} for
 * projects with the {@link SymfonyNature}.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class SymfonyBuildParticipantFactory implements IBuildParticipantFactory {


	@Override
	public IBuildParticipant createBuildParticipant(IScriptProject project)
			throws CoreException {
		
		IProjectNature nature = project.getProject().getNature(SymfonyNature.NATURE_ID);
			
		
		if (nature instanceof SymfonyNature) {			
			return new SymfonyBuildParticipant();			
		}
		
		return null;		
		
	}

}
