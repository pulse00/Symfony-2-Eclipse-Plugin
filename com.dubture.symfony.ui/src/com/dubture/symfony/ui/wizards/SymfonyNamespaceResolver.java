/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.wizards;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IScriptFolder;
import org.pdtextensions.core.ui.extension.INamespaceResolver;

import com.dubture.composer.core.model.ModelAccess;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.SymfonyModelAccess;

public class SymfonyNamespaceResolver implements INamespaceResolver {

	@Override
	public String resolve(IScriptFolder container) {

	    if (container == null) {
	        Logger.log(Logger.WARNING, "Unable to resolve namespace, no container available");
	        return null;
	    }
	    
	    // Try composer first - Symfony 2.1
	    try {
	        IPath path = ModelAccess.getInstance().resolve(container.getResource());
	        if (path != null) {
	            return path.toString().replace("/", "\\");
	        }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(e);
        }
		
		// fallback to Symfony 2.0
		return SymfonyModelAccess.getDefault().findNameSpace(container.getScriptProject(), container.getPath());
		
	}
}
