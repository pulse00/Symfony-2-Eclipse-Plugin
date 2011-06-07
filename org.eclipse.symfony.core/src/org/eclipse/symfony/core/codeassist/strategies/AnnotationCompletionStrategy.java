package org.eclipse.symfony.core.codeassist.strategies;

import java.util.List;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.ClassInstantiationStrategy;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;
import org.eclipse.php.internal.core.typeinference.FakeMethod;
import org.eclipse.symfony.core.codeassist.contexts.AnnotationCompletionContext;
import org.eclipse.symfony.core.model.Annotation;
import org.eclipse.symfony.core.model.ModelManager;


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
		final SourceRange range = getReplacementRange(context);
		List<Annotation> annotations = ModelManager.getInstance().getAnnotations(context.getSourceModule());
		CompletionRequestor requestor = context.getCompletionRequestor();
		String tagName = context.getTagName();
		
		String suffix = "";
		
		IDLTKSearchScope scope = createSearchScope();

		for (Annotation annotation : annotations) {
			String nextTag = annotation.getName();
			if (CodeAssistUtils.startsWithIgnoreCase(nextTag, tagName)) {
				if (!requestor.isContextInformationMode()
						|| nextTag.length() == tagName.length()) {
					
					if (tagName.equals(nextTag))
						continue;

					try {
						
						IType[] types = PhpModelAccess.getDefault().findTypes(annotation.getName(), MatchRule.EXACT, 0, 0, scope, null);
						
						if (types.length != 1)
							continue;
						
						IMethod ctor = FakeConstructor.createFakeConstructor(null, types[0], false);
						reporter.reportMethod(ctor, suffix, range);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}			
		}
	}
}