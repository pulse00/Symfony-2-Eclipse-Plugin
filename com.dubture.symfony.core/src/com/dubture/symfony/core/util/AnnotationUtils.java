/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.util;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.IPHPDocAwareDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;

import com.dubture.doctrine.annotation.model.Annotation;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;
import com.dubture.symfony.index.model.Route;

@SuppressWarnings("restriction")
public class AnnotationUtils {
  
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
