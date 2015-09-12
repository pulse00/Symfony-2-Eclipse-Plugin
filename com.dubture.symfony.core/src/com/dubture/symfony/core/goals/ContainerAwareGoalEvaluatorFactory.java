/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.goals;

import org.eclipse.core.resources.IProjectNature;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;

import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.core.goals.evaluator.DoctrineManagerGoalEvaluator;
import com.dubture.symfony.core.goals.evaluator.ServiceGoalEvaluator;
import com.dubture.symfony.core.goals.evaluator.ServiceTypeGoalEvaluator;

/**
 *
 * {@link ContainerAwareGoalEvaluatorFactory} is the first evaluator
 *
 * to add code hints in actual php code. At the moment it evaluates service
 * goals, ie $em-> | where $em is a service retrieved from the DI container.
 *
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ContainerAwareGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {

		try {

			if (goal.getContext() instanceof ISourceModuleContext) {
				ISourceModuleContext context = (ISourceModuleContext) goal.getContext();
				IProjectNature nature = context.getSourceModule().getScriptProject().getProject()
						.getNature(SymfonyNature.NATURE_ID);
				if (!(nature instanceof SymfonyNature)) {
					return null;
				}
			} else {
				return null;
			}
			// TODO: add more evaluators...
			return evaluateServiceCalls(goal);

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Evaluates goals for
	 *
	 * <pre>
	 *     $em = $this->get('doctrine');
	 *  $em |
	 * </pre>
	 *
	 * @param goal
	 * @return
	 */
	private GoalEvaluator evaluateServiceCalls(IGoal goal) {

		Class<?> goalClass = goal.getClass();

		if (!(goal.getContext() instanceof MethodContext)) {
			return null;
		}

		// MethodContext context = (MethodContext) goal.getContext();
		// PHPClassType classType = (PHPClassType) context.getInstanceType();
		if (goalClass == PHPDocMethodReturnTypeGoal.class) {
			PHPDocMethodReturnTypeGoal g = (PHPDocMethodReturnTypeGoal) goal;
			if (g.getArgNames() != null && g.getArgNames().length > 0 && g.getArgNames()[0] != null) {
				if (g.getMethodName().equals("get")) {
					return new ServiceGoalEvaluator(g, ASTUtils.stripQuotes(g.getArgNames()[0]));
				} else if (g.getMethodName().equals("getManager")) {
					return new DoctrineManagerGoalEvaluator(g, ASTUtils.stripQuotes(g.getArgNames()[0]));
				}
			} else if (g.getMethodName().equals("getManager")) {
				return new DoctrineManagerGoalEvaluator(g, null);
			}
		} else if (goalClass == ServiceTypeGoal.class) {
			return new ServiceTypeGoalEvaluator((ServiceTypeGoal) goal);
		}

		// Give the control to the default PHP goal evaluator
		return null;
	}
}
