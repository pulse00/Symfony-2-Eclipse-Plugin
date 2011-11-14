/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.editor.hyperlink;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.dubture.symfony.core.log.Logger;

/**
 * 
 * Hyperlink for css and js files.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class AssetHyperlink implements IHyperlink {

	private IRegion region ;
	private IFile file;
	
	
	public AssetHyperlink(IRegion wordRegion, IFile file) {
 
		region = wordRegion;
		this.file = file;		
		
	}

	@Override
	public IRegion getHyperlinkRegion() {

		return region;

	}

	@Override
	public String getTypeLabel() {

		return "Symfony asset";

	}

	@Override
	public String getHyperlinkText() {

		return file.getName();

	}

	@Override
	public void open() {

		try {

			if (!file.exists())
				return;
			
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();			
			IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
			page.openEditor(new FileEditorInput(file), desc.getId());
			
		} catch (CoreException e) {
			Logger.logException(e);
		}		

	}

}
