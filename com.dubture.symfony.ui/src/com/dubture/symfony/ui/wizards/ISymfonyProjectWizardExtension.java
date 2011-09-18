package com.dubture.symfony.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Group;

public interface ISymfonyProjectWizardExtension {

	void addElements(Group fGroup);

	void performFinish(IProject project);

}
