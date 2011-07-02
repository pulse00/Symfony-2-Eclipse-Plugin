package org.eclipse.symfony.core.goals.evaluator;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IParameter;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.evaluators.phpdoc.PHPDocMethodReturnTypeEvaluator;
import org.eclipse.symfony.core.Logger;

/**
 *  
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ContainerMethodReturnTypeEvaluator extends
		PHPDocMethodReturnTypeEvaluator {

	public ContainerMethodReturnTypeEvaluator(IGoal goal) {
		super(goal);

	}
	
	@Override
	public IGoal[] init() {
				
		for (IMethod method : getMethods()) {
					
			try {
				if (method.getParameters().length == 1) {
					IParameter[] params = method.getParameters();					
					IParameter param = params[0];
				}
			} catch (ModelException e) {
			
				Logger.logException(e);
			}
		}
		
		return IGoal.NO_GOALS;
	}
	

	@Override
	public Object produceResult() {

		return super.produceResult();
	}
	
	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
	
		return IGoal.NO_GOALS;
	
	}
}