package com.dubture.symfony.ui.wizards.classes;

import org.eclipse.jface.text.templates.Template;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore.CompiledTemplate;

import com.dubture.symfony.ui.editor.template.CodeTemplateVariableHolder;
import com.dubture.symfony.ui.preferences.SymfonyTemplateStore;
import com.dubture.symfony.ui.wizards.CodeTemplateWizard;

@SuppressWarnings("restriction")
public class ClassCreationWizard extends CodeTemplateWizard {

	
	public ClassCreationWizard() {
		super();
		setWindowTitle("New PHP Class"); 
		setNeedsProgressMonitor(true);
	}	

	public CompiledTemplate compileTemplate() {
		
		final String containerName = codeTemplateWizardPage.getContainerName();
		final String fileName = getFileName();

		Template template = getTemplateStore().findTemplate(getTemplateName(), getContextTypeID());

		CodeTemplateVariableHolder varHolder = new CodeTemplateVariableHolder();
		ClassCreationWizardPage page = (ClassCreationWizardPage) codeTemplateWizardPage;
				
		String superclass = page.getSuperclass();		
		String[] parts = superclass.split("\\\\");		
		superclass = parts.length > 0 ? parts[parts.length-1] : "";
		
		varHolder.set("extends", superclass);
		varHolder.set("use_parent", page.getSuperclass());
		varHolder.set("interfaces", page.getInterfaces());
		varHolder.set("class_modifiers", page.getModifiers());
		varHolder.set("generate_comments", page.shouldGenerateComments());
		
		
		if (template != null) {
			return SymfonyTemplateStore.compileTemplate(getTemplatesContextTypeRegistry(), template, containerName, fileName, varHolder);	
		}

		return null;
		
	}
	
	
	public void addPages() {
		
		codeTemplateWizardPage = new ClassCreationWizardPage(selection, "NewClass");
		addPage(codeTemplateWizardPage);

	}
		

	@Override
	protected String getFileName() {
		
		return codeTemplateWizardPage.getFileName();
		
	}
	
	@Override
	protected String getTemplateName() {

		return "symfonyclass";
	}


	@Override
	protected String getContextTypeID() {
		
		return "php_new_file_context";
		
	}	
}
