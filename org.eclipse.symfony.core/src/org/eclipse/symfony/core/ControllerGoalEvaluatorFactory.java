package org.eclipse.symfony.core;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.symfony.core.goal.ServiceGoalEvaluator;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.Service;


/**
 * 
 * {@link ControllerGoalEvaluatorFactory} is the first evaluator
 * 
 * to add code hints in actual php code. At the moment it evaluates
 * service goals, ie $em-> | where $em is a service retrieved from the
 * DI container.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ControllerGoalEvaluatorFactory implements IGoalEvaluatorFactory {


	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {

		GoalEvaluator evaluator = evaluateServiceCalls(goal);
		
		//TODO: add more evaluators... 
		return evaluator;

	}


	
	/**
	 * Evaluates goals for
	 * 
	 * <pre>
	 * 	$em = $this->get('doctrine');
	 *  $em | 
	 * </pre>
	 * 
	 * @param goal
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private GoalEvaluator evaluateServiceCalls(IGoal goal) {

		Class<?>goalClass = goal.getClass();

		if (goalClass == ExpressionTypeGoal.class) {

			ASTNode expression = ((ExpressionTypeGoal) goal).getExpression();

			// only call expressions ($this->foo) need to be evaluated for services
			if (expression instanceof PHPCallExpression) {

				PHPCallExpression exp = (PHPCallExpression) expression;
				ASTNode receiver = exp.getReceiver();

				// are we calling a method named "get" ?
				if (exp.getName().equals("get") && receiver instanceof VariableReference) {

					VariableReference ref = (VariableReference) receiver;

					// is the receiver an object instance ?
					if (ref.getName().equals("$this")) {

						List args = exp.getArgs().getChilds();

						// does the get() method have exact one argument?
						if (args.size() == 1) {

							Object first = args.get(0);

							if (first instanceof Scalar && ((Scalar)first).getScalarType() == Scalar.TYPE_STRING) {

								//TODO: check if there are PDT utils for stripping away quotes from
								// string literals.
								String className = ((Scalar)first).getValue().replace("'", "").replace("\"", "");
								Service service = ModelManager.getInstance().getService(className);

								// we got a service match, return the goalevaluator.
								if (service != null) {
									return new ServiceGoalEvaluator(goal, service);
								}
							}							
						}
					}
				}
			}
		}
		return null;
	}

}
