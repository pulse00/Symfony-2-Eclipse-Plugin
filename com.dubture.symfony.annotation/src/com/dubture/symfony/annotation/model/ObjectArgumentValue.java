package com.dubture.symfony.annotation.model;

/**
 * Interace for ObjectArgumentValue object. The following
 * are valid ObjectArgumentValue:
 *
 *     - {@link StringValue}
 *     - {@link BooleanValue}
 *     - {@link NullValue}
 *
 * This interface is there only to avoid to have Annotation
 * of ObjectValue as the value part of an ObjectValue's pair.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public interface ObjectArgumentValue extends ArgumentValue {

}
