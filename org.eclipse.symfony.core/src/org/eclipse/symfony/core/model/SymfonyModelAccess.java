package org.eclipse.symfony.core.model;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTagKinds;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.internal.core.model.PhpModelAccess;

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

	public static SymfonyModelAccess getDefault() {

		if (modelInstance == null)
			modelInstance = new SymfonyModelAccess();

		return modelInstance;
	}


	public TemplateVariable createTemplateVariableByReturnType(ISourceModule sourceModule,
			String callName, String className, String namespace, String variableName) {

		IDLTKSearchScope scope = createSearchScope(sourceModule);

		if (scope == null)
			return null;

		IType[] types = findTypes(namespace, className, MatchRule.EXACT, 0, 0, scope, null);

		if (types.length != 1)
			return null;

		IType type = types[0];

		final IMethod method = type.getMethod(callName);

		if (method == null)
			return null;


		ModuleDeclaration module = SourceParserUtil.getModuleDeclaration(method.getSourceModule());
		ReturnTypeVisitor visitor = new ReturnTypeVisitor(method.getElementName());
		try {
			module.traverse(visitor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (visitor.className == null || visitor.namespace == null)
			return null;
		
		return new TemplateVariable(sourceModule, variableName, visitor.namespace, visitor.className);
		
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

}
