package com.dubture.symfony.core.codeassist.contexts;

import org.eclipse.php.internal.core.codeassist.contexts.QuotesContext;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * Allow identifiers with dots. Like security.context
 * 
 * @author zulus
 */
@SuppressWarnings("restriction")
abstract public class QuoteIdentifierContext extends QuotesContext {
	@Override
	public String getPrefixWithoutProcessing() {
		if (hasWhitespaceBeforeCursor()) {
			return ""; //$NON-NLS-1$
		}
		TextSequence statementText = getStatementText();
		int statementLength = statementText.length();
		int prefixEnd = PHPTextSequenceUtilities.readBackwardSpaces(
				statementText, statementLength); // read whitespace
		int prefixStart = PHPTextSequenceUtilities.readIdentifierStartIndex(
				getPHPVersion(), statementText, prefixEnd, true);
		
		while (prefixStart > 0 && statementText.charAt(prefixStart - 1) == '.') {
			prefixStart = PHPTextSequenceUtilities.readIdentifierStartIndex(
					getPHPVersion(), statementText, prefixStart - 1, true);
		}
		
		return statementText.subSequence(prefixStart, prefixEnd).toString();
	}
}
