package org.eclipse.symfony.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
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
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTagKinds;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.symfony.core.SymfonyLanguageToolkit;
import org.eclipse.symfony.core.index.SymfonyElementResolver.TemplateField;
import org.eclipse.symfony.core.log.Logger;
import org.eclipse.symfony.core.util.PathUtils;
import org.eclipse.symfony.index.IServiceHandler;
import org.eclipse.symfony.index.SymfonyIndexer;
import org.eclipse.symfony.index.dao.Route;

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
	
	/**
	 * 
	 * Retrieve all bundles inside a Project.
	 * 
	 * @param project
	 * @return
	 */
	public List<String> findBundles(IScriptProject project) {
		
		 IDLTKSearchScope scope = SearchEngine.createSearchScope(project.getScriptProject());			 		 
		 ISearchEngine engine = ModelAccess.getSearchEngine(PHPLanguageToolkit.getDefault());		 
		 final List<String> bundles = new ArrayList<String>();
		 
		 engine.search(ISymfonyModelElement.BUNDLE, null, null, 0, 0, 100, SearchFor.REFERENCES, MatchRule.PREFIX, scope, new ISearchRequestor() {
			
			@Override
			public void match(int elementType, int flags, int offset, int length,
					int nameOffset, int nameLength, String elementName,
					String metadata, String doc, String qualifier, String parent,
					ISourceModule sourceModule, boolean isReference) {

				bundles.add(elementName);				
				
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
			IProjectFragment fragment = (IProjectFragment) bundleFolder.getParent();
			IScriptFolder folder = fragment.getScriptFolder("Resources/views/" + controller.replace("Controller", ""));
			if (folder.exists() && folder.hasChildren()) {
				return folder.getChildren();
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

	public Service findService(String className) {
	
		return findService(className, null);
	}

	/**
	 * 
	 * @param Retrieve a single service by service id
	 * 
	 * 
	 * @return
	 */
	public Service findService(final String id, IPath path) {
		
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
				
				if (reference != null)
					return reference;
				
			}			
			
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
}
