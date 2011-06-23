package org.eclipse.symfony.core.visitor;



import java.io.BufferedReader;
import java.io.StringReader;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.ASTNodeKinds;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayCreation;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayElement;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.ReturnStatement;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.symfony.core.SymfonyCoreConstants;
import org.eclipse.symfony.core.model.Action;
import org.eclipse.symfony.core.model.Controller;
import org.eclipse.symfony.core.model.TemplateVariable;


/**
 * 
 * The {@link ControllerVisitor} parses the AST
 * of Symfony controllers and collects TemplateVariables.
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ControllerVisitor extends PHPASTVisitor {

	private Controller controller = null;	
	private IBuildContext context;


	public ControllerVisitor(IBuildContext context) {

		this.context = context;

	}

	public Controller getController() {

		return controller;
	}


	@Override
	public boolean visit(ClassDeclaration classDeclaration) throws Exception {

		//TODO: check against fully qualified class name
		for(String superClass : classDeclaration.getSuperClassNames()) {			
			if (superClass.equals(SymfonyCoreConstants.CONTROLLER_CLASS)) {

				controller = new Controller(context.getSourceModule(), classDeclaration);
				break;
			}
		}

		return controller != null;

	}


	@Override
	public boolean visit(PHPMethodDeclaration method) throws Exception {

		if (controller == null)
			return true;

		if (method.getName().endsWith(SymfonyCoreConstants.ACTION_SUFFIX)) {

			boolean foundAnnotation = false;
			PHPDocBlock docs = method.getPHPDoc();

			if (docs != null) {

				BufferedReader buffer = new BufferedReader(new StringReader(docs.getShortDescription()));

				try {
					String line;
					while((line = buffer.readLine()) != null) {

						//TODO: parse @Template() parameters
						if (line.startsWith(SymfonyCoreConstants.TEMPLATE_ANNOTATION)) {
							foundAnnotation = true;
							break;

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}

			// check the return statement for template variables
			method.traverse(new ReturnStatementVisitor(method));


		}
		return true;
	}

	private class ReturnStatementVisitor extends PHPASTVisitor {

		private PHPMethodDeclaration method;

		public ReturnStatementVisitor(PHPMethodDeclaration method) {

			this.method = method;

		}

		@Override
		public boolean visit(ReturnStatement statement) throws Exception {

			if (statement.getExpr().getKind() == ASTNodeKinds.ARRAY_CREATION) {

				Action action = new Action(controller, method);
				ArrayCreation array = (ArrayCreation) statement.getExpr();

				for (ArrayElement element : array.getElements()) {

					//					System.err.println(element.getKey().getClass() + " " + element.getValue().getClass());

					Expression key = element.getKey();
					Expression value = element.getValue();

					if (key.getClass() == Scalar.class) {

						Scalar varName = (Scalar) key;

						if (value.getClass() == VariableReference.class) {

							VariableReference var = (VariableReference) element.getValue();						
							TemplateVariable variable = new TemplateVariable(context.getSourceModule(), varName, var);						
							action.addTemplateVariable(variable);


						} else if(value.getClass() == PHPCallExpression.class) {
							
							PHPCallExpression call = (PHPCallExpression) value;


						}
					}
				}

				controller.addAction(action);
			}	
			return false;
		}		
	}
}