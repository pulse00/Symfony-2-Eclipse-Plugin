package com.dubture.symfony.annotation.model;

/**
 * This object represents a boolean value.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class BooleanValue implements ArgumentValue {

    private Boolean value;

    public BooleanValue() {
        value = false;
    }

    public BooleanValue(boolean value) {
        this.value = Boolean.valueOf(value);
    }

    public BooleanValue(String value) {
        this.value = Boolean.parseBoolean(value);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public ArgumentValueType getType() {
        return ArgumentValueType.BOOLEAN;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
