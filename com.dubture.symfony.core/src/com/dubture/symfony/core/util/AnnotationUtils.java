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
import com.dubture.symfony.index.model.Route;

@SuppressWarnings("restriction")
public class AnnotationUtils {

    protected static final String[] PHPDOC_TAGS_EXTRA = {"api", "inheritdoc"};
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
        return extractAnnotations(createParser(), comment, sourceModule);
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
            int commentEndOffset = comment.getEnd();
            String commentSource = getCommentSource(sourceModule, commentStartOffset, commentEndOffset);

            return parser.parse(commentSource, commentStartOffset);
        } catch (Exception exception) {
            Logger.logException("Unable to extract annotations from comment", exception);
            return EMPTY_ANNOTATIONS;
        }
    }

    /**
     * Parse a comment from a PHP doc aware declaration. This is not the most accurate
     * way to extract annotations from a comment since the comment source returned by
     * the declaration is not really the same as the one found in the PHP file. Mainly,
     * some line endings and some formatting is lost when using this method.
     *
     * <p>
     * To get a more accurate extraction, provide the source module associated with
     * this declaration. Use {@link #extractAnnotations(IPHPDocAwareDeclaration, ISourceModule)}
     * to do this.
     * </p>
     *
     * @param declaration The declaration to parse the comment from
     *
     * @return A list of valid annotations
     */
    public static List<Annotation> extractAnnotations(IPHPDocAwareDeclaration declaration) {
        return extractAnnotations(declaration, null);
    }

    /**
     * See {@link #extractAnnotations(AnnotationCommentParser, IPHPDocAwareDeclaration,
     * ISourceModule)} for a more detailed description.
     *
     * @param declaration The declaration to parse the comment from
     * @param sourceModule The source module to extract the comment from
     *
     * @return A list of valid annotations
     */
    public static List<Annotation> extractAnnotations(IPHPDocAwareDeclaration declaration,
                                                      ISourceModule sourceModule) {
        return extractAnnotations(declaration, null);
    }

    /**
     * Parse a comment from a PHP doc aware declaration. This is not the most accurate
     * way to extract annotations from a comment since the comment source returned by
     * the declaration is not really the same as the one found in the PHP file. Mainly,
     * some line endings and some formatting is lost when using this method.
     *
     * <p>
     * To get a more accurate extraction, provide the source module associated with
     * this declaration. Use {@link #extractAnnotations(AnnotationCommentParser,
     * IPHPDocAwareDeclaration, ISourceModule)} to do this.
     * </p>
     *
     * @param parser The parser used to parse a comment
     * @param declaration The declaration to parse the comment from
     *
     * @return A list of valid annotations according to the parser
     */
    public static List<Annotation> extractAnnotations(AnnotationCommentParser parser,
                                                      IPHPDocAwareDeclaration declaration) {
        return extractAnnotations(parser, declaration, null);
    }

    /**
     * Parse a comment from a PHP doc aware declaration. By using the source module,
     * you ensure that source positions of the annotations will be set correctly
     * relative to the PHP file.
     *
     * @param parser The parser used to parse a comment
     * @param declaration The declaration to parse the comment from
     * @param sourceModule The source module to extract the comment from
     *
     * @return A list of valid annotations according to the parser
     */
    public static List<Annotation> extractAnnotations(AnnotationCommentParser parser,
                                                      IPHPDocAwareDeclaration declaration,
                                                      ISourceModule sourceModule) {
        try {
            PHPDocBlock comment = declaration.getPHPDoc();
            if (comment == null || comment.getCommentType() != Comment.TYPE_PHPDOC) {
                return EMPTY_ANNOTATIONS;
            }

            int commentStartOffset = comment.sourceStart();
            String commentSource;
            if (sourceModule != null) {
                commentSource = getCommentSource(sourceModule, commentStartOffset, comment.sourceEnd());
            } else {
                commentSource = comment.getShortDescription() + comment.getLongDescription();
            }

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

    /**
     * Get the comment source from a source module. This will simply get the source
     * and extract the substring representing the comment.
     *
     * @param sourceModule The source module to extract the comment from
     * @param start The start offset of the comment
     * @param end The end offset of the comment
     *
     * @return The comment source extracted from the source module
     *
     * @throws ModelException If an exception occurs while accessing the underlying resource
     */
    private static String getCommentSource(ISourceModule sourceModule, int start, int end) throws ModelException {
        String source = sourceModule.getSource();

        return source.substring(start, end);
    }
}
