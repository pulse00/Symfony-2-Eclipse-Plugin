/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.util;

import com.dubture.doctrine.annotation.model.Annotation;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;
import com.dubture.symfony.index.model.Route;

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
        String name = (String) routeAnnotation.getArgumentValue("name").getValue(); //$NON-NLS-1$

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

        String defaultViewPath = bundle + ":" + controller + ":" + action; //$NON-NLS-1$ //$NON-NLS-2$
        String viewPath = defaultViewPath;
        if (templateAnnotation.hasArgument(0)) {
            viewPath = (String) templateAnnotation.getArgumentValue(0).getValue();
        }

        return PathUtils.createViewPath(viewPath);
    }
}
