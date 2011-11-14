/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.editor.hover;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.php.internal.ui.editor.hover.PHPSourceViewerInformationControl;
import org.eclipse.swt.widgets.Shell;


/**
 * 
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonySourceViewerInformationControl extends PHPSourceViewerInformationControl {

	public SymfonySourceViewerInformationControl(Shell parent) {
		super(parent);

	}
	
	
	@Override
	public TextAttribute getAttribute(String namedStyle) {
	
		return super.getAttribute(namedStyle);
	}
	

}
