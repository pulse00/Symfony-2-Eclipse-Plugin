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
import com.dubture.symfony.annotation.model.ArrayValue;
import com.dubture.symfony.annotation.parser.tree.AnnotationCommonTree;

/**
 * {@link AnnotationNodeVisitor} parses the structured elements
 * from an object value node only. An object value node has
 * the following form:
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 *
 */
public class AnnotationArrayValueNodeVisitor  extends AbstractAnnotationNodeVisitor {

    private ArrayValue arrayValue = new ArrayValue();

    @Override
    public void visit(AnnotationCommonTree node) {
        for (AnnotationCommonTree valueNode : node.getChildTrees()) {
            ArgumentValue argumentValue = visitArgumentValue(valueNode);
            arrayValue.add(argumentValue);
        }
    }

    public ArrayValue getArrayValue() {
        return arrayValue;
    }
}
