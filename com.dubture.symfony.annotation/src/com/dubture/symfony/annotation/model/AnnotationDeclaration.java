package com.dubture.symfony.annotation.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The annotation declaration is in fact a list of arguments. It
 * is what between the parenthesis of an Annotation. For example,
 * in the string {@literal "@Orm\Join("argument")"}, the declaration
 * is represented by the parenthesis ({@literal "("argument")"}).
 *
 * <p>
 * The main purpose of this class is to hold the source position
 * of this annotation element. This way, the highlighting can be
 * done more precisely if needed.
 * </p>
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class AnnotationDeclaration extends AnnotationSourceElement {

    protected List<Argument> arguments = new LinkedList<Argument>();

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

    public void addArgument(String name, IArgumentValue value) {
        arguments.add(new NamedArgument(name, value));
    }

    public String getArgument(String name) {
        IArgumentValue argumentValue = getArgumentValue(name);
        if (argumentValue == null) {
            return null;
        }

        return argumentValue.toString();
    }

    public IArgumentValue getArgumentValue(String name) {
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

    @Override
    public String toString() {
        return "(" + arguments.toString() + ")";
    }

}
