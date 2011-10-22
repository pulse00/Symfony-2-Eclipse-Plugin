package com.dubture.symfony.core.codeassist.strategies;

import java.util.List;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.MethodParameterKeywordStrategy;

import com.dubture.symfony.core.codeassist.contexts.TransUnitCompletionContext;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.Translation;
import com.dubture.symfony.index.dao.TransUnit;

/**
 * 
 * Strategy to complete Symfony translations. 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class TransUnitCompletionStrategy extends MethodParameterKeywordStrategy {

	public TransUnitCompletionStrategy(ICompletionContext context) {
		super(context);
		
	}
	
	
	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {


		TransUnitCompletionContext context = (TransUnitCompletionContext) getContext();
		IScriptProject project = context.getSourceModule().getScriptProject();
		
		SymfonyModelAccess model= SymfonyModelAccess.getDefault();
		
		List<TransUnit> units = model.findTranslations(project.getPath());		
		SourceRange range = getReplacementRange(context);		
		String prefix = context.getPrefix();
		
		if (units == null) {
			return;
		}
		
		IType type = null;
		
		try {
			IType[] types = context.getSourceModule().getTypes();
			if (types == null || types.length == 0)
				return;
			
			type = types[0];
			
		} catch (ModelException e) {
			e.printStackTrace();
			return;
		}
		
		for (TransUnit unit : units) {
			
			Translation trans = new Translation((ModelElement) type,unit);
			
			if (CodeAssistUtils.startsWithIgnoreCase(unit.name, prefix)) {
				reporter.reportType(trans, "", range);	
			}
		}	
	}
}
