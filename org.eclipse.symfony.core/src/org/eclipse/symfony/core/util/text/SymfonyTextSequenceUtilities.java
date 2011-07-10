package org.eclipse.symfony.core.util.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.symfony.core.model.SymfonyModelAccess;


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
		return readViewPathStartIndex(textSequence, startPosition);

	}
	
	
	public static int readViewPathStartIndex(CharSequence textSequence, int startPosition) {
		
		while (startPosition > 0) {

			char ch = textSequence.charAt(startPosition - 1);
			if (!Character.isLetterOrDigit(ch) && ch != ':' && ch != '.') {
				break;
			}
			startPosition--;
		}
		if (startPosition > 0
				&& (textSequence.charAt(startPosition) == '"' || textSequence.charAt(startPosition) == '\'' )) {
			startPosition++;

		}

		return startPosition;		
		
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

		return source.replaceAll("['\"]", "");

	}

	
	/**
	 * Retrieve the service name from a PHP method call,
	 * ie: $this->get('session') will return the session literal.
	 *  
	 * @param sequence
	 * @return
	 */
	public static String getServiceFromMethodParam(TextSequence sequence) {

		String source = sequence.toString();

		int start = source.indexOf("(") +1;
		int end = source.indexOf(")") -1;

		if (start < 0 || end < 0 || (end < start))
			return null;


		return removeQuotes(source.substring(start, end)); 

	}
	
	
	/**
	 * 
	 * Extract the methodName of a TextSequence, ie,
	 * 
	 * "$this->generate('"  will return generate 
	 * 
	 * @param statement
	 * @return
	 */
	public static String getMethodName(TextSequence statement) {
		
		String text = statement.toString();
		
		int start = text.indexOf("->");
		
		if (start == -1)
			return null;
		
		int end = text.indexOf("(");
		
		if (end == -1)
			return null;
		
		String method = text.substring(start+2, end);
		return method;
		
		
	}


	/**
	 * Check if the TextSequence is inside PHP method accepting
	 * viewPath parameters. 
	 * 
	 * @param statement
	 * @param project
	 * @return
	 */
	public static boolean isInViewPathFunctionParameter(TextSequence statement, IScriptProject project) {

		String method = getMethodName(statement);		
		return SymfonyModelAccess.getDefault().hasViewMethod(method, project);

	}


	public static boolean isInRouteFunctionParameter(TextSequence statement,
			IScriptProject project) {
		
		String method = getMethodName(statement);
		return SymfonyModelAccess.getDefault().hasRouteMethod(method, project);

	}


	public static int readViewPathEndIndex(CharSequence textSequence, int startPosition) {

				
		int max = textSequence.length() -1;
		
		while (startPosition < max) {

			char ch = textSequence.charAt(startPosition);
			if (!Character.isLetterOrDigit(ch) && ch != ':' && ch != '.') {
				break;
			}
			startPosition++;
		}
		if (startPosition > 0
				&& (textSequence.charAt(startPosition-1) == '"' || textSequence.charAt(startPosition-1) == '\'' )) {
			startPosition--;

		}

		return startPosition;				

	}
}