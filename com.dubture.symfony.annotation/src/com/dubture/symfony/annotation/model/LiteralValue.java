package com.dubture.symfony.annotation.model;

/**
 * This object represents a literal value. The literal
 * value is a special value argument that is not
 * explicitly assigned to a name. They have the following
 * form:
 *     ("literale1", {"literal2", "literal3"})
 *
 * This object is then used to retrieve literal
 * values from annotation.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class LiteralValue implements ArgumentValue {

    private String value;

    public LiteralValue() {
        value = null;
    }

    public LiteralValue(String value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public ArgumentValueTypes getType() {
        return ArgumentValueTypes.LITERAL;
    }

    @Override
    public String toString() {
        return value;
    }
}
