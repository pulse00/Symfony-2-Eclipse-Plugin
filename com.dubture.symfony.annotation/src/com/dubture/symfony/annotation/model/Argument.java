package com.dubture.symfony.annotation.model;

/**
 * This represents an {@link Annotation} argument. The argument
 * value is an {@link ArgumentValue}.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class Argument {

    protected ArgumentValue value;

    public Argument(ArgumentValue value){
        this.value = value;
    }

    public ArgumentValue getValue() {
        return value;
    }

    public String getValueAsString() {
        return value.toString();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
