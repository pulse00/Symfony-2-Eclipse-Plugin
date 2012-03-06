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
import org.eclipse.php.internal.core.compiler.ast.nodes.IPHPDocAwareDeclaration;
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

    /**
     * See {@link #extractAnnotations(AnnotationCommentParser, Comment, ISourceModule)} for
     * a more detailed description.
     *
     * @param comment The comment node to parse the comment from
     * @param sourceModule The source module where the comment is located
     *
     * @return A list of valid annotations
     */
    public static List<Annotation> extractAnnotations(Comment comment, ISourceModule sourceModule) {
        return extractAnnotations(comment, sourceModule, null);
    }

    /**
     * See {@link #extractAnnotations(AnnotationCommentParser, Comment, ISourceModule)} for
     * a more detailed description.
     *
     * @param comment The comment node to parse the comment from
     * @param sourceModule The source module where the comment is located
     * @param includedClassNames The array of included class names
     *
     * @return A list of valid annotations
     */
    public static List<Annotation> extractAnnotations(Comment comment,
                                                      ISourceModule sourceModule,
                                                      String[] includedClassNames) {
        return extractAnnotations(createParser(includedClassNames), comment, sourceModule);
    }

    /**
     * Parse a comment from a comment PHP AST node. This is the preferred method to
     * use if you need exact source position in the annotations returned.
     *
     * @param parser The parser used to parse a comment
     * @param comment The comment node to parse the comment from
     * @param sourceModule The source module where the comment is located
     *
     * @return A list of valid annotations
     */
    public static List<Annotation> extractAnnotations(AnnotationCommentParser parser,
                                                      Comment comment,
                                                      ISourceModule sourceModule) {
        if (comment == null || comment.getCommentType() != Comment.TYPE_PHPDOC) {
            return EMPTY_ANNOTATIONS;
        }

        try {
            int commentStartOffset = comment.getStart();
            int commentEndOffset = comment.getStart() + comment.getLength();
            String source = sourceModule.getSource();
            String commentSource = source.substring(commentStartOffset, commentEndOffset);

            return parser.parse(commentSource, commentStartOffset);
        } catch (ModelException exception) {
            Logger.logException("Unable to extract annotations from comment", exception);
            return EMPTY_ANNOTATIONS;
        }
    }

    /**
     * See {@link #extractAnnotations(AnnotationCommentParser, IPHPDocAwareDeclaration)} for
     * a more detailed description.
     *
     * @param declaration The declaration to parse the comment from
     *
     * @return A list of valid annotations
     */
    public static List<Annotation> extractAnnotations(IPHPDocAwareDeclaration declaration) {
        return extractAnnotations(declaration, null);
    }

    /**
     * See {@link #extractAnnotations(AnnotationCommentParser, IPHPDocAwareDeclaration)} for
     * a more detailed description.
     *
     * @param declaration The declaration to parse the comment from
     * @param includedClassNames The array of included class names
     *
     * @return A list of valid annotations
     */
    public static List<Annotation> extractAnnotations(IPHPDocAwareDeclaration declaration,
                                                      String[] includedClassNames) {
        return extractAnnotations(createParser(includedClassNames), declaration);
    }

    /**
     * Parse a comment from a PHP doc aware declaration. For now, this not the best way to
     * extract annotations from a comment since there is no way to retrieve the exact
     * comment as it appears in the source. To do extraction on the exact comment source,
     * refer to {@link #extractAnnotations(Comment, ISourceModule)} or
     * {@link #extractAnnotations(AnnotationCommentParser, Comment, ISourceModule)} for a
     * more accurate extraction method.
     *
     * @param parser The parser used to parse a comment
     * @param declaration The declaration to parse the comment from
     *
     * @return A list of valid annotations according to the parser
     */
    public static List<Annotation> extractAnnotations(AnnotationCommentParser parser,
                                                      IPHPDocAwareDeclaration declaration) {
        try {
            PHPDocBlock comment = declaration.getPHPDoc();
            if (comment == null || comment.getCommentType() != Comment.TYPE_PHPDOC) {
                return EMPTY_ANNOTATIONS;
            }

            int commentStartOffset = comment.sourceStart();
            String commentSource = comment.getShortDescription() + comment.getLongDescription();

            return parser.parse(commentSource, commentStartOffset);
        } catch (Exception exception) {
            Logger.logException("Unable to extract annotations from declaration " + getDeclarationName(declaration), exception);
            return EMPTY_ANNOTATIONS;
        }
    }

    /**
     * This will create a {@link Route} object from a route annotation.
     *
     * @param routeAnnotation The annotation to extract the route from
     * @param bundle The bundle where the annotation is located
     * @param controller The controller where the annotation is located
     * @param action The action associated with the route annotation
     *
     * @return The {@link Route} object represented by the annotation
     */
    public static Route extractRoute(Annotation routeAnnotation, String bundle, String controller, String action) {
        assert(routeAnnotation.getClassName().startsWith(SymfonyCoreConstants.ROUTE_ANNOTATION));

        String pattern = (String) routeAnnotation.getArgumentValue(0).getValue();
        String name = (String) routeAnnotation.getArgumentValue("name").getValue();

        return new Route(bundle, controller, action, name, pattern);
    }

    /**
     * This will create a view path from a template annotation. This method uses
     * {@link PathUtils#createViewPath(String)} internally to convert the annotation
     * to a view path.
     *
     * @param templateAnnotation The annotation to extract the template view path from
     * @param bundle The bundle where the annotation is located
     * @param controller The controller where the annotation is located
     * @param action The action associated with the template annotation
     *
     * @return The view path represented by the annotation
     */
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

    /**
     * This will create a default {@link AnnotationCommentParser}. This default
     * parser will exclude base PHP doc tags and also some extra PHP doc tags (
     * {@literal @inheritdoc} and {@literal @api}).
     *
     * @return A default {@link AnnotationCommentParser}
     */
    public static AnnotationCommentParser createParser() {
        return createParser(null);
    }

    /**
     * This will create a default {@link AnnotationCommentParser}. This default
     * parser will exclude base PHP doc tags and also some extra PHP doc tags (
     * {@literal @inheritdoc} and {@literal @api}).
     *
     * <p>
     * With this method, you can also specify which classes would like to include
     * in the list of annotations. This is useful when only want a certain types
     * of annotation like {@literal @Template} ones. The class names are the fully
     * qualified names. So, to include only {@literal "@Orm\Entity"} annotations,
     * you will need to pass the array new String[] {"@Orm\Entity"} to the method.
     * </p>
     *
     * @param includedClassNames The class names to includes, can be null
     *
     * @return A default {@link AnnotationCommentParser} set to include only the classes specified
     */
    public static AnnotationCommentParser createParser(String[] includedClassNames) {
        AnnotationCommentParser parser = new AnnotationCommentParser();
        parser.addExcludedClassNames(PHPDocTagStrategy.PHPDOC_TAGS);
        parser.addExcludedClassNames(PHPDOC_TAGS_EXTRA);
        if (includedClassNames != null) {
            parser.addIncludedClassNames(includedClassNames);
        }

        return parser;
    }

    /**
     * This is used for logging purpose. It takes an IPHPDocAwareDeclaration declaration
     * and tries to extract the name from it. For now, this method handle method and
     * class declaration.
     *
     * @param declaration The PHP doc aware declaration
     *
     * @return The name associated with this declaration if it can be found, string "unknown" otherwise
     */
    private static String getDeclarationName(IPHPDocAwareDeclaration declaration) {

        if (declaration instanceof PHPMethodDeclaration) {
            return "method: " + ((PHPMethodDeclaration) declaration).getName();
        }

        if (declaration instanceof ClassDeclaration) {
            return "class: " + ((ClassDeclaration) declaration).getName();
        }

        return "unknown";
    }
}
