/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.goals.evaluator;


import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.context.IModelCacheContext;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;

/**
 *
 *
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ServiceGoalEvaluator extends GoalEvaluator {

	private ISourceModule sourceModule;

	private final static int STATE_INIT = 0;
	private final static int STATE_WAITING_RECEIVER = 1;
	private final static int STATE_GOT_RECEIVER = 2;

	private IEvaluatedType receiverType;
	private IEvaluatedType result;

	private int state = STATE_INIT;

	private String serviceName;

	public ServiceGoalEvaluator(IGoal goal, String serviceName, ISourceModule sourceModule) {
		super(goal);
		this.sourceModule = sourceModule;
		this.serviceName = serviceName;
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

		if (state == STATE_GOT_RECEIVER) {
			if (receiverType.getTypeName().equals(SymfonyCoreConstants.CONTAINER_INTERFACE) || receiverType.getTypeName().equals(SymfonyCoreConstants.CONTROLLER_PARENT)) {
				setResult();
			} else {
				IModelAccessCache accessCache = null;
				if (goal.getContext() instanceof IModelCacheContext) {
					accessCache = ((IModelCacheContext)goal.getContext()).getCache();
				}
				try {
					IType[] types = PHPModelUtils.getTypes(receiverType.getTypeName(), sourceModule, 0, accessCache, null);
					for (IType type : types) {
						if (type.getFullyQualifiedName("\\").equals(SymfonyCoreConstants.CONTAINER_INTERFACE) || type.getFullyQualifiedName("\\").equals(SymfonyCoreConstants.CONTROLLER_PARENT)) {
							setResult();
							break;
						}
						IType[] superClasses = PHPModelUtils.getSuperClasses(type, accessCache != null ? accessCache.getSuperTypeHierarchy(type, null) : null);
						for (IType sc : superClasses) {
							if (sc.getFullyQualifiedName("\\").equals(SymfonyCoreConstants.CONTAINER_INTERFACE) || sc.getFullyQualifiedName("\\").equals(SymfonyCoreConstants.CONTROLLER_PARENT)) {
								setResult();
								return null;
							}
						}

					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}
		}

		return null;
	}

	private void setResult() {
		// TODO do it as next Goal
		Service findService = SymfonyModelAccess.getDefault().findService(serviceName,sourceModule.getScriptProject().getPath());
		if (findService != null)
			result = new PHPClassType(findService.getFullyQualifiedName());
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
