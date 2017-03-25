/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist.strategies;

import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.MethodParameterKeywordStrategy;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.context.IModelCacheContext;

import com.dubture.doctrine.core.model.DoctrineModelAccess;
import com.dubture.doctrine.core.model.Entity;
import com.dubture.symfony.core.codeassist.contexts.EntityCompletionContext;
import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.EntityAlias;
import com.dubture.symfony.core.model.SymfonyModelAccess;

/**
 * Completes the parts of doctrine entity aliases, ie
 *
 * <pre>
 *     $this->getDoctrine->getRepository('| <-- completes the bundleAlias
 * </pre>
 *
 * <pre>
 * $this->getDoctrine->getRepository('AcmeDemoBundle:| <-- completes the available entity classes
 * </pre>
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class EntityCompletionStrategy extends MethodParameterKeywordStrategy {
	private final static IType[] EMPTY_TYPES = new IType[0];

    public EntityCompletionStrategy(ICompletionContext context) {
        super(context);
    }


    @Override
    public void apply(ICompletionReporter reporter) throws BadLocationException {

        EntityCompletionContext context = (EntityCompletionContext) getContext();
        SymfonyModelAccess model = SymfonyModelAccess.getDefault();
        IScriptProject project = context.getSourceModule().getScriptProject();
        ISourceRange range = getReplacementRange(context);
        IDLTKSearchScope projectScope = SearchEngine.createSearchScope(context.getSourceModule().getScriptProject());
        DoctrineModelAccess doctrineModel =  DoctrineModelAccess.getDefault();
        IModelAccessCache cache = null;
        if (context instanceof IModelCacheContext) {
        	cache = ((IModelCacheContext) context).getCache();
        }
        EntityAlias alias = context.getAlias();
        String prefix = context.getPrefix();
        
        if (alias.hasBundle() == false) {
            List<Bundle> bundles = model.findBundles(project);
            for (Bundle b : bundles) {
                IType[] bundleTypes = EMPTY_TYPES;
                if (cache != null) {
                	Collection<IType> types = cache.getTypes(context.getSourceModule(), b.getElementName(), null, null);
                	if (types != null) {
                		bundleTypes = types.toArray(new IType[types.size()]);
                	}
                } else {
                	bundleTypes = PhpModelAccess.getDefault().findTypes(b.getElementName(), MatchRule.EXACT, 0, 0, projectScope, null);
                }
                if (bundleTypes.length == 1) {
                    ModelElement bType = (ModelElement) bundleTypes[0];
                    if (CodeAssistUtils.startsWithIgnoreCase(bType.getElementName(), prefix)) {
                        Bundle bundleType = new Bundle(bType, b.getElementName());
                        reporter.reportType(bundleType, ":", range);
                    }
                }
            }
        } else {
            List<Entity> entities = doctrineModel.getEntities(project);
            //TODO: cache the entities
            for (Entity entity : entities) {
                EntityAlias newAliase = new EntityAlias(alias.getBundleAlias(), entity.getElementName());
                IType type = model.findEntity(newAliase, project);

                if (type != null) {
                    reporter.reportType(new Entity((ModelElement) type, type.getElementName()), "", range);
                }
            }
        }
    }
}
