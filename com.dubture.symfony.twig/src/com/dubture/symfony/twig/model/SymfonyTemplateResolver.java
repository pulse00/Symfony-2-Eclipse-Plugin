package com.dubture.symfony.twig.model;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.core.SourceModule;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.ViewPath;
import com.dubture.twig.core.model.ITemplateResolver;

@SuppressWarnings("restriction")
public class SymfonyTemplateResolver implements ITemplateResolver
{
    @Override
    public SourceModule revolePath(String path, IScriptProject project)
    {
        if (path == null || !path.contains(".")) {
            return null;
        }
        
        IModelElement template = SymfonyModelAccess.getDefault().findTemplate(new ViewPath(path), project);
        if (template != null && template instanceof SourceModule) {
            try {
                SourceModule module = (SourceModule) template;
                return module;
            } catch (Exception e) {
              Logger.logException(e);  
            }
        }
        return null;
    }
}
