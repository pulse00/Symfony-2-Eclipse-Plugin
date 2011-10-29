package com.dubture.symfony.core.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;

import com.dubture.symfony.core.model.Resource;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.SymfonyResourceAccess;
import com.dubture.symfony.index.dao.Route;
import com.dubture.symfony.index.dao.RouteParameter;

/**
 * 
 * Utility class for Route conversions.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class RoutingUtil {

	
	@SuppressWarnings("rawtypes")
	public static String getURL(String base, Route route, IScriptProject project) {
		
		String URL = null;		
		
		List<Resource> resources = SymfonyResourceAccess.getDefault().getResources(project);
		IType controller = SymfonyModelAccess.getDefault().findController(route.bundle, route.controller, project);				
		String prefix = null;

		if (controller != null) {					
			for (Resource resource : resources) {			
				if (resource.type == Resource.ROUTE_RESOURCE) {
					String controllerp = ModelUtils.resolveControllerShortcut(resource.path, project);							
					if (controllerp != null) {
						if (controllerp.equals(controller.getPath().toString())) {
							prefix = resource.prefix;
							break;
						}
					}
				}										
			}
		}
		
		if (route.hasParameters()) {					
			
			Map<String, RouteParameter> parameters = route.getParameters();
			Iterator it = parameters.keySet().iterator();
			
			while(it.hasNext()) {						
				String name = (String) it.next();
				RouteParameter param = parameters.get(name);
				param.setValue(param.getName());
			}					
			URL = base + route.getURL(parameters.values(), prefix);
			
		} else {					
			URL = base + route.getURL(prefix); 
		}

		return URL;
		
	}
}
