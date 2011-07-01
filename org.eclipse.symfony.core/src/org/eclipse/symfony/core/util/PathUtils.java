package org.eclipse.symfony.core.util;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.symfony.core.model.SymfonyModelAccess;
import org.eclipse.symfony.core.preferences.SymfonyCoreConstants;
import org.eclipse.wst.sse.core.utils.StringUtils;


/**
 * 
 * 
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class PathUtils {
	

	/**
	 * 
	 * Return the name of the Controller for a given ViewPath
	 * 
	 * @param path
	 * @return
	 */
	public static String getControllerFromTemplatePath(IPath path) {
	
		int size = path.segmentCount();
		
		for (int i=0; i < size; i++) {
			if (path.segment(i).equals("views") && (i+1 < size)) {
				
				return path.segment(i+1) + "Controller";
			}
			
		}
		
		return null;
	}

	
	/**
	 * Extract the Viewname from a given template path.
	 * 
	 * Returns null if it's not a valid template path.
	 * 
	 * @param path
	 * @return
	 */
	public static String getViewFromTemplatePath(IPath path) {

		String fileName = path.lastSegment();
		
		if (fileName.indexOf(".") > -1)
			return fileName.substring(0, fileName.indexOf('.'));
		
		return null;
		
	}
	
	/**
	 * 
	 * Checks if the field is declared in a controller matching
	 * the template module.
	 *  
	 * @param field
	 * @param module
	 * @return
	 */
	public static boolean isTemplateVariable(IField field, ISourceModule module) {

		ISourceModule fieldModule = field.getSourceModule();
		IPath fieldPath = fieldModule.getPath();
		
		String controller = getControllerFromFieldPath(fieldPath);
		String viewName = getViewFromTemplatePath(module.getPath());
		
		if (controller == null || viewName == null)
			return false;
		
		IDLTKSearchScope scope = SearchEngine.createSearchScope(field.getSourceModule());		
		IMethod[] methods = PhpModelAccess.getDefault().findMethods(null, MatchRule.PREFIX, 0, 0, scope, null);
		
		for (IMethod method : methods) {						
			if (method.getElementName().startsWith(viewName))
				return true;			
		}		
		
		return false;
	}

	/**
	 * Get the Controller name from a given Path. Returns
	 * null if it's not a controller path. 
	 * 
	 * @param path
	 * @return
	 */
	public static String getControllerFromFieldPath(IPath path) {

		if (path.segment(path.segmentCount()-2).equals(SymfonyCoreConstants.CONTROLLER_CLASS) 
				&& path.getFileExtension().equals("php")) {
			return path.lastSegment().replace(".php", "");
		}
		
		return null;
	}
	
	
	/**
	 * Very simple check, this needs to be refined...
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isViewPath(String path) {
		
		return StringUtils.occurrencesOf(path, ':') == 2;
		
	}


	public static String createViewPathFromTemplate(ISourceModule sourceModule) {

		SymfonyModelAccess model = SymfonyModelAccess.getDefault();
		
		String viewName = PathUtils.getViewFromTemplatePath(sourceModule.getPath());
		
		IType controller = model.findControllerByTemplate(sourceModule);		
		String bundle = ModelUtils.extractBundleName(controller.getFullyQualifiedName());
		
		if (bundle == null)
			return null;
		
		String controllerPart = controller.getElementName().replace(SymfonyCoreConstants.CONTROLLER_CLASS, "");
		return String.format("%s:%s:%s", bundle, controllerPart, viewName);

	}


	/**
	 * 
	 * Creates a valid ViewPath from a {@link Scalar} stripping
	 * away any file extensions and quotes:
	 * 
	 * <pre>
	 *  
	 *  'AcmeBlogBundle:Blog:show.html.twig' => AcmeBlogBundle:Blog:show
	 * 
	 * </pre>
	 * 
	 * @param view
	 * @return
	 */
	public static String createViewPath(Scalar view) {

		return createViewPath(view.getValue());
		
	}
	
	
	public static String createViewPath(String viewString) {
		
		String path = viewString.replaceAll("['\"]", "");
		
		int dotIndex = path.indexOf('.');
		
		if (dotIndex == -1)
			return path;

		return path.substring(0, dotIndex);		

		
	}
}