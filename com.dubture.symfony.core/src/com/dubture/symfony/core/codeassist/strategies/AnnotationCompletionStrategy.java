package com.dubture.symfony.core.codeassist.strategies;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.GlobalTypesStrategy;
import org.eclipse.php.internal.core.codeassist.strategies.PHPDocTagStrategy;
import org.eclipse.php.internal.core.model.PhpModelAccess;

import com.dubture.symfony.core.codeassist.contexts.AnnotationCompletionContext;


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
		
		String suffix = "()";		
		String prefix = context.getPrefix();
		
		IType[] types = getTypes(context, prefix);
		for (IType type : types) {			
			reporter.reportType(type, suffix, replaceRange);
		}		

		// handles ORM\ cases for "use Doctrine\ORM\Mapping as ORM; UseStatements
		GlobalTypesStrategy gt = new GlobalTypesStrategy(context);		
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
	
}