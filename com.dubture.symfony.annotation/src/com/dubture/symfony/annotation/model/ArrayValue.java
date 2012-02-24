package com.dubture.symfony.annotation.model;

import java.util.LinkedList;
import java.util.List;

/**
 * This object represents an array value. This object can
 * be assigned to annotation argument. It holds the values
 * of the array. The values holded are all of type
 * {@link ArgumentValue}
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class ArrayValue implements ArgumentValue {

    private List<ArgumentValue> values = new LinkedList<ArgumentValue>();

    public void add(ArgumentValue value) {
        values.add(value);
    }

    public Object get(int index) {
        if (index >= 0 && index < values.size()) {
            return values.get(index).getValue();
        }

        return null;
    }

    public ArgumentValue getArgumentValue(int index) {
        if (index >= 0 && index < values.size()) {
            return values.get(index);
        }

        return null;
    }

    @Override
    public Object getValue() {
        return values;
    }

    @Override
    public ArgumentValueType getType() {
        return ArgumentValueType.ARRAY;
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
