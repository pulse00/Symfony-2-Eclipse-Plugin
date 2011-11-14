/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.index;

import org.eclipse.dltk.core.ISourceModule;

import org.eclipse.dltk.core.index2.IIndexingRequestor;
import org.eclipse.php.internal.core.index.PhpIndexingVisitor;

@SuppressWarnings("restriction")
public class SymfonyIndexingVisitor extends PhpIndexingVisitor {

	public SymfonyIndexingVisitor(IIndexingRequestor requestor, ISourceModule sourceModule) {
		super(requestor, sourceModule);
	}

}
