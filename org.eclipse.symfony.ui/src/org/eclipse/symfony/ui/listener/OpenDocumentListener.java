package org.eclipse.symfony.ui.listener;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * 
 * 
 * This only works through hacks at the moment, as
 * eclipse doesn't register a URL scheme with operating systems.
 * 
 * Furthermore, plugins have no way to intercept the event,
 * for example to handle line arguments.
 * 
 * At the current point it's only possible to provide helper
 * scripts like the one in launcher/osx which register
 * a symfony:// url protocol and pass over the "open with"
 * event to eclipse.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class OpenDocumentListener implements Listener {

	@Override
	public void handleEvent(Event event) {

		

	}

}
