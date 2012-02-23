package com.dubture.symfony.annotation.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * This object represents a Symfony2 annotation. It holds
 * information about the annotation like the class name,
 * the namespace and the arguments of it. The argument
 * valid values of an annotation are:
 *
 *    - {@link Annotation}
 *    - {@link ObjectValue}
 *    - {@link StringValue}
 *    - {@link BooleanValue}
 *    - {@link NullValue}
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class Annotation implements ArgumentValue {
    private String className = "";
    private Stack<String> namespace = new Stack<String>();

    private Map<String, ArgumentValue> arguments = new HashMap<String, ArgumentValue>();

    public Annotation() {
    }

    public Annotation(Stack<String> namespace, String className) {
        this.namespace.addAll(namespace);
        this.className = className;
    }

    /**
     * Returns the class name of the annotation. With the
     * string "Orm\Assert\Special", the class name returned
     * will be "Special".
     *
     * @return The class name of the annotation
     */
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Returns the namespace of the annotation. With the string
     * "Orm\Assert\Special", the namespace returned will be
     * "Orm\Assert\".
     *
     * @return The namespace with a trailing backslash
     */
    public String getNamespace() {
        StringBuilder namespaceBuilder = new StringBuilder();

        for (String part : namespace) {
            namespaceBuilder.append(part);
            namespaceBuilder.append('\\');
        }

        return namespaceBuilder.toString();
    }

    public void pushNamespaceSegment(String namespaceSegment) {
        namespace.push(namespaceSegment);
    }

    /**
     * Get the fully qualified name of the annotation. This
     * is the concatenation of {@link getNamespace} and
     * {@link getClassName}.
     *
     * @return The fully qualified name of the annotation.
     */
    public String getFullyQualifiedName() {
        return getNamespace() + getClassName();
    }

    public Map<String, ArgumentValue> getArguments() {
        return arguments;
    }

    public void putArgument(String name, ArgumentValue value) {
        arguments.put(name, value);
    }

    public String getArgument(String name) {
        if (arguments.containsKey(name)) {
            return arguments.get(name).toString();
        }

        return null;
    }

    public ArgumentValue getArgumentValue(String name) {
        if (arguments.containsKey(name)) {
            return arguments.get(name);
        }

        return null;
    }

    public String getFirstNamespacePart() {
        if (namespace.size() > 0) {
            return namespace.get(0);
        }

        return null;
    }

    @Override
    public Object getValue() {
        return this;
    }

    @Override
    public ArgumentValueTypes getType() {
        return ArgumentValueTypes.ANNOTATION;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append('@');
        builder.append(getFullyQualifiedName());
        builder.append('(');
        builder.append(arguments.toString());
        builder.append(')');

        return builder.toString();
    }
}
