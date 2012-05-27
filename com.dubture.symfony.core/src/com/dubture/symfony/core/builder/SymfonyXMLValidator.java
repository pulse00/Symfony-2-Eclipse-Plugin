package com.dubture.symfony.core.builder;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.validation.AbstractValidator;
import org.eclipse.wst.validation.ValidationResult;
import org.eclipse.wst.validation.ValidationState;

public class SymfonyXMLValidator extends AbstractValidator
{
    public SymfonyXMLValidator()
    {
        
    }
    
    @Override
    public ValidationResult validate(IResource resource, int kind,
            ValidationState state, IProgressMonitor monitor)
    {
        return super.validate(resource, kind, state, monitor);
    }

}
