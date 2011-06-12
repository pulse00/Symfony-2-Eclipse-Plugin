package org.eclipse.symfony.core.model;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.BuiltinProjectFragment;
import org.eclipse.dltk.internal.core.ScriptProject;


/**
 * 
 * The {@link DependencyInjectionContainer} will be addad
 * as an additional model element to the DLTK structured
 * model so it can be shown in the project explorer.
 * 
 * 
 * It's children are {@link Service} modelelements.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class DependencyInjectionContainer extends BuiltinProjectFragment {

	protected DependencyInjectionContainer(IPath path, ScriptProject project) {
		super(path, project);
	}
	
	
	@Override
	public String getElementName() {
				
		return "DIC";
	}
	
	@Override
	public IModelElement[] getChildren() throws ModelException {
		
		IModelElement[] children = super.getChildren();
		return children;
	}
	
	
	@Override
	public IBuildpathEntry getRawBuildpathEntry() throws ModelException {
			
		IBuildpathEntry entry = super.getRawBuildpathEntry();
		return entry;
	}
}
