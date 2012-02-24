package com.dubture.symfony.annotation.model;

/**
 * Interace for ArgumentValue object. The following
 * are valid ArgumentValue:
 *
 *     - {@link AnnotationValue}
 *     - {@link ObjectValue}
 *     - {@link ArrayValue}
 *     - {@link StringValue}
 *     - {@link NumberValue}
 *     - {@link BooleanValue}
 *     - {@link NullValue}
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public interface ArgumentValue {
    /**
     * Returns the value holded by this argument. The value
     * is different depending on the result of the getType()
     * method. The following is a list of what type is
     * returned depending of the type of the {@link ArgumentValue}
     * concrete type:
     *    <ul>
     *     <li> {@link AnnotationValue} => {@link Annotation} </li>
     *     <li> {@link ObjectValue}  => {@link Map}<{@link String}, {@link ArgumentValue}> </li>
     *     <li> {@link ArrayValue} => {@link List}<{@link ArgumentValue}> </li>
     *     <li> {@link StringValue}  => {@link String} </li>
     *     <li> {@link NumberValue} => {@link Double} </li>
     *     <li> {@link BooleanValue} => {@link Boolean} </li>
     *     <li> {@link NullValue}    => null </li>
     *  </ul>
     *
     * @return An object representing the value holded by this argument value.
     */
    Object getValue();

    /**
     * This will return the concrete type of the ArgumentValue object.
     * This is useful to cast the getValue() result to specific ArgumentValue
     * object.
     *
     * @return The current concrete type of this ArgumentValue instance.
     */
    ArgumentValueType getType();
}
