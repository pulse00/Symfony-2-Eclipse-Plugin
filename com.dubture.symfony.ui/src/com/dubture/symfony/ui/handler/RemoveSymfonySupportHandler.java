package com.dubture.symfony.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.dubture.symfony.core.facet.FacetManager;

public class RemoveSymfonySupportHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event)
				.getActivePage().getSelection();

		if (selection instanceof IStructuredSelection) {
			Object item = ((IStructuredSelection)selection).getFirstElement();

			if (item instanceof IAdaptable) {
				IAdaptable adaptable = (IAdaptable)item;
				IProject project = ((IResource)adaptable.getAdapter(IResource.class)).getProject();
				
				FacetManager.uninstallFacets(project, null);
			}
		}
		return null;
		
	}
}
