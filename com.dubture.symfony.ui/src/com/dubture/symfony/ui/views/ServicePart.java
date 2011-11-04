package com.dubture.symfony.ui.views;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.SymfonyModelAccess;

public class ServicePart extends ViewPart {

	private IResourceChangeListener changeListener = new IResourceChangeListener() {
		
		@Override
		public void resourceChanged(IResourceChangeEvent event)
		{			
			if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
								
				IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();				
				viewer.setInput(projects);
				viewer.refresh();
			}
		}
	};
	private TreeViewer viewer;
	private ServiceDetailView detail;
	private Combo tags;
	
	public ServicePart() {

	}

	@Override
	public void createPartControl(Composite parent)
	{

		parent.setLayout(new GridLayout(1, true));
		PatternFilter filter = new PatternFilter();
		FilteredTree tree = new FilteredTree(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL, filter, true);
		
		viewer = tree.getViewer();
		
		Composite cp = tree.getFilterControl().getParent();
		
		tags = new Combo(cp, SWT.READ_ONLY);
		tags.setData(SymfonyModelAccess.getDefault().findServiceTags(new Path("/")));
		GridData gd = new GridData();
		gd.widthHint = 200;
		tags.setText("Filter by tag");
		tags.setLayoutData(gd);
		
		
		viewer.setContentProvider(new ServiceContentProvider());
		viewer.setLabelProvider(new ServiceLabelProvider());
		viewer.setSorter(new ViewerSorter() {
			
			@SuppressWarnings("unchecked")
			@Override
			public int compare(Viewer viewer, Object e1, Object e2)
			{
				if (e1 instanceof Service && e2 instanceof Service) {
					Service left = (Service) e1;
					Service right = (Service) e2;					
					return getComparator().compare(left.getElementName(), right.getElementName());
					
					
				}
				return super.compare(viewer, e1, e2);
			}			
		});
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event)
			{

				if (event.getSelection() instanceof IStructuredSelection) {
					
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					
					if (selection.getFirstElement() instanceof Service) {
						detail.updateService((Service) (selection.getFirstElement()));
					}
				}
				ISelection selection = event.getSelection();
				
			}
		});
		
		
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event)
			{

				if (event.getSelection() instanceof IStructuredSelection) {
					
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					
					if (selection.getFirstElement() instanceof Service) {
						
						Service service = (Service) selection.getFirstElement();
						
						
						if (service.getSourceModule() != null) {
							
							try {
								IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
								IFile file = (IFile) service.getSourceModule().getUnderlyingResource();
								IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
								page.openEditor(new FileEditorInput(file), desc.getId());
							} catch (PartInitException e) {
								e.printStackTrace();
							} catch (ModelException e) {
								e.printStackTrace();
							}							
						} else {
							Logger.debugMSG("couldnt determint sourcemodule for service " + service.getElementName());
						}
					}
				}				
			}
		});
		
		detail = new ServiceDetailView(parent, SWT.NONE);		
		
		updateTags();
		
		ResourcesPlugin.getWorkspace().addResourceChangeListener(changeListener);


	}
	
	protected void updateTags() {
				
		List<String> tags = SymfonyModelAccess.getDefault().findServiceTags(new Path("/"));
		ServicePart.this.tags.removeAll();
		for (String tag : tags) {
			ServicePart.this.tags.add(tag);
		}
		
		ServicePart.this.tags.redraw();		
		
	}
	
	

	@Override
	public void setFocus()
	{

		viewer.getControl().setFocus();

	}
	

	
	
	@Override
	public void dispose()
	{
		super.dispose();
		
		if (changeListener != null)
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(changeListener);
		
	} 

}
