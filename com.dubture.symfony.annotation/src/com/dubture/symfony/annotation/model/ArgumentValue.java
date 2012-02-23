package com.dubture.symfony.annotation.model;

/**
 * Interace for ArgumentValue object. The following
 * are valid ArgumentValue:
 *
 *     - {@link Annotation}
 *     - {@link ObjectValue}
 *     - {@link StringValue}
 *     - {@link LiteralValue}
 *     - {@link BooleanValue}
 *     - {@link NullValue}
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public interface ArgumentValue {
    /**
     * Returns the value holded by this argument. The value
     * is different depending on the result of the getType()
     * methode. The following is a list of what is returned by
     * which type:
     *
     *     - {@link Annotation}   => The Annotation itself
     *     - {@link ObjectValue}  => The list of pairs: Map<String, ArgumentValue>
     *     - {@link StringValue}  => The string value: String
     *     - {@link LiteralValue} => The literal value: String
     *     - {@link BooleanValue} => The boolean value: Boolean
     *     - {@link NullValue}    => The null value: null
     *
     * @return An object representing the value holded by this argument value.
     */
    Object getValue();

    /**
     * This will return the concrete type of the ArgumentValue object.
     * This is useful to cast the getValue() result of the ArgumentValue
     * directly to another more specific ArgumentValue object.
     *
     * @return The current concrete type of this ArgumentValue instance.
     */
    ArgumentValueTypes getType();
}
