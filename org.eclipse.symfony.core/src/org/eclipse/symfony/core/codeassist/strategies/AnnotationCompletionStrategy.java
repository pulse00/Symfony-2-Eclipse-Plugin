package org.eclipse.symfony.core.codeassist.strategies;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.ClassInstantiationStrategy;
import org.eclipse.symfony.core.codeassist.contexts.AnnotationCompletionContext;


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
public class AnnotationCompletionStrategy extends ClassInstantiationStrategy
implements ICompletionStrategy {
	
	public static final String[] EXTRA_ANNOTATIONS = { "Template", "Route", "ParamConverter", "Cache" };	

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
		
		String suffix = "";
		
		IType[] types = getTypes(context);
		for (IType type : types) {			
			reporter.reportType(type, suffix, replaceRange);
		}		
	}
}