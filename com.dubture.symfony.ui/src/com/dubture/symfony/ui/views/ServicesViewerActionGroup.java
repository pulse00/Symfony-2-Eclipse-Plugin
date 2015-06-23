/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.internal.ui.actions.CompositeActionGroup;
import org.eclipse.dltk.internal.ui.scriptview.ScriptMessages;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IActionBars;

import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.ui.SymfonyPluginImages;

@SuppressWarnings("restriction")
public class ServicesViewerActionGroup extends CompositeActionGroup {

	private ServicePart part;
		
	private class CollapseAction extends Action {
		
		public CollapseAction() {
					
			super(ScriptMessages.CollapseAllAction_label); 
			setDescription(ScriptMessages.CollapseAllAction_description); 
			setToolTipText(ScriptMessages.CollapseAllAction_tooltip); 
			DLTKPluginImages.setLocalImageDescriptors(this, "collapseall.png"); //$NON-NLS-1$			
			
		}
		@Override
		public void run()
		{
			super.run();
		}
		
	}
	
	private class FlatViewAction extends Action {
		
		public FlatViewAction() {
		
			setText("Flat");
			DLTKPluginImages.setLocalImageDescriptors(this, "flatLayout.png"); //$NON-NLS-1$
			
		}
		
		@Override
		public void run()
		{		
			part.switchFlatView();
		}
	}
	
	private class BundleViewAction extends Action {
		
		public BundleViewAction() {			
			setText("Bundles");
			DLTKPluginImages.setLocalImageDescriptors(this, "hierarchicalLayout.png"); //$NON-NLS-1$
		}
		
		@Override
		public void run()
		{		
			part.switchBundleView();		
		}		
	}
	
	private class PublicFilter extends Action {
		
		public PublicFilter() {
			super("Hide private services", IAction.AS_CHECK_BOX);			
			DLTKPluginImages.setLocalImageDescriptors(this, "public_co.png"); //$NON-NLS-1$
		}
		
		
		@Override
		public void run()
		{
			part.setPublicFilter(isChecked());
			
		}		
	}
	
	private class SortAction extends Action  {
		
		
		public SortAction() {
			super("Sort order", IAction.AS_CHECK_BOX);
			DLTKPluginImages.setLocalImageDescriptors(this, "alphab_sort_co.png"); //$NON-NLS-1$
			
			
		}
		
		@Override
		public void run()
		{
					
			part.setSort(!isChecked());
		
		}

	}
	
	private class TagFilterAction extends Action implements IMenuCreator {
					
		public TagFilterAction() {			
			super("Filter by tags", IAction.AS_DROP_DOWN_MENU);
			setImageDescriptor(SymfonyPluginImages.DESC_OBJS_TAG);
			setMenuCreator(this);
		}
		
		@Override
		public void run()
		{
			
			
		}
		
		@Override
		public void dispose()
		{

			
		}

		@Override
		public Menu getMenu(Control parent)
		{

			Menu menu = new Menu(parent);
			final String remove = "Remove filter"; 

			List<String> tags = new ArrayList<String>();
			tags.add(remove);
			
			// get all workspace tags
			tags.addAll(SymfonyModelAccess.getDefault().findServiceTags(new Path("/")));
			
			int i = 0;
			
			for (String tag : tags) {
				
				if(tag == null || tag.length() == 0)
					continue;
				
				Action action = new Action(tag) {			
					@Override
					public void run()
					{					
						
						if (getText().equals(remove))
							part.filterByTag(null);
						else  part.filterByTag(getText());
					}
				};
				
				ActionContributionItem item = new ActionContributionItem(action);
				item.fill(menu, -1);
				
				if (i++ == 0) {
					new MenuItem(menu, SWT.SEPARATOR);					
				}				
			}
						
			return menu;
		}

		@Override
		public Menu getMenu(Menu parent)
		{

			return null;
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
		final MenuManager subMenu = new MenuManager("Service Representation");
		subMenu.add(new FlatViewAction());
		subMenu.add(new BundleViewAction());
		menuManager.add(subMenu);
		
	}


	private void fillToolBar(IToolBarManager toolBarManager)
	{

		toolBarManager.add(new CollapseAction());
		toolBarManager.add(new SortAction());		
		toolBarManager.add(new PublicFilter());
		toolBarManager.add(new TagFilterAction());
		
	}


	private void setGlobalActionHandlers(IActionBars actionBars)
	{
		
	}

}
