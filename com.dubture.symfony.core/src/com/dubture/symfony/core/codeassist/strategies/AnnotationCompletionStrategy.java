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
		
		
		// getting the parameter suffix from the class constructor
		// makes no sense in this context, as we can't parse
		// the annotation parameters from PHP classes
		// unless there's at least some semi-standardized
		// way of describing annotations in the classes
		// which represent them
		//String suffix = getSuffix(context);
		
		
		String suffix = "()";
		
		IType[] types = getTypes(context);
		for (IType type : types) {			
			reporter.reportType(type, suffix, replaceRange);
		}		
	}
	
	private IType[] getTypes(AnnotationCompletionContext context) {
		

		//TODO: this is basically just c/p from ClassInstantiationStrategy
		// 
		// check if we're in the right scope in here and what those
		// trueFlag / falseFlags actually do.
		String prefix = "";

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