package org.eclipse.symfony.core.util.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.php.internal.core.util.text.TextSequence;


/**
 * 
 * {@link SymfonyTextSequenceUtilities} is a utility class for {@link TextSequence}.
 *  Use it to  check if you're inside a special pattern - ie. a servicecontainer
 * getter function function like:
 * 
 * <pre>
 * 
 * $this->get(|
 * 
 * or 
 * 
 * $this->container->get('
 * 
 * </pre>
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyTextSequenceUtilities {

	private static final Pattern SERVICE_PATTERN = Pattern.compile("(\\$this->get\\(|\\$this->container->get\\()");
	

	
	private SymfonyTextSequenceUtilities() {
		
		
		
	}
	
	/**
	 * Checks for the existance of a service container function, ie. $this->get( or $this->container->get(
	 * @param sequence
	 * @return
	 */
	public static int isInServiceContainerFunction(CharSequence sequence) {
		
		Matcher matcher = SERVICE_PATTERN.matcher(sequence);
		
		while (matcher.find()) {		
			return matcher.start();			
		}
		return -1;		
		
	}
}