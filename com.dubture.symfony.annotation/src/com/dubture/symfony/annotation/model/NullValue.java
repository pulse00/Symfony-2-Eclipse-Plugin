package com.dubture.symfony.annotation.model;

/**
 * This object represents a null value. This object can
 * be assigned to annotation argument. This object is not
 * intended to be instantiated. Use the static instance
 * variable to get a NullValue object.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public final class NullValue implements ObjectArgumentValue {

    static public NullValue instance = new NullValue();

    private NullValue() {
        // Cannot be instantiated
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public ArgumentValueTypes getType() {
        return ArgumentValueTypes.NULL;
    }

    @Override
    public String toString() {
        return "null";
    }
}
