package com.dubture.symfony.annotation.model;

/**
 * This represents an {@link Annotation} named argument. The argument has
 * a name and its value is an {@link ArgumentValue}.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class NamedArgument extends Argument {

    protected String name;

    public NamedArgument(String name, ArgumentValue value){
        super(value);

        this.name = name;
    }

    public String getName() {
        return name;
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
