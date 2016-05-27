package com.dubture.symfony.twig.model;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.core.SourceModule;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.ViewPath;
import com.dubture.twig.core.model.ITemplateResolver;

@SuppressWarnings("restriction")
public class SymfonyTemplateResolver implements ITemplateResolver
{
    @Override
    public IResource revolePath(String path, IProject project)
    {
        if (path == null || !path.contains(".")) {
            return null;
        }
        
        IModelElement template = SymfonyModelAccess.getDefault().findTemplate(new ViewPath(path), DLTKCore.create(project));
        if (template != null && template instanceof SourceModule) {
            try {
                SourceModule module = (SourceModule) template;
                return module.getResource();
            } catch (Exception e) {
              Logger.logException(e);  
            }
        }
        return null;
    }
}
