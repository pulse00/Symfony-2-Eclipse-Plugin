package org.eclipse.symfony.ui.editor.hover;

import javax.sound.midi.SysexMessage;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.php.internal.ui.editor.hover.PHPSourceViewerInformationControl;
import org.eclipse.swt.widgets.Shell;

@SuppressWarnings("restriction")
public class SymfonySourceViewerInformationControl extends PHPSourceViewerInformationControl {

	public SymfonySourceViewerInformationControl(Shell parent) {
		super(parent);

	}
	
	
	@Override
	public TextAttribute getAttribute(String namedStyle) {
	
		System.err.println("get attribute " + namedStyle);
		return super.getAttribute(namedStyle);
	}
	

}
