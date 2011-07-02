package org.eclipse.symfony.core.goals;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;
import org.eclipse.symfony.core.goals.evaluator.ServiceGoalEvaluator;
import org.eclipse.symfony.core.model.Service;
import org.eclipse.symfony.core.model.SymfonyModelAccess;


/**
 * 
 * {@link ContainerAwareGoalEvaluatorFactory} is the first evaluator
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
public class ContainerAwareGoalEvaluatorFactory implements IGoalEvaluatorFactory {


	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {

		try {
			
			//TODO: find a way to check project nature
			// and return null if it's not a symfonyNature
			// goal.getContext().getLangNature() always returns the PHPNature...
			GoalEvaluator evaluator = evaluateServiceCalls(goal);
			
			//TODO: add more evaluators... 
			return evaluator;
			
		} catch (Exception e) {
			return null;
		}
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
		
		if (!(goal.getContext() instanceof MethodContext)) {
			return null;
		}
		
		// MethodContext context = (MethodContext) goal.getContext();		
		// PHPClassType classType = (PHPClassType) context.getInstanceType();
				
		
		if (goalClass == ExpressionTypeGoal.class) {
			
			ExpressionTypeGoal expGoal = (ExpressionTypeGoal) goal;			
			ASTNode expression = expGoal.getExpression();
			
			// we're inside a call expression in the form $em->| 
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
								
								Service service = SymfonyModelAccess.getDefault().findService(className);

								// we got a service match, return the goalevaluator.
								if (service != null) {
									return new ServiceGoalEvaluator(goal, service);
								}
							}							
						}
					}
				}
			}
		// we're checking a PHPDocMethodReturnTypeGoal like $em = $this->get('doctrine')->| 
		// to support fluent interfaces.			
		}  else if (goalClass == PHPDocMethodReturnTypeGoal.class) {
			
//			PHPDocMethodReturnTypeGoal mGoal = (PHPDocMethodReturnTypeGoal) goal;
//			if (mGoal.getMethodName().equals("get")) {
//				return new ContainerMethodReturnTypeEvaluator(mGoal);
//			}
		}
		
		// Give the control to the default PHP goal evaluator
		return null;
	}
}
