/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.annotation.parser.tree.visitor;

import com.dubture.symfony.annotation.model.IArgumentValue;
import com.dubture.symfony.annotation.model.BooleanValue;
import com.dubture.symfony.annotation.model.NullValue;
import com.dubture.symfony.annotation.model.NumberValue;
import com.dubture.symfony.annotation.model.StringValue;
import com.dubture.symfony.annotation.parser.antlr.AnnotationParser;
import com.dubture.symfony.annotation.parser.tree.AnnotationCommonTree;

/**
 * {@link AnnotationArgumentValueNodeVisitor} parses the structured elements
 * of an argument value node. The argument value node is used at at different
 * places in the AST like when dealing with an object or with an array.
 *
 * <p>
 * This visitor is reused by multiple other nodes that requires an argument
 * value. The node received is of the following form:
 * {@literal "(argumentValueType value)"}
 * </p>
 *
 * <p>
 * The argumentValueType can be one of the following:
 *  <ul>
 *      <li> NULL_VALUE </li>
 *      <li> BOOLEAN_VALUE </li>
 *      <li> NUMBER_VALUE </li>
 *      <li> STRING_VALUE </li>
 *      <li> ARRAY_VALUE </li>
 *      <li> OBJECT_VALUE </li>
 *      <li> ANNOTATION_VALUE </li>
 *  </ul>
 * </p>
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class AnnotationArgumentValueNodeVisitor extends AbstractAnnotationNodeVisitor {

    private IArgumentValue argumentValue;

    @Override
    public void visit(AnnotationCommonTree node) {
        switch(node.getType()) {
            case AnnotationParser.NULL_VALUE:
                argumentValue = new NullValue();
                argumentValue.getSourcePosition().setToken(node.getChild(0).getToken());
                break;

            case AnnotationParser.BOOLEAN_VALUE:
                argumentValue = new BooleanValue(node.getChild(0).getText());
                argumentValue.getSourcePosition().setToken(node.getChild(0).getToken());
                break;

            case AnnotationParser.NUMBER_VALUE:
                argumentValue = new NumberValue(node.getChild(0).getText());
                argumentValue.getSourcePosition().setToken(node.getChild(0).getToken());
                break;

            case AnnotationParser.STRING_VALUE:
                argumentValue = new StringValue(node.getChild(0).getText());
                argumentValue.getSourcePosition().setToken(node.getChild(0).getToken());
                break;

            case AnnotationParser.ARRAY_VALUE:
                argumentValue = visitArrayValue(node);
                break;

            case AnnotationParser.OBJECT_VALUE:
                argumentValue = visitObjectValue(node);
                break;

            case AnnotationParser.ANNOTATION_VALUE:
                argumentValue = visitAnnotationValue(node);
                break;

            default:
                // This is not normal, you should have been able to read the value
                break;
        }
    }

    protected IArgumentValue visitArrayValue(AnnotationCommonTree arrayValueNode) {
        AnnotationArrayValueNodeVisitor visitor = new AnnotationArrayValueNodeVisitor();
        arrayValueNode.accept(visitor);

        return visitor.getArrayValue();
    }

    protected IArgumentValue visitObjectValue(AnnotationCommonTree objectValueNode) {
        AnnotationObjectValueNodeVisitor visitor = new AnnotationObjectValueNodeVisitor();
        objectValueNode.accept(visitor);

        return visitor.getObjectValue();
    }

    protected IArgumentValue visitAnnotationValue(AnnotationCommonTree annotationValueNode) {
        AnnotationValueNodeVisitor visitor = new AnnotationValueNodeVisitor();
        annotationValueNode.accept(visitor);

        return visitor.getAnnotationValue();
    }

    public IArgumentValue getArgumentValue() {
        return argumentValue;
    }
}
