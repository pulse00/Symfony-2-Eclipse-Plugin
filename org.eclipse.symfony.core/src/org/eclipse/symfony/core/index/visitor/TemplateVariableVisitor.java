package org.eclipse.symfony.core.index.visitor;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayCreation;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayElement;
import org.eclipse.php.internal.core.compiler.ast.nodes.Assignment;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassInstanceCreation;
import org.eclipse.php.internal.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.ReturnStatement;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.symfony.core.log.Logger;
import org.eclipse.symfony.core.model.Service;
import org.eclipse.symfony.core.model.SymfonyModelAccess;
import org.eclipse.symfony.core.model.TemplateVariable;
import org.eclipse.symfony.core.preferences.SymfonyCoreConstants;
import org.eclipse.symfony.core.util.AnnotationUtils;
import org.eclipse.symfony.core.util.ModelUtils;
import org.eclipse.symfony.core.util.PathUtils;
import org.eclipse.symfony.index.dao.Route;


/**
 * 
 * The {@link TemplateVariableVisitor} indexes collects
 * templateVariables in Symfony2 controller classes.  
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class TemplateVariableVisitor extends PHPASTVisitor {

	// The templatevariables with their corresponding ViewPath
	private Map<TemplateVariable, String> templateVariables = new HashMap<TemplateVariable, String>();
	
	// The variables found during method parsing
	private Stack<TemplateVariable> deferredVariables = new Stack<TemplateVariable>();

	private PHPMethodDeclaration currentMethod;
	
	final private List<UseStatement> useStatements;
	final private NamespaceDeclaration namespace;
	
	private boolean inAction = false;

	private String currentAnnotationPath;

	private Stack<Route> routes = new Stack<Route>();

	final private String bundle;

	private String controller;

	public TemplateVariableVisitor(List<UseStatement> useStatements, NamespaceDeclaration namespace) {

		this.namespace = namespace;
		this.useStatements = useStatements;
		
		bundle = ModelUtils.extractBundleName(namespace);

		
	}


	public Map<TemplateVariable, String> getTemplateVariables() {
		return templateVariables;
	}


	/**
	 * 
	 * Visit a {@link PHPMethodDeclaration} and check
	 * if it's an Action.
	 * 
	 */
	@Override
	public boolean visit(PHPMethodDeclaration method) throws Exception {

		currentMethod = method;
		deferredVariables = new Stack<TemplateVariable>();
		controller = currentMethod.getDeclaringTypeName().replace(SymfonyCoreConstants.CONTROLLER_CLASS, "");

		if (method.getName().endsWith(SymfonyCoreConstants.ACTION_SUFFIX)) {

			inAction = true;
			PHPDocBlock docs = method.getPHPDoc();

			if (docs != null) {

				//TODO: use the AnnotationParser for this instead
				BufferedReader buffer = new BufferedReader(new StringReader(docs.getShortDescription()));

				try {
					String line;
					while((line = buffer.readLine()) != null) {

						if (line.startsWith(SymfonyCoreConstants.TEMPLATE_ANNOTATION)) {
							parseTemplateAnnotation(line);							
						} else if (line.startsWith(SymfonyCoreConstants.ROUTE_ANNOTATION)) {							
							processRoute(line);
						}
						
					}
				} catch (Exception e) {
					Logger.logException(e);
				}
			}
		}

		return true;
	}
	
	/**
	 * 
	 * Parse a route from the annotation and 
	 * push it to the route stack for later indexing.
	 * 
	 * @param annotation
	 */
	private void processRoute(String annotation) {

		String action = currentMethod.getName().replace(SymfonyCoreConstants.ACTION_SUFFIX, "");
		Route route = AnnotationUtils.getRoute(annotation, bundle, controller, action);
		
		if (route != null) {
			routes.push(route);
		}		
	}


	private void parseTemplateAnnotation(String annotation) {
		
		int start = -1;
		int end = -1;
		
		if ((start = annotation.indexOf("\"")) > -1) {								
			end = annotation.lastIndexOf("\"");								
		} else if ( (start = annotation.indexOf("'")) > -1) {								
			end = annotation.lastIndexOf("'");								
		}
		

		if (start > -1 && end > -1) {								
			String path = annotation.substring(start, end+1);
			currentAnnotationPath = PathUtils.createViewPath(path);
		}
		
	}

	@Override
	public boolean endvisit(PHPMethodDeclaration s) throws Exception {

		currentAnnotationPath = null;
		deferredVariables = null;
		currentMethod = null;
		inAction = false;
		return true;
	}


	/**
	 * Parse {@link ReturnStatement}s and try to evaluate
	 * the variables.
	 * 
	 */	
	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(ReturnStatement statement) throws Exception {

		// we're inside an action, find the template variables
		if (inAction) {			

			// the simplest case:
			// return array('foo' => $bar);
			if (statement.getExpr().getClass() == ArrayCreation.class) {
			
				if (namespace != null) {

					String viewPath = null;
					
					if (currentAnnotationPath != null) {
						viewPath = currentAnnotationPath;
						
					} else {
						
						String template = currentMethod.getName().replace(SymfonyCoreConstants.ACTION_SUFFIX, "");
						viewPath = String.format("%s:%s:%s", bundle, controller, template);
						
					}
										
					if (viewPath != null ) {						
						
						ArrayCreation array = (ArrayCreation) statement.getExpr();
						parseVariablesFromArray(array, viewPath);						
					}
				}
				
			// a render call:
			// return return $this->render("DemoBundle:Test:index.html.twig", array('foo' => $bar));
			} else if (statement.getExpr().getClass() == PHPCallExpression.class) {
				
				PHPCallExpression expression = (PHPCallExpression) statement.getExpr();
				String callName = expression.getCallName().getName();
				
				if (callName.startsWith(SymfonyCoreConstants.RENDER_PREFIX)) {
				
					CallArgumentsList args = expression.getArgs();
					List<Object> children = args.getChilds();
					
					Scalar view = (Scalar) children.get(0);
					
					if (children.get(1).getClass() == ArrayCreation.class) {
						parseVariablesFromArray((ArrayCreation) children.get(1), PathUtils.createViewPath(view));
					}					
				}
			}
		}

		return true;
	}		


	/**
	 * 
	 * Parse the TemplateVariables from the given {@link ReturnStatement}
	 * @param viewPath 
	 * @param statement
	 */
	private void parseVariablesFromArray(ArrayCreation array, String viewPath) {


		for (ArrayElement element : array.getElements()) {

			
			Expression key = element.getKey();
			Expression value = element.getValue();

			if (key.getClass() == Scalar.class) {

				Scalar varName = (Scalar) key;
				
				// something in the form:  return array ('foo' => $bar);
				// check the type of $bar:
				if (value.getClass() == VariableReference.class) {

					VariableReference ref = (VariableReference) value;

					for (TemplateVariable variable : deferredVariables) {

						// we got the variable, add it the the templateVariables
						if (ref.getName().equals(variable.getName())) {								
							// alter the variable name
														
							variable.setName(varName.getValue());							
							
							templateVariables.put(variable, viewPath);
							break;
						}							
					}

					// this is more complicated, something like:
					// return array('form' => $form->createView());
					// we need to infer $form and then check the returntype of createView()
				} else if(value.getClass() == PHPCallExpression.class) {

					PHPCallExpression callExp = (PHPCallExpression) value;
					VariableReference varRef = (VariableReference) callExp.getReceiver();

					if (varRef == null) {
						continue;
					}

					SimpleReference callName = callExp.getCallName();

					// we got the variable name (in this case $form)
					// now search for the defferedVariable:						
					for (TemplateVariable deferred : deferredVariables) {

						// we got it, find the returntype of the
						// callExpression
						if (deferred.getName().equals(varRef.getName())) {

							TemplateVariable tempVar = SymfonyModelAccess.getDefault()
									.createTemplateVariableByReturnType(currentMethod, 
											callName, deferred.getClassName(), deferred.getNamespace(), 
											varRef.getName());

							templateVariables.put(tempVar, viewPath);
							break;
						}
					}

					// this is a direct ClassInstanceCreation, ie:
					// return array('user' => new User());
				} else if (value.getClass() == ClassInstanceCreation.class) {

					ClassInstanceCreation instance = (ClassInstanceCreation) value;

					if (instance.getClassName().getClass() == FullyQualifiedReference.class) {

						FullyQualifiedReference fqcn = (FullyQualifiedReference) instance.getClassName();
						NamespaceReference nsRef = createFromFQCN(fqcn);

						if (nsRef != null) {
							TemplateVariable variable = new TemplateVariable(currentMethod, varName.getValue(), 
									varName.sourceStart(), varName.sourceEnd(), nsRef.getNamespace(), nsRef.getClassName());
							templateVariables.put(variable, viewPath);
						}
					}
				} else {

					Logger.debugMSG("array value: " + value.getClass());
				}
			}
		}
	}


	/**
	 * 
	 * Collect all Assignments inside a {@link PHPMethodDeclaration}
	 * to infer them in the ReturnStatements and add it to the
	 * templateVariables.
	 * 
	 */
	@Override
	public boolean visit(Assignment s) throws Exception {

		if (inAction) {

			Service service = null;
			if (s.getVariable().getClass() == VariableReference.class) {

				VariableReference var = (VariableReference) s.getVariable();		

				// A call expression like $foo = $this->get('bar');
				//
				if (s.getValue().getClass() == PHPCallExpression.class) {

					PHPCallExpression exp = (PHPCallExpression) s.getValue();

					// are we requesting a Service?
					if (exp.getName().equals("get")) {

						service = ModelUtils.extractServiceFromCall(exp);

						if (service != null) {
							TemplateVariable tempVar= new TemplateVariable(currentMethod, var.getName(), exp.sourceStart(), exp.sourceEnd(), service.getNamespace(), service.getClassName());							
							deferredVariables.push(tempVar);
						}

						// a more complex expression like
						// $form = $this->get('form.factory')->create(new ContactType());
					} else if (exp.getReceiver().getClass() == PHPCallExpression.class) {

						// try to extract a service if it's a Servicecontainer call
						service = ModelUtils.extractServiceFromCall((PHPCallExpression) exp.getReceiver());

						// nothing found, return
						if (service == null || exp.getCallName() == null) {
							return true;
						}

						SimpleReference callName = exp.getCallName();

						//TODO: this is a problematic case, as during a clean build
						// it's possible that the SourceModule in which the 
						// called method was declared is not yet in the index, so
						// the return type cannot be evaluated and therefore
						// the templatevariable won't be created...
						//
						// Possible solution: check if there's an event fired when the
						// build is completed and store those return types in a global
						// singleton, evaluate them when the whole build process is finished.
						TemplateVariable tempVar = SymfonyModelAccess.getDefault()
								.createTemplateVariableByReturnType(currentMethod, callName, 
										service.getClassName(), service.getNamespace(), var.getName());

						if (tempVar != null) {								
														
							deferredVariables.push(tempVar);
						}

						// something like $formView = $form->createView(); 
					} else if (exp.getReceiver().getClass() == VariableReference.class) {

						VariableReference varRef = (VariableReference) exp.getReceiver();
						SimpleReference ref = exp.getCallName();

						// check for a previosly declared variable
						for (TemplateVariable tempVar : deferredVariables) {							
							if (tempVar.getName().equals(varRef.getName())) {

								TemplateVariable tVar = SymfonyModelAccess.getDefault()
										.createTemplateVariableByReturnType(currentMethod, ref, tempVar.getClassName(), tempVar.getNamespace(), var.getName());

								if (tVar != null) {
									deferredVariables.push(tVar);
									break;
								}								
							}							
						}
					}
					// a simple ClassInstanceCreation, ie. $contact = new ContactType();
				} else if (s.getValue().getClass() == ClassInstanceCreation.class) {

					ClassInstanceCreation instance = (ClassInstanceCreation) s.getValue();

					if (instance.getClassName().getClass() == FullyQualifiedReference.class) {

						FullyQualifiedReference fqcn = (FullyQualifiedReference) instance.getClassName();
						NamespaceReference nsRef = createFromFQCN(fqcn);

						if (nsRef != null) {
							TemplateVariable variable = new TemplateVariable(currentMethod, var.getName(), 
									var.sourceStart(), var.sourceEnd(), nsRef.getNamespace(), nsRef.getClassName());
							deferredVariables.push(variable);
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * Get the ClassName and Namespace from a {@link FullyQualifiedReference}
	 * 
	 * @param fqcn
	 * @return
	 */
	private NamespaceReference createFromFQCN(FullyQualifiedReference fqcn) {

		for (UseStatement use : useStatements) {
			for (UsePart part : use.getParts()) {					
				if (part.getNamespace().getName().equals(fqcn.getName())) {

					String name = fqcn.getName();
					String qualifier = part.getNamespace().getNamespace().getName();

					return new NamespaceReference(qualifier, name);
				}
			}								
		}

		return null;
	}


	/**
	 * Simple helper class to pass around namespaces.
	 */
	private class NamespaceReference {		

		private String namespace;
		private String className;

		public NamespaceReference(String qualifier, String name) {

			this.namespace = qualifier;
			this.className = name;
		}

		public String getNamespace() {
			return namespace;
		}

		public String getClassName() {
			return className;
		}

	}


	public Stack<Route> getRoutes() {

		return routes;
	}
}