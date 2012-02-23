package com.dubture.symfony.annotation.model;

/**
 * This object represents a boolean value. This object can
 * be assigned to annotation argument.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class BooleanValue implements ObjectArgumentValue {

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
    public ArgumentValueTypes getType() {
        return ArgumentValueTypes.BOOLEAN;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
