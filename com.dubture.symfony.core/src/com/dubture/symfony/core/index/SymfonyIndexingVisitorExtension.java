/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.index;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.index2.IIndexingRequestor.ReferenceInfo;
import org.eclipse.php.core.index.PhpIndexingVisitorExtension;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassInstanceCreation;
import org.eclipse.php.internal.core.compiler.ast.nodes.Comment;
import org.eclipse.php.internal.core.compiler.ast.nodes.ExpressionStatement;
import org.eclipse.php.internal.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.wst.sse.core.utils.StringUtils;
import org.json.simple.JSONObject;

import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.core.index.visitor.RegisterNamespaceVisitor;
import com.dubture.symfony.core.index.visitor.TemplateVariableVisitor;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.ISymfonyModelElement;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.TemplateVariable;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;
import com.dubture.symfony.core.util.JsonUtils;
import com.dubture.symfony.core.util.text.SymfonyTextSequenceUtilities;
import com.dubture.symfony.index.SymfonyIndexer;
import com.dubture.symfony.index.model.Route;


/**
 *
 * {@link SymfonyIndexingVisitorExtension} contributes model elements
 * to the index.
 *
 *
 * TODO: This indexer is currently called on any PHP Project, regardless
 * of a Symfony nature: Find a way to check if the Indexer is indexing
 * a Sourcemodule of a project with the SymfonyNature.
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyIndexingVisitorExtension extends
PhpIndexingVisitorExtension {


    private boolean inController = false;
    private ClassDeclaration currentClass;
    private NamespaceDeclaration namespace;
    private TemplateVariableVisitor controllerIndexer;
    private SymfonyIndexer indexer;

    private List<UseStatement> useStatements = new ArrayList<UseStatement>();
    private boolean isSymfonyResource;


    @Override
    public void setSourceModule(ISourceModule module) {

        super.setSourceModule(module);

        try {
            isSymfonyResource = sourceModule.getScriptProject().getProject().getNature(SymfonyNature.NATURE_ID) != null;
        } catch (CoreException e) {
            Logger.logException(e);
            isSymfonyResource = false;
        }
    }


    @Override
    public boolean visit(ASTNode s) throws Exception {

        return true;
    }


    @Override
    public boolean visit(ModuleDeclaration s) throws Exception {

        return true;
    }

    public boolean isInController() {
        return inController;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean visit(Statement s) throws Exception {

        if (s instanceof ExpressionStatement) {

            ExpressionStatement stmt = (ExpressionStatement) s;

            if (stmt.getExpr() instanceof PHPCallExpression) {

                PHPCallExpression call = (PHPCallExpression) stmt.getExpr();

                if (("registerNamespaces".equals(call.getName()) || "registerNamespaceFallbacks".equals(call.getName()))
                        && call.getReceiver() instanceof VariableReference) {

                    if (sourceModule.getElementName().equals("bootstrap.php"))
                        return false;

                    RegisterNamespaceVisitor registrar = new RegisterNamespaceVisitor(sourceModule);
                    registrar.visit(call);

                    for (IPath namespace : registrar.getNamespaces()) {
                        ReferenceInfo info = new ReferenceInfo(ISymfonyModelElement.NAMESPACE, 0, 0, namespace.toString(), null, null);
                        requestor.addReference(info);
                    }
                }
                // TODO: check if the variable implements Symfony\Component\DependencyInjection\ContainerInterface
                else if("setAlias".equals(call.getName())) {

                    if (call.getReceiver() instanceof VariableReference) {

                        CallArgumentsList args = call.getArgs();

                        if (args.getChilds().size() == 2) {

                            List<ASTNode> nodes = args.getChilds();

                            try {

                                Scalar alias = (Scalar) nodes.get(0);
                                Scalar reference = (Scalar) nodes.get(1);

                                if (alias != null && reference != null) {

                                    String id = SymfonyTextSequenceUtilities.removeQuotes(alias.getValue());
//                                    String ref = "alias_" + SymfonyTextSequenceUtilities.removeQuotes(reference.getValue());

                                    com.dubture.symfony.core.model.Service _service = SymfonyModelAccess.getDefault().findService(StringUtils.stripQuotes(reference.getValue()), sourceModule.getScriptProject().getPath());

                                    if (_service != null) {

                                        indexer.addService(id, _service.getClassName(), _service.getPublicString(), _service.getTags(), sourceModule.getScriptProject().getPath().toString(), 0);
                                        indexer.exitServices();

                                    }
                                }

                            } catch (ClassCastException e) {
                                //ignore cause not a valid node
                            }
                        }
                    }
                }
            }
        }

        return true;

    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean visit(Expression s) throws Exception {

        if(!isSymfonyResource)
            return false;

        if (s.getClass() == Block.class) {

            s.traverse(new PHPASTVisitor() {

                @Override
                public boolean visit(UseStatement s) throws Exception {
                    useStatements.add(s);
                    return true;
                }
            });
        }

        if (s instanceof ClassInstanceCreation) {

            ClassInstanceCreation instance = (ClassInstanceCreation) s;

            if (SymfonyCoreConstants.APP_KERNEL.equals(instance.getClassName().toString())) {

                List args = instance.getCtorParams().getChilds();

                if (args.get(0) instanceof Scalar) {
                    Scalar environment = (Scalar) args.get(0);

                    String value = environment.getValue().replace("\"", "").replace("'", "");
                    String path = sourceModule.getPath().toString();
                    Logger.debugMSG("indexing environment: " + value + " => " + path);

                    ReferenceInfo info = new ReferenceInfo(ISymfonyModelElement.ENVIRONMENT, instance.sourceStart(), instance.sourceEnd()-instance.sourceStart(), value, null, path);
                    requestor.addReference(info);

                }
            }
        }
        return super.visit(s);
    }


    @Override
    public boolean visit(TypeDeclaration s) throws Exception {

        if(!isSymfonyResource)
            return false;

        if (indexer == null)
            indexer = SymfonyIndexer.getInstance();

        if (s instanceof ClassDeclaration) {

            currentClass = (ClassDeclaration) s;

            parseAnnotation(currentClass);

            for (Object o : currentClass.getSuperClasses().getChilds()) {

                if (o instanceof FullyQualifiedReference) {

                    FullyQualifiedReference superReference = (FullyQualifiedReference) o;
                    String ns = getUseStatement(superReference.getName());

                    if (ns != null) {

                        String fqcn = ns + "\\" + superReference.getName();


                        boolean isTestOrFixture = false;

                        if (namespace != null && namespace.getName() != null)
                            isTestOrFixture = namespace.getName().contains("Test") || namespace.getName().contains("Fixtures");

                        // we got a bundle definition, index it
                        if (fqcn.equals(SymfonyCoreConstants.BUNDLE_FQCN) && ! isTestOrFixture) {

                            int length = (currentClass.sourceEnd() - currentClass.sourceEnd());

                            JSONObject meta = JsonUtils.createBundle(sourceModule, currentClass, namespace);
                            ReferenceInfo info = new ReferenceInfo(ISymfonyModelElement.BUNDLE, currentClass.sourceStart(), length, currentClass.getName(), meta.toJSONString(), namespace.getName());
                            requestor.addReference(info);

                        }
                    }

                    //TODO: Check against an implementation of Symfony\Component\DependencyInjection\ContainerAwareInterface
                    //
                    // see http://symfony.com/doc/current/cookbook/bundles/best_practices.html#controllers
                    // and http://api.symfony.com/2.0/Symfony/Component/DependencyInjection/ContainerAwareInterface.html
                    if (superReference.getName().equals(SymfonyCoreConstants.CONTROLLER_CLASS)) {

                        inController = true;


                        // the ControllerIndexer does the actual work of parsing the
                        // the relevant elements inside the controller
                        // which are then being collected in the endVisit() method
                        controllerIndexer = new TemplateVariableVisitor(useStatements, namespace, sourceModule);
                        currentClass.traverse(controllerIndexer);

                    }
                }
            }
        } else if (s instanceof NamespaceDeclaration) {
            namespace = (NamespaceDeclaration) s;
        }

        return true;
    }

    /**
    *
    * Check if the class is tagged to be used as an annotation.
    *
    */
    private void parseAnnotation(ClassDeclaration clazz) {

        PHPDocBlock block = clazz.getPHPDoc();

        if (block == null)
            return;

        boolean isAnnotation = false;

        if (block.getCommentType() == Comment.TYPE_PHPDOC) {

            for (PHPDocTag tag  : block.getTags()) {

                if (tag.getValue() != null &&
                        tag.getValue().contains("Annotation")) {
                    isAnnotation = true;
                }

            }

            if (isAnnotation == false) {

                String comment = block.getShortDescription();

                if (comment.length() == 0) {
                    comment = block.getLongDescription();
                }

                if ((comment == null || !comment.contains("@Annotation")))
                    return;

                isAnnotation = true;

            }

        }


        if (!isAnnotation)
            return;

        String ns = "";
        if (namespace != null) {
            ns = namespace.getName();
        }

        Logger.debugMSG("indexing annotation class: " + clazz.getName() + " " + ns);

        ReferenceInfo info = new ReferenceInfo(ISymfonyModelElement.ANNOTATION, clazz.sourceStart(), clazz.sourceEnd(), clazz.getName(), null, ns);
        requestor.addReference(info);

    }


    @Override
    @SuppressWarnings({ "rawtypes" })
    public boolean endvisit(TypeDeclaration s) throws Exception {


        if (controllerIndexer != null) {

            Map<TemplateVariable, String> variables = controllerIndexer.getTemplateVariables();
            Iterator it = variables.keySet().iterator();

            while(it.hasNext()) {

                TemplateVariable variable = (TemplateVariable) it.next();
                String viewPath = variables.get(variable);

                int start = variable.sourceStart();
                int length = variable.sourceEnd() - variable.sourceStart();
                String name = null;

                if (variable.isReference()) {

                    name = variable.getName();

                    String phpClass = variable.getClassName();
                    String namespace = variable.getNamespace();
                    String method = variable.getMethod().getName();
                    String metadata = JsonUtils.createReference(phpClass, namespace, viewPath, method);

                    if (viewPath.contains(".")) {
                        viewPath = viewPath.substring(0, viewPath.indexOf("."));
                    }

                    Logger.debugMSG("add reference info: " + name +  " =>  " + viewPath + " with metadata " + metadata);
                    ReferenceInfo info = new ReferenceInfo(ISymfonyModelElement.TEMPLATE_VARIABLE, start, length, name, metadata, viewPath);
                    requestor.addReference(info);

                } else if (variable.isScalar()) {

                    name = variable.getName();
                    String method = variable.getMethod().getName();
                    String metadata = JsonUtils.createScalar(name, viewPath, method);

                    if (viewPath.contains(".")) {
                        viewPath = viewPath.substring(0, viewPath.indexOf("."));
                    }

                    Logger.debugMSG("add scalar info: " + name +  " => " + viewPath + " with metadata: "  + metadata );
                    ReferenceInfo info = new ReferenceInfo(ISymfonyModelElement.TEMPLATE_VARIABLE, start, length, name, metadata, viewPath);
                    requestor.addReference(info);

                } else {

                    Logger.debugMSG("Unable to resolve template variable: " + variable.getClass().toString());
                }


            }

            Stack<Route> routes = controllerIndexer.getRoutes();


            for (Route route : routes) {
                Logger.debugMSG("indexing route: " + route.toString());
                indexer.addRoute(route, sourceModule.getScriptProject().getPath());
            }

            indexer.exitRoutes();
        }

        inController = false;
        currentClass = null;
//        namespace = null;
        controllerIndexer = null;


        return true;
    }

    private String getUseStatement(String name) {

        for (UseStatement statement : useStatements) {

            for (UsePart part : statement.getParts()) {

                if (part.getNamespace().getName().equals(name)) {

                    //TODO: clean this up ;)
                    if (part.getNamespace() == null || part.getNamespace().getNamespace() == null
                            || part.getNamespace().getNamespace().getName() == null) {

                        Logger.log(Logger.WARNING, sourceModule.getPath().toString() + ": error parsing namespace parts for " + name);
                        return null;
                    }

                    return part.getNamespace().getNamespace().getName();
                }
            }

        }

        return null;

    }

    /**
    * Parse {@link PHPMethodDeclaration} to index a couple of elements needed for code-assist
    * like Methods that accept viewpaths as parameters.
    */
    @Override
    public boolean visit(MethodDeclaration s) throws Exception {

        if(!isSymfonyResource)
            return false;

        if (s instanceof PHPMethodDeclaration) {

            PHPMethodDeclaration method = (PHPMethodDeclaration) s;
            PHPDocBlock docBlock = method.getPHPDoc();

            if (docBlock != null) {

                PHPDocTag[] tags = docBlock.getTags();

                for (PHPDocTag tag : tags) {

                    String value = tag.getValue();

                    if (tag.getReferences().length == 2) {

                        SimpleReference[] refs = tag.getReferences();
                        SimpleReference varName = refs[0];
                        SimpleReference varType = refs[1];

                        if(varName.getName().equals("$view") && varType.getName().equals("string")) {

                            int length = method.sourceEnd() - method.sourceStart();
                            ReferenceInfo viewMethod = new ReferenceInfo(ISymfonyModelElement.VIEW_METHOD, method.sourceStart()    , length, method.getName(), null, null);
                            requestor.addReference(viewMethod);

                        } else if (value.contains("route") || value.contains("url")) {

                            int length = method.sourceEnd() - method.sourceStart();
                            ReferenceInfo routeMethod = new ReferenceInfo(ISymfonyModelElement.ROUTE_METHOD, method.sourceStart(), length, method.getName(), null, null);
                            requestor.addReference(routeMethod);

                        }
                    }
                }
            }
        }

        return true;
    }


}
