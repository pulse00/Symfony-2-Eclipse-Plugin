/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.index;

import org.eclipse.dltk.core.index2.IElementResolver;
import org.eclipse.dltk.core.index2.IIndexingParser;
import org.eclipse.php.internal.core.index.PHPIndexerParticipant;

/**
 * 
 * Provides additional indexing capabilities to the 
 * PDT {@link PHPIndexerParticipant}
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyIndexerParticipant extends PHPIndexerParticipant  {

	public SymfonyIndexerParticipant() {
		
	}
	
	@Override
	public IElementResolver getElementResolver() {

		return new SymfonyElementResolver();
	}
	
	@Override
	public IIndexingParser getIndexingParser() {

		return new SymfonyIndexingParser();
	}

}
