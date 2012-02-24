/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.annotation.parser.tree.visitor;

import com.dubture.symfony.annotation.model.ArgumentValue;
import com.dubture.symfony.annotation.model.ObjectValue;
import com.dubture.symfony.annotation.parser.tree.AnnotationCommonTree;

/**
 * {@link AnnotationNodeVisitor} parses the structured elements
 * from an object value node only. An object value node has
 * the following form:
 *
 *     (OBJECT_VALUE (PAIR (STRING_VALUE "name") (argumentType argumentValue))+)
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 *
 */
public class AnnotationObjectValueNodeVisitor extends AbstractAnnotationNodeVisitor {

    private ObjectValue objectValue = new ObjectValue();

    @Override
    public void visit(AnnotationCommonTree node) {
        for (AnnotationCommonTree pairNode : node.getChildTrees()) {
            String name = pairNode.getChild(0).getChild(0).getText();

            AnnotationCommonTree nodeValue = (AnnotationCommonTree) pairNode.getChild(1);
            ArgumentValue argumentValue = visitArgumentValue(nodeValue);

            objectValue.put(name, argumentValue);
        }
    }

    public ObjectValue getObjectValue() {
        return objectValue;
    }
}
