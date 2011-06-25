package org.eclipse.symfony.core.goals;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.ast.nodes.Expression;
import org.eclipse.php.internal.core.typeinference.context.FileContext;
import org.eclipse.symfony.core.util.PathUtils;

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
					
					
					//System.err.println(expression.getClass());
					context.getSourceModule().getPath();
					
				}
			}			
		}		
		
		return null;
	}

}
