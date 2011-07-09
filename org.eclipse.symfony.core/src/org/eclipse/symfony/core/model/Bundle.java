package org.eclipse.symfony.core.model;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceType;
import org.eclipse.dltk.internal.core.hierarchy.FakeType;

@SuppressWarnings("restriction")
public class Bundle extends SourceType {

	public Bundle(ModelElement parent, String name) {
		super(parent, name);

	}
	
	@Override
	public int getFlags() throws ModelException {

		return Modifiers.AccPublic;
	}
	
	@Override
	public Object getElementInfo() throws ModelException {

		return new FakeTypeElementInfo();
	}
	
	@Override
	protected Object openWhenClosed(Object info, IProgressMonitor monitor)
			throws ModelException {

		return new FakeTypeElementInfo();

	}
	
	@Override
	public ISourceModule getSourceModule() {
	
		return super.getSourceModule();
	}
	
	@Override
	public IModelElement getParent() {
	
		// avoid showing the same name twice in each codeassist
		// popup row, ie:
		// AcmeDemoBundle - AcmeDemoBundle
		return new FakeType(parent, "");		
		
	}
	
}
