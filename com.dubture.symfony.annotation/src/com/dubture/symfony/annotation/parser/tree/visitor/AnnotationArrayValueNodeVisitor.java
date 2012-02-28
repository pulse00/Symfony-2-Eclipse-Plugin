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
import com.dubture.symfony.annotation.model.ArrayValue;
import com.dubture.symfony.annotation.parser.antlr.AnnotationParser;
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
        for (AnnotationCommonTree childNode : node.getChildTrees()) {
            switch(childNode.getType()) {
                case AnnotationParser.CURLY_START:
                    arrayValue.getSourcePosition().setStart(childNode.getToken());
                    break;

                // Intended Fallback
                case AnnotationParser.ANNOTATION_VALUE:
                case AnnotationParser.OBJECT_VALUE:
                case AnnotationParser.ARRAY_VALUE:
                case AnnotationParser.STRING_VALUE:
                case AnnotationParser.NUMBER_VALUE:
                case AnnotationParser.BOOLEAN_VALUE:
                case AnnotationParser.NULL_VALUE:
                    visitValue(childNode);
                    break;

                case AnnotationParser.CURLY_END:
                    arrayValue.getSourcePosition().setEnd(childNode.getToken());
                    break;
            }
        }
    }

    public void visitValue(AnnotationCommonTree valueNode) {
        IArgumentValue argumentValue = visitArgumentValue(valueNode);
        arrayValue.add(argumentValue);
    }

    public ArrayValue getArrayValue() {
        return arrayValue;
    }
}
