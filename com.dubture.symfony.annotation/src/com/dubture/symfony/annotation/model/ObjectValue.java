package com.dubture.symfony.annotation.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This object represents a object value. This object can
 * be assigned to annotation argument. It holds
 * information about the attributes of an object. This object
 * has the following string form:
 *   {"name1" = "value", "name2" = false, "name3" = null}
 *
 * The valid values for the arguments are:
 *
 *    - {@link StringValue}
 *    - {@link BooleanValue}
 *    - {@link NullValue}
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class ObjectValue implements ArgumentValue {

    private Map<String, ObjectArgumentValue> pairs = new HashMap<String, ObjectArgumentValue>();

    public void put(String name, ObjectArgumentValue value) {
        pairs.put(name, value);
    }

    public Object get(String name) {
        if (pairs.containsKey(name)) {
            return pairs.get(name).getValue();
        }

        return null;
    }

    public ObjectArgumentValue getArgumentValue(String name) {
        ObjectArgumentValue argumentValue = null;
        if (pairs.containsKey(name)) {
            argumentValue = pairs.get(name);
        }

        return argumentValue;
    }

    @Override
    public Object getValue() {
        return pairs;
    }

    @Override
    public ArgumentValueTypes getType() {
        return ArgumentValueTypes.OBJECT;
    }
}
