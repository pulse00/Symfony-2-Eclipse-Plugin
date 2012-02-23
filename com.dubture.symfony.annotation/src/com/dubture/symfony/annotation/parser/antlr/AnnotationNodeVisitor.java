/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.annotation.parser.antlr;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.core.builder.IBuildContext;

import com.dubture.symfony.annotation.model.Annotation;
import com.dubture.symfony.annotation.model.ArgumentValue;
import com.dubture.symfony.annotation.model.ArgumentValueTypes;
import com.dubture.symfony.annotation.model.BooleanValue;
import com.dubture.symfony.annotation.model.LiteralValue;
import com.dubture.symfony.annotation.model.NullValue;
import com.dubture.symfony.annotation.model.StringValue;

/**
 * {@link AnnotationNodeVisitor} parses the structured elements
 * from an annotation like namespace classname and parameters.
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 *
 */
public class AnnotationNodeVisitor implements IAnnotationNodeVisitor {

    private Annotation annotation = new Annotation();

    public AnnotationNodeVisitor() {
    }

    public AnnotationNodeVisitor(IBuildContext context) {
    }

    @Override
    public void beginVisit(AnnotationCommonTree node) {

        int kind = node.getType();

        switch(kind) {

        case AnnotationParser.ARGUMENT:

            if (node.getChildCount() != 2) {
                // The value is probably missing, maybe we should add it as a null value ?!?
                break;
            }

            String name = node.getFirstChildWithType(AnnotationParser.ARGUMENT_NAME).getChild(0).getText();

            AnnotationCommonTree nodeArgumentValue = (AnnotationCommonTree) node.getFirstChildWithType(AnnotationParser.ARGUMENT_VALUE);
            AnnotationCommonTree nodeValue = (AnnotationCommonTree) nodeArgumentValue.getChild(0);

            ArgumentValue argumentValue = null;
            IAnnotationNodeVisitor visitor = null;
            switch(nodeValue.getType()) {

            case AnnotationParser.BOOLEAN_VALUE:
                argumentValue = new BooleanValue(nodeValue.getChild(0).getText());

                break;

            case AnnotationParser.NULL_VALUE:
                argumentValue = NullValue.instance;

                break;

            case AnnotationParser.STRING_VALUE:
                argumentValue = new StringValue(nodeValue.getChild(0).getText());

                break;

            case AnnotationParser.OBJECT_VALUE:
                visitor = new AnnotationObjectValueNodeVisitor();
                nodeValue.accept(visitor);

                argumentValue = ((AnnotationObjectValueNodeVisitor) visitor).getObjectValue();

                break;

            case AnnotationParser.ANNOTATION:
                visitor = new AnnotationNodeVisitor();
                nodeValue.accept(visitor);

                argumentValue = ((AnnotationNodeVisitor)visitor).getAnnotation();

                break;
            }

            // We visited the value node, we don't need it anymore in the tree
            nodeArgumentValue.deleteChild(0);
            annotation.putArgument(name, argumentValue);

            break;

        case AnnotationParser.ANNOTATION:

            break;

        case AnnotationParser.CLASS:

            annotation.setClassName(node.getChild(1).getText());

            break;

        case AnnotationParser.LITERAL:

            String literal = node.getChild(0).toString();
            annotation.putArgument(literal, new LiteralValue(literal));

            break;

        case AnnotationParser.NAMESPACE:

            List<AnnotationCommonTree> children = node.getChildTrees();
            if (children != null) {
                for (Object child : node.getChildren()) {
                    AnnotationCommonTree childNode = (AnnotationCommonTree) child;
                    if (childNode.getText() != "\\") {
                        annotation.pushNamespaceSegment(childNode.getText());
                    }
                }
            }

            break;

        default:

            break;

        }
    }

    @Override
    public void endVisit(AnnotationCommonTree node) {

        int kind = node.getType();

        switch(kind) {

        case AnnotationParser.ANNOTATION:

            break;

        }
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
        Map<String, ArgumentValue> annotationArguments = annotation.getArguments();
        for (String argumentName : annotationArguments.keySet()) {
            arguments.put(argumentName, annotationArguments.get(argumentName).toString());
        }

        return arguments;
    }

    public List<String> getLiteralArguments() {
        List<String> arguments = new LinkedList<String>();
        Map<String, ArgumentValue> annotationArguments = annotation.getArguments();
        for (ArgumentValue argumentValue : annotationArguments.values()) {
            if (argumentValue.getType() == ArgumentValueTypes.LITERAL) {
                arguments.add(argumentValue.getValue().toString());
            }
        }

        return arguments;
    }

    public String getArgument(String name) {
        return annotation.getArgument(name);
    }

    public ArgumentValue getArgumentValue(String name) {
        return annotation.getArgumentValue(name);
    }

    public String getFirstNamespacePart() {
        return annotation.getFirstNamespacePart();
    }
}
