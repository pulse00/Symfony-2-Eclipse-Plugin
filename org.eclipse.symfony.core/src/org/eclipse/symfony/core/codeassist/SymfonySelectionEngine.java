package org.eclipse.symfony.core.codeassist;

import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.core.codeassist.PHPSelectionEngine;
import org.eclipse.symfony.core.model.Service;
import org.eclipse.symfony.core.model.SymfonyModelAccess;
import org.eclipse.symfony.core.model.ViewPath;
import org.eclipse.symfony.core.util.text.SymfonyTextSequenceUtilities;
import org.eclipse.symfony.index.dao.Route;


/**
 * 
 * 
 * The {@link SymfonySelectionEngine} helps DLTK to identify
 * symfony model elements for actions like "Open Declaration" - F3 and
 * Hyperlinking.
 * 
 * TODO: I think a cleaner way to implement is to actually provide the 
 * model elements as native DLTK model elements somehow, so DLTK knows what 
 * a Route/Viewpath etc. is and how to resolve it.
 * 
 * 
 * @see http://wiki.eclipse.org/DLTK_IDE_Guide:Step_3._Towards_an_IDE#Open_declaration_feature.
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonySelectionEngine extends PHPSelectionEngine {
	
	private static final IModelElement[] NONE = {};	
	
	@Override
	public IModelElement[] select(IModuleSource sourceUnit, int offset, int end) {

		IModelElement[] result = super.select(sourceUnit, offset, end);
		
		if (result.length > 0) {
			return result;
		}
		
		ISourceModule sourceModule = (ISourceModule) sourceUnit
				.getModelElement();		
		
		String content = sourceUnit.getSourceContents();
		
		int startOffset = SymfonyTextSequenceUtilities.readLiteralStartIndex(content, offset);
		int endOffset = SymfonyTextSequenceUtilities.readLiteralEndIndex(content, offset);
		
		SymfonyModelAccess model = SymfonyModelAccess.getDefault();
		IScriptProject project = sourceModule.getScriptProject();
		
		
		if (startOffset >= 0 && endOffset != 0 && (endOffset > startOffset)) {
			
			// try to resolve a viewepath first
			String literal = content.substring(startOffset, endOffset);
			ViewPath viewPath = new ViewPath(literal);
			
			if (viewPath.isValid()) {
				
				IModelElement template = model.findTemplate(viewPath, project);
				
				if (template != null) {
					return new IModelElement[] { template };
				}				
			}
			
			// nope, not a viewpath, check for a route
			Route route = model.findRoute(literal, project);
			
			if (route != null) {

				IMethod method = model.findAction(route, project);

				if (method != null)
					return new IModelElement[] { method };
			}
			
			
			// next search for a service
			Service service = model.findService(literal, project.getPath());
			
			if (service != null) {

				IType serviceType = model.findServiceType(service, project);
				
				if (serviceType != null)
					return new IModelElement[] { serviceType };
				
			}			
		}
		
		// couldn't find anything
		return NONE;
	}
}
