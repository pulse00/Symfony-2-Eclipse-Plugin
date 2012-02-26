/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.annotation.parser.tree.visitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.core.builder.IBuildContext;

import com.dubture.symfony.annotation.model.Annotation;
import com.dubture.symfony.annotation.model.AnnotationClass;
import com.dubture.symfony.annotation.model.Argument;
import com.dubture.symfony.annotation.model.IArgumentValue;
import com.dubture.symfony.annotation.model.NamedArgument;
import com.dubture.symfony.annotation.parser.antlr.AnnotationParser;
import com.dubture.symfony.annotation.parser.antlr.AnnotationToken;
import com.dubture.symfony.annotation.parser.antlr.SourcePosition;
import com.dubture.symfony.annotation.parser.tree.AnnotationCommonTree;

/**
 * {@link AnnotationNodeVisitor} parses the structured elements
 * from an annotation like namespace classname and parameters.
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 *
 */
public class AnnotationNodeVisitor  extends AbstractAnnotationNodeVisitor {

    protected Annotation annotation;

    public AnnotationNodeVisitor() {
        annotation = new Annotation();
    }

    public AnnotationNodeVisitor(int lineOffset, int columnOffset, int charOffset) {
        annotation = new Annotation();

        AnnotationCommonTree.lineOffset = lineOffset;
        AnnotationCommonTree.columnOffset = columnOffset;
        AnnotationCommonTree.charOffset = charOffset;
    }

    public AnnotationNodeVisitor(IBuildContext context) {
        annotation = new Annotation();
    }

    /**
     * This method will visit an Annotation node. The node should have the following
     * form:
     *   (ANNOTATION AT (CLASS (NAMESPACE segments*) name) (DECLARATION arguments*))
     */
    @Override
    public void visit(AnnotationCommonTree node) {
        // No parse
        if (node.isNil()) {
            annotation = null;
            return;
        }

        annotation.getSourcePosition().setStart(node.getChild(0).getToken());

        visitClass(node.getChild(1));
        if (node.getChildCount() > 2) {
            visitDeclaration(node.getChild(2));
        } else {
            SourcePosition classPosition = annotation.getAnnotationClass().getSourcePosition();
            annotation.getSourcePosition().setEnd(classPosition.endOffset);
        }
    }

    protected void visitClass(AnnotationCommonTree classNode) {
        AnnotationClass annotationClass = annotation.getAnnotationClass();

        boolean hasNamespaceSegment = false;
        AnnotationCommonTree namespaceNode = classNode.getChild(0);
        List<AnnotationCommonTree> children = namespaceNode.getChildTrees();

        if (children != null) {
            for (AnnotationCommonTree childNode : children) {
                if (childNode.getText() != "\\") {
                    annotation.pushNamespaceSegment(childNode.getText());
                    hasNamespaceSegment = true;
                }
            }
        }

        AnnotationCommonTree classNameNode = classNode.getChild(1);
        annotationClass.setClassName(classNameNode.getText());

        AnnotationToken classNameToken = classNameNode.getToken();
        annotationClass.getClassNamePosition().setToken(classNameToken);
        if (!hasNamespaceSegment) {
            annotationClass.getSourcePosition().setToken(classNameToken);
            return;
        }

        // We have at least one namespace segment
        annotationClass.getSourcePosition().setStart(children.get(0).getToken());
        annotationClass.getSourcePosition().setEnd(classNameToken);

        annotationClass.getNamespacePosition().setStart(children.get(0).getToken());
        annotationClass.getNamespacePosition().setEnd(children.get(children.size() - 1).getToken());
    }

    protected void visitDeclaration(AnnotationCommonTree declarationNode) {
        if (declarationNode.getChildCount() == 0) {
            return;
        }

        for (AnnotationCommonTree childNode : declarationNode.getChildTrees()) {
            switch (childNode.getType()) {
                case AnnotationParser.PARAM_START:
                    annotation.getDeclaration().getSourcePosition().setStart(childNode.getToken());
                    break;

                case AnnotationParser.ARGUMENT:
                    visitArgument(childNode);
                    break;

                case AnnotationParser.NAMED_ARGUMENT:
                    visitNamedArgument(childNode);
                    break;

                case AnnotationParser.PARAM_END:
                    annotation.getSourcePosition().setEnd(childNode.getToken());
                    annotation.getDeclaration().getSourcePosition().setEnd(childNode.getToken());
                    break;

                default:
                    // This is correct, we should have only argument or named argument here
                    break;
            }
        }
    }

    protected void visitArgument(AnnotationCommonTree argumentNode) {
        IArgumentValue argumentValue = visitArgumentValue(argumentNode.getChild(0));
        annotation.addArgument(new Argument(argumentValue));
    }

    protected void visitNamedArgument(AnnotationCommonTree namedArgumentNode) {
        AnnotationCommonTree argumentNameNode = namedArgumentNode.getFirstChildFromType(AnnotationParser.ARGUMENT_NAME);
        String name = argumentNameNode.getChild(0).getText();

        AnnotationCommonTree argumentValueNode = namedArgumentNode.getFirstChildFromType(AnnotationParser.ARGUMENT_VALUE);
        IArgumentValue argumentValue = null;
        if (argumentValueNode != null && argumentValueNode.getChildCount() > 0) {
            // There is a value provided, get the argument from it
            argumentValue = visitArgumentValue(argumentValueNode.getChild(0));
        }

        annotation.addArgument(name, argumentValue);
    }

    public Annotation getAnnotation () {
        return annotation;
    }

    public String getClassName() {
        return annotation.getClassName();
    }

    public String getNamespace() {
        return annotation.getNamespace();
    }

    public String getFullyQualifiedName() {
        return getNamespace() + getClassName();
    }

    public Map<String, String> getArguments() {
        Map<String, String> arguments = new HashMap<String, String>();
        Map<String, NamedArgument> annotationArguments = annotation.getNamedArguments();
        for (String argumentName : annotationArguments.keySet()) {
            arguments.put(argumentName, annotationArguments.get(argumentName).toString());
        }

        return arguments;
    }

    public List<String> getLiteralArguments() {
        List<String> arguments = new LinkedList<String>();
        List<Argument> annotationArguments = annotation.getArguments();
        for (Argument argument : annotationArguments) {
            if (!(argument instanceof NamedArgument)) {
                arguments.add(argument.getValue().toString());
            }
        }

        return arguments;
    }

    public String getArgument(String name) {
        return annotation.getArgument(name);
    }

    public IArgumentValue getArgumentValue(String name) {
        return annotation.getArgumentValue(name);
    }

    public String getFirstNamespacePart() {
        return annotation.getFirstNamespacePart();
    }
}
