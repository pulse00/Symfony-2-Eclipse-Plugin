package com.dubture.symfony.core.codeassist;

import java.io.BufferedReader;
import java.io.StringReader;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.php.internal.core.codeassist.PHPSelectionEngine;
import org.eclipse.php.internal.core.codeassist.strategies.PHPDocTagStrategy;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;

import com.dubture.symfony.annotation.parser.antlr.AnnotationCommonTree;
import com.dubture.symfony.annotation.parser.antlr.AnnotationCommonTreeAdaptor;
import com.dubture.symfony.annotation.parser.antlr.AnnotationLexer;
import com.dubture.symfony.annotation.parser.antlr.AnnotationNodeVisitor;
import com.dubture.symfony.annotation.parser.antlr.AnnotationParser;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.ViewPath;
import com.dubture.symfony.core.util.text.SymfonyTextSequenceUtilities;
import com.dubture.symfony.index.dao.Route;


/**
 * 
 * 
 * The {@link SymfonySelectionEngine} helps DLTK to identify
 * symfony model elements for actions like "Open Declaration" - F3 and
 * Hyperlinking.
 * 
 * TODO: I think a cleaner way to implement is to actually provide the 
 * model elements as native DLTK model elements somehow, so DLTK knows what 
 * a Route/Viewpath etc. is and how to resolve it.
 * 
 * 
 * @see http://wiki.eclipse.org/DLTK_IDE_Guide:Step_3._Towards_an_IDE#Open_declaration_feature.
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonySelectionEngine extends PHPSelectionEngine {

	private static final IModelElement[] NONE = {};	

	@Override
	public IModelElement[] select(IModuleSource sourceUnit, final int offset, int end) {

		IModelElement[] result = super.select(sourceUnit, offset, end);

		if (result.length > 0) {
			return result;
		}

		ISourceModule sourceModule = (ISourceModule) sourceUnit
				.getModelElement();		

		String content = sourceUnit.getSourceContents();

		int startOffset = SymfonyTextSequenceUtilities.readLiteralStartIndex(content, offset);
		int endOffset = SymfonyTextSequenceUtilities.readLiteralEndIndex(content, offset);

		SymfonyModelAccess model = SymfonyModelAccess.getDefault();
		IScriptProject project = sourceModule.getScriptProject();


		if (startOffset >= 0 && endOffset != 0 && (endOffset > startOffset)) {


			String literal = content.substring(startOffset, endOffset);
			
			// viewpaths are linked using ViewpathHyperlinkDetector
			
//			// try to resolve a viewepath first
//			ViewPath viewPath = new ViewPath(literal);
//
//			if (viewPath.isValid()) {
//
//				IModelElement template = model.findTemplate(viewPath, project);
//
//				if (template != null) {
//					return new IModelElement[] { template };
//				}				
//			}

			// nope, not a viewpath, check for a route
			Route route = model.findRoute(literal, project);

			if (route != null) {

				IMethod method = model.findAction(route, project);

				if (method != null)
					return new IModelElement[] { method };
			}

			// next search for a service
			Service service = model.findService(literal, project.getPath());

			if (service != null) {

				IType serviceType = model.findServiceType(service, project);

				if (serviceType != null)
					return new IModelElement[] { serviceType };

			}			
		}

		try {

			ModuleDeclaration parsedUnit = SourceParserUtil.getModuleDeclaration(
					sourceModule, null);

			AnnotationPathVisitor visitor = new AnnotationPathVisitor(offset, project);
			parsedUnit.traverse(visitor);
			
			if (visitor.getTemplate() != null) {
				return new IModelElement[] { visitor.getTemplate() };
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// couldn't find anything
		return NONE;
	}

	private class AnnotationPathVisitor extends PHPASTVisitor {

		private int offset;
		private IScriptProject project;

		private NamespaceDeclaration namespace;
		private ClassDeclaration classDeclaration;

		private IModelElement template = null;

		public AnnotationPathVisitor(int offset, IScriptProject project) {

			this.offset = offset;
			this.project = project;
		}

		public IModelElement getTemplate() {

			return template;
		}


		@Override
		public boolean visit(NamespaceDeclaration s) throws Exception {

			this.namespace = s;
			return true;
		}

		@Override
		public boolean visit(ClassDeclaration s) throws Exception {

			classDeclaration = s;
			return true;
		}

		@Override
		public boolean visit(PHPMethodDeclaration s) throws Exception {

			PHPDocBlock doc = s.getPHPDoc();

			if (doc == null)
				return false;

			String comment = doc.getShortDescription();
			int sourceStart = offset;
			if (doc.sourceStart() < offset && doc.sourceEnd() > offset) {

				BufferedReader buffer = new BufferedReader(new StringReader(comment));

				try {

					String line;

					while((line = buffer.readLine()) != null) {

						int length = line.length();

						if ( (sourceStart + length) < offset) {
							
							continue;
						}

						sourceStart += length;

						int start = line.indexOf('@');
						int end = line.length()-1;

						if ((start == -1 || end == -1)) continue;

						boolean isTag = false;				
						String aTag = line.substring(start +1);

						// check for built-int phpdoc tags and don't parse them
						// as annotations
						for(String tag : PHPDocTagStrategy.PHPDOC_TAGS) {					
							if (tag.equals(aTag)) {
								isTag = true;
								break;
							}					
						}

						if (isTag) 
							continue;

						String annotation = line.substring(start, end+1);
						CharStream content = new ANTLRStringStream(annotation);

						AnnotationLexer lexer = new AnnotationLexer(content);
						AnnotationParser parser = new AnnotationParser(new CommonTokenStream(lexer));
						parser.setTreeAdaptor(new AnnotationCommonTreeAdaptor());
						AnnotationParser.annotation_return root = parser.annotation();
						AnnotationCommonTree tree = (AnnotationCommonTree) root.getTree();
						AnnotationNodeVisitor visitor = new AnnotationNodeVisitor();
						tree.accept(visitor);

						String className = visitor.getClassName();
						
						if ("Template".equals(className)) {

							if (namespace.getName().endsWith("\\Controller")) {

								String bundle = namespace.getName().replace("\\Controller", "").replace("\\", "");
								String controller = classDeclaration.getName().replace("Controller", "");
								String action = s.getName().replace("Action", "");
								ViewPath path = new ViewPath(String.format("%s:%s:%s", bundle, controller,action));

								if (path.isValid()) {

									IModelElement[] templates = SymfonyModelAccess.getDefault()
											.findTemplates(bundle, controller, project);

									for (IModelElement template : templates) {

										if (template.getElementName().startsWith(action)) {
											this.template = template;
											return false;
										}
									}
								}
							}
						}
					}
				} catch (Exception e) {
					Logger.logException(e);		
				}						
				return false;				
			}
			return false;
		}		
	}
}