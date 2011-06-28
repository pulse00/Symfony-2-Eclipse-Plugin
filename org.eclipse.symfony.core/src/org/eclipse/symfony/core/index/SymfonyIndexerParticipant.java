package org.eclipse.symfony.core.index;

import org.eclipse.dltk.core.index2.IElementResolver;
import org.eclipse.dltk.core.index2.IIndexingParser;
import org.eclipse.php.internal.core.index.PhpIndexerParticipant;
import org.eclipse.symfony.core.model.IBundle;

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
		
		int foo = IBundle.ID;

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
