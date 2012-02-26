package com.dubture.symfony.annotation.model;

import java.util.Stack;

import com.dubture.symfony.annotation.parser.antlr.SourcePosition;

public class AnnotationClass extends AnnotationSourceElement {

    protected String className = "";
    protected Stack<String> namespace = new Stack<String>();

    protected SourcePosition classNamePosition = new SourcePosition();
    protected SourcePosition namespacePosition = new SourcePosition();

    public SourcePosition getClassNamePosition() {
        return classNamePosition;
    }

    public void setClassNamePosition(SourcePosition classNamePosition) {
        this.classNamePosition = classNamePosition;
    }

    public SourcePosition getNamespacePosition() {
        return namespacePosition;
    }

    public void setNamespacePosition(SourcePosition namespacePosition) {
        this.namespacePosition = namespacePosition;
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

    public String getFirstNamespacePart() {
        if (namespace.size() > 0) {
            return namespace.get(0);
        }

        return null;
    }

    public void pushNamespaceSegment(String namespaceSegment) {
        namespace.push(namespaceSegment);
    }
}
