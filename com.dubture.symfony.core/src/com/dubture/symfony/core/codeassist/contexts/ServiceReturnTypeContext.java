/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist.contexts;

import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;
import org.eclipse.php.internal.core.format.PHPHeuristicScanner;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;
import com.dubture.symfony.core.util.text.SymfonyTextSequenceUtilities;

/**
 *
 * A context which is valid when completing services directly from
 * the DI container:
 *
 *
 * <pre>
 *
 *   $em = $this->get('doctrine')-> |
 *
 * </pre>
 *
 *
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ServiceReturnTypeContext extends ClassMemberContext {


	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		if (super.isValid(sourceModule, offset, requestor))
		{

			try {
				IProjectNature nature;
				nature = sourceModule.getScriptProject().getProject().getNature(SymfonyNature.NATURE_ID);

				// wrong nature
				if(!(nature instanceof SymfonyNature)) {
					return false;
				}
				// Check function name
				if (SymfonyTextSequenceUtilities.isGetFunction(getStatementText())  == -1) {
					return false;
				}

				TextSequence statementText = getStatementText();
				int totalLength = statementText.length();
				int elementStart = PHPTextSequenceUtilities.readBackwardSpaces(
						statementText, totalLength);
				elementStart = PHPTextSequenceUtilities.readIdentifierStartIndex(
						statementText, elementStart, true);
				elementStart = PHPTextSequenceUtilities.readBackwardSpaces(
						statementText, elementStart);
				elementStart-=3;
				if (statementText.charAt(elementStart) != ')') { //
					return false;
				}

				// find get()
				PHPHeuristicScanner scanner = PHPHeuristicScanner.createHeuristicScanner(getDocument(), offset - statementText.length() + elementStart-1, true);
				int open = scanner.findOpeningPeer(offset - statementText.length() + elementStart-1, PHPHeuristicScanner.UNBOUND, PHPHeuristicScanner.LPAREN, PHPHeuristicScanner.RPAREN);
				statementText = PHPTextSequenceUtilities.getStatement(open, getStructuredDocumentRegion(), true);;
				totalLength = statementText.length();
				elementStart = PHPTextSequenceUtilities.readBackwardSpaces(
						statementText, totalLength);
				elementStart = PHPTextSequenceUtilities.readIdentifierStartIndex(
						statementText, elementStart, true);


				// read lhs types
				IType[] lhsTypes = CodeAssistUtils.getTypesFor(getSourceModule(), statementText, elementStart, open);

				for (IType type : lhsTypes) {
					if (type.getFullyQualifiedName("\\").equals(SymfonyCoreConstants.CONTAINER_INTERFACE) || type.getFullyQualifiedName("\\").equals(SymfonyCoreConstants.CONTROLLER_PARENT)) {
						return true;
					}

					IType[] superClasses = PHPModelUtils.getSuperClasses(type, getCompanion().getSuperTypeHierarchy(type, null));
					for (IType sc : superClasses) {
						if (sc.getFullyQualifiedName("\\").equals(SymfonyCoreConstants.CONTAINER_INTERFACE) || sc.getFullyQualifiedName("\\").equals(SymfonyCoreConstants.CONTROLLER_PARENT)) {
							return true;
						}
					}
				}

			} catch (CoreException e) {
				Logger.logException(e);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}
}
