package com.dubture.symfony.core.validation;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.wst.validation.AbstractValidator;
import org.eclipse.wst.validation.ValidationResult;
import org.eclipse.wst.validation.ValidationState;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.parser.XMLConfigParser;
import com.dubture.symfony.core.resources.SymfonyMarker;
import com.dubture.symfony.index.dao.Service;

@SuppressWarnings("restriction")
public class ServiceDefinitionValidator extends AbstractValidator {

	
	@Override
	public ValidationResult validate(IResource resource, int kind,
			ValidationState state, IProgressMonitor monitor) {

		if (resource.getType() != IResource.FILE) {
			return null;
		}
		
		ValidationResult result = new ValidationResult();
//		IReporter reporter = result.getReporter(monitor);
		IFile file = (IFile) resource;
		
		//TODO: check why the include nodes don't work in plugin.xml and we get
		// everything passed in here...
		if (!"xml".equals(file.getFileExtension())) {
			return null;
		}
		
		IScriptProject scriptProject = DLTKCore.create(resource.getProject());
		XMLConfigParser parser;
		try {
			parser = new XMLConfigParser(file.getContents());
			parser.parse();
			HashMap<String,Service> services = parser.getServices();
			
			Iterator<String> it = services.keySet().iterator();
			PhpModelAccess model = PhpModelAccess.getDefault();
			IDLTKSearchScope scope = SearchEngine.createSearchScope(scriptProject);
			
			while(it.hasNext()) {
				String key = it.next();
				Service service = services.get(key);
				
				if (service == null) {
					Logger.log(Logger.INFO, "Error setting marker for service " + key);
					continue;
				}
				String phpClass = service.getPHPClass();
				
				if ("synthetic".equals(phpClass)) {
					Logger.log(Logger.INFO, "Error setting marker for synthetic service " + key);
					continue;
				}
				
				IType[] types = model.findTypes(phpClass, MatchRule.EXACT, 0, 0, scope, null);
				
				if (types.length == 0) {
					
		            IMarker marker = resource.createMarker(SymfonyMarker.MISSING_SERVICE_CLASS);
		            marker.setAttribute(SymfonyMarker.SERVICE_CLASS, phpClass);
		            marker.setAttribute(SymfonyMarker.RESOLUTION_TEXT, "Create class " + phpClass);
		            marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
		            marker.setAttribute("problemType", SymfonyMarker.MISSING_SERVICE_CLASS);
		            marker.setAttribute(IMarker.MESSAGE, "Class " + phpClass + " does not exist");
		            marker.setAttribute(IMarker.LINE_NUMBER, service.getLine());
				}
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
