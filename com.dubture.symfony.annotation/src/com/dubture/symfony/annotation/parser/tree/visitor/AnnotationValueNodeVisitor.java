/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.annotation.parser.tree.visitor;

import org.eclipse.dltk.core.builder.IBuildContext;

import com.dubture.symfony.annotation.model.AnnotationValue;

/**
 * {@link AnnotationValueNodeVisitor} parses the structured elements
 * from an annotation like namespace classname and parameters.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class AnnotationValueNodeVisitor extends AnnotationNodeVisitor {

    public AnnotationValueNodeVisitor() {
        annotation = new AnnotationValue();
    }

    public AnnotationValueNodeVisitor(IBuildContext context) {
        annotation = new AnnotationValue();
    }

    public AnnotationValue getAnnotationValue() {
        return (AnnotationValue) annotation;
    }
}
