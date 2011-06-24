package org.eclipse.symfony.core.model;

import org.eclipse.dltk.core.IModelElement;

abstract public class AbstractSymfonyModel implements ISymfonyModelElement {
	
	
	protected IModelElement sourceModule;
	
	
	public AbstractSymfonyModel(IModelElement sourceModule) {
		
		this.sourceModule = sourceModule;
		
	}
	
	public IModelElement getSourceModule() {
		
		return sourceModule;
	}

}
