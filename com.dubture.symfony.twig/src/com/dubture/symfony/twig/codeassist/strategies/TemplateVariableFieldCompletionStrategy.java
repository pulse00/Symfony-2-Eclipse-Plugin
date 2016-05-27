/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.twig.codeassist.strategies;

import com.dubture.twig.core.codeassist.ICompletionContext;
import com.dubture.twig.core.codeassist.ICompletionReporter;
import com.dubture.twig.core.codeassist.strategies.AbstractTwigCompletionStrategy;

/**
 * 
 * The {@link TemplateVariableFieldCompletionStrategy} completes variable fields
 * inside Symfony2 Twig templates:
 * 
 * 
 * <pre>
 * 
 * 	{{ form.|  <-- completes fields and methods of the Type "form" created in a controller
 * 
 * </pre>
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction" })
public class TemplateVariableFieldCompletionStrategy extends AbstractTwigCompletionStrategy {

	public TemplateVariableFieldCompletionStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws Exception {


//		VariableFieldContext ctx = (VariableFieldContext) getContext();
//		String varName = ctx.getVariable();
//		TemplateField var = SymfonyModelAccess.getDefault().findTemplateVariableType("$" + varName,
//				ctx.getSourceModule());
//
//		if (var == null) {
//			return;
//		}
//
//		String className = var.getClassName();
//		IDLTKSearchScope scope = SearchEngine.createSearchScope(ctx.getScriptProject());
//
//		String prefix = ctx.getPrefix();
//
//		ISourceRange range = getReplacementRange(getContext());
//
//		if (className != null) {
//
//			IType[] types = PhpModelAccess.getDefault().findTypes(var.getQualifier(), var.getClassName(),
//					MatchRule.EXACT, 0, 0, scope, null);
//
//			if (types.length == 1) {
//				IType type = types[0];
//
//				IDLTKSearchScope methodScope = SearchEngine.createSuperHierarchyScope(type);
//
//				IMethod[] methods = PhpModelAccess.getDefault().findMethods(prefix, MatchRule.PREFIX, 0, 0, methodScope,
//						null);
//
//				List<String> reported = new ArrayList<String>();
//
//				for (IMethod method : methods) {
//
//					if (!reported.contains(method.getElementName())) {
//						reporter.reportMethod(method, "()", range);
//						reported.add(method.getElementName());
//					}
//				}
//
//				IDLTKSearchScope fieldScope = SearchEngine.createSuperHierarchyScope(type);
//				IField[] fields = PhpModelAccess.getDefault().findFields(prefix, MatchRule.PREFIX, 0, 0, fieldScope,
//						null);
//
//				for (IField field : fields) {
//
//					if (field.getFlags() == Modifiers.AccPublic)
//						reporter.reportField(field, "", range, true);
//				}
//			}
//		}
	}
}
