package com.dubture.symfony.annotation.model;

/**
 * This object represents a null value. This object is not
 * intended to be instantiated. Use the static instance
 * variable to get a NullValue object.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public final class NullValue implements ArgumentValue {

    static public NullValue instance = new NullValue();

    private NullValue() {
        // Cannot be instantiated
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public ArgumentValueType getType() {
        return ArgumentValueType.NULL;
    }

    @Override
    public String toString() {
        return "null";
    }
}
