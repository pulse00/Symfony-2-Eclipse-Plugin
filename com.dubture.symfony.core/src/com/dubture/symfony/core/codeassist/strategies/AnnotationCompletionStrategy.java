/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist.strategies;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.strategies.GlobalTypesStrategy;
import org.eclipse.php.internal.core.codeassist.strategies.PHPDocTagStrategy;
import org.eclipse.php.internal.core.model.PhpModelAccess;

import com.dubture.symfony.core.codeassist.contexts.AnnotationCompletionContext;
import com.dubture.symfony.core.model.SymfonyModelAccess;


/**
 * 
 * The {@link AnnotationCompletionStrategy} parses the UseStatements
 * of the current class and reports the aliases to 
 * the completion engine:
 * 
 *  <pre>
 * 
 *   use Doctrine\ORM\Mapping as ORM;
 *   
 *   ...
 *   
 *   /**
 *   @ | <-- add ORM\ to the code completion suggestions
 *  
 *  </pre>
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class AnnotationCompletionStrategy extends PHPDocTagStrategy {

	private int trueFlag = 0;
	private int falseFlag = 0;
	private Map<String, String> annotations;

	public AnnotationCompletionStrategy(ICompletionContext context) {
		super(context);

	}

	@Override
	public void apply(final ICompletionReporter reporter) throws BadLocationException {

		ICompletionContext ctx = getContext();

		if (!(ctx instanceof AnnotationCompletionContext)) {
			return;
		}		

		AnnotationCompletionContext context = (AnnotationCompletionContext) ctx;
		SourceRange replaceRange = getReplacementRange(context);

		annotations = SymfonyModelAccess.getDefault()
				.findAnnotationClasses(context.getSourceModule().getScriptProject());

		String suffix = "()";		
		String prefix = context.getPrefix();

		IType[] types = getTypes(context, prefix);
		for (IType type : types) {

			if (annotations.containsKey(type.getElementName())) {

				String qualifier = annotations.get(type.getElementName());

				if (qualifier != null && type.getTypeQualifiedName().equals(qualifier + "$" + type.getElementName()) ) {
					reporter.reportType(type, suffix, replaceRange);
				}
			}
		}		


//		handles ORM\ cases for "use Doctrine\ORM\Mapping as ORM; UseStatements
		AnnotationAliasCompletionStrategy gt = new AnnotationAliasCompletionStrategy(context);		
		gt.apply(reporter);

	}

	private IType[] getTypes(AnnotationCompletionContext context, String prefix) {


		IDLTKSearchScope scope = createSearchScope();
		if (context.getCompletionRequestor().isContextInformationMode()) {
			return PhpModelAccess.getDefault().findTypes(prefix,
					MatchRule.EXACT, trueFlag, falseFlag, scope, null);
		}

		List<IType> result = new LinkedList<IType>();
		if (prefix.length() > 1 && prefix.toUpperCase().equals(prefix)) {
			// Search by camel-case
			IType[] types = PhpModelAccess.getDefault().findTypes(prefix,
					MatchRule.CAMEL_CASE, trueFlag, falseFlag, scope, null);
			result.addAll(Arrays.asList(types));
		}
		IType[] types = PhpModelAccess.getDefault().findTypes(null, prefix,
				MatchRule.PREFIX, trueFlag, falseFlag, scope, null);
		result.addAll(Arrays.asList(types));

		return (IType[]) result.toArray(new IType[result.size()]);		

	}


	private class AnnotationAliasCompletionStrategy extends GlobalTypesStrategy {

		public AnnotationAliasCompletionStrategy(ICompletionContext context) {
			super(context);

		}

		public void apply(ICompletionReporter reporter) throws BadLocationException {

			ICompletionContext context = getContext();
			AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
			if (abstractContext.getPrefixWithoutProcessing().trim().length() == 0) {
				return;
			}
			SourceRange replacementRange = getReplacementRange(abstractContext);


			IType[] types = getTypes(abstractContext);
			// now we compute type suffix in PHPCompletionProposalCollector
			String suffix = "";//$NON-NLS-1$ 
			String nsSuffix = getNSSuffix(abstractContext);

			for (IType type : types) {
				try {
					int flags = type.getFlags();
					
					if (annotations.containsKey(type.getElementName())) {

						String qualifier = annotations.get(type.getElementName());

						if (qualifier != null && type.getTypeQualifiedName().equals(qualifier + "$" + type.getElementName()) ) {

							reporter.reportType(type,
									PHPFlags.isNamespace(flags) ? nsSuffix : suffix,
											replacementRange, getExtraInfo());
							
						}
					}					
					
				} catch (ModelException e) {
					PHPCorePlugin.log(e);
				}
			}
			addAlias(reporter, suffix);
		}
	}
}
