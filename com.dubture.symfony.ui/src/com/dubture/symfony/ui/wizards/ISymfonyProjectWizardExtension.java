/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.wizards;

import org.eclipse.swt.widgets.Group;

public interface ISymfonyProjectWizardExtension {

	void addElements(Group fGroup);

	String getNature();
	
	boolean isActivated();

}
