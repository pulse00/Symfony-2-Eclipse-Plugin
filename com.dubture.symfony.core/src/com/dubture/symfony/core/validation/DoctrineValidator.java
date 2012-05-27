package com.dubture.symfony.core.validation;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.sse.core.internal.validate.ValidationMessage;
import org.eclipse.wst.validation.AbstractValidator;
import org.eclipse.wst.validation.ValidationResult;
import org.eclipse.wst.validation.ValidationState;
import org.eclipse.wst.validation.ValidatorMessage;

public class DoctrineValidator extends AbstractValidator {

	public DoctrineValidator() {
	
		System.err.println("create source validator");
	}
	
	
	
	@Override
	public ValidationResult validate(IResource resource, int kind,
			ValidationState state, IProgressMonitor monitor) {
	
		System.err.println("validate");
	
		ValidationResult result = new ValidationResult();
		
		ValidatorMessage message = ValidatorMessage.create("foobar", resource);
		message.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		
		result.add(message);
		return result;
	}
}
