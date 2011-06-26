package org.eclipse.symfony.core.goals;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.context.FileContext;
import org.eclipse.symfony.core.goals.evaluator.TemplateVariableGoalEvaluator;
import org.eclipse.symfony.core.index.SymfonyElementResolver.TemplateField;
import org.eclipse.symfony.core.model.SymfonyModelAccess;

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
					 IModelElement element = SymfonyModelAccess.getDefault().findTemplateVariableType(variable.toString(), context.getSourceModule());
					 
					 if (element != null && (element instanceof TemplateField)) {
						 
						 return new TemplateVariableGoalEvaluator(goal, (TemplateField) element);
					 }
				}
			}			
		}				
		return null;
	}
}