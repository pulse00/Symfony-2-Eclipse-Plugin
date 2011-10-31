package com.dubture.symfony.core.goals;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.typeinference.context.FileContext;

import com.dubture.symfony.core.goals.evaluator.TemplateVariableGoalEvaluator;
import com.dubture.symfony.core.index.SymfonyElementResolver.TemplateField;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.util.PathUtils;

/**
 * 
 * 
 * 
 * @see TemplateVariableGoalEvaluator
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class TemplateGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	public TemplateGoalEvaluatorFactory() {

	}

	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {

		if (goal instanceof ExpressionTypeGoal) {

			ExpressionTypeGoal expressionGoal = (ExpressionTypeGoal) goal;
			
			if (expressionGoal.getContext() instanceof FileContext) {

				FileContext context = (FileContext) expressionGoal.getContext();
				
				ASTNode node = expressionGoal.getExpression();
				
				if (node instanceof VariableReference) {
					
					VariableReference variable = (VariableReference) node;
					TemplateField element = SymfonyModelAccess.getDefault().findTemplateVariableType(variable.toString(), context.getSourceModule());
					 
					 if (element != null) {
						 						
						 String viewName = PathUtils.createViewPathFromTemplate(context.getSourceModule(), false);
						 
						 if (viewName != null && element.getViewPath().equals(viewName))
							 return new TemplateVariableGoalEvaluator(goal, element);
					 }
				}
			}			
		}				
		return null;
	}
}