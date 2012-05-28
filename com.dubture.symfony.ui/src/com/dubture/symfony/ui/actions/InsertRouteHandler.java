/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.actions;

import java.util.ArrayList;
import java.util.List;

import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.index.dao.Route;

public class InsertRouteHandler extends BaseTextInsertionHandler
{
    
    @Override
    protected List<String[]> getInput()
    {
        SymfonyModelAccess modelAccess = SymfonyModelAccess.getDefault();
        
        List<Route> routes = modelAccess.findRoutes(project);
        List<String[]> input = new ArrayList<String[]>();
        
        for (Route route : routes) {
            String display = route.getName() + " - " + route.getViewPath();
            
            input.add(new String[]{display, route.getName()});
        }
        
        return input;
    }

    @Override
    protected String getTitle()
    {
        return "Select a route to insert";
    }
}
