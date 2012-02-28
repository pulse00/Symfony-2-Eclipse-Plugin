/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.annotation.parser.tree.visitor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.builder.IBuildContext;

import com.dubture.symfony.annotation.model.Annotation;
import com.dubture.symfony.annotation.model.Argument;
import com.dubture.symfony.annotation.model.IArgumentValue;
import com.dubture.symfony.annotation.parser.antlr.AnnotationParser;
import com.dubture.symfony.annotation.parser.tree.AnnotationCommonTree;

/**
 * {@link AnnotationNodeVisitor} parses the structured elements
 * from an annotation like namespace, class name and arguments.
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
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
     *   (ANNOTATION (DECLARATION arguments*))
     */
    @Override
    public void visit(AnnotationCommonTree node) {
        // No parse
        if (node.isNil()) {
            annotation = null;
            return;
        }

        visitClass(node.getChild(0));
        if (node.getChildCount() > 1) {
            visitDeclaration(node.getChild(1));
        } else {
            annotation.getSourcePosition().setEnd(node.getChild(0).getToken());
        }
    }

    protected void visitClass(AnnotationCommonTree classNode) {
        annotation.getSourcePosition().setStart(classNode.getToken());
        annotation.getAnnotationClass().getSourcePosition().setToken(classNode.getToken());

        String fullyQualifiedClass = classNode.getText().substring(1);
        List<String> namespaceSegments = new LinkedList<String>(Arrays.asList(fullyQualifiedClass.split("\\\\")));
        String className = namespaceSegments.remove(namespaceSegments.size() - 1);

        annotation.setClassName(className);
        for (String namespaceSegment : namespaceSegments) {
            annotation.pushNamespaceSegment(namespaceSegment);
        }
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
}
