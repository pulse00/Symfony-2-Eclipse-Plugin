package com.dubture.symfony.ui.wizards;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IScriptFolder;

import com.dubture.pdt.ui.extension.INamespaceResolver;
import com.dubture.symfony.core.model.SymfonyModelAccess;

public class SymfonyNamespaceResolver implements INamespaceResolver {

	@Override
	public String resolve(IScriptFolder container) {

		IPath path = container.getPath();
		return SymfonyModelAccess.getDefault().findNameSpace(container.getScriptProject(), path);
		
	}

}
