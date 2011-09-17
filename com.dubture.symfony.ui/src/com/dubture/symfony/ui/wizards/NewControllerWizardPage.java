package com.dubture.symfony.ui.wizards;


import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore.CompiledTemplate;
import org.eclipse.php.internal.ui.wizards.NewPhpTemplatesWizardPage;

import com.dubture.symfony.ui.SymfonyUiPlugin;
import com.dubture.symfony.ui.preferences.SymfonyTemplateStore;

/**
 * 
 * Override the {@link NewPhpTemplatesWizardPage} to compile the controller template.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class NewControllerWizardPage extends NewPhpTemplatesWizardPage {


	public CompiledTemplate compileTemplate(String containerName,
			String fileName) {

		Template template = getTemplateStore().findTemplate("symfonycontroller", "php_new_file_context");

		if (template != null) {
			return SymfonyTemplateStore.compileTemplate(getTemplatesContextTypeRegistry(), template, containerName, fileName);	
		}

		return null;

	}


	public CompiledTemplate compileTemplate() {

		Template template = getTemplateStore().findTemplate("com.dubture.symfony.ui.editor.templates.new.controller", "symfony");

		if (template != null) {
			return SymfonyTemplateStore.compileTemplate(getTemplatesContextTypeRegistry(), template);	
		}

		return null;

	}	

	protected ContextTypeRegistry getTemplatesContextTypeRegistry() {
		return SymfonyUiPlugin.getDefault().getCodeTemplateContextRegistry();
	}




	protected IProject getProject() {
		IWizard wizard = getWizard();
		IProject project = null;
		if (wizard instanceof ControllerCreationWizard) {
			project = ((ControllerCreationWizard) wizard).getProject();
		}
		return project;
	}


	@Override
	public void resetTableViewerInput() {

	
	}

}