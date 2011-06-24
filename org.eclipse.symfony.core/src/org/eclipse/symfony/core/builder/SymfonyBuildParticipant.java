package org.eclipse.symfony.core.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.symfony.core.model.Controller;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.visitor.AnnotationVisitor;
import org.eclipse.symfony.core.visitor.BundleVisitor;
import org.eclipse.symfony.core.visitor.ControllerVisitor;
import org.eclipse.symfony.core.visitor.KernelVisitor;

/**
 * 
 * Not used yet.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class SymfonyBuildParticipant implements IBuildParticipant {

	
	
	
	private ModuleDeclaration getModuleDeclaration(IBuildContext context) {
		
		ISourceModule sourceModule = context.getSourceModule();		
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
		
		return moduleDeclaration;		
		
	}
	
	@Override
	public void build(IBuildContext context) throws CoreException {
			
		try {
			
			IFile file = context.getFile();
			
			ModelManager model = ModelManager.getInstance();
			ModuleDeclaration module = getModuleDeclaration(context);			
						
			if (file.getName().endsWith("Bundle.php") && !file.getName().equals("Bundle.php")) {				
				module.traverse(new BundleVisitor(context));
				
			} else if (file.getName().equals("AppKernel.php")) {
				
				module.traverse(new KernelVisitor(context));
				
			} else if (file.getName().endsWith("Controller.php")) {
				
				ControllerVisitor visitor = new ControllerVisitor(context);
				module.traverse(visitor);
				
				Controller ctrl = visitor.getController();
				
				
				
				if (ctrl != null) {
					System.err.println("++++ add controller to model " + ctrl.getName());
					model.addController(ctrl);
				}
			}
			
			if (file.getFileExtension().equals("php")) {
				
				module.traverse(new AnnotationVisitor(context));				
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}