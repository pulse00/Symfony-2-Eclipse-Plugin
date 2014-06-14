/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.goals.evaluator;


import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.context.IModelCacheContext;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;

import com.dubture.symfony.core.goals.ServiceTypeGoal;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;

/**
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
@SuppressWarnings("restriction")
public class DoctrineManagerGoalEvaluator extends GoalEvaluator {

	private final static int STATE_INIT = 0;
	private final static int STATE_GOT_RECEIVER = 2;
	private final static int STATE_WAITING_SERVICE_TYPE = 3;
	private final static int STATE_GOT_SERVICE_TYPE = 4;
	
	private final static String SLASH = "\\"; //$NON-NLS-1$
	private final static String ENTITY_MANAGER_PREFIX = "doctrine.orm."; //$NON-NLS-1$
	private final static String ENTITY_MANAGER_SUFIX = "_entity_manger"; //$NON-NLS-1$

	private IEvaluatedType result;

	private int state = STATE_INIT;

	private String managerName;

	public DoctrineManagerGoalEvaluator(IGoal goal, String managerName) {
		super(goal);
		this.managerName = managerName;
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
		MethodElementReturnTypeGoal typedGoal = (MethodElementReturnTypeGoal) goal;
		// just starting to evaluate method, evaluate method receiver first:
		if (state == STATE_INIT) {
			if(typedGoal.getTypes() == null || typedGoal.getTypes().length < 1) {
				return null;
			}
			state = STATE_GOT_RECEIVER;
		}

		if (state == STATE_GOT_RECEIVER) {
			IModelAccessCache accessCache = null;
			if (goal.getContext() instanceof IModelCacheContext) {
				accessCache = ((IModelCacheContext)goal.getContext()).getCache();
			}
			try {
				IType[] types = typedGoal.getTypes();
				for (IType type : types) {
					String fq = type.getFullyQualifiedName(SLASH);
					IGoal result = checkName(fq);
					if (result != null) {
						return result;
					}
					
					IType[] superClasses = PHPModelUtils.getSuperClasses(type, accessCache != null ? accessCache.getSuperTypeHierarchy(type, null) : null);
					for (IType sc : superClasses) {
						result = checkName(sc.getFullyQualifiedName(SLASH));
						if (result != null) {
							return result;
						}
					}

				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}
		if (state == STATE_WAITING_SERVICE_TYPE) {
			result = previousResult;
			state = STATE_GOT_SERVICE_TYPE;
		}

		return null;
	}
	
	private IGoal checkName(String fq) {
		if (fq.equals(SymfonyCoreConstants.DOCTRINE_REGISTRY_NAME)) {
			return generateRegistryTypeGoal();
		}
		
		return null;
	}

	private IGoal generateRegistryTypeGoal() {
		state = STATE_WAITING_SERVICE_TYPE;
		return new ServiceTypeGoal(goal.getContext(), ENTITY_MANAGER_PREFIX + managerName + ENTITY_MANAGER_SUFIX);
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
