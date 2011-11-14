/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.model;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceType;

import com.dubture.symfony.index.dao.TransUnit;

/**
 * 
 * A Translation {@link SourceType}.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class Translation extends SourceType {

	
	private TransUnit unit;
	
	
	public Translation(ModelElement parent, TransUnit unit) {		
		super(parent, unit.name);
		
		this.unit = unit;
		
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
	public String getElementName() {
	
		if (unit != null && unit.name != null)
			return unit.name;
		
		return super.getElementName();
		
	}
	
	
	@Override
	public IScriptProject getScriptProject() {
	
		IScriptProject project = super.getScriptProject();
		
		if (project == null) {
			
			return parent.getScriptProject();

		}
		
		return project;
	}
}
