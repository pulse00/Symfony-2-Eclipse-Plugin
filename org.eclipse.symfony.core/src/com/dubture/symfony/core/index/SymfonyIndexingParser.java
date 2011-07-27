package com.dubture.symfony.core.index;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.index2.IIndexingRequestor;
import org.eclipse.php.internal.core.index.PhpIndexingParser;

import com.dubture.symfony.core.builder.SymfonyBuildParticipant;

/**
 * 
 * Can eventually be uses to natively parse xml/yml config
 * files.
 * 
 * Does nothing currently though...
 * 
 * XML/Yaml parsing currently takes place in the
 * {@link SymfonyBuildParticipant}
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyIndexingParser extends PhpIndexingParser {
	
	@Override
	public void parseSourceModule(ISourceModule module,
			IIndexingRequestor requestor) {

		super.parseSourceModule(module, requestor);
		
	}

}
