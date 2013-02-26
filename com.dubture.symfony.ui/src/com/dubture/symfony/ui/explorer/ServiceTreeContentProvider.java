package com.dubture.symfony.ui.explorer;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;

@SuppressWarnings("restriction")
public class ServiceTreeContentProvider extends ScriptExplorerContentProvider {

    public ServiceTreeContentProvider() {
        super(true);
    }
	
    @Override
    public Object[] getChildren(Object parentElement)
    {
    	if (parentElement instanceof IScriptProject) {
    		return new Object[]{new ServiceFragmentContainer((IScriptProject) parentElement)};
    	}
    	return null;
    }
}
