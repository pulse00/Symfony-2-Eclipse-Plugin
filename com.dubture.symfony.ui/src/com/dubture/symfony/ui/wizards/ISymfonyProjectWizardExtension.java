package com.dubture.symfony.ui.wizards;

import org.eclipse.swt.widgets.Group;

public interface ISymfonyProjectWizardExtension {

	void addElements(Group fGroup);

	String getNature();

}
