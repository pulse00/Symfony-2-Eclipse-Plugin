package com.dubture.symfony.annotation.model;

import com.dubture.symfony.annotation.parser.antlr.SourcePosition;

/**
 * This class implements the {@link ISourceElement} interface.
 * It is defined to ease the creation of new annotation source
 * element like argument value, named element, declaration, etc.
 *
 * <p>
 * This base class has a {@link SourcePosition} member which
 * is initialized at instance level. It defines a setter and
 * a getter for this member.
 * </p>
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class AnnotationSourceElement implements ISourceElement {

    protected SourcePosition sourcePosition = new SourcePosition();

    @Override
    public SourcePosition getSourcePosition() {
        return sourcePosition;
    }

    @Override
    public void setSourcePosition(SourcePosition position) {
        sourcePosition = position;
    }
}
