package com.dubture.symfony.annotation.model;

import java.util.List;
import java.util.Map;

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
public class Annotation extends AnnotationSourceElement {

    protected AnnotationClass annotationClass = new AnnotationClass();
    protected AnnotationDeclaration annotationDeclaration = new AnnotationDeclaration();

    public AnnotationClass getAnnotationClass() {
        return annotationClass;
    }

    public AnnotationDeclaration getDeclaration() {
        return annotationDeclaration;
    }

    /**
     * Returns the class name of the annotation. With the
     * string "Orm\Assert\Special", the class name returned
     * will be "Special".
     *
     * @return The class name of the annotation
     */
    public String getClassName() {
        return annotationClass.getClassName();
    }

    public void setClassName(String className) {
        annotationClass.setClassName(className);
    }

    public String getNamespace() {
        return annotationClass.getNamespace();
    }

    public void pushNamespaceSegment(String namespaceSegment) {
        annotationClass.pushNamespaceSegment(namespaceSegment);
    }

    public String getFullyQualifiedName() {
        return annotationClass.getFullyQualifiedName();
    }

    public List<Argument> getArguments() {
        return annotationDeclaration.getArguments();
    }

    public Map<String, NamedArgument> getNamedArguments() {
        return annotationDeclaration.getNamedArguments();
    }

    public void addArgument(Argument argument) {
        annotationDeclaration.addArgument(argument);
    }

    public void addArgument(String name, IArgumentValue value) {
        annotationDeclaration.addArgument(name, value);
    }

    public boolean hasArgument(int index) {
        return annotationDeclaration.hasArgument(index);
    }

    public String getArgument(int index) {
        return annotationDeclaration.getArgument(index);
    }

    public String getArgument(String name) {
        return annotationDeclaration.getArgument(name);
    }

    public IArgumentValue getArgumentValue(int index) {
        return annotationDeclaration.getArgumentValue(index);
    }

    public IArgumentValue getArgumentValue(String name) {
        return annotationDeclaration.getArgumentValue(name);
    }

    public String getFirstNamespacePart() {
        return annotationClass.getFirstNamespacePart();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append('@');
        builder.append(getFullyQualifiedName());
        builder.append(annotationDeclaration.toString());

        return builder.toString();
    }
}
