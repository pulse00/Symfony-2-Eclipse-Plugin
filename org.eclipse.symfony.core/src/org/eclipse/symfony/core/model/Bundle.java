package org.eclipse.symfony.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.symfony.core.model.exception.InvalidBundleException;
import org.eclipse.symfony.core.util.PathUtils;
import org.eclipse.symfony.core.visitor.KernelVisitor;

/**
 * 
 * The Bunndle class represents a Symfony2 Bundle.
 * 
 * A bundle can be active/inactive - whether is's
 * loaded in the AppKernel::registerBundles() or not.
 * 
 * @see KernelVisitor 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class Bundle extends AbstractSymfonyModel {
	
	
	
	private IScriptProject project;
	private ClassDeclaration classDeclaration;
	private String name;
	private String namespace;
	private ISourceModule sourceModule;
	
	private List<Service> services = new ArrayList<Service>();
	private List<Controller> controllers = new ArrayList<Controller>();
			
	private IPath basePath;
	
	private boolean isActive;

	public Bundle(ISourceModule iSourceModule, ClassDeclaration classDeclaration, String namespace) throws InvalidBundleException {
				
		super(iSourceModule);
		basePath = iSourceModule.getPath().removeLastSegments(1);
		
		if (iSourceModule.getPath().toString().contains("/Tests/"))
			throw new InvalidBundleException(iSourceModule.getPath().toString());
		
		
		this.sourceModule = iSourceModule;
		this.project = sourceModule.getScriptProject();
		this.classDeclaration = classDeclaration;
		this.namespace = namespace;
		
		name = namespace + "\\" + classDeclaration.getName();
		isActive = false;
		

		
	}

	public IScriptProject getProject() {

		return project;
	}

	public String getName() {

		return classDeclaration.getName();
	
	}
	
	
	public IPath getBasePath() {
		
		
		return basePath;
		
	}

	public IResource getResource() {

		return sourceModule.getResource();
		
	}

	public void addService(Service service) {

		service.setBundle(this);
		services.remove(service);
		services.add(service);
		
	}

	public List<Service> getServices() {

		return services;
	}

	public String getFullyQualifiedName() {

		return name;
	}

	public void activate() {

		isActive = true;
		
	}
	
	public void deactivate() {
		
		isActive = false;
	}
	
	public boolean isActive() {
		
		return isActive;
		
	}

	public String getNamespace() {
		return namespace;
	}

	public void addController(Controller controller) {

		if (controllers.contains(controller)) {			
			controllers.remove(controller);
		}
		
		controllers.add(controller);
		
	}

	public List<Controller> getControllers() {

		return controllers;
	}

	public List<TemplateVariable> getTemplateVariables(
			ISourceModule sourceModule) {

		IPath path = sourceModule.getPath();
		String controller = PathUtils.getControllerFromTemplatePath(path);
		
		if (controller == null) {
			return null;
		}

		
		String viewName = PathUtils.getViewFromTemplatePath(path);
		
		if (viewName == null) {			
			return null;
		}
		

		for (Controller ctrl : controllers) {

			if (ctrl.getName().equals(controller)) {
				
				return ctrl.getTemplateVariables(viewName);
				
			}
		}
				
		return null;
	}

	public TemplateVariable getTemplateVariable(ISourceModule sourceModule2,
			String varName) {
		
		IPath path = sourceModule2.getPath();
		String controller = PathUtils.getControllerFromTemplatePath(path);
		
		if (controller == null) {
			return null;
		}

		
		String viewName = PathUtils.getViewFromTemplatePath(path);
		
		if (viewName == null) {			
			return null;
		}
		

		for (Controller ctrl : controllers) {
			if (ctrl.getName().equals(controller)) {				
				return ctrl.getTemplateVariable(viewName, varName);
				
			}
		}
				

		return null;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Bundle))
			return false;
		

		Bundle other = (Bundle) obj;
		
		return other.getName().equals(this.getName());
	}
}