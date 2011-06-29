package org.eclipse.symfony.core.util.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
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
 * @see PHPTextSequenceUtilities
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
	 * Retrieve the startOffset of a ViewPath inside a textSeauence
	 * 
	 *  
	 * @param textSequence
	 * @return
	 */
	public static int readViewPathStartIndex(CharSequence textSequence) {
		
		int startPosition = textSequence.length() -1;
		
		while (startPosition > 0) {
			
			char ch = textSequence.charAt(startPosition - 1);
			if (!Character.isLetterOrDigit(ch) && ch != ':') {
				break;
			}
			startPosition--;
		}
		if (startPosition > 0
				&& textSequence.charAt(startPosition - 1) == '"') {
			startPosition--;
		}
		
		return startPosition + 1;
		
	}
	
	/**
	 * Checks for the existance of a service container function, ie. $this->get( or $this->container->get(
	 * @param sequence
	 * @return
	 */
	public static int isInServiceContainerFunction(CharSequence sequence) {
		
		Matcher matcher = SERVICE_PATTERN.matcher(sequence);
		
		while (matcher.find()) {	
			
			int pos = matcher.end();
			
			int lastMethodCall = sequence.toString().lastIndexOf("(");
			
			if (lastMethodCall > pos)
				return -1;
			
			return pos;
		}
		return -1;		
		
	}
	
	public static String removeQuotes(String source) {
				
		return source.replace("'", "").replace("\"", "");
		
	}

	public static String getServiceFromMethodParam(TextSequence sequence) {

		String source = sequence.toString();
		
		int start = source.indexOf("(") +1;
		int end = source.indexOf(")") -1;
		
		if (start < 0 || end < 0 || (end < start))
			return null;
		
		
		return removeQuotes(source.substring(start, end)); 

	}
}