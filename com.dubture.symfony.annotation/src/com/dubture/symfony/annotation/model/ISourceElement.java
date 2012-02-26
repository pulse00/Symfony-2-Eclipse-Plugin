package com.dubture.symfony.annotation.model;

import com.dubture.symfony.annotation.parser.antlr.SourcePosition;

/**
 * This represent a source element. A source element
 * is an element that has a source position within
 * it. This source position is used to locate the element
 * in a particular text source.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public interface ISourceElement {

    /**
     * Returns the source position associated with this
     * source element.
     *
     * @return The associated source position
     */
    SourcePosition getSourcePosition();

    /**
     * Set the source position associated with this value
     *
     * @param position The new source position
     */
    void setSourcePosition(SourcePosition position);

}
