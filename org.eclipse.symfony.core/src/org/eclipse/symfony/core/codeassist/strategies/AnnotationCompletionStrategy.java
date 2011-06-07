package org.eclipse.symfony.core.codeassist.strategies;

import java.util.Collection;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.PHPDocTagStrategy;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
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
public class AnnotationCompletionStrategy extends PHPDocTagStrategy
		implements ICompletionStrategy {

	public AnnotationCompletionStrategy(ICompletionContext context) {
		super(context);

	}
	
	@Override
	public void apply(final ICompletionReporter reporter) throws BadLocationException {
	
		AnnotationCompletionContext context = (AnnotationCompletionContext) getContext();
		ISourceModule sourceModule = context.getSourceModule();
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);

		final SourceRange range = getReplacementRange(context);
				
		try {
			moduleDeclaration.traverse(new PHPASTVisitor() {
				
				@Override
				public boolean visit(UseStatement s) throws Exception {
					Collection<UsePart> parts = s.getParts();
					for (UsePart part : parts) {
						String keyword = part.getAlias().getName() + "\\";						
						reporter.reportKeyword(keyword, "", range);
					}
					return true;
				}				
			});
		} catch (Exception e) {

			e.printStackTrace();
		}
			
		super.apply(reporter);
		
	}
}