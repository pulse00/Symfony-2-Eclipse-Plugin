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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.Service;

@SuppressWarnings("restriction")
public class ServicePart extends ViewPart {

    private TreeViewer viewer;
    private List<ViewerFilter> filters = new ArrayList<ViewerFilter>();

    private PublicFilter publicFilter = new PublicFilter();
    private TagFilter tagFilter = new TagFilter();

    private Sorter sorter = new Sorter();

    private ServicesViewerActionGroup actionGroup;

    private IResourceChangeListener changeListener = new IResourceChangeListener() {

        @Override
        public void resourceChanged(IResourceChangeEvent event)
        {
            if (event.getType() == IResourceChangeEvent.POST_CHANGE ||
                    event.getType() == IResourceChangeEvent.PRE_DELETE) {

                updateViewer();
            }
        }
    };


    public ServicePart() {

    }

    @Override
    public void createPartControl(Composite parent)
    {

        parent.setLayout(new GridLayout(1, true));
        PatternFilter filter = new PatternFilter();
        FilteredTree tree = new FilteredTree(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL, filter, true);

        viewer = tree.getViewer();

        viewer.setContentProvider(new ServiceContentProviderFlatView());
        viewer.setLabelProvider(new ServiceLabelProvider());
        viewer.setSorter(sorter);

//        viewer.addSelectionChangedListener(new ISelectionChangedListener() {
//            @Override
//            public void selectionChanged(SelectionChangedEvent event)
//            {
//                if (event.getSelection() instanceof IStructuredSelection) {
//                    IStructuredSelection selection = (IStructuredSelection) event.getSelection();
//
//                    if (selection.getFirstElement() instanceof Service) {
////                        detail.updateService((Service) (selection.getFirstElement()));
//                    }
//                }
//            }
//        });


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
                                if (file.exists()) {
                                    IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
                                    page.openEditor(new FileEditorInput(file), desc.getId());
                                }
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

//        detail = new ServiceDetailView(parent, SWT.NONE);
        update();

        actionGroup = new ServicesViewerActionGroup(this);
        IActionBars actionBar = getViewSite().getActionBars();
        actionGroup.fillActionBars(actionBar);
        actionBar.updateActionBars();

        ResourcesPlugin.getWorkspace().addResourceChangeListener(changeListener);


    }

    protected void updateTags() {

//        List<String> tags = SymfonyModelAccess.getDefault().findServiceTags(new Path("/"));
//        ServicePart.this.tags.removeAll();
//        for (String tag : tags) {
//            ServicePart.this.tags.add(tag);
//        }
//
//        ServicePart.this.tags.redraw();

    }

    protected void updateViewer() {

        Display.getDefault().asyncExec(new Runnable(){
            public void run() {
                if (viewer.getControl().isDisposed()) {
                    return;
                }

                IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
                viewer.setInput(projects);
                viewer.refresh();
            }
        });

    }

    protected void update() {

        updateTags();
        updateViewer();
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

    public void switchFlatView()
    {
        viewer.setContentProvider(new ServiceContentProviderFlatView());
        updateViewer();
    }

    public void switchBundleView()
    {

        viewer.setContentProvider(new ServiceContentProviderBundleView());
        updateViewer();

    }

    public void setPublicFilter(boolean doFilter) {

        if (doFilter) {
            if (!filters.contains(publicFilter)) {
                filters.add(publicFilter);
            }
        } else {
            filters.remove(publicFilter);
        }

        viewer.setFilters((ViewerFilter[]) filters.toArray(new ViewerFilter[filters.size()]));
        viewer.refresh();

    }

    /**
    * true = ascending, false = descending
    * @param order
    */
    public void setSort(boolean order)
    {

        sorter.setOrder(order);
        viewer.setSorter(sorter);
        viewer.refresh();

    }

    private class PublicFilter extends ViewerFilter {

        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element)
        {
            if (element instanceof Service) {
                Service service = (Service) element;
                return service.isPublic();
            }

            return true;
        }
    }

    private class TagFilter extends ViewerFilter {

        private String tag = "";


        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element)
        {

            if (element instanceof Service) {
                Service service = (Service) element;
                return service.getStringTags().contains(tag);
            }

            return true;
        }

        public void setTag(String tag) {

            this.tag = tag;
        }
    }

    private class Sorter extends ViewerSorter {

        private boolean ascending = true;

        @SuppressWarnings("unchecked")
        @Override
        public int compare(Viewer viewer, Object e1, Object e2)
        {
            ModelElement left = null;
            ModelElement right = null;
            if (e1 instanceof Service && e2 instanceof Service) {
                left = (Service) e1;
                right = (Service) e2;
            } else if (e1 instanceof Bundle && e2 instanceof Bundle) {
                left = (Bundle) e1;
                right = (Bundle) e2;
            }

            if (left != null && right != null) {
                int result = getComparator().compare(left.getElementName(), right.getElementName());

                if (!ascending) {
                    result *= -1;
                }
                return result;
            }

            return super.compare(viewer, e1, e2);
        }

        public void setOrder(boolean order) {

            ascending = order;
        }
    }

    public void filterByTag(String text)
    {

        if (text != null && text.length() > 0) {

            if (filters.contains(tagFilter))
                filters.remove(tagFilter);

            tagFilter.setTag(text);
            filters.add(tagFilter);

        } else {
            filters.remove(tagFilter);
        }

        viewer.setFilters((ViewerFilter[]) filters.toArray(new ViewerFilter[filters.size()]));
        viewer.refresh();


    }
}
