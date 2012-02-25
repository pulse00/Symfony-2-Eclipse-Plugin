package com.dubture.symfony.annotation.model;

import com.dubture.symfony.annotation.parser.antlr.SourcePosition;

/**
 * This represent a value. A value in the context of an
 * annotation is anything that can be associated with a
 * source position.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public interface IValue {

    /**
     * Returns the source position associated with this value
     * @return
     */
    SourcePosition getSourcePosition();

    /**
     * Set the source position associated with this value
     *
     * @param position The new source position
     */
    void setSourcePosition(SourcePosition position);

}
