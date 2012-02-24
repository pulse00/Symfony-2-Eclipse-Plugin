package com.dubture.symfony.annotation.model;

/**
 * This object represents a string value. This object can
 * be assigned to annotation argument.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class NumberValue implements ArgumentValue {

    private Double value;

    public NumberValue() {
        value = null;
    }

    public NumberValue(double value) {
        this.value = value;
    }

    public NumberValue(String value) {
        this.value = Double.parseDouble(value);
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
        return value.toString();
    }
}
