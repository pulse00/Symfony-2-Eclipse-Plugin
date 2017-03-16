/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.INamespace;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.index2.IElementResolver;
import org.eclipse.dltk.core.index2.search.ISearchEngine;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.index2.search.ISearchEngine.SearchFor;
import org.eclipse.dltk.core.index2.search.ISearchRequestor;
import org.eclipse.dltk.core.index2.search.ModelAccess;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.ScriptFolder;
import org.eclipse.dltk.internal.core.ScriptProject;
import org.eclipse.dltk.internal.core.util.LRUCache;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;

import com.dubture.symfony.core.SymfonyLanguageToolkit;
import com.dubture.symfony.core.index.SymfonyElementResolver.TemplateField;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;
import com.dubture.symfony.core.util.JsonUtils;
import com.dubture.symfony.core.util.ModelUtils;
import com.dubture.symfony.core.util.PathUtils;
import com.dubture.symfony.index.SymfonyIndexer;
import com.dubture.symfony.index.handler.IServiceHandler;
import com.dubture.symfony.index.handler.ITranslationHandler;
import com.dubture.symfony.index.model.Parameter;
import com.dubture.symfony.index.model.Route;
import com.dubture.symfony.index.model.TransUnit;

/**
 *
 * The {@link SymfonyModelAccess} is an extension to the
 * {@link PhpModelAccess} and provides additional helper
 * methods to find Symfony2 model elements.
 *
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyModelAccess extends PhpModelAccess {


    private static SymfonyModelAccess modelInstance = null;
    private SymfonyIndexer index;
    private Map<Service, IType[]> serviceCache = new HashMap<Service, IType[]>();

    private LRUCache controllerCache = new LRUCache();
    private LRUCache serviceCache2 = new LRUCache();
    private LRUCache entityCache = new LRUCache();
    private LRUCache bundleCache = new LRUCache();

    private static final String NULL_ENTRY = "$$THIS_IS_NULL_ENTRY$$";

    private SymfonyModelAccess() {

        try {
            index = SymfonyIndexer.getInstance();
        } catch (Exception e) {
            Logger.logException(e);
        }
    }

    public static SymfonyModelAccess getDefault() {

        if (modelInstance == null)
            modelInstance = new SymfonyModelAccess();

        return modelInstance;
    }
    
    public TemplateVariable createTemplateVariableByReturnType(ISourceModule sourceModule, PHPMethodDeclaration controllerMethod, SimpleReference callName,
            String className, String namespace, String variableName) {
    	return createTemplateVariableByReturnType(sourceModule, controllerMethod, callName, className, namespace, variableName, null);
    }


    public TemplateVariable createTemplateVariableByReturnType(ISourceModule sourceModule, PHPMethodDeclaration controllerMethod, SimpleReference callName,
            String className, String namespace, String variableName, IModelAccessCache cache) {
    	IType[] types = null;
    	if (cache != null) {
    		Collection<IType> typesList = cache.getTypes(sourceModule, className, namespace, null);
    		types = typesList.toArray(new IType[typesList.size()]);
    	} else {
	        IDLTKSearchScope scope = createSearchScope(sourceModule);
	
	        if (scope == null)
	            return null;
	
	        types = findTypes(namespace, className, MatchRule.EXACT, 0, 0, scope, null);
    	}
        if (types.length != 1)
            return null;

        IType type = types[0];

        final IMethod method = type.getMethod(callName.getName());

        if (method == null)
            return null;


        ModuleDeclaration module = SourceParserUtil.getModuleDeclaration(method.getSourceModule());
        ReturnTypeVisitor visitor = new ReturnTypeVisitor(method.getElementName());
        try {
            module.traverse(visitor);
        } catch (Exception e) {
            Logger.logException(e);
        }

        if (visitor.className == null || visitor.namespace == null)
            return null;

        return new TemplateVariable(controllerMethod, variableName, callName.sourceStart(), callName.sourceEnd(), visitor.namespace, visitor.className);

    }

    protected IDLTKSearchScope createSearchScope(ISourceModule module) {

        IScriptProject scriptProject = module.getScriptProject();
        if (scriptProject != null) {
            return SearchEngine.createSearchScope(scriptProject);
        }

        return null;
    }

    private class ReturnTypeVisitor extends PHPASTVisitor {


        public String namespace;
        public String className;
        private String method;

        public ReturnTypeVisitor(String method) {
            this.method = method;
        }

        @Override
        public boolean visit(NamespaceDeclaration s) throws Exception {
            namespace = s.getName();
            return true;
        }

        @Override
        public boolean visit(PHPMethodDeclaration s) throws Exception {
            if (s.getName().equals(method)) {
                PHPDocBlock docs = s.getPHPDoc();
                PHPDocTag[] returnTags = docs.getTags(PHPDocTag.TagKind.RETURN);
                if (returnTags.length == 1) {
                    PHPDocTag tag = returnTags[0];

                    if (tag.getTypeReferences().size() == 1) {
                        SimpleReference ref = tag.getTypeReferences().get(0);
                        className = ref.getName();
                        return false;
                    }
                }

            }
            return true;
        }

    }


    /**
    *
    * Resolve TemplateVariables for a controller.
    *
    *
    * @param controller
    * @return
    */
    public List<TemplateField> findTemplateVariables(IType controller) {
        if (controller == null) {
            return new ArrayList<TemplateField>();
        }

        // create a searchscope for the whole ScriptProject,
        // as view variables can be declared across controllers
        IDLTKSearchScope scope = SearchEngine.createSearchScope(controller.getScriptProject());
        ISearchEngine engine = ModelAccess.getSearchEngine(SymfonyLanguageToolkit.getDefault());
        final List<TemplateField> variables = new ArrayList<TemplateField>();
        if (scope == null || engine == null) {
            return variables;
        }

        final IElementResolver resolver = ModelAccess.getElementResolver(SymfonyLanguageToolkit.getDefault());
        engine.search(ISymfonyModelElement.TEMPLATE_VARIABLE, null, null, 0, 0, 100, SearchFor.REFERENCES, MatchRule.PREFIX, scope, new ISearchRequestor() {
            @Override
            public void match(int elementType, int flags, int offset, int length,
                    int nameOffset, int nameLength, String elementName,
                    String metadata, String doc, String qualifier, String parent,
                    ISourceModule sourceModule, boolean isReference) {
                IModelElement element = resolver.resolve(elementType, flags, offset, length, nameOffset, nameLength, elementName, metadata, doc, qualifier, parent, sourceModule);

                if (element != null) {
                    if (element instanceof TemplateField)
                        variables.add((TemplateField) element);
                }
            }
        }, null);

        return variables;
    }


    /**
    * Try to find the corresponding controller IType for
    * a given template.
    *
    * @param module
    * @return
    */
    public IType findControllerByTemplate(ISourceModule module) {

        // get the name of the Controller to search for
        String controller = PathUtils.getControllerFromTemplatePath(module.getPath());

        if (controller == null) {
            return null;
        }

        // find the type
        IType types[] = PhpModelAccess.getDefault().findTypes(controller,
                MatchRule.EXACT, 0, 0,
                SearchEngine.createSearchScope(module.getScriptProject()), null);

        // type is ambigous
        if (types.length != 1)
            return null;


        return types[0];


    }


    /**
    *
    *
    *
    * @param variableName
    * @param sourceModule
    * @return
    */
    public TemplateField findTemplateVariableType(String variableName, ISourceModule sourceModule) {
        // find the corresponding controller for the template
        IType controller = findControllerByTemplate(sourceModule);
        if (controller == null) {
            return null;
        }

        // create a searchscope for the Controller class only
        IDLTKSearchScope scope = SearchEngine.createSearchScope(controller);
        ISearchEngine engine = ModelAccess.getSearchEngine(SymfonyLanguageToolkit.getDefault());
        if (scope == null || engine == null) {
            return null;
        }

        final List<TemplateField> variables = new ArrayList<TemplateField>();
        final IElementResolver resolver = ModelAccess.getElementResolver(SymfonyLanguageToolkit.getDefault());

        engine.search(ISymfonyModelElement.TEMPLATE_VARIABLE, null, variableName, 0, 0, 100, SearchFor.REFERENCES, MatchRule.EXACT, scope, new ISearchRequestor() {
            @Override
            public void match(int elementType, int flags, int offset, int length,
                    int nameOffset, int nameLength, String elementName,
                    String metadata, String doc, String qualifier, String parent,
                    ISourceModule sourceModule, boolean isReference) {
                IModelElement element = resolver.resolve(elementType, flags, offset, length, nameOffset, nameLength, elementName, metadata, doc, qualifier, parent, sourceModule);

                if (element != null && element instanceof TemplateField) {
                    variables.add((TemplateField) element);
                }
            }
        }, null);

        if (variables.size() > 0)
            return variables.get(0);

        return null;
    }

    public List<Parameter> findParameters(IScriptProject project) {
    	return index.findParameters(project.getPath());
    }

    /**
    *
    * Return a List of all Routes for a given project.
    *
    * @param project
    * @return
    */
    public List<Route> findRoutes(IScriptProject project) {
        if (index == null) {
            Logger.log(Logger.ERROR, "The SymfonyIndexer has not been instantiated...");
            return new ArrayList<Route>();
        }

        return index.findRoutes(project.getPath());
    }

    /**
    * Find routes by prefix.
    *
    * @param project
    * @param prefix
    * @return
    */
    public List<Route> findRoutes(IScriptProject project, String prefix) {


        if (index == null) {
            Logger.log(Logger.ERROR, "The SymfonyIndexer has not been instantiated...");
            return new ArrayList<Route>();
        }


        return index.findRoutes(project.getPath(), prefix);

    }
    /**
    *
    * Retrieve all bundles inside a Project.
    *
    * @param project
    * @return
    */
    public List<Bundle> findBundles(IScriptProject project) {
        IDLTKSearchScope scope = SearchEngine.createSearchScope(project.getScriptProject());
        ISearchEngine engine = ModelAccess.getSearchEngine(PHPLanguageToolkit.getDefault());
        final List<Bundle> bundles = new ArrayList<Bundle>();
        if (scope == null || engine == null) {
            return bundles;
        }

        engine.search(ISymfonyModelElement.BUNDLE, null, null, 0, 0, 100, SearchFor.REFERENCES, MatchRule.PREFIX, scope, new ISearchRequestor() {
            @Override
            public void match(int elementType, int flags, int offset, int length,
                    int nameOffset, int nameLength, String elementName,
                    String metadata, String doc, String qualifier, String parent,
                    ISourceModule sourceModule, boolean isReference) {
                bundles.add(JsonUtils.unpackBundle(metadata));
            }
        }, null);

        return bundles;
    }


    /**
    * Finds the {@link ScriptFolder} of corresponding bundle inside the project.
    *
    * @param bundle
    * @param project
    * @return
    */
    public ScriptFolder findBundleFolder(String bundle, IScriptProject project) {
        IType bundleType = findBundleType(bundle, project);
        return  (ScriptFolder) bundleType.getSourceModule().getParent();

    }
    
    public IType findBundleType(String bundle, IScriptProject project)
    {
    	  IDLTKSearchScope scope = SearchEngine.createSearchScope(project);

          IType[] types = findTypes(bundle, MatchRule.EXACT, 0, 0, scope, null);

          if (types.length != 1)
              return null;
          
          return types[0];
    }

    /**
    *
    * Find all controllers in a given bundle.
    *
    * @param bundle
    * @param project
    * @return
    */
    public IType[] findBundleControllers(String bundle, IScriptProject project) {


        ScriptFolder bundleFolder = findBundleFolder(bundle, project);
        if(bundleFolder == null)
            return null;

        ISourceModule controllerSource = bundleFolder.getSourceModule("Controller");

        if (controllerSource == null)
            return null;

        IDLTKSearchScope controllerScope = SearchEngine.createSearchScope(controllerSource);


        return findTypes("", MatchRule.PREFIX, 0, 0, controllerScope, null);

    }

    public List<IPath> findBundleViewPaths(String bundle, IScriptProject project)
    {
        ScriptFolder folder = findBundleFolder(bundle, project);
        List<IPath> viewPaths = new LinkedList<IPath>();

        if (folder == null) {
            return viewPaths;
        }

        try {

            IResource resource = folder.getUnderlyingResource();
            List<IPath> structure = new LinkedList<IPath>();
            IPath path = resource.getFullPath();
            path = path.removeFirstSegments(1).append("Resources").append("views");
            int num = path.segmentCount() + 1;

            IFolder viewFolder = project.getProject().getFolder(path);
            getFolderStructure(viewFolder, structure);

            for (IPath p : structure) {
                viewPaths.add(p.removeFirstSegments(num));
            }

        } catch (ModelException e) {
            Logger.logException(e);
        } catch (CoreException e) {
            Logger.logException(e);
        }

        return viewPaths;

    }

    protected List<IPath> getFolderStructure(IFolder folder, List<IPath> structure) throws CoreException
    {
        for (IResource child : folder.members()) {
            if (child instanceof IFolder) {
                structure.add(child.getFullPath());
                getFolderStructure((IFolder) child, structure);
            }
        }
        return structure;
    }


    /**
    * Retrieve templates for a given bundle and controller path inside
    * the ScriptProject.
    *
    * Returns an empty array if nothing found.
    *
    *
    * @param bundle
    * @param controller
    * @param project
    * @return
    */
    public IModelElement[] findTemplates(String bundle, String controller, IScriptProject project) {

        try {

            ScriptFolder bundleFolder = findBundleFolder(bundle, project);
            IPath relative = new Path("Resources/views/" + controller.replace("Controller", ""));
            IPath path = bundleFolder.getPath().append(relative);
            IScriptFolder sfolder = project.findScriptFolder(path);

            if (sfolder != null && sfolder.exists() && sfolder.hasChildren()) {
                return sfolder.getChildren();
            }

        } catch (Exception e) {
            Logger.logException(e);
        }

        return new IModelElement[] {};
    }

    /**
    * Check if the {@link ScriptProject} has a PHPMethod
    * named "method" which accepts viewPaths as parameter.
    *
    * @param method
    * @param project
    * @return
    */
    public boolean hasViewMethod(final String method, IScriptProject project) {
        IDLTKSearchScope scope = SearchEngine.createSearchScope(project);
        ISearchEngine engine = getSearchEngine(PHPLanguageToolkit.getDefault());
        if (scope == null || engine == null) {
            return false;
        }

        final List<String> methods = new ArrayList<String>();
        engine.search(ISymfonyModelElement.VIEW_METHOD, null, method, 0, 0, 10, SearchFor.REFERENCES, MatchRule.EXACT, scope, new ISearchRequestor() {

            @Override
            public void match(int elementType, int flags, int offset, int length,
                    int nameOffset, int nameLength, String elementName,
                    String metadata, String doc, String qualifier, String parent,
                    ISourceModule sourceModule, boolean isReference) {

                methods.add(elementName);

            }
        }, null);

        return methods.size() > 0;
    }

    /**
    * Find ITypes of a service
    *
    *
    * @param service
    * @param project
    * @return
    */
    public IType[] findServiceTypes(Service service, IScriptProject project) {

        if (serviceCache.containsKey(service))
            return serviceCache.get(service);

        try {
            IDLTKSearchScope scope = SearchEngine.createSearchScope(project);
            String namespace = service.getNamespace() != null ? service.getNamespace().getQualifiedName() : null;
            IType[] types = findTypes(namespace, service.getClassName(), MatchRule.EXACT, 0, 0, scope, null);
            serviceCache.put(service, types);
            return types;

        } catch (Exception e) {
            Logger.logException(e);

        }

        if (service != null)
            Logger.debugMSG("No types found for service: " + service.getId());
        else Logger.debugMSG("Cannot find service type, service is null");

        return new IType[] {};

    }


    public IType findServiceType(Service service, IScriptProject project) {


        IType[] types = findServiceTypes(service, project);

        if (types.length > 0)
            return types[0];

        return null;

    }

    /**
    *
    * @param Retrieve a single service by service id
    *
    *
    * @return
    */
    public Service findService(final String id, IPath path) {

        if (path == null) {

            Logger.debugMSG("cannot find service without path: " + id);
            return null;
        }
        String key = id + path.toString();

        if (serviceCache2.get(key) != null) {
            return serviceCache2.get(key) == NULL_ENTRY ? null : (Service) serviceCache2.get(key);
        }

        final List<Service> services = new ArrayList<Service>();

        if (index == null) {
            Logger.log(Logger.ERROR, "The SymfonyIndexer has not been instantiated...");
            return null;
        }

        String pathString = path == null ? "" : path.toString();

        index.findService(id, pathString, new IServiceHandler() {

            @Override
            public void handle(String id, String phpClass, String path, String _public, String tags) {
                Service s = new Service(id, phpClass, path, null);
                s.setTags(tags);
                s.setPublic(_public);
                services.add(s);
            }
        });


        if (services.size() == 1) {

            Service service = services.get(0);
            String fqcn = service.getFullyQualifiedName();

            if (fqcn.startsWith("alias_")) {

                String alias = fqcn.substring(fqcn.indexOf("_")+1);
                Service reference = SymfonyModelAccess.getDefault().findService(alias, service.getPath());

                if (reference != null) {
                    serviceCache2.put(key, reference);
                    return reference;
                }

            }

            serviceCache2.put(key, service);
            return service;
        }
        serviceCache2.put(key, NULL_ENTRY);

        return null;
    }

    /**
    * Return all services of a {@link Project} or null if the
    * project hasn't been found.
    *
    * @param path
    * @return
    */
    public List<Service> findServices(IPath path) {

        final List<Service> services = new ArrayList<Service>();

        if (index == null) {
            Logger.log(Logger.ERROR, "The SymfonyIndexer has not been instantiated...");
            return null;
        }

        index.findServices(path.toString(), new IServiceHandler() {

            @Override
            public void handle(String id, String phpClass, String path, String _public, String tags) {

                Service s = new Service(id, phpClass, path);
                s.setTags(tags);
                s.setPublic(_public);

                if (!services.contains(s)) {
                    services.add(s);
                }
            }
        });

        return services;

    }


    public List<String> findServiceTags(IPath path) {

        try {
            return index.findTags(path);
        } catch (Exception e) {
            return null;
        }

    }

    public boolean hasRouteMethod(String method, IScriptProject project) {
        IDLTKSearchScope scope = SearchEngine.createSearchScope(project);
        ISearchEngine engine = getSearchEngine(PHPLanguageToolkit.getDefault());
        if (scope == null || engine == null) {
            return false;
        }

        final List<String> methods = new ArrayList<String>();
        engine.search(ISymfonyModelElement.ROUTE_METHOD, null, method, 0, 0, 10, SearchFor.REFERENCES, MatchRule.EXACT, scope, new ISearchRequestor() {
            @Override
            public void match(int elementType, int flags, int offset, int length,
                    int nameOffset, int nameLength, String elementName,
                    String metadata, String doc, String qualifier, String parent,
                    ISourceModule sourceModule, boolean isReference) {
                methods.add(elementName);
            }
        }, null);

        return methods.size() > 0;
    }

    public Route findRoute(String route, IScriptProject scriptProject) {

        if (index == null) {
            Logger.log(Logger.ERROR, "The SymfonyIndexer has not been instantiated...");
            return null;
        }


        return index.findRoute(route, scriptProject.getPath());


    }

    public IType findController(String bundle, String controller,
            IScriptProject scriptProject) {

        String name = scriptProject.getElementName();
        String key = bundle + controller + name;

        if ( (controllerCache.get(key)) != null) {
            return controllerCache.get(key) == NULL_ENTRY ? null : (IType) controllerCache.get(key);
        }
        IType findBundleType = findBundleType(bundle, scriptProject);
        if (findBundleType == null) {
        	return null;
        }
        IModelElement parent = findBundleType.getParent();
        StringBuilder ns = new StringBuilder();
        if (parent.getElementType() == IModelElement.TYPE) {
        	ns.append(parent.getElementName());
        }
        ScriptFolder bundleFolder = (ScriptFolder) parent.getAncestor(IScriptFolder.class);
        if(bundleFolder == null) {
            return null;
        }

        ISourceModule controllerSource = bundleFolder.getSourceModule("Controller");

        if (controllerSource == null) {
            return null;
        }

        IDLTKSearchScope controllerScope = SearchEngine.createSearchScope(controllerSource);
        
        String[] split = controller.split("/");
        ns.append("\\Controller");
        if (split.length > 1) {
        	for (int i = 0; i + 1 < split.length; i++) {
        		ns.append('\\').append(split[i]);
        	}
        	controller = split[split.length -1];
        }
        
        IType[] controllers = findTypes(ns.toString(), controller + "Controller", MatchRule.PREFIX, 0, 0, controllerScope, null);

        if(controllers.length == 1) {
            controllerCache.put(key, controllers[0]);
            return controllers[0];
        }

        controllerCache.put(key, NULL_ENTRY);

        return null;

    }

    /**
    *
    * Find the templates in the projects' root view path.
    *
    * FIXME: find a way to not hardcode the path to app/Resource/views
    *
    * @param scriptProject
    * @return
    */
    public IModelElement[] findRootTemplates(IScriptProject scriptProject) {

        try {

            IPath path = scriptProject.getPath().append(new Path("app/Resources/views"));
            IScriptFolder sfolder = scriptProject.findScriptFolder(path);

            if (sfolder != null && sfolder.exists() && sfolder.hasChildren()) {
                return sfolder.getChildren();
            }

        } catch (Exception e) {
            Logger.logException(e);
        }

        return new IModelElement[] {};

    }

    /**
    *
    * Return the templates inside the bundles root viewpath:
    *
    * Bundle/Resources/views/*
    *
    * @param bundle
    * @param project
    * @return
    */
    public IModelElement[] findBundleRootTemplates(String bundle,
            IScriptProject project) {

        try {

            ScriptFolder bundleFolder = findBundleFolder(bundle, project);
            IPath path = new Path("Resources/views/");
            IPath viewPath = bundleFolder.getPath().append(path);
            IScriptFolder sfolder = project.findScriptFolder(viewPath);

            if (sfolder != null && sfolder.exists() && sfolder.hasChildren()) {
                return sfolder.getChildren();
            }

        } catch (Exception e) {
            Logger.logException(e);
        }

        return new IModelElement[] {};

    }

    /**
    * Find the template for the given viewpath.
    *
    *
    * @param viewPath
    * @param scriptProject
    * @return
    */
    public IModelElement findTemplate(ViewPath viewPath,
            IScriptProject project) {

        try {

            String bundle = viewPath.getBundle();
            String controller = viewPath.getController();
            String template = viewPath.getTemplate();


            if (viewPath.isRoot()) {

                IPath path = project.getPath().append(new Path("app/Resources/views"));
                IScriptFolder sfolder = project.findScriptFolder(path);

                if (sfolder != null) {
                    return sfolder.getSourceModule(template);
                }
            } else if (viewPath.isBundleBasePath()) {

                ScriptFolder bundleFolder = findBundleFolder(bundle, project);

                if (bundleFolder == null)
                    return null;

                IScriptFolder viewFolder = project.findScriptFolder(bundleFolder.getPath().append(new Path("Resources/views")));

                if (viewFolder != null) {
                    return viewFolder.getSourceModule(template);
                }

            } else {

                if (bundle == null || controller == null)
                    return null;

                ScriptFolder bundleFolder = findBundleFolder(bundle, project);
                IPath path = new Path("Resources/views/" + controller.replace("Controller", ""));
                IPath iPath = bundleFolder.getPath().append(path);
                IScriptFolder sfolder = project.findScriptFolder(iPath);

                if (sfolder != null) {
                    return sfolder.getSourceModule(template);
                }
            }


        } catch (Exception e) {
            Logger.logException(e);
        }

        return null;

    }


    /**
    * Find the corresponding {@link IMethod} to a {@link Route}.
    *
    * @param route
    * @param project
    * @return
    */
    public IMethod findAction(Route route, IScriptProject project) {

        ViewPath vPath = new ViewPath(route.getViewPath());

        if (!vPath.isValid())
            return null;

        IType type = null;

        IType[] controllers = findBundleControllers(vPath.getBundle(), project);

        if (controllers == null) {
            String msg = "Unable to find bundle controllers ";
            if (vPath != null)
                msg += vPath.getBundle();

            if (project != null) {
                msg += " project: " + project.getElementName();
            }
            Logger.debugMSG(msg);
            return null;
        }

        String ctrl = vPath.getController() + "Controller";

        for (IType t : controllers) {

            if (t.getElementName().equals(ctrl)) {
                type = t;
                break;
            }
        }

        if (type == null) {
            return null;
        }

        IMethod method = type.getMethod(vPath.getTemplate() + "Action");

        if (method != null)
            return method;

        return type.getMethod(vPath.getTemplate());


    }

    public String findNameSpace(IScriptProject iScriptProject, final IPath path) {
        IDLTKSearchScope scope = SearchEngine.createSearchScope(iScriptProject);
        ISearchEngine engine = ModelAccess.getSearchEngine(PHPLanguageToolkit.getDefault());
        if (scope == null || engine == null) {
            return null;
        }

        final List<String> namespaces = new ArrayList<String>();
        engine.search(ISymfonyModelElement.NAMESPACE, null, null, 0, 0, 100, SearchFor.REFERENCES, MatchRule.PREFIX, scope, new ISearchRequestor() {
            @Override
            public void match(int elementType, int flags, int offset, int length,
                    int nameOffset, int nameLength, String elementName,
                    String metadata, String doc, String qualifier, String parent,
                    ISourceModule sourceModule, boolean isReference) {


                if (path != null && path.toString().startsWith(elementName)) {
                    namespaces.add(path.toString().replace(elementName, ""));
                }
            }
        }, null);

        if (namespaces.size() > 0) {
            String namespace = namespaces.get(0);
            if (namespace.startsWith("/")) {
                namespace = namespace.replaceFirst("/", "");
            }

            return namespace.replace("/", "\\");
        }

        return null;
    }


    public List<String> getNameSpaces(IScriptProject project) {
        IDLTKSearchScope scope = SearchEngine.createSearchScope(project);
        ISearchEngine engine = ModelAccess.getSearchEngine(PHPLanguageToolkit.getDefault());
        final List<String> namespaces = new ArrayList<String>();
        if (scope == null || engine == null) {
            return namespaces;
        }

        engine.search(ISymfonyModelElement.NAMESPACE, null, null, 0, 0, 100, SearchFor.REFERENCES, MatchRule.PREFIX, scope, new ISearchRequestor() {
            @Override
            public void match(int elementType, int flags, int offset, int length,
                    int nameOffset, int nameLength, String elementName,
                    String metadata, String doc, String qualifier, String parent,
                    ISourceModule sourceModule, boolean isReference) {
                namespaces.add(elementName);
            }
        }, null);

        return namespaces;
    }

    public IPath resolveBundleShortcut(String string, IScriptProject project) {

        String bundle = string.replace("@", "");
        // split at camelcase
        //        String[] parts = bundle.split("(?<!^)(?=[A-Z])");

        IScriptFolder folder = findBundleFolder(bundle, project);


        if (folder != null)
            return folder.getPath();

        return null;

    }

    /**
    * Search for a specific {@link Bundle} by alias, ie 'AcmeDemoBundle';
    *
    * @param bundleAlias
    * @param scriptProject
    * @return {@link Bundle}
    */
    public Bundle findBundle(String bundleAlias, IScriptProject scriptProject) {
        String key = bundleAlias + scriptProject.getElementName();
        if (bundleCache.get(key) != null) {
            return bundleCache.get(key) == NULL_ENTRY ? null :(Bundle) bundleCache.get(key);
        }

        IDLTKSearchScope scope = SearchEngine.createSearchScope(scriptProject);
        ISearchEngine engine = ModelAccess.getSearchEngine(PHPLanguageToolkit.getDefault());
        if (scope == null || engine == null) {
            return null;
        }

        final List<Bundle> bundles = new ArrayList<Bundle>();
        engine.search(ISymfonyModelElement.BUNDLE, null, bundleAlias, 0, 0, 100, SearchFor.REFERENCES, MatchRule.EXACT, scope, new ISearchRequestor() {
            @Override
            public void match(int elementType, int flags, int offset, int length,
                    int nameOffset, int nameLength, String elementName,
                    String metadata, String doc, String qualifier, String parent,
                    ISourceModule sourceModule, boolean isReference) {
                bundles.add(JsonUtils.unpackBundle(metadata));
            }
        }, null);

        if (bundles.size() == 1) {
            Bundle b = bundles.get(0);
            bundleCache.put(key, b);
            return b;
        }
        bundleCache.put(key, NULL_ENTRY);

        return null;
    }

    /**
    * Search for a specific {@link IType} by an {@link EntityAlias} like 'AcmeDemoBundle:SomeEntityClass'
    *
    * @param alias
    * @param scriptProject
    * @return {@link IType} or null
    */
    public IType findEntity(EntityAlias alias, IScriptProject scriptProject) {

        String key = alias + scriptProject.getElementName();
        if (entityCache.get(key) != null) {
            return entityCache.get(key) == NULL_ENTRY ? null : (IType) entityCache.get(key);
        }

        Bundle bundle = findBundle(alias.getBundleAlias(), scriptProject);

        if (bundle == null) {
        	entityCache.put(key, NULL_ENTRY);
            return null;
        }

        INamespace ns;

        try {
            ns = bundle.getNamespace();
        } catch (ModelException e) {
            return null;
        }

        if (ns == null) {
        	entityCache.put(key, NULL_ENTRY);
            return null;
        }


        String[] entityNS = new String[ns.getStrings().length + 1];

        System.arraycopy(ns.getStrings(), 0, entityNS, 0, ns.getStrings().length);
        entityNS[ns.getStrings().length] = "Entity";
        IDLTKSearchScope scope = SearchEngine.createSearchScope(scriptProject);

        StringBuilder sb = new StringBuilder();
        sb.append(entityNS[0]);

        for (int i=1; i<entityNS.length; i++) {
            sb.append("\\");
            sb.append(entityNS[i]);
        }

        String entity = alias.getEntity();

        if (entity.contains("\\")) {
            String[] strings = entity.split("\\\\");
            for(int i=0; i < strings.length; i++) {

                if (i < strings.length-1) {
                    sb.append("\\");
                    sb.append(strings[i]);
                } else {
                    entity = strings[i];
                }
            }

        }

        IType[] types = findTypes(sb.toString(), entity, MatchRule.EXACT, 0, 0, scope, null);

        if (types.length == 1) {
            IType t = types[0];
            entityCache.put(key, t);
            return t;
        }
        entityCache.put(key, NULL_ENTRY);
        return null;
    }

    public List<TransUnit> findTranslations(IPath path) {


        final List<TransUnit> translations = new ArrayList<TransUnit>();

        if (index == null) {
            Logger.log(Logger.ERROR, "The SymfonyIndexer has not been instantiated...");
            return null;
        }

        index.findTranslations(path.toString(), new ITranslationHandler() {

            @Override
            public void handle(String name, String value, String language, String path) {

                TransUnit trans = new TransUnit(name, value, language, path);
                translations.add(trans);

            }
        });

        return translations;

    }

    public List<TransUnit> findTranslations(Translation translation) {

        final List<TransUnit> units = new ArrayList<TransUnit>();

        index.findTranslations(translation.getElementName(), translation.getPath().toString(), new ITranslationHandler() {

            @Override
            public void handle(String name, String value, String language, String path) {

                TransUnit unit = new TransUnit(name, value, language);
                units.add(unit);

            }
        });

        return units;
    }

    public Route getRoute(IType type, IMethod method) {

        IScriptProject project = type.getScriptProject();

        String bundleAlias = ModelUtils.extractBundleName(type.getFullyQualifiedName("\\"));

        String controller = ModelUtils.getControllerName(type);

        List<Route> routes = index.findRoutesByController(bundleAlias, controller, project.getPath());

        String action = method.getElementName().endsWith(SymfonyCoreConstants.ACTION_SUFFIX) ?
                method.getElementName().replace(SymfonyCoreConstants.ACTION_SUFFIX, "") : method.getElementName();

        for (Route route : routes) {
            if (route.getAction().equals(action)) {
                return route;
            }
        }

        return null;
    }

    /**
    * Retrieve all templateVariables for the given sourceModule, given the sourceModule is
    * a template (php or twig).
    *
    * @param sourceModule
    */
    public List<TemplateField> findTemplateVariables(ISourceModule sourceModule, String varName) {
        String viewPath = PathUtils.createViewPathFromTemplate(sourceModule, false);
        IDLTKSearchScope scope = SearchEngine.createSearchScope(sourceModule.getScriptProject());
        ISearchEngine engine = ModelAccess.getSearchEngine(SymfonyLanguageToolkit.getDefault());
        if (scope == null || engine == null) {
            return null;
        }

        final List<TemplateField> variables = new ArrayList<TemplateField>();
        final IElementResolver resolver = ModelAccess.getElementResolver(SymfonyLanguageToolkit.getDefault());

        // handle twig variables
        if (!varName.startsWith("$")) {
            varName = "$" + varName;
        }

        engine.search(ISymfonyModelElement.TEMPLATE_VARIABLE, viewPath, varName, 0, 0, 100, SearchFor.REFERENCES, MatchRule.EXACT, scope, new ISearchRequestor() {
            @Override
            public void match(int elementType, int flags, int offset, int length,
                    int nameOffset, int nameLength, String elementName,
                    String metadata, String doc, String qualifier, String parent,
                    ISourceModule sourceModule, boolean isReference) {
                IModelElement element = resolver.resolve(elementType, flags, offset, length, nameOffset, nameLength, elementName, metadata, doc, qualifier, parent, sourceModule);

                if (element != null) {
                    if (element instanceof TemplateField)
                        variables.add((TemplateField) element);
                }
            }
        }, null);

        return variables;
    }

}
