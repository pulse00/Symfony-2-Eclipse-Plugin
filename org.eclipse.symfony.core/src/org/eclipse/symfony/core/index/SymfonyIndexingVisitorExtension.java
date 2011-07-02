package org.eclipse.symfony.core.index;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.index2.IIndexingRequestor.ReferenceInfo;
import org.eclipse.php.core.index.PhpIndexingVisitorExtension;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.symfony.core.Logger;
import org.eclipse.symfony.core.index.visitor.TemplateVariableVisitor;
import org.eclipse.symfony.core.model.ISymfonyModelElement;
import org.eclipse.symfony.core.model.TemplateVariable;
import org.eclipse.symfony.core.preferences.SymfonyCoreConstants;
import org.eclipse.symfony.core.util.JsonUtils;
import org.eclipse.symfony.index.SymfonyIndexer;
import org.eclipse.symfony.index.dao.Route;


/**
 * 
 * {@link SymfonyIndexingVisitorExtension} contributes model elements
 * to the index.
 * 
 *
 * TODO: This indexer is currently called on any PHP Project, regardless
 * of a Symfony nature: Find a way to check if the Indexer is indexing
 * a Sourcemodule of a project with the SymfonyNature.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyIndexingVisitorExtension extends
PhpIndexingVisitorExtension {


	private boolean inController = false;
	private ClassDeclaration currentClass;
	private NamespaceDeclaration namespace;
	private TemplateVariableVisitor controllerIndexer;
	private SymfonyIndexer indexer;
	
	private List<UseStatement> useStatements = new ArrayList<UseStatement>();
	

	@Override
	public boolean visit(ASTNode s) throws Exception {

		return true;
	}
	
	
	@Override
	public boolean visit(ModuleDeclaration s) throws Exception {
	
		return true;
	}

	@Override
	public boolean visit(Statement s) throws Exception {

		return true;

	}

	@Override
	public boolean visit(Expression s) throws Exception {

		if (s.getClass() == Block.class) {
			
			s.traverse(new PHPASTVisitor() {
				
				@Override
				public boolean visit(UseStatement s) throws Exception {
					useStatements.add(s);
					return true;
				}
			});
		}

		return super.visit(s);
	}
	

	@Override
	public boolean visit(TypeDeclaration s) throws Exception {

		if (indexer == null)
			indexer = SymfonyIndexer.getInstance();

		if (s instanceof ClassDeclaration) {

			currentClass = (ClassDeclaration) s;
			
			for (Object o : currentClass.getSuperClasses().getChilds()) {

				if (o instanceof FullyQualifiedReference) {

					FullyQualifiedReference superReference = (FullyQualifiedReference) o;					
					String ns = getUseStatement(superReference.getName());
					
					if (ns != null) {
						
						String fqcn = ns + "\\" + superReference.getName();						
						boolean isTestOrFixture = namespace.getName().contains("Test") || namespace.getName().contains("Fixtures");
						
						// we got a bundle definition, index it
						if (fqcn.equals(SymfonyCoreConstants.BUNDLE_FQCN) && ! isTestOrFixture) {

							int length = (currentClass.sourceEnd() - currentClass.sourceEnd());
							ReferenceInfo info = new ReferenceInfo(ISymfonyModelElement.BUNDLE, currentClass.sourceStart(), length, currentClass.getName(), null, namespace.getName());
							requestor.addReference(info);
							
						}						
					}

					//TODO: Check against an implementation of Symfony\Component\DependencyInjection\ContainerAwareInterface
					//
					// see http://symfony.com/doc/current/cookbook/bundles/best_practices.html#controllers
					// and http://api.symfony.com/2.0/Symfony/Component/DependencyInjection/ContainerAwareInterface.html
					if (superReference.getName().equals(SymfonyCoreConstants.CONTROLLER_CLASS)) {

						inController = true;
						
					
						// the ControllerIndexer does the actual work of parsing the
						// the relevant elements inside the controller
						// which are then being collected in the endVisit() method
						controllerIndexer = new TemplateVariableVisitor(useStatements, namespace);
						currentClass.traverse(controllerIndexer);

					} 
				} 
			}
		} else if (s instanceof NamespaceDeclaration) {
			namespace = (NamespaceDeclaration) s;
		}

		return true; 
	}

	@Override
	@SuppressWarnings({ "rawtypes" })
	public boolean endvisit(TypeDeclaration s) throws Exception {


		if (controllerIndexer != null) {

			Map<TemplateVariable, String> variables = controllerIndexer.getTemplateVariables();			
			Iterator it = variables.keySet().iterator();
			
			while(it.hasNext()) {
				
				TemplateVariable variable = (TemplateVariable) it.next();
				String viewPath = variables.get(variable);
						
				int start = variable.sourceStart();
				int length = variable.sourceEnd() - variable.sourceStart();
				String name = null;				
				
				if (variable.isReference()) {
					
					name = variable.getName();

					String phpClass = variable.getClassName();
					String namespace = variable.getNamespace();					
					String metadata = JsonUtils.createReference(phpClass, namespace, viewPath);
					
					Logger.debugMSG("add reference info: " + name +  " " + metadata + " " + namespace);
					
					ReferenceInfo info = new ReferenceInfo(IModelElement.USER_ELEMENT, start, length, name, metadata, namespace);
					requestor.addReference(info);
					
				}
			}
						
			for (Route route : controllerIndexer.getRoutes()) {
				Logger.debugMSG("indexing route: " + route.toString());
				indexer.addRoute(route, sourceModule.getScriptProject().getPath());
			}
			
			indexer.exitRoutes();
		}
		
		inController = false;
		currentClass = null;
		namespace = null;
		controllerIndexer = null;
		return true;
	}
	
	private String getUseStatement(String name) {
		
		for (UseStatement statement : useStatements) {
			
			for (UsePart part : statement.getParts()) {
				
				if (part.getNamespace().getName().equals(name)) {
					return part.getNamespace().getNamespace().getName();
				}
			}
			
		}
		
		return null;
		
	}

	/**
	 * Parse {@link PHPMethodDeclaration} to index a couple of elements needed for code-assist
	 * like Methods that accept viewpaths as parameters.
	 */
	@Override
	public boolean visit(MethodDeclaration s) throws Exception {

		if (s instanceof PHPMethodDeclaration) {
			
			PHPMethodDeclaration method = (PHPMethodDeclaration) s;
			PHPDocBlock docBlock = method.getPHPDoc();
			
			if (docBlock != null) {

				PHPDocTag[] tags = docBlock.getTags();
				
				for (PHPDocTag tag : tags) {

					if (tag.getReferences().length == 2) {
						
						SimpleReference[] refs = tag.getReferences();
						SimpleReference varName = refs[0];
						SimpleReference varType = refs[1];
						
						if(varName.getName().equals("$view") && varType.getName().equals("string")) {							
							int length = method.sourceEnd() - method.sourceStart();
							
							ReferenceInfo viewMethod = new ReferenceInfo(ISymfonyModelElement.VIEW_METHOD, method.sourceStart()	, length, method.getName(), null, null);
							requestor.addReference(viewMethod);
						}
					}					
				}				
			}
		}
		
		return true;
	}
}