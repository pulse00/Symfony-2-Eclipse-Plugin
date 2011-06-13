package org.eclipse.symfony.ui.twig;

import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.twig.ui.editor.completion.ITwigCompletionProvider;


/**
 * 
 * {@link SymfonyTwigCompletionProvider} hooks into the
 * codeassist process of the Twig plugin and provides
 * contextual codeassistance in the current template.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class SymfonyTwigCompletionProvider implements ITwigCompletionProvider {

	public SymfonyTwigCompletionProvider() {

	}


	@Override
	public String[] provideCompletions(IModuleSource module, int position, int i) {

		return new String[] {"hi", "from", "symfony"};
		

	}
}