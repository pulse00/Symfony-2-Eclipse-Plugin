package org.eclipse.symfony.core.model;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceType;
import org.eclipse.symfony.index.dao.Route;

/**
 * 
 * A {@link IModelElement} for Routes - to display custom
 * context information in the codeassist popups. 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings("restriction")
public class RouteSource extends SourceType {

	
	private Route route;
	
	
	public RouteSource(ModelElement parent, String name, Route route) throws IllegalArgumentException {
		super(parent, name);
		
		this.route = route;

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
	
	public Route getRoute() {
		
		return route;
	}
	


}