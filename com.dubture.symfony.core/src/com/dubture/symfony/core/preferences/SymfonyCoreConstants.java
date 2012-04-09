/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.preferences;




/**
 *
 * Preference constants for the Symfony plugin.
 *
 * @see PreferenceInitializer, SymfonyCorePreferences
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class SymfonyCoreConstants {

    /**
    * Option for severity level or annotation problems
    */
    public static final String ANNOTATION_PROBLEM_SEVERITY = "annotation_problem_severity";


    /**
    * Annotation problem severity levels.
    */
    public static final String ANNOTATION_ERROR      = "Error";
    public static final String ANNOTATION_WARNING    = "Warning";
    public static final String ANNOTATION_IGNORE     = "Ignore";


    // Symfony Class and Method names
    public static final String CONTROLLER_CLASS      = "Controller";
    public static final String ACTION_SUFFIX         = "Action";
    public static final String APP_KERNEL            = "AppKernel";



    // FrameworkExtraBundle Annotations
    public static final String TEMPLATE_ANNOTATION   = "@Template";
    public static final String ROUTE_ANNOTATION      = "@Route";

    // prefix for methods which render views
    public static final String RENDER_PREFIX         = "render";


    // namespaces
    public static final String CONTROLLER_NS         ="Symfony\\Bundle\\FrameworkBundle\\Controller";

    // core classes
    public static final String BUNDLE_FQCN           = "Symfony\\Component\\HttpKernel\\Bundle\\Bundle";

    // the symfony version
    public static final String SYMFONY_VERSION       = "symfony_version";

    // Paths
    public static final String APP_PATH              = "app";
    public static final String VENDOR_PATH           = "vendor";
    public static final String CACHE_PATH            = "**/cache/";
    public static final String LOG_PATH              = "**/logs/";
    public static final String SKELETON_PATH         = "**/skeleton/";
    public static final String TEST_PATH             = "**/Tests/";
    public static final String TEST_PATH_LC          = "**/tests/";
    public static final String CG_FIXTURE_PATH       = "**/CG/**/Fixture/";
    public static final String SRC_PATH              = "src";

    public static final String BUILTIN_SYMFONY       = "/Resources/vendor/symfony2/";
    public static final String BUILTIN_VENDOR        = "vendors";
    public static final String BUILTIN_NO_VENDOR     = "novendors";


    // routing types
    public static final String ANNOTATION            = "annotation";
    public static final String YAML                  = "yaml";
    public static final String XML                   = "xml";



}
