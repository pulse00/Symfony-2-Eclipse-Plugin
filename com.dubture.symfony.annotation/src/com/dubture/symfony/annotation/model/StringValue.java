package com.dubture.symfony.annotation.model;

/**
 * This object represents a string value. This object can
 * be assigned to annotation argument.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class StringValue extends ArgumentValue {

    private String value;

    public StringValue() {
        value = null;
    }

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public ArgumentValueType getType() {
        return ArgumentValueType.STRING;
    }

    @Override
    public String toString() {
        return value;
    }
}
