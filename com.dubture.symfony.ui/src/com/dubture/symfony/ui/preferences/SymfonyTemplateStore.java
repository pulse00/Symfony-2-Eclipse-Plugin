package com.dubture.symfony.ui.preferences;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.templates.ScriptTemplateContextType;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateBuffer;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore;

import com.dubture.symfony.ui.editor.template.SymfonyTemplateContext;

@SuppressWarnings("restriction")
public class SymfonyTemplateStore extends PHPTemplateStore {

	public SymfonyTemplateStore(ContextTypeRegistry registry,
			IPreferenceStore store, String key) {
		super(registry, store, key);

	}
	
	
	public static CompiledTemplate compileTemplate(
			ContextTypeRegistry contextTypeRegistry, Template template,
			String containerName, String fileName) {
		String string = null;
		int offset = 0;
		if (template != null) {
			IDocument document = new Document();
			DocumentTemplateContext context = getContext(contextTypeRegistry,
					template, containerName, fileName, document);
			TemplateBuffer buffer = null;
			try {
				buffer = context.evaluate(template);
			} catch (BadLocationException e) {
				PHPUiPlugin.log(e);
			} catch (TemplateException e) {
				PHPUiPlugin.log(e);
			}
			if (buffer != null) {
				string = buffer.getString();
				TemplateVariable[] variables = buffer.getVariables();
				for (int i = 0; i != variables.length; i++) {
					TemplateVariable variable = variables[i];
					if ("cursor".equals(variable.getName())) {//$NON-NLS-1$
						offset = variable.getOffsets()[0];
					}
				}
			}
		}
		return new CompiledTemplate(string, offset);
	}
	
	
	public static CompiledTemplate compileTemplate(
			ContextTypeRegistry contextTypeRegistry, Template template) {
		return compileTemplate(contextTypeRegistry, template, null, null);
	}
	
	private static DocumentTemplateContext getContext(
			ContextTypeRegistry contextTypeRegistry, Template template,
			String containerName, String fileName, IDocument document) {

		if (fileName == null) {
			return new DocumentTemplateContext(contextTypeRegistry
					.getContextType(template.getContextTypeId()), document, 0,
					0);

		}

		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(containerName + "/" + fileName));
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
		TemplateContextType type = contextTypeRegistry.getContextType(template
				.getContextTypeId());
		return ((ScriptTemplateContextType) type).createContext(document, 0, 0,
				sourceModule);
	}
	


}
