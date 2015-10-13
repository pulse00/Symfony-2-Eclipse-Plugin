/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.index.visitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayCreation;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayElement;
import org.eclipse.php.internal.core.compiler.ast.nodes.Assignment;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassInstanceCreation;
import org.eclipse.php.internal.core.compiler.ast.nodes.FormalParameter;
import org.eclipse.php.internal.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.ReturnStatement;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.internal.core.model.PerFileModelAccessCache;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.wst.sse.core.utils.StringUtils;

import com.dubture.doctrine.annotation.model.Annotation;
import com.dubture.doctrine.core.AnnotationParserUtil;
import com.dubture.doctrine.core.compiler.IAnnotationModuleDeclaration;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.TemplateVariable;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;
import com.dubture.symfony.core.util.AnnotationUtils;
import com.dubture.symfony.core.util.ModelUtils;
import com.dubture.symfony.core.util.PathUtils;
import com.dubture.symfony.index.model.Route;

/**
 *
 * The {@link TemplateVariableVisitor} indexes collects templateVariables in
 * Symfony2 controller classes.
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

	private ISourceModule source;
	private IModelAccessCache cache;
	private IAnnotationModuleDeclaration annotationModule;

	public TemplateVariableVisitor(List<UseStatement> useStatements, NamespaceDeclaration namespace, ISourceModule source) {
		this.namespace = namespace;
		this.useStatements = useStatements;

		this.source = source;
		try {
			this.annotationModule = AnnotationParserUtil.getModule(source);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		this.cache = new PerFileModelAccessCache(source);

		bundle = ModelUtils.extractBundleName(namespace);
	}

	public Map<TemplateVariable, String> getTemplateVariables() {
		return templateVariables;
	}

	/**
	 *
	 * Visit a {@link PHPMethodDeclaration} and check if it's an Action.
	 *
	 */
	@Override
	public boolean visit(PHPMethodDeclaration methodDeclaration) throws Exception {

		currentMethod = methodDeclaration;
		deferredVariables = new Stack<TemplateVariable>();
		controller = currentMethod.getDeclaringTypeName().replace(SymfonyCoreConstants.CONTROLLER_CLASS, "");
		inAction = methodDeclaration.getName().endsWith(SymfonyCoreConstants.ACTION_SUFFIX);

		if (!inAction) {
			return false;
		}

		String action = currentMethod.getName().replace(SymfonyCoreConstants.ACTION_SUFFIX, "");
		if (annotationModule != null) {
			List<Annotation> annotations = annotationModule.readAnnotations((ASTNode)methodDeclaration).getAnnotations();
			for (Annotation annotation : annotations) {
				String className = annotation.getClassName();

				if (className.startsWith(SymfonyCoreConstants.TEMPLATE_ANNOTATION)) {
					currentAnnotationPath = AnnotationUtils.extractTemplate(annotation, bundle, controller, action);
				} else if (className.startsWith(SymfonyCoreConstants.ROUTE_ANNOTATION)) {
					Route route = AnnotationUtils.extractRoute(annotation, bundle, controller, action);
					routes.push(route);
				}
			}
		}

		for (Object argument : currentMethod.getArguments()) {
			/* public function (Form $form) { } */
			if (argument instanceof FormalParameter) {
				FormalParameter param = (FormalParameter) argument;

				if (param.getParameterType() instanceof FullyQualifiedReference) {

					FullyQualifiedReference ref = (FullyQualifiedReference) param.getParameterType();
					NamespaceReference nsRef = createFromFQCN(ref);

					if (nsRef != null) {
						TemplateVariable tempVar = new TemplateVariable(currentMethod, param.getName(), param.sourceStart(), param.sourceEnd(), nsRef.namespace,
								nsRef.className);
						deferredVariables.push(tempVar);
					}

					/* public function ($somevar) { } */
				} else {
					TemplateVariable tempVar = new TemplateVariable(currentMethod, param.getName(), param.sourceStart(), param.sourceEnd(), null, null);
					deferredVariables.push(tempVar);
				}
			}
		}

		return true;
	}

	@Override
	public boolean endvisit(PHPMethodDeclaration s) throws Exception {
		currentAnnotationPath = null;
		deferredVariables = null;
		currentMethod = null;
		inAction = false;
		return true;
	}

	public boolean endvisit(PHPCallExpression s) throws Exception {

		if (!s.getName().startsWith("render"))
			return false;

		CallArgumentsList list = s.getArgs();

		if (list.getChilds().size() > 1) {

			if (list.getChilds().get(0) instanceof Scalar && list.getChilds().get(1) instanceof ArrayCreation) {

				Scalar scalar = (Scalar) list.getChilds().get(0);
				String viewPath = StringUtils.stripQuotes(scalar.getValue());
				ArrayCreation params = (ArrayCreation) list.getChilds().get(1);
				parseVariablesFromArray(params, viewPath);

			}
		}

		return true;
	}

	@Override
	public boolean visit(PHPCallExpression s) throws Exception {
		return true;
	}

	/**
	 * Parse {@link ReturnStatement}s and try to evaluate the variables.
	 *
	 */
	@Override
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

					if (viewPath != null) {

						ArrayCreation array = (ArrayCreation) statement.getExpr();
						parseVariablesFromArray(array, viewPath);
					}
				}

				// a render call:
				// return return
				// $this->render("DemoBundle:Test:index.html.twig", array('foo'
				// => $bar));
			} else if (statement.getExpr().getClass() == PHPCallExpression.class) {

				PHPCallExpression expression = (PHPCallExpression) statement.getExpr();
				String callName = expression.getCallName().getName();

				if (callName.startsWith(SymfonyCoreConstants.RENDER_PREFIX)) {

					CallArgumentsList args = expression.getArgs();
					List<ASTNode> children = args.getChilds();

					Scalar view = (Scalar) children.get(0);

					if (children.size() >= 2 && children.get(1).getClass() == ArrayCreation.class) {
						parseVariablesFromArray((ArrayCreation) children.get(1), PathUtils.createViewPath(view));
					} else {
						Logger.log(Logger.WARNING, "Unable to parse view variable from " + children.toString());
					}
				}
			}
		}

		return true;
	}

	/**
	 *
	 * Parse the TemplateVariables from the given {@link ReturnStatement}
	 * 
	 * @param viewPath
	 * @param statement
	 */
	private void parseVariablesFromArray(ArrayCreation array, String viewPath) {

		for (ArrayElement element : array.getElements()) {

			Expression key = element.getKey();
			Expression value = element.getValue();

			if (key.getClass() == Scalar.class) {

				Scalar varName = (Scalar) key;

				// something in the form: return array ('foo' => $bar);
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
					// we need to infer $form and then check the returntype of
					// createView()
				} else if (value.getClass() == PHPCallExpression.class) {

					PHPCallExpression callExp = (PHPCallExpression) value;

					VariableReference varRef = null;
					try {
						varRef = (VariableReference) callExp.getReceiver();
					} catch (ClassCastException e) {
						Logger.log(Logger.WARNING,
								callExp.getReceiver().getClass().toString() + " could not be cast to VariableReference in " + currentMethod.getName());

					}

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

							TemplateVariable tempVar = SymfonyModelAccess.getDefault().createTemplateVariableByReturnType(source, currentMethod, callName,
									deferred.getClassName(), deferred.getNamespace(), varRef.getName(), cache);

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
							TemplateVariable variable = new TemplateVariable(currentMethod, varName.getValue(), varName.sourceStart(), varName.sourceEnd(),
									nsRef.getNamespace(), nsRef.getClassName());
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
	 * Collect all Assignments inside a {@link PHPMethodDeclaration} to infer
	 * them in the ReturnStatements and add it to the templateVariables.
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

						service = ModelUtils.extractServiceFromCall(exp, source.getScriptProject().getPath());

						if (service != null) {

							String fqsn = service.getNamespace() != null ? service.getNamespace().getQualifiedName() : null;

							TemplateVariable tempVar = new TemplateVariable(currentMethod, var.getName(), exp.sourceStart(), exp.sourceEnd(), fqsn,
									service.getClassName());
							deferredVariables.push(tempVar);
						}

						// a more complex expression like
						// $form = $this->get('form.factory')->create(new
						// ContactType());
					} else if (exp.getReceiver().getClass() == PHPCallExpression.class) {

						// try to extract a service if it's a Servicecontainer
						// call
						service = ModelUtils.extractServiceFromCall((PHPCallExpression) exp.getReceiver(), source.getScriptProject().getPath());

						// nothing found, return
						if (service == null || exp.getCallName() == null) {
							return true;
						}

						SimpleReference callName = exp.getCallName();

						// TODO: this is a problematic case, as during a clean
						// build
						// it's possible that the SourceModule in which the
						// called method was declared is not yet in the index,
						// so
						// the return type cannot be evaluated and therefore
						// the templatevariable won't be created...
						//
						// Possible solution: check if there's an event fired
						// when the
						// build is completed and store those return types in a
						// global
						// singleton, evaluate them when the whole build process
						// is finished.

						String fqsn = service.getNamespace() != null ? service.getNamespace().getQualifiedName() : null;
						TemplateVariable tempVar = null;

						tempVar = SymfonyModelAccess.getDefault().createTemplateVariableByReturnType(source, currentMethod, callName, service.getClassName(),
								fqsn, var.getName(), cache);

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

								TemplateVariable tVar = SymfonyModelAccess.getDefault().createTemplateVariableByReturnType(source, currentMethod, ref,
										tempVar.getClassName(), tempVar.getNamespace(), var.getName(), cache);

								if (tVar != null) {
									deferredVariables.push(tVar);
									break;
								}
							}
						}
					}
					// a simple ClassInstanceCreation, ie. $contact = new
					// ContactType();
				} else if (s.getValue().getClass() == ClassInstanceCreation.class) {

					ClassInstanceCreation instance = (ClassInstanceCreation) s.getValue();

					if (instance.getClassName().getClass() == FullyQualifiedReference.class) {

						FullyQualifiedReference fqcn = (FullyQualifiedReference) instance.getClassName();
						NamespaceReference nsRef = createFromFQCN(fqcn);

						if (nsRef != null) {
							TemplateVariable variable = new TemplateVariable(currentMethod, var.getName(), var.sourceStart(), var.sourceEnd(),
									nsRef.getNamespace(), nsRef.getClassName());
							deferredVariables.push(variable);
						}
					}
				} else if (s.getValue().getClass() == Scalar.class) {

					TemplateVariable variable = new TemplateVariable(currentMethod, var.getName(), var.sourceStart(), var.sourceEnd(), null, null);
					deferredVariables.push(variable);
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
