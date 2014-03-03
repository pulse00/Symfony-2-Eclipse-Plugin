/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.goals;

import java.util.List;

import org.eclipse.core.resources.IProjectNature;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.goals.MethodReturnTypeGoal;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;

import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.core.goals.evaluator.ContainerMethodReturnTypeEvaluator;
import com.dubture.symfony.core.goals.evaluator.ServiceGoalEvaluator;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.SymfonyModelAccess;


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


    private IGoal goal;
    private MethodContext context;


    @Override
    public GoalEvaluator createEvaluator(IGoal goal) {

        try {

            this.goal = goal;
            if (goal.getContext() instanceof ISourceModuleContext) {
            	ISourceModuleContext context = (ISourceModuleContext) goal.getContext();
            	IProjectNature nature = context.getSourceModule().getScriptProject().getProject().getNature(SymfonyNature.NATURE_ID);
            	if(!(nature instanceof SymfonyNature)) {
					return null;
				}
            } else {
            	return null;
            }
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
    *     $em = $this->get('doctrine');
    *  $em |
    * </pre>
    *
    * @param goal
    * @return
    */
    private GoalEvaluator evaluateServiceCalls(IGoal goal) {

        Class<?>goalClass = goal.getClass();

        if (!(goal.getContext() instanceof MethodContext)) {
            return null;
        }

        context = (MethodContext) goal.getContext();

        // MethodContext context = (MethodContext) goal.getContext();
        // PHPClassType classType = (PHPClassType) context.getInstanceType();


        if (goalClass == ExpressionTypeGoal.class) {


            ExpressionTypeGoal expGoal = (ExpressionTypeGoal) goal;
            ASTNode expression = expGoal.getExpression();


            // we're inside a call expression in the form $em->|
            if (expression instanceof PHPCallExpression) {
                PHPCallExpression exp = (PHPCallExpression) expression;

                // are we calling a method named "get" ?
                if (exp.getName().equals("get")) {
                	return getEvaluator(exp);
                }
            }
        // we're checking a PHPDocMethodReturnTypeGoal like $em = $this->get('doctrine')->|
        // to support fluent interfaces.
        }

        // Give the control to the default PHP goal evaluator
        return null;
    }


    @SuppressWarnings("rawtypes")
    private ServiceGoalEvaluator getEvaluator(PHPCallExpression exp) {

        List args = exp.getArgs().getChilds();

        // does the get() method have minimum one argument?
        if (args.size() >= 1) {

            Object first = args.get(0);
            // TODO resolve quotes
            if (first instanceof Scalar && ((Scalar)first).getScalarType() == Scalar.TYPE_STRING) {

                String serviceName = ASTUtils.stripQuotes(((Scalar)first).getValue());
                //Service service = SymfonyModelAccess.getDefault().findService(className,context.getSourceModule().getScriptProject().getPath());

                // we got a service match, return the goalevaluator.
                if (serviceName != null) {
                    return new ServiceGoalEvaluator(goal, serviceName, context.getSourceModule());
                }
            }
        }

        return null;
    }
}
