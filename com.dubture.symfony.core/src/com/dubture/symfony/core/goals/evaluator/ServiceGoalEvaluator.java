/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.goals.evaluator;


import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.context.IModelCacheContext;

import com.dubture.symfony.core.goals.ServiceTypeGoal;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;

/**
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
@SuppressWarnings("restriction")
public class ServiceGoalEvaluator extends GoalEvaluator {

	private ISourceModule sourceModule;

	private final static int STATE_INIT = 0;
	private final static int STATE_WAITING_RECEIVER = 1;
	private final static int STATE_GOT_RECEIVER = 2;
	private final static int STATE_WAITING_SERVICE_TYPE = 3;
	private final static int STATE_GOT_SERVICE_TYPE = 4;

	private IEvaluatedType receiverType;
	private IEvaluatedType result;

	private int state = STATE_INIT;

	private String serviceName;

	public ServiceGoalEvaluator(IGoal goal, String serviceName) {
		super(goal);
		this.serviceName = serviceName;
		if (goal.getContext() instanceof ISourceModuleContext) {
			sourceModule = ((ISourceModuleContext)goal.getContext()).getSourceModule();
		}
	}

	@Override
	public IGoal[] init() {
		IGoal goal = produceNextSubgoal(null, null, null);
		if (goal != null) {
			return new IGoal[] { goal };
		}
		return IGoal.NO_GOALS;
	}

	private IGoal produceNextSubgoal(IGoal previousGoal,
			IEvaluatedType previousResult, GoalState goalState) {
		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		CallExpression expression = (CallExpression) typedGoal.getExpression();
		if (sourceModule == null) {
			return null;
		}
		// just starting to evaluate method, evaluate method receiver first:
		if (state == STATE_INIT) {
			ASTNode receiver = expression.getReceiver();
			if (receiver == null) {
				state = STATE_GOT_RECEIVER;
			} else {
				state = STATE_WAITING_RECEIVER;
				return new ExpressionTypeGoal(goal.getContext(), receiver);
			}
		}

		if (state == STATE_WAITING_RECEIVER) {
			receiverType = previousResult;
			previousResult = null;
			if (receiverType == null) {
				return null;
			}
			state = STATE_GOT_RECEIVER;
		}

		if (state == STATE_GOT_RECEIVER && receiverType != null) {
			if (receiverType.getTypeName().equals(SymfonyCoreConstants.CONTAINER_INTERFACE) || receiverType.getTypeName().equals(SymfonyCoreConstants.CONTROLLER_PARENT)) {
				return generateServiceTypeGoal();
			} else {
				IModelAccessCache accessCache = null;
				if (goal.getContext() instanceof IModelCacheContext) {
					accessCache = ((IModelCacheContext)goal.getContext()).getCache();
				}
				try {
					IType[] types = PHPModelUtils.getTypes(receiverType.getTypeName(), sourceModule, 0, accessCache, null);
					for (IType type : types) {
						if (type.getFullyQualifiedName("\\").equals(SymfonyCoreConstants.CONTAINER_INTERFACE) || type.getFullyQualifiedName("\\").equals(SymfonyCoreConstants.CONTROLLER_PARENT)) {
							return generateServiceTypeGoal();
						}
						IType[] superClasses = PHPModelUtils.getSuperClasses(type, accessCache != null ? accessCache.getSuperTypeHierarchy(type, null) : null);
						for (IType sc : superClasses) {
							if (sc.getFullyQualifiedName("\\").equals(SymfonyCoreConstants.CONTAINER_INTERFACE) || sc.getFullyQualifiedName("\\").equals(SymfonyCoreConstants.CONTROLLER_PARENT)) {
								return generateServiceTypeGoal();
							}
						}

					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}
		}
		if (state == STATE_WAITING_SERVICE_TYPE) {
			result = previousResult;
			state = STATE_GOT_SERVICE_TYPE;
		}

		return null;
	}

	private IGoal generateServiceTypeGoal() {
		state = STATE_WAITING_SERVICE_TYPE;
		return new ServiceTypeGoal(goal.getContext(), serviceName);
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		IGoal goal = produceNextSubgoal(subgoal, (IEvaluatedType) result, state);
		if (goal != null) {
			return new IGoal[] { goal };
		}
		return IGoal.NO_GOALS;
	}

	@Override
	public Object produceResult() {
		return result;
	}
}
