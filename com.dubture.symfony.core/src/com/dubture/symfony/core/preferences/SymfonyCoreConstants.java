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
    public static final String TEMPLATE_ANNOTATION   = "Template";
    public static final String ROUTE_ANNOTATION      = "Route";

    // prefix for methods which render views
    public static final String RENDER_PREFIX         = "render";

    // namespaces
    public static final String CONTROLLER_NS         = "Symfony\\Bundle\\FrameworkBundle\\Controller";

    // core classes
    public static final String BUNDLE_FQCN           = "Symfony\\Component\\HttpKernel\\Bundle\\Bundle";

    // the symfony version
    public static final String SYMFONY_VERSION       = "symfony_version";

    // Paths
    public static final String DEFAULT_CONSOLE		 = "app/console";
    public static final String DEFAULT_CONTAINER     = "app/cache/dev/appDevDebugProjectContainer.xml";
    public static final String APP_PATH              = "app";
    public static final String VENDOR_PATH           = "vendor";
    public static final String CACHE_PATH            = "app/cache";
    public static final String LOG_PATH              = "app/logs";

    public static final String CACHE_PATTERN         = "**/cache/";
    public static final String LOG_PATTERN           = "**/logs/";
    public static final String SKELETON_PATTERN      = "**/skeleton/";
    public static final String TEST_PATTERN          = "**/Test*/";
    public static final String CG_FIXTURE_PATTERN    = "**/CG/**/Fixture/";
    public static final String SRC_PATH              = "src";
    public static final String WEB_PATH              = "web";
    public static final String WEB_BUNDLE_PATH       = "bundles/*";

    public static final String BUILTIN_SYMFONY       = "/Resources/vendor/symfony2/";
    public static final String BUILTIN_VENDOR        = "vendors";
    public static final String BUILTIN_NO_VENDOR     = "novendors";

    // routing types
    public static final String ANNOTATION            = "annotation";
    public static final String YAML                  = "yaml";
    public static final String XML                   = "xml";

	public static final String SYMFONY_STANDARD_EDITION = "symfony/framework-standard-edition";

	public static final String CONTROLLER_PARENT     = "Symfony\\Bundle\\FrameworkBundle\\Controller\\Controller";

	public static final String CONTAINER_INTERFACE   = "Symfony\\Component\\DependencyInjection\\ContainerInterface";
	
	public static final String DOCTRINE_REGISTRY_NAME         = "Doctrine\\Bundle\\DoctrineBundle\\Registry";



}
