/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.listener;

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
 * TODO: open feature request in bugzilla
 * 
 * 1. To make eclipse register a url scheme with the host operating system
 * 2. To create an extension point which lets plugins handle opening of documents
 * , so switching to a specific line can be done 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class OpenDocumentListener implements Listener {

	@Override
	public void handleEvent(Event event) {

		

	}

}
