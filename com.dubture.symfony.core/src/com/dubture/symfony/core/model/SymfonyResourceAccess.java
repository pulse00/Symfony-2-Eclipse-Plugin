/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.IScriptProject;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.index.IResourceHandler;
import com.dubture.symfony.index.SymfonyIndexer;
import com.dubture.symfony.index.dao.RoutingResource;


public class SymfonyResourceAccess {
	
	private static SymfonyResourceAccess instance = null;
	private SymfonyIndexer index;
	
	private SymfonyResourceAccess() {
	
		try {
			index = SymfonyIndexer.getInstance();
		} catch (Exception e) {
			Logger.logException(e);
		}		
	}
	
	public static SymfonyResourceAccess getDefault() {
		
		
		if (instance == null)
			instance = new SymfonyResourceAccess();
		
		return instance;
		
	}
	
	public List<Resource> getResources(IScriptProject project) {
		
		final List<Resource> resources = new ArrayList<Resource>();
		
		index.findResources(project.getPath(), new IResourceHandler() {
			
			@Override
			public void handle(RoutingResource resource) {

				Resource r = new Resource();
				r.type = Resource.ROUTE_RESOURCE;
				r.path = resource.getPath();
				r.prefix = resource.getPrefix();
				resources.add(r);
				
			}
		});
		
		return resources;		
		
	}
	

}
