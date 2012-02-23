/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.annotation.parser.antlr;

import org.eclipse.dltk.core.builder.IBuildContext;

import com.dubture.symfony.annotation.model.BooleanValue;
import com.dubture.symfony.annotation.model.NullValue;
import com.dubture.symfony.annotation.model.ObjectArgumentValue;
import com.dubture.symfony.annotation.model.ObjectValue;
import com.dubture.symfony.annotation.model.StringValue;

/**
 * {@link AnnotationNodeVisitor} parses the structured elements
 * from an object value node only. An object value node has
 * the following form:
 *
 *     (OBJECT_VALUE (PAIR (STRING_VALUE "name") (STRING_VALUE|BOOLEAN_VALUE|NULL_VALUE "value"))*)
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 *
 */
public class AnnotationObjectValueNodeVisitor implements IAnnotationNodeVisitor {

    private ObjectValue objectValue = new ObjectValue();

    public AnnotationObjectValueNodeVisitor(IBuildContext context) {


    }

    public AnnotationObjectValueNodeVisitor() {

    }

    @Override
    public void beginVisit(AnnotationCommonTree node) {

        int kind = node.getType();

        switch(kind) {

        case AnnotationParser.ARGUMENT:

            break;

        case AnnotationParser.PAIR:

            // Node of the form (PAIR (STRING_VALUE "name") (STRING_VALUE|BOOLEAN_VALUE|NULL_VALUE "value"))
            if (node.getChildCount() != 2) {
                break;
            }

            String name = node.getChild(0).getChild(0).getText();

            AnnotationCommonTree nodeValue = (AnnotationCommonTree) node.getChild(1);

            ObjectArgumentValue argumentValue = null;
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
            }

            objectValue.put(name, argumentValue);

            break;

        default:

            break;

        }
    }

    @Override
    public void endVisit(AnnotationCommonTree node) {

        int kind = node.getType();

        switch(kind) {

        case AnnotationParser.ARGUMENT:

            break;

        }
    }

    public ObjectValue getObjectValue() {
        return objectValue;
    }
}
