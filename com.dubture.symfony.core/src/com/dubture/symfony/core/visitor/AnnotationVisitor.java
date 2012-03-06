/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.visitor;

import java.util.List;
import java.util.Stack;

import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;

import com.dubture.symfony.annotation.model.Annotation;
import com.dubture.symfony.annotation.parser.AnnotationCommentParser;
import com.dubture.symfony.annotation.parser.antlr.SourcePosition;
import com.dubture.symfony.core.codeassist.strategies.AnnotationCompletionStrategy;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;
import com.dubture.symfony.core.preferences.SymfonyCorePreferences;
import com.dubture.symfony.core.util.AnnotationUtils;

/**
 *
 * {@link AnnotationVisitor} parses annotations from
 * PHPDocBlocks.
 *
 * This will mainly be used for error reporting purposes
 * and maybe syntax highlighting.
 *
 * For code-assistance in annotations, see {@link AnnotationCompletionStrategy}
 *
 * @see http://symfony.com/blog/symfony2-annotations-gets-better
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class AnnotationVisitor extends PHPASTVisitor {

    private ClassDeclaration currentClass = null;
    private PHPMethodDeclaration currentMethod = null;

    private boolean isAction = false;
    private IBuildContext context;

    private Stack<UseStatement> useStatements = new Stack<UseStatement>();

    private AnnotationCommentParser parser;

    public AnnotationVisitor(IBuildContext context) {
        this.context = context;
        this.parser = AnnotationUtils.createParser();
    }

    @Override
    public boolean visit(UseStatement useStatement) throws Exception {
        useStatements.push(useStatement);
        return true;
    }

    /**
    * This could be used to parse Annotationclasses themselves
    * to build up an internal model about the annotation.
    *
    * However, there's no clean way at the moment as pretty much
    * any class can be used as an annotation and there's no proper
    * way to detect the semantics of the annotation from the php code.
    *
    * @see http://www.doctrine-project.org/jira/browse/DDC-1198
    */
    @Override
    public boolean visit(ClassDeclaration classDeclaration) throws Exception {
        currentClass = classDeclaration;
        return true;
    }

    @Override
    public boolean endvisit(ClassDeclaration classDeclaration) throws Exception {
        currentClass = null;
        return true;
    }

    /**
     * Parses annotations from method declarations.
     */
    @Override
    public boolean visit(PHPMethodDeclaration methodDeclaration) throws Exception {
        currentMethod = methodDeclaration;
        isAction = currentMethod.getName().endsWith(SymfonyCoreConstants.ACTION_SUFFIX);

        if (currentClass == null || isAction == false) {
            return false;
        }

        List<Annotation> annotations = AnnotationUtils.extractAnnotations(parser, methodDeclaration);
        for (Annotation annotation : annotations) {
            reportUnresolvableAnnotation(annotation);
        }

        return true;
    }

    @Override
    public boolean endvisit(PHPMethodDeclaration methodDeclaration) throws Exception {
        currentMethod = null;
        return true;
    }

    /**
     * Checks if an annotation can be resolved via a {@link UseStatement}
     * and adds an {@link IProblem} to the {@link IProblemReporter}
     * if the annotation cannot be resolved.
     *
     *
     * @param annotation The annotation object
     */
    @SuppressWarnings("deprecation")
    private void reportUnresolvableAnnotation(Annotation annotation) {

        String annotationClass = annotation.getClassName();
        String annotationNamespace = annotation.getNamespace();
        String fqcn = annotation.getFullyQualifiedName();

        boolean found = false;

        for (UseStatement statement : useStatements) {
            for (UsePart part : statement.getParts()) {
                SimpleReference alias = part.getAlias();
                FullyQualifiedReference namespace = part.getNamespace();

                //statement has no alias and classname no namespace, simply
                // compare them to each other
                if (alias == null && annotationNamespace.length() == 0) {

                    if (namespace.getName().equals(annotationClass)) {
                        found = true;
                    }

                    /*
                    * something like
                    *
                    * use use Doctrine\Common\Mapping as SomeMapping;
                    *
                    * @SomeMapping
                    *
                    */
                } else if (alias != null && annotationNamespace.length() == 0) {


                    if (alias.getName().equals(annotationClass))
                        found = true;
                    /*
                    * something like
                    *
                    * use use Doctrine\Common\Mapping as ORM;
                    *
                    * @ORM\Table
                    *
                    */

                } else if (alias != null && annotationNamespace.length() > 0) {

                    if (alias.equals(annotation.getFirstNamespacePart())) {

                        //TODO: search for matching classes using PDT SearchEngine

                    }
                } else if (annotationNamespace != null && annotationClass != null) {

                    if (fqcn.startsWith(annotationNamespace))
                        found = true;
                }

                if (found == true)
                    break;

            }

            if (found == true)
                break;
        }

        if (found == false) {
            SourcePosition sourcePosition = annotation.getSourcePosition();
            String filename = context.getFile().getName();
            String message = "Unable to resolve annotation '" + fqcn + "'";
            int lineNo = context.getLineTracker().getLineInformationOfOffset(sourcePosition.startOffset).getOffset();

            /**
            * this should be the way to create the problem without the deprecation
            * warning, but then our QuickFixProcessor doesn't get called.
            */
            //IProblem newProblem = new DefaultProblem(filename, message, DefaultProblemIdentifier.NULL, new String[0], ProblemSeverities.Error, start+1, end+1, lineNo, start);

            ProblemSeverity severity = SymfonyCorePreferences.getAnnotationSeverity();

            IProblem problem = new DefaultProblem(filename, message, IProblem.ImportRelated,
                    new String[0], severity, sourcePosition.startOffset + 1, sourcePosition.endOffset + 1, lineNo);

            context.getProblemReporter().reportProblem(problem);
        }
    }
}
