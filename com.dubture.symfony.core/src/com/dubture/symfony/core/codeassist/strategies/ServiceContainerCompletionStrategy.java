/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.codeassist.strategies;

import java.util.List;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.strategies.MethodParameterKeywordStrategy;

import com.dubture.symfony.core.codeassist.contexts.ServiceContainerContext;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.SymfonyModelAccess;


/**
 * CompletionStrategy to provide service names for DI::get calls like
 *
 *
 * <pre>
 *
 *  // inside a ContainerAware interface
 *     $this->get('|
 *  $this->container->get('|
 *
 * </pre>
 *
 *
 *
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class ServiceContainerCompletionStrategy extends
        MethodParameterKeywordStrategy {

    public ServiceContainerCompletionStrategy(ICompletionContext context) {
        super(context);

    }

    @Override
    public void apply(ICompletionReporter reporter) throws BadLocationException {

        ServiceContainerContext context = (ServiceContainerContext) getContext();
        IScriptProject project = context.getSourceModule().getScriptProject();

        SymfonyModelAccess model= SymfonyModelAccess.getDefault();
        List<Service> services = model.findServices(project.getPath());
        SourceRange range = getReplacementRange(context);

        String prefix = context.getPrefix();

        if (services == null) {
            return;
        }

        for(Service service : services) {
            if (CodeAssistUtils.startsWithIgnoreCase(service.getId(), prefix)) {
            	
                IType[] serviceTypes = model.findServiceTypes(service, project);
                ModelElement parent = null;
                
                if (serviceTypes.length > 0) {
                    parent = (ModelElement) serviceTypes[0];
                } else {
                    parent = (ModelElement) context.getSourceModule();
                }

                Service s = new Service(parent, service.getElementName());
                s.setId(service.getId());
                reporter.reportType(s, "", range);
            }
        }
    }
}
