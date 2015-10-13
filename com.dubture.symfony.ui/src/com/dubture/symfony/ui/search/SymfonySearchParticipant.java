/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.ui.search.ElementQuerySpecification;
import org.eclipse.dltk.ui.search.IMatchPresentation;
import org.eclipse.dltk.ui.search.IQueryParticipant;
import org.eclipse.dltk.ui.search.ISearchRequestor;
import org.eclipse.dltk.ui.search.QuerySpecification;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.search.core.text.TextSearchEngine;
import org.eclipse.search.core.text.TextSearchMatchAccess;
import org.eclipse.search.core.text.TextSearchRequestor;
import org.eclipse.search.core.text.TextSearchScope;
import org.eclipse.search.ui.text.Match;

import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.SymfonyModelAccess;

/**
 * @author Dawid Pakuła (zulus)
 */
public class SymfonySearchParticipant implements IQueryParticipant {

    @Override
    public void search(final ISearchRequestor requestor, final QuerySpecification querySpecification,
	    IProgressMonitor monitor) throws CoreException {
	if (querySpecification.getLimitTo() != IDLTKSearchConstants.REFERENCES
		&& querySpecification.getLimitTo() != IDLTKSearchConstants.ALL_OCCURRENCES)
	    return;
	if (querySpecification instanceof ElementQuerySpecification) {
	    IModelElement element = ((ElementQuerySpecification) querySpecification).getElement();
	    if (element instanceof IType) {
		IType type = (IType) element;
		final List<IResource> potentialResources = new ArrayList<IResource>();
		potentialResources.add(type.getScriptProject().getProject().getFolder("app/config")); //$NON-NLS-1$
		List<Bundle> findBundles = SymfonyModelAccess.getDefault().findBundles(type.getScriptProject());
		for (Bundle bundle : findBundles) {
		    potentialResources.add(ResourcesPlugin.getWorkspace().getRoot()
			    .getFolder(bundle.getPath().append("Resources").append("config"))); //$NON-NLS-1$ //$NON-NLS-2$
		}
		TextSearchScope searchScope = TextSearchScope.newSearchScope(
			potentialResources.toArray(new IResource[potentialResources.size()]), Pattern.compile(".*"),
			true);
		Pattern search = TextSearchEngine.createPattern(PHPModelUtils.getFullName(type), false, false);

		TextSearchRequestor collector = new TextSearchRequestor() {
		    public boolean acceptPatternMatch(TextSearchMatchAccess matchAccess) throws CoreException {
			IFile file = matchAccess.getFile();
			if (matchAccess.getMatchOffset() == 0) {
			    return true;
			}
			char fileContentChar = matchAccess.getFileContentChar(matchAccess.getMatchOffset() - 1);
			if (!(Character.isWhitespace(fileContentChar) || fileContentChar == '\''
				|| fileContentChar == '"' || fileContentChar == ':' || fileContentChar == '>')) {
			    return false;
			}
			int end = matchAccess.getMatchOffset() + matchAccess.getMatchLength();
			if (end < matchAccess.getFileContentLength()) {
			    fileContentChar = matchAccess.getFileContentChar(end);
			    if (!(Character.isWhitespace(fileContentChar) || fileContentChar == '\''
				    || fileContentChar == '"' || fileContentChar == '<')) {
				return false;
			    }
			}
			if (querySpecification.getScope().encloses(file.getFullPath().toString())) {
			    requestor.reportMatch(
				    new Match(file, matchAccess.getMatchOffset(), matchAccess.getMatchLength()));
			}

			return true;
		    }
		};
		TextSearchEngine.create().search(searchScope, collector, search, null);
	    }
	}

    }

    @Override
    public int estimateTicks(QuerySpecification specification) {
	return 100;
    }

    @Override
    public IMatchPresentation getUIParticipant() {
	return null;
    }

}
