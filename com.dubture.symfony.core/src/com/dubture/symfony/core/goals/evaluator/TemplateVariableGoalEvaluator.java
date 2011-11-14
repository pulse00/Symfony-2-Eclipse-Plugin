/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.goals.evaluator;

import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

import com.dubture.symfony.core.index.SymfonyElementResolver.TemplateField;


/**
 * 
 * Evaluates templatevariables.
 * 
 * $form->|  <-- evalutates to Symfony\Component\Form\Form 
 * if it's instantiated in the corresponding controller
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class TemplateVariableGoalEvaluator extends GoalEvaluator {

	private TemplateField element;
	
	public TemplateVariableGoalEvaluator(IGoal goal, TemplateField element) {
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
		
		return new PHPClassType(element.getQualifier(), element.getClassName());
	}
}
