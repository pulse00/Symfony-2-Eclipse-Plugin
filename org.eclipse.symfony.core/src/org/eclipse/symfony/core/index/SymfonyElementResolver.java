package org.eclipse.symfony.core.index;

import org.eclipse.dltk.ast.Modifiers;
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
import org.eclipse.symfony.core.util.JsonUtils;
import org.json.simple.JSONObject;


/**
 * 
 * 
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


		if (elementType == IModelElement.USER_ELEMENT) {

			try {				
				if (JsonUtils.getElementType(metadata).equals("reference")) {
					
					JSONObject data = JsonUtils.getReferenceData(metadata);
					System.out.println(data.get("elementName"));
					System.out.println(data.get("qualifier"));
					
					ModelElement parentElement = (ModelElement) sourceModule;

					if (qualifier != null) {
						parentElement = new ControllerType(parentElement, qualifier,
								Modifiers.AccNameSpace, 0, 0, 0, 0, null, doc);

						return new TemplateField(parentElement, elementName, flags, offset,
								length, nameOffset, nameLength, doc);

					}			
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return super
				.resolve(elementType, flags, offset, length, nameOffset, nameLength,
						elementName, metadata, doc, qualifier, parent, sourceModule);
	}

	private static class TemplateField extends SourceField  {

		private int flags;
		private ISourceRange sourceRange;
		private ISourceRange nameRange;
		
		public TemplateField(ModelElement parent, String name, int flags,
				int offset, int length, int nameOffset, int nameLength,
				String doc) {
			super(parent, name);
			this.flags = flags;
			this.sourceRange = new SourceRange(offset, length);
			this.nameRange = new SourceRange(nameOffset, nameLength);
		}

		public int getFlags() throws ModelException {
			return flags;
		}

		public ISourceRange getNameRange() throws ModelException {
			return nameRange;
		}

		public ISourceRange getSourceRange() throws ModelException {
			return sourceRange;
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
