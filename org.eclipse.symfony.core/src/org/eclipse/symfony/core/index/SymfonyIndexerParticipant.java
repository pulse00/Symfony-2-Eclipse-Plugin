package org.eclipse.symfony.core.index;

import org.eclipse.dltk.core.index2.IElementResolver;
import org.eclipse.dltk.core.index2.IIndexingParser;
import org.eclipse.php.internal.core.index.PhpIndexerParticipant;

/**
 * 
 * Provides additional indexing capabilities to the 
 * PDT {@link PhpIndexerParticipant}
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyIndexerParticipant extends PhpIndexerParticipant  {

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
