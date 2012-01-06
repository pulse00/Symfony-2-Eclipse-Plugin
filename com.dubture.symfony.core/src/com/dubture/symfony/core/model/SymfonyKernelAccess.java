/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.index2.search.ISearchEngine;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.index2.search.ISearchEngine.SearchFor;
import org.eclipse.dltk.core.index2.search.ISearchRequestor;
import org.eclipse.dltk.core.index2.search.ModelAccess;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.internal.core.model.PhpModelAccess;

import com.dubture.symfony.core.SymfonyLanguageToolkit;
import com.dubture.symfony.core.log.Logger;

@SuppressWarnings("restriction")
public class SymfonyKernelAccess extends PhpModelAccess {
	
	
	private static SymfonyKernelAccess instance = null;
	
	
	private SymfonyKernelAccess() {
		
		
	}
	
	public static SymfonyKernelAccess getDefault() {
		
		if (instance == null)
			instance = new SymfonyKernelAccess();
		
		return instance;
	}
	
	/**
	 * Get the installed Kernels for a specific projects.
	 * 
	 * @param project
	 * @return
	 */
	public List<AppKernel> getKernels(IScriptProject project) {

		final List<AppKernel> kernels = new ArrayList<AppKernel>();

		IDLTKSearchScope scope = SearchEngine.createSearchScope(project);
		ISearchEngine engine = ModelAccess.getSearchEngine(SymfonyLanguageToolkit.getDefault());		

		engine.search(ISymfonyModelElement.ENVIRONMENT, null, null, 0, 0, 100, SearchFor.REFERENCES, MatchRule.PREFIX, scope, new ISearchRequestor() {

			@Override
			public void match(int elementType, int flags, int offset, int length,
					int nameOffset, int nameLength, String elementName,
					String metadata, String doc, String qualifier, String parent,
					ISourceModule sourceModule, boolean isReference) {

				AppKernel kernel = new AppKernel(elementName, sourceModule);				
				kernels.add(kernel);

			}
		}, null);
				
		return kernels;
	}
	
	/**
	 * 
	 * Retrieve a specific AppKernel from a project.
	 * 
	 * 
	 * 
	 * @param project
	 * @param name
	 * @return
	 */
	public AppKernel getKernel(IScriptProject project, String name) {
		
		final List<AppKernel> kernels = new ArrayList<AppKernel>();

		IDLTKSearchScope scope = SearchEngine.createSearchScope(project);
		ISearchEngine engine = ModelAccess.getSearchEngine(SymfonyLanguageToolkit.getDefault());
		
		if (project == null || name == null || scope == null) {
		    Logger.log(Logger.WARNING, "unable to retrieve kernel");
		    return null;
		}

		engine.search(ISymfonyModelElement.ENVIRONMENT, null, name, 0, 0, 100, SearchFor.REFERENCES, MatchRule.EXACT, scope, new ISearchRequestor() {

			@Override
			public void match(int elementType, int flags, int offset, int length,
					int nameOffset, int nameLength, String elementName,
					String metadata, String doc, String qualifier, String parent,
					ISourceModule sourceModule, boolean isReference) {

				AppKernel kernel = new AppKernel(elementName, sourceModule);				
				kernels.add(kernel);

			}
		}, null);
				
		if (kernels.size() == 1) {
			return kernels.get(0);
		}
		
		return null;		
		
		
	}
	
	/**
	 * Retrieve the development kernel for a project.
	 * 
	 * @param project
	 * @return
	 */
	public AppKernel getDevelopmentKernel(IScriptProject project) {

		return getKernel(project, AppKernel.DEV);
		
	}

}
