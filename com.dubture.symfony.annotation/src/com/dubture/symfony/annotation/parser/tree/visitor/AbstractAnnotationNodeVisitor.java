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
import com.dubture.symfony.annotation.parser.tree.AnnotationCommonTree;

/**
 * AbstractAnnotationNodeVisitor base class. This class offers
 * utilities to classes inheriting it. The main utility
 * is visitArgumentValue which visit an argument value node
 * and return its ArgumentValue.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public abstract class AbstractAnnotationNodeVisitor implements IAnnotationNodeVisitor  {

    abstract public void visit(AnnotationCommonTree node);

    protected ArgumentValue visitArgumentValue(AnnotationCommonTree argumentValueNode) {
        AnnotationArgumentValueNodeVisitor argumentValueVisitor = new AnnotationArgumentValueNodeVisitor();
        argumentValueNode.accept(argumentValueVisitor);

        return argumentValueVisitor.getArgumentValue();
    }
}
