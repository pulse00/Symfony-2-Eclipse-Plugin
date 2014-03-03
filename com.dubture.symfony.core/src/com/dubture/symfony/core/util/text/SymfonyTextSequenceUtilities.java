/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.util.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

import com.dubture.symfony.core.model.SymfonyModelAccess;


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

	private static final Pattern SERVICE_PATTERN = Pattern.compile("(\\$this->get\\(|\\$this->container->get\\(|->getContainer\\(\\)->get\\()");

	private static final Pattern GET_PATTERN = Pattern.compile("(->get\\()");

	private static final Pattern REPOSITORY_PATTERN = Pattern.compile("\\->getRepository\\(");

	private static final Pattern TRANSLATION_PATTERN = Pattern.compile("(\\->transChoice\\(|\\->trans\\()");

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
		return readLiteralStartIndex(textSequence, startPosition);

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

	/**
	 * Checks for the existance of a get function, ie. ->get(
	 * @param sequence
	 * @return
	 */
	public static int isGetFunction(CharSequence sequence) {

		Matcher matcher = GET_PATTERN.matcher(sequence);

		while (matcher.find()) {

			int pos = matcher.end();

			int lastMethodCall = sequence.toString().lastIndexOf("(");

			if (lastMethodCall > pos)
				return -1;

			return pos;
		}
		return -1;
	}


	/**
	 * Check if the sequence is a method call for a doctrine repository ie
	 *
	 *   $this->getDoctrine()->getRepository(|
	 *
	 * @param sequence
	 * @return
	 */
	public static int isInEntityFunctionParameter(CharSequence sequence) {

		Matcher matcher = REPOSITORY_PATTERN.matcher(sequence);

		while (matcher.find()) {

			int pos = matcher.end();

			int lastMethodCall = sequence.toString().lastIndexOf("(");

			if (lastMethodCall > pos)
				return -1;

			return pos;
		}
		return -1;



	}


	public static int isInTranslationFunctionParameter(CharSequence sequence) {

		Matcher matcher = TRANSLATION_PATTERN.matcher(sequence);

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
	public static String getMethodName(CharSequence statement) {

		String text = statement.toString();

		int start = text.indexOf("->");
		int end = text.lastIndexOf("(");

		int current = end;

		while( current > 0 && current > start) {
			char c = text.charAt(current--);
			if (c == '>') {
				return text.substring(current+2, end);
			}
		}

		return null;


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

		if (method == null)
			return false;

		return SymfonyModelAccess.getDefault().hasViewMethod(method, project);

	}


	/**
	 * Check if the TextSequence is a function which accept route parameters.
	 *
	 *
	 * @param statement
	 * @param project
	 * @return
	 */
	public static boolean isInRouteFunctionParameter(TextSequence statement,
			IScriptProject project) {

		String method = getMethodName(statement);
		if (method == null)
			return false;


		return SymfonyModelAccess.getDefault().hasRouteMethod(method, project);

	}



	/**
	 *
	 * Read to the start index of a String literal.
	 *
	 * @param textSequence
	 * @param startPosition
	 * @return
	 */
	public static int readLiteralStartIndex(CharSequence textSequence, int startPosition) {

		while (startPosition > 0) {

			char ch = textSequence.charAt(startPosition - 1);
			if (!Character.isLetterOrDigit(ch) && ch != ':' && ch != '.' && ch != '_' && ch !=  '/') {
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
	 *
	 * Read to the end index of a String literal.
	 *
	 * @param textSequence
	 * @param startPosition
	 * @return
	 */
	public static int readLiteralEndIndex(CharSequence textSequence, int startPosition) {


		int max = textSequence.length() -1;

		while (startPosition < max) {

			char ch = textSequence.charAt(startPosition);
			if (!Character.isLetterOrDigit(ch) && ch != ':' && ch != '.' && ch != '_') {
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
