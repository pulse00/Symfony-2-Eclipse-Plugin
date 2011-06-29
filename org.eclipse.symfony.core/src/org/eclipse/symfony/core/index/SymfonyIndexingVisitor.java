package org.eclipse.symfony.core.index;

import org.eclipse.dltk.core.ISourceModule;

import org.eclipse.dltk.core.index2.IIndexingRequestor;
import org.eclipse.php.internal.core.index.PhpIndexingVisitor;

@SuppressWarnings("restriction")
public class SymfonyIndexingVisitor extends PhpIndexingVisitor {

	public SymfonyIndexingVisitor(IIndexingRequestor requestor, ISourceModule sourceModule) {
		super(requestor, sourceModule);
	}

}
