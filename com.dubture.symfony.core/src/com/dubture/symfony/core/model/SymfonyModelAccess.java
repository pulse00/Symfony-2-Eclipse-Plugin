package com.dubture.symfony.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.internal.resources.Project;
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
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTagKinds;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.internal.core.model.PhpModelAccess;

import com.dubture.symfony.core.SymfonyLanguageToolkit;
import com.dubture.symfony.core.index.SymfonyElementResolver.TemplateField;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.util.JsonUtils;
import com.dubture.symfony.core.util.PathUtils;
import com.dubture.symfony.index.IServiceHandler;
import com.dubture.symfony.index.ITranslationHandler;
import com.dubture.symfony.index.SymfonyIndexer;
import com.dubture.symfony.index.dao.Route;
import com.dubture.symfony.index.dao.TransUnit;

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

	private static final IModelElement[] EMPTY = {};	


	private LRUCache controllerCache = new LRUCache();
	private LRUCache serviceCache2 = new LRUCache();
	private LRUCache entityCache = new LRUCache();

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


	public TemplateVariable createTemplateVariableByReturnType(PHPMethodDeclaration controllerMethod, SimpleReference callName, 
			String className, String namespace, String variableName) {

		IDLTKSearchScope scope = SearchEngine.createWorkspaceScope(PHPLanguageToolkit.getDefault());

		if (scope == null)
			return null;

		IType[] types = findTypes(namespace, className, MatchRule.EXACT, 0, 0, scope, null);

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
				PHPDocTag[] returnTags = docs.getTags(PHPDocTagKinds.RETURN);						
				if (returnTags.length == 1) {							
					PHPDocTag tag = returnTags[0];

					if (tag.getReferences().length == 1) {								
						SimpleReference ref = tag.getReferences()[0];
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

		if (controller == null)
			return new ArrayList<TemplateField>();

		// create a searchscope for the whole ScriptProject,
		// as view variables can be declared across controllers
		IDLTKSearchScope scope = SearchEngine.createSearchScope(controller.getScriptProject());

		if(scope == null) {
			return null;
		}

		final List<TemplateField> variables = new ArrayList<TemplateField>();
		ISearchEngine engine = ModelAccess.getSearchEngine(SymfonyLanguageToolkit.getDefault());		
		final IElementResolver resolver = ModelAccess.getElementResolver(SymfonyLanguageToolkit.getDefault());

		engine.search(IModelElement.USER_ELEMENT, null, null, 0, 0, 100, SearchFor.REFERENCES, MatchRule.PREFIX, scope, new ISearchRequestor() {

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

		if (controller == null)
			return null;

		// create a searchscope for the Controller class only		
		IDLTKSearchScope scope = SearchEngine.createSearchScope(controller);

		if(scope == null) {
			return null;
		}

		final List<TemplateField> variables = new ArrayList<TemplateField>();
		ISearchEngine engine = ModelAccess.getSearchEngine(SymfonyLanguageToolkit.getDefault());		
		final IElementResolver resolver = ModelAccess.getElementResolver(SymfonyLanguageToolkit.getDefault());

		engine.search(IModelElement.USER_ELEMENT, null, variableName, 0, 0, 100, SearchFor.REFERENCES, MatchRule.EXACT, scope, new ISearchRequestor() {

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


		// TODO: ensure unique constraint for IModelElement.USER_ELEMENT + Path
		// during indexing
		if (variables.size() > 0)
			return variables.get(0);

		return null;

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


	public Map<String, String> findAnnotationClasses(IScriptProject project) {

		IDLTKSearchScope scope = SearchEngine.createSearchScope(project.getScriptProject());			 		 
		ISearchEngine engine = ModelAccess.getSearchEngine(PHPLanguageToolkit.getDefault());		 
		final Map<String, String> annotations = new HashMap<String, String>();

		engine.search(ISymfonyModelElement.ANNOTATION, null, null, 0, 0, 100, SearchFor.REFERENCES, MatchRule.PREFIX, scope, new ISearchRequestor() {

			@Override
			public void match(int elementType, int flags, int offset, int length,
					int nameOffset, int nameLength, String elementName,
					String metadata, String doc, String qualifier, String parent,
					ISourceModule sourceModule, boolean isReference) {

				annotations.put(elementName, qualifier);				

			}
		}, null);


		return annotations;

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

		IDLTKSearchScope scope = SearchEngine.createSearchScope(project);

		IType[] types = findTypes(bundle, MatchRule.EXACT, 0, 0, scope, null);

		if (types.length != 1)
			return null;

		IType bundleType = types[0];
		return  (ScriptFolder) bundleType.getSourceModule().getParent(); 		

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

			if (sfolder.exists() && sfolder.hasChildren()) {				
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
			return (Service) serviceCache2.get(key);
		}

		final List<Service> services = new ArrayList<Service>();

		if (index == null) {
			Logger.log(Logger.ERROR, "The SymfonyIndexer has not been instantiated...");
			return null;
		}

		String pathString = path == null ? "" : path.toString();

		index.findService(id, pathString, new IServiceHandler() {

			@Override
			public void handle(String id, String phpClass, String path) {
				services.add(new Service(id, phpClass, path, null));				
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
			public void handle(String id, String phpClass, String path) {
				services.add(new Service(id, phpClass, path));

			}
		});

		return services;

	}

	public boolean hasRouteMethod(String method, IScriptProject project) {

		IDLTKSearchScope scope = SearchEngine.createSearchScope(project);		
		ISearchEngine engine = getSearchEngine(PHPLanguageToolkit.getDefault());		
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
			return (IType) controllerCache.get(key);
		}

		ScriptFolder bundleFolder = findBundleFolder(bundle, scriptProject);
		if(bundleFolder == null)
			return null;

		ISourceModule controllerSource = bundleFolder.getSourceModule("Controller");

		if (controllerSource == null)
			return null;

		IDLTKSearchScope controllerScope = SearchEngine.createSearchScope(controllerSource);

		IType[] controllers = findTypes(controller, MatchRule.PREFIX, 0, 0, controllerScope, null); 

		if(controllers.length == 1) {			
			controllerCache.put(key, controllers[0]);
			return controllers[0];
		}

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

			if (sfolder.exists() && sfolder.hasChildren()) {				
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
		final List<String> namespaces = new ArrayList<String>();

		engine.search(ISymfonyModelElement.NAMESPACE, null, null, 0, 0, 100, SearchFor.REFERENCES, MatchRule.PREFIX, scope, new ISearchRequestor() {

			@Override
			public void match(int elementType, int flags, int offset, int length,
					int nameOffset, int nameLength, String elementName,
					String metadata, String doc, String qualifier, String parent,
					ISourceModule sourceModule, boolean isReference) {

				if (path.toString().startsWith(elementName)) {					
					namespaces.add(path.toString().replace(elementName, ""));
				}

			}
		}, null);


		if (namespaces.size() == 1) {			
			return namespaces.get(0).replaceFirst("/", "").replace("/", "\\");
		}
		return null;

	}


	public List<String> getNameSpaces(IScriptProject project) {

		IDLTKSearchScope scope = SearchEngine.createSearchScope(project);
		ISearchEngine engine = ModelAccess.getSearchEngine(PHPLanguageToolkit.getDefault());
		final List<String> namespaces = new ArrayList<String>();


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
		//		String[] parts = bundle.split("(?<!^)(?=[A-Z])");

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


		IDLTKSearchScope scope = SearchEngine.createSearchScope(scriptProject);			 		 
		ISearchEngine engine = ModelAccess.getSearchEngine(PHPLanguageToolkit.getDefault());		 
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
			return bundles.get(0);
		}
		
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
		
		if (entityCache.get(alias) != null)
			return (IType) entityCache.get(alias);
					
		Bundle bundle = findBundle(alias.getBundleAlias(), scriptProject);
		
		if (bundle == null) {
			return null;
		}
		
		INamespace ns;
		
		try {
			ns = bundle.getNamespace();
		} catch (ModelException e) {
			return null;
		}		
		
		if (ns == null)
			return null;
		
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
			    
		
		IType[] types = findTypes(sb.toString(), alias.getEntity(), MatchRule.EXACT, 0, 0, scope, null);

		if (types.length == 1) {			
			entityCache.put(alias, types[0]);
			return types[0];
		}
				
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

				System.err.println("found translation");
				TransUnit trans = new TransUnit(name, value, language);
				translations.add(trans);
				
			}
		});

		return translations;
		
	}	
}