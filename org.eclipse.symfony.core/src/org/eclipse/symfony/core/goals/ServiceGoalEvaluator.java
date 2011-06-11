package org.eclipse.symfony.core.goals;


import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.symfony.core.model.Service;

/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ServiceGoalEvaluator extends GoalEvaluator {

	private Service service;
	
	public ServiceGoalEvaluator(IGoal goal) {
		super(goal);

	}

	public ServiceGoalEvaluator(IGoal goal, Service service) {
		super(goal);
		this.service = service;
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

		if (service == null)
			return null;
				
		return new PHPClassType(service.getFullyQualifiedName());
		
	}
}