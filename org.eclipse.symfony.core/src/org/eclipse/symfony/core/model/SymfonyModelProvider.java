package org.eclipse.symfony.core.model;


import java.util.List;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IModelProvider;


/**
 * 
 * The {@link SymfonyModelProvider} can be used to contribute
 * additional model elements to the DLTK structured model.
 * 
 * Not working yet, but the idea is to add Meta-Nodes to the package
 * explorer like a DependencyInjectionContainer to provide
 * an overview of all available Services in a project - similar
 * to the Deployment Descriptor in a JavaEE Eclipse project.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyModelProvider implements IModelProvider {

//	private boolean added = false;
	
	
	public SymfonyModelProvider() {

	}

	
	@Override
	public void provideModelChanges(IModelElement parentElement,
			List<IModelElement> children) {
		
//		if (parentElement instanceof ScriptProject && added == false) {
//		
//			ScriptProject project = (ScriptProject) parentElement;
//			IPath path = project.getPath();			
//			
//			DependencyInjectionContainer dic = new DependencyInjectionContainer(path, project);
//			children.add(dic);			
//			added = true;
//		}
	}

	@Override
	public boolean isModelChangesProvidedFor(IModelElement modelElement,
			String name) {

		return false;
	}

}
