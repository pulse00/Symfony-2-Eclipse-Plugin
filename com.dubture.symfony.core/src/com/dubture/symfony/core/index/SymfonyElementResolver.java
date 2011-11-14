/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.index;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceField;
import org.eclipse.dltk.internal.core.SourceType;
import org.eclipse.php.internal.core.index.IPHPDocAwareElement;
import org.eclipse.php.internal.core.index.PhpElementResolver;
import org.json.simple.JSONObject;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.ISymfonyModelElement;
import com.dubture.symfony.core.util.JsonUtils;


/**
 * 
 * {@link SymfonyElementResolver} resolves {@link IModelElement} 
 * types from the H2 index with the Type {@link ISymfonyModelElement.TEMPLATE_VARIABLE}
 *  
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyElementResolver extends PhpElementResolver {


	@Override
	public IModelElement resolve(int elementType, int flags, int offset,
			int length, int nameOffset, int nameLength, String elementName,
			String metadata, String doc, String qualifier, String parent,
			ISourceModule sourceModule) {


		// this is a Symfony2 element, try to resolve it
		if (elementType == ISymfonyModelElement.TEMPLATE_VARIABLE) {

			try {				
				
				String type = JsonUtils.getElementType(metadata);
				
				if (type == null)
					return null;
				
				// we can only handle references at the moment
				if (type.equals("reference")) {
					
					JSONObject data = JsonUtils.getReferenceData(metadata);
					
					String className = (String) data.get("elementName");
					String q = (String) data.get("qualifier");
					String viewPath = (String) data.get("viewPath");
					String method = (String) data.get("method");
					
					ModelElement parentElement = (ModelElement) sourceModule;

					if (qualifier != null) {
						parentElement = new ControllerType(parentElement, qualifier,
								Modifiers.AccNameSpace, 0, 0, 0, 0, null, doc);

						return new TemplateField(parentElement, elementName, q, className, viewPath, method);

					}			
				} else if (type.equals("scalar")) {
					
					JSONObject data = JsonUtils.getScalar(metadata);

					String className = (String) data.get("elementName");
					String viewPath = (String) data.get("viewPath");
					String method = (String) data.get("method");
					
					ModelElement parentElement = (ModelElement) sourceModule;
					return new TemplateField(parentElement, elementName, null, className, viewPath, method);			

				}
			} catch (Exception e) {
				Logger.logException(e);
			}
		}

		return super
				.resolve(elementType, flags, offset, length, nameOffset, nameLength,
						elementName, metadata, doc, qualifier, parent, sourceModule);
	}

	public static class TemplateField extends SourceField  {

		private String qualifier;
		private String className;
		private String viewPath;
		private String method;
		
		public TemplateField(ModelElement parent, String name, String qualifier, String className, String viewPath, String method) {
			super(parent, name);

			this.qualifier = qualifier;
			this.className = className;
			this.viewPath = viewPath;
			this.method = method;

		}
		
		public String getQualifier() {
			return qualifier;
		}
		public String getClassName() {
			return className;
		}
		public String getViewPath() {
			return viewPath;
		}		
		public IMethod getMethod() {			
			return getSourceModule().getMethod(method);
		}
		public String getMethodName() {			
			return method;
		}
	}

	private static class ControllerType extends SourceType implements
	IPHPDocAwareElement {

		private int flags;
		private ISourceRange sourceRange;
		private String[] superClassNames;
		private String doc;

		public ControllerType(ModelElement parent, String name, int flags,
				int offset, int length, int nameOffset, int nameLength,
				String[] superClassNames, String doc) {
			super(parent, name);
			this.flags = flags;
			this.sourceRange = new SourceRange(offset, length);
			new SourceRange(nameOffset, nameLength);
			this.superClassNames = superClassNames;
			this.doc = doc;
		}

		public int getFlags() throws ModelException {
			return flags;
		}

		public ISourceRange getNameRange() throws ModelException {
			return super.getNameRange();
		}

		public ISourceRange getSourceRange() throws ModelException {
			return sourceRange;
		}

		public String[] getSuperClasses() throws ModelException {
			return superClassNames;
		}

		public boolean isDeprecated() {
			return PhpElementResolver.isDeprecated(doc);
		}

		public String[] getReturnTypes() {
			return null;
		}
	}	


}
