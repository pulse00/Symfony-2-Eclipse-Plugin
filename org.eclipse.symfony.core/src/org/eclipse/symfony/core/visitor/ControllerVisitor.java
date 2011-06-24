package org.eclipse.symfony.core.visitor;



import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Iterator;

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
			method.traverse(new TemplateVariableVisitor(method));


		}
		return true;
	}

	private class TemplateVariableVisitor extends ServiceContainerVisitor {

		private PHPMethodDeclaration method;

		public TemplateVariableVisitor(PHPMethodDeclaration method) {

			this.method = method;

		}
		

		
		@Override
		@SuppressWarnings("rawtypes")
		public boolean visit(ReturnStatement statement) throws Exception {

			if (statement.getExpr().getKind() == ASTNodeKinds.ARRAY_CREATION) {

				Action action = new Action(controller, method);
				ArrayCreation array = (ArrayCreation) statement.getExpr();

				for (ArrayElement element : array.getElements()) {

					Expression key = element.getKey();
					Expression value = element.getValue();
					
					if (key.getClass() == Scalar.class) {

						Scalar varName = (Scalar) key;

						if (value.getClass() == VariableReference.class) {

							TemplateVariable variable = new TemplateVariable(context.getSourceModule(), varName.getValue().replace("\"", "").replace("'", ""));						
							action.addTemplateVariable(variable);

						} else if(value.getClass() == PHPCallExpression.class) {

							Iterator it = services.keySet().iterator();
							
							while(it.hasNext()) {
								
								String var = (String) it.next();
								
								String scalar = varName.getValue().replace("\"", "").replace("'", "");
								var = var.replace("$", "");
								
								if (var.equals(scalar)) {
									
									System.err.println("create template variable");
									TemplateVariable variable = new TemplateVariable(context.getSourceModule(), var);
									action.addTemplateVariable(variable);
								} else {
									System.out.println("no scalar " + var + " " + scalar);
								}
							}
						}
					}
				}

				controller.addAction(action);
			}	
			return false;
		}		
	}
}