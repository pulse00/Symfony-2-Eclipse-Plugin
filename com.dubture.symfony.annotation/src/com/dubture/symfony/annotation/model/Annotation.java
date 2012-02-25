package com.dubture.symfony.annotation.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.dubture.symfony.annotation.parser.antlr.Position;

/**
 * This object represents a Symfony2 annotation. It holds
 * information about the annotation like the class name,
 * the namespace and its argument.
 *
 * <p>
 * There is two kind of argument: Argument and NamedArgument.
 * </p>
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class Annotation {

    protected Position startPosition;
    protected Position endPosition;

    protected String className = "";
    protected Stack<String> namespace = new Stack<String>();
    protected List<Argument> arguments = new LinkedList<Argument>();

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

    public List<Argument> getArguments() {
        return arguments;
    }

    public Map<String, NamedArgument> getNamedArguments() {
        Map<String, NamedArgument> namedArguments = new HashMap<String, NamedArgument>();

        for (Argument argument : arguments) {
            if (argument instanceof NamedArgument) {
                NamedArgument namedArgument = (NamedArgument) argument;
                namedArguments.put(namedArgument.getName(), namedArgument);
            }
        }

        return namedArguments;
    }

    public void addArgument(Argument argument) {
        arguments.add(argument);
    }

    public void addArgument(String name, ArgumentValue value) {
        arguments.add(new NamedArgument(name, value));
    }

    public String getArgument(String name) {
        ArgumentValue argumentValue = getArgumentValue(name);
        if (argumentValue == null) {
            return null;
        }

        return argumentValue.toString();
    }

    public ArgumentValue getArgumentValue(String name) {
        for (Argument argument : arguments) {
            if (!(argument instanceof NamedArgument)) {
                continue;
            }

            NamedArgument namedArgument = (NamedArgument) argument;
            if (namedArgument.getName().equals(name)) {
                return namedArgument.getValue();
            }
        }

        return null;
    }

    public String getFirstNamespacePart() {
        if (namespace.size() > 0) {
            return namespace.get(0);
        }

        return null;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Position endPosition) {
        this.endPosition = endPosition;
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
