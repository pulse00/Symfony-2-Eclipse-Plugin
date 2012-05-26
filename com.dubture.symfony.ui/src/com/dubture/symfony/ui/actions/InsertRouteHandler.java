package com.dubture.symfony.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.index.dao.Route;

@SuppressWarnings("restriction")
public class InsertRouteHandler extends BaseTextInsertionHandler
{

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException
    {
        IModelElement modelElement = getCurrentModelElement(event);
        Assert.isNotNull(modelElement);
        
        IScriptProject scriptProject = modelElement.getScriptProject();
        Assert.isNotNull(scriptProject);
        
        final Shell p = DLTKUIPlugin.getActiveWorkbenchShell();
        ILabelProvider labelRenderer  = new ArrayLabelProvider();
        
        ElementListSelectionDialog dialog = new ElementListSelectionDialog(p, labelRenderer);
        
        SymfonyModelAccess modelAccess = SymfonyModelAccess.getDefault();
        
        List<Route> routes = modelAccess.findRoutes(scriptProject);
        List<String[]> input = new ArrayList<String[]>(); 
        
        for (Route route : routes) {
            String display = route.getName() + " - " + route.getViewPath();
            
            input.add(new String[]{display, route.getName()});
        }
        
        dialog.setElements(input.toArray());
        dialog.setMultipleSelection(true);
        dialog.setTitle("Select a route to insert");
        dialog.setMessage("Wildcardsearch enabled");
        
        if (dialog.open() == Window.OK) {
            Object[] result = dialog.getResult();
            for (int i = 0; i < result.length; i++) {
                String[] ss = (String[])result[i];
                insertText(ss[1]);
            }
        }
        
        return null;
    }
    
    static class ArrayLabelProvider extends LabelProvider{
        public String getText(Object element) {
            return ((String[]) element)[0].toString();
        }
    }


}
