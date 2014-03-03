/*******************************************************************************

 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Dawid Pakuła <zulus@w3des.net>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.goals.evaluator;

import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.evaluators.AbstractPHPGoalEvaluator;

import com.dubture.symfony.core.goals.ServiceTypeGoal;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.SymfonyModelAccess;

@SuppressWarnings("restriction")
public class ServiceTypeGoalEvaluator extends AbstractPHPGoalEvaluator {
	protected ServiceTypeGoal goal;
	private IEvaluatedType result = null;

	public ServiceTypeGoalEvaluator(ServiceTypeGoal goal) {
		super(goal);
		this.goal = goal;

	}

	@Override
	public IGoal[] init() {
		if (goal.getContext() instanceof ISourceModuleContext) {
			Service findService = SymfonyModelAccess.getDefault().findService(goal.getServiceId(),((ISourceModuleContext)goal.getContext()).getSourceModule().getScriptProject().getPath());
			if (findService != null)
				result  = new PHPClassType(findService.getFullyQualifiedName());
		}
		return IGoal.NO_GOALS;
	}

	@Override
	public Object produceResult() {
		return result;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}
}
