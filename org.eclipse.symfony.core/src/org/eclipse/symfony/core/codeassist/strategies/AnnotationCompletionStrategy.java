package org.eclipse.symfony.core.codeassist.strategies;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.strategies.PHPDocParamVariableStrategy;

@SuppressWarnings("restriction")
public class AnnotationCompletionStrategy extends PHPDocParamVariableStrategy
		implements ICompletionStrategy {

	public AnnotationCompletionStrategy(ICompletionContext context) {
		super(context);

	}

}
