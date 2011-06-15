#Symfony2 Eclipse Plugin

The plugin is in alpha state and still in development.

If you want to test it and use the existing features, you
can install the current version via the updatesite http://pulse00.github.com/Symfony-2-Eclipse-Plugin/

## Installation

* Add the http://pulse00.github.com/Symfony-2-Eclipse-Plugin/ to the available
software sites (Help -> Install new software...)
* Install the Symfony2 Feature and restart eclipse

The plugin requires Eclipse >= 3.6.

## Usage

To make a Symfony2 PHP project symfony-aware, right-click on the project in the explorer and
run the "Add/Remove Symfony Nature" action in the "Symfony" submenu.

After adding the symfony nature, rebuild the project (Project -> clean). 


## Currently implemented features

### Annotations

When the curser is located after the @ character inside a PHPDocBlock, triggering the codeassistance (Ctrl + .) will
display a list of available classes. 

By selecting a class the fully qualified classname will be automatically imported.

There's currently no general solution to detect if a PHP class is explicitly designed to be used
as an annotation, so you'll get the full list of all classes available in the project (see http://www.doctrine-project.org/jira/browse/DDC-1198).

Annotations provide validation for syntax errors and/or missing imports. The error-reporting level of the validation can be set in the 
Symfony preference page.


### Dependency Injection Container


Classes implementing the `Symfony\Component\DependencyInjection\ContainerAware` interface will provide code assist in the following contexts:


#### Servicename completion

`$this->get('`   or `$this->container->get('` will provide a list of available services.

#### Type inference


    $session = $this->get('session');
    $session->  // <--- will show the methods of the 'session' service.



## Limitations

Services injected dynamically can't provide code assistance at the moment.


## Twig

There's a separate (alpha) Twig plugin available: https://github.com/pulse00/Twig-Eclipse-Plugin

Both plugins will eventually be able to communicate with each other, so providing contextual Symfony2
code-assistance in twig templates can be added to the featurelist ;)
 

## FAQ

Q: How do i know a PHP project is symfony-aware?

A: Check for a "Symfony Builder" in the projects builders (Project -> Properties -> Builders). If it's not there, run the "Add/Remove Symfony Nature" (right-click on the project -> Symfony).



Q: There's no additional codeassistance showing up, what can i do?

A: The plugin indexes services and annotation during the build process. Try to clean the project (Project -> clean). If it's still not working, check if the project has a Symfony nature.


## Contribute

If you're interested in contributing you can get started by setting up the [development environment](https://github.com/pulse00/Symfony-2-Eclipse-Plugin/wiki/Development-environment).

Please report any bugs or feature requests through github issues.