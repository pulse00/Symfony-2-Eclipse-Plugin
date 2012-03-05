/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.util;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.ast.nodes.Comment;
import org.eclipse.php.internal.core.codeassist.strategies.PHPDocTagStrategy;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.ui.Logger;

import com.dubture.symfony.annotation.model.Annotation;
import com.dubture.symfony.annotation.parser.AnnotationCommentParser;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;
import com.dubture.symfony.index.dao.Route;

@SuppressWarnings("restriction")
public class AnnotationUtils {

    protected static String[] PHPDOC_TAGS_EXTRA = {"api", "inheritdoc"};
    protected static final List<Annotation> EMPTY_ANNOTATIONS = new LinkedList<Annotation>();

    public static List<Annotation> extractAnnotations(Comment comment, ISourceModule sourceModule) {
        if (comment.getCommentType() != Comment.TYPE_PHPDOC) {
            return EMPTY_ANNOTATIONS;
        }

        try {
            int commentStartOffset = comment.getStart();
            int commentEndOffset = comment.getStart() + comment.getLength();
            String source = sourceModule.getSource();
            String commentSource = source.substring(commentStartOffset, commentEndOffset);
            AnnotationCommentParser parser = createParser(commentSource, commentStartOffset);

            return parser.parse();
        } catch (ModelException exception) {
            Logger.logException("Unable to extract annotations from comment", exception);
            return EMPTY_ANNOTATIONS;
        }
    }

    public static List<Annotation> extractAnnotations(PHPMethodDeclaration methodDeclaration) {
        return extractAnnotations(methodDeclaration, null);
    }

    public static List<Annotation> extractAnnotations(PHPMethodDeclaration methodDeclaration,
                                                      String[] includedClassNames) {
        try {
            PHPDocBlock comment = methodDeclaration.getPHPDoc();
            if (comment == null || comment.getCommentType() != Comment.TYPE_PHPDOC) {
                return EMPTY_ANNOTATIONS;
            }

            int commentStartOffset = comment.sourceStart();
            String commentSource = comment.getShortDescription();
            AnnotationCommentParser parser = createParser(commentSource, commentStartOffset, includedClassNames);

            return parser.parse();
        } catch (Exception exception) {
            Logger.logException("Unable to extract annotations from method " + methodDeclaration.getName(), exception);
            return EMPTY_ANNOTATIONS;
        }
    }

    public static List<Annotation> extractAnnotations(ClassDeclaration classDeclaration) {
        return extractAnnotations(classDeclaration, null);
    }

    public static List<Annotation> extractAnnotations(ClassDeclaration classDeclaration, String[] includedClassNames) {
        try {
            PHPDocBlock comment = classDeclaration.getPHPDoc();
            if (comment == null || comment.getCommentType() != Comment.TYPE_PHPDOC) {
                return new LinkedList<Annotation>();
            }

            int commentStartOffset = comment.sourceStart();
            String commentSource = comment.getLongDescription();
            if (commentSource.length() == 0) {
                commentSource = comment.getShortDescription();
            }

            AnnotationCommentParser parser = createParser(commentSource, commentStartOffset, includedClassNames);

            return parser.parse();
        } catch (Exception exception) {
            Logger.logException("Unable to extract annotations from  class " + classDeclaration.getName(), exception);
            return EMPTY_ANNOTATIONS;
        }
    }

    public static Route extractRoute(Annotation routeAnnotation, String bundle, String controller, String action) {
        assert(routeAnnotation.getClassName().startsWith(SymfonyCoreConstants.ROUTE_ANNOTATION));

        String pattern = (String) routeAnnotation.getArgumentValue(0).getValue();
        String name = (String) routeAnnotation.getArgumentValue("name").getValue();

        return new Route(bundle, controller, action, name, pattern);
    }

    public static String extractTemplate(Annotation templateAnnotation,
                                         String bundle,
                                         String controller,
                                         String action) {
        assert(templateAnnotation.getClassName().startsWith(SymfonyCoreConstants.TEMPLATE_ANNOTATION));

        String defaultViewPath = bundle + ":" + controller + ":" + action;
        String viewPath = defaultViewPath;
        if (templateAnnotation.hasArgument(0)) {
            viewPath = (String) templateAnnotation.getArgumentValue(0).getValue();
        }

        return PathUtils.createViewPath(viewPath);
    }

    protected static AnnotationCommentParser createParser(String source, int sourceStartOffset) {
        return createParser(source, sourceStartOffset, null);
    }

    protected static AnnotationCommentParser createParser(String source,
                                                          int sourceStartOffset,
                                                          String[] includedClassNames) {
        AnnotationCommentParser parser = new AnnotationCommentParser(source, sourceStartOffset);
        parser.addExcludedClassNames(PHPDocTagStrategy.PHPDOC_TAGS);
        parser.addExcludedClassNames(PHPDOC_TAGS_EXTRA);
        if (includedClassNames != null) {
            parser.addIncludedClassNames(includedClassNames);
        }

        return parser;
    }
}
