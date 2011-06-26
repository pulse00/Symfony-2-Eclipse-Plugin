package org.eclipse.symfony.core.goals.evaluator;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;

public class TemplateVariableGoalEvaluator extends GoalEvaluator {

	private IModelElement element;
	
	public TemplateVariableGoalEvaluator(IGoal goal, IModelElement element) {
		super(goal);
		
		this.element = element;

	}

	@Override
	public IGoal[] init() {

		return null;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {

		return IGoal.NO_GOALS;
	}

	@Override
	public Object produceResult() {

		
		if (element == null)
			return null;
		
		System.err.println(element.getParent().toString());
				

		return null;
	}

}
