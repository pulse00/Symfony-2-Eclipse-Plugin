/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist;

import java.util.List;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.php.internal.core.codeassist.PHPSelectionEngine;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;

import com.dubture.doctrine.annotation.model.Annotation;
import com.dubture.doctrine.annotation.parser.AnnotationCommentParser;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.ViewPath;
import com.dubture.symfony.core.util.AnnotationUtils;
import com.dubture.symfony.core.util.text.SymfonyTextSequenceUtilities;
import com.dubture.symfony.index.model.Route;


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

        //IModelElement[] result = super.select(sourceUnit, offset, end);

        //if (result.length > 0) {
        //    return result;
        //}

        ISourceModule sourceModule = (ISourceModule) sourceUnit
                .getModelElement();

        String content = sourceUnit.getSourceContents();
        if (content.length() < offset) {
        	return NONE;
        }
        int startOffset = SymfonyTextSequenceUtilities.readLiteralStartIndex(content, offset);
        int endOffset = SymfonyTextSequenceUtilities.readLiteralEndIndex(content, offset);

        SymfonyModelAccess model = SymfonyModelAccess.getDefault();
        IScriptProject project = sourceModule.getScriptProject();


        if (startOffset >= 0 && endOffset != 0 && (endOffset > startOffset)) {


            String literal = content.substring(startOffset, endOffset);

            // viewpaths are linked using ViewpathHyperlinkDetector

//            // try to resolve a viewepath first
//            ViewPath viewPath = new ViewPath(literal);
//
//            if (viewPath.isValid()) {
//
//                IModelElement template = model.findTemplate(viewPath, project);
//
//                if (template != null) {
//                    return new IModelElement[] { template };
//                }
//            }

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

            AnnotationPathVisitor visitor = new AnnotationPathVisitor(project, offset);
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

    /**
     *
     * Parses @Template annotations in controllers to link to the corresponding template.
     *
     *
     * @author Robert Gruendler <r.gruendler@gmail.com>
     *
     */
    private static class AnnotationPathVisitor extends PHPASTVisitor {

        private static final String[] TEMPLATE_CLASS_NAMES = new String[]{"Template"};

        private IScriptProject project;
        private NamespaceDeclaration namespaceDeclaration;
        private ClassDeclaration classDeclaration;

        private AnnotationCommentParser parser;

        private IModelElement template = null;
        private int offset;

        public AnnotationPathVisitor(IScriptProject project, int offset) {
            this.project = project;
            this.parser = AnnotationUtils.createParser(TEMPLATE_CLASS_NAMES);
            this.offset = offset;
        }

        public IModelElement getTemplate() {
            return template;
        }

        @Override
        public boolean visit(NamespaceDeclaration namespaceDeclaration) throws Exception {
            this.namespaceDeclaration = namespaceDeclaration;
            return true;
        }

        @Override
        public boolean visit(ClassDeclaration classDeclaration) throws Exception {
            this.classDeclaration = classDeclaration;
            return true;
        }

        @Override
        public boolean visit(PHPMethodDeclaration methodDeclaration) throws Exception {
            
            if (namespaceDeclaration == null || !namespaceDeclaration.getName().endsWith("\\Controller")) {
                return false;
            }
            
            PHPDocBlock phpDoc = methodDeclaration.getPHPDoc();
            
            if (phpDoc == null || (phpDoc.sourceStart() > offset || phpDoc.sourceEnd() < offset)) {
                return false;
            }
            
            List<Annotation> annotations = AnnotationUtils.extractAnnotations(parser, methodDeclaration);
            if (annotations.size() < 1) {
                return false;
            }

            // We found at least of annotation with class Template, create a view path
            String bundle = namespaceDeclaration.getName().replace("\\Controller", "").replace("\\", "");
            String controller = classDeclaration.getName().replace("Controller", "");
            String action = methodDeclaration.getName().replace("Action", "");

            ViewPath path = new ViewPath(String.format("%s:%s:%s", bundle, controller, action));
            
            if (!path.isValid()) {
                return false;
            }

            IModelElement[] templates = SymfonyModelAccess.getDefault().findTemplates(bundle, controller, project);
            this.template = null;
            
            for (IModelElement template : templates) {
                if (template.getElementName().startsWith(action)) {
                    // We found a matching template, set template and return true
                    this.template = template;
                    return false;
                }
            }

            return false;
        }
    }
}
