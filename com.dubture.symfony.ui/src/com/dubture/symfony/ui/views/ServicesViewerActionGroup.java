package com.dubture.symfony.ui.views;

import org.eclipse.dltk.internal.ui.actions.CompositeActionGroup;
import org.eclipse.dltk.internal.ui.scriptview.ScriptMessages;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;

import com.dubture.symfony.ui.SymfonyPluginImages;

@SuppressWarnings("restriction")
public class ServicesViewerActionGroup extends CompositeActionGroup {

	private ServicePart part;
		
	private class TogglePublicAction extends Action {
		
		public TogglePublicAction() {
					
			super(ScriptMessages.CollapseAllAction_label); 
			setDescription(ScriptMessages.CollapseAllAction_description); 
			setToolTipText(ScriptMessages.CollapseAllAction_tooltip); 
			DLTKPluginImages.setLocalImageDescriptors(this, "collapseall.gif"); //$NON-NLS-1$			
			
		}
		@Override
		public void run()
		{
			super.run();
		}
		
	}
	
	private class FlatViewAction extends Action {
		
		public FlatViewAction() {
		
			setText("flat view");
			setImageDescriptor(DLTKPluginImages.DESC_DLCL_VIEW_MENU);
			
		}
		
		@Override
		public void run()
		{		
			part.switchFlatView();
		}
	}
	
	private class BundleViewAction extends Action {
		
		public BundleViewAction() {
			
			setText("Bundle view");
			setImageDescriptor(SymfonyPluginImages.DESC_OBJS_BUNDLE);
			
		}
		
		@Override
		public void run()
		{
		
			part.switchBundleView();
		
		}
		
	}
	
	public ServicesViewerActionGroup(ServicePart servicePart) {

		part = servicePart;
	}
	
	
	@Override
	public void fillActionBars(IActionBars actionBars)
	{
	
		super.fillActionBars(actionBars);
		setGlobalActionHandlers(actionBars);
		fillToolBar(actionBars.getToolBarManager());
		fillViewMenu(actionBars.getMenuManager());
				
		
	}


	private void fillViewMenu(IMenuManager menuManager)
	{
				
		menuManager.add(new FlatViewAction());
		menuManager.add(new BundleViewAction());
		
	}


	private void fillToolBar(IToolBarManager toolBarManager)
	{
				
		toolBarManager.add(new TogglePublicAction());

		
	}


	private void setGlobalActionHandlers(IActionBars actionBars)
	{
		
	}

}
