package com.dubture.symfony.debug.launch;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.SourceMethod;
import org.eclipse.dltk.internal.core.SourceType;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.debug.core.debugger.launching.ILaunchDelegateListener;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.index.dao.Route;
import com.dubture.symfony.index.dao.RouteParameter;

/**
 * 
 * A PHP Debug launchDelegate that injects the correct Launch URL
 * for a corresponding Symfony route.
 *  
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class LaunchDelegateListener implements ILaunchDelegateListener {

	private Route route = null;
	
	public LaunchDelegateListener() {

	}

	@SuppressWarnings("rawtypes")
	@Override
	public int preLaunch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) {
		
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {

				IWorkbench wb = PlatformUI.getWorkbench();
				IWorkbenchWindow window = wb.getActiveWorkbenchWindow();				
				IWorkbenchPage page = window.getActivePage();				
				IEditorPart part = page.getActiveEditor();
				
				if (part instanceof PHPStructuredEditor) {
					
					PHPStructuredEditor editor = (PHPStructuredEditor) part;
					ISourceModule source = EditorUtility.getEditorInputModelElement(editor, false);					
					ISelectionProvider provider = editor.getSelectionProvider();					
					ISelection selection = provider.getSelection();
					
					if (selection instanceof ITextSelection) {
						
						ITextSelection textSelection = (ITextSelection) selection;						
						int offset = textSelection.getOffset();
						
						try {
							
							IModelElement element = source.getElementAt(offset);
							
							if (element != null) {															
								SourceMethod sourceMethod = getMethod(element);								
								
								if (sourceMethod != null && sourceMethod.getParent() != null) {
									
									SourceType sourceType = (SourceType) sourceMethod.getParent();
									String fqn = sourceType.getFullyQualifiedName("\\");
									IDLTKSearchScope scope = SearchEngine.createSearchScope(source);
									SymfonyModelAccess model = SymfonyModelAccess.getDefault();
									IType[] types = model.findTypes(fqn, MatchRule.EXACT, 0, 0, scope, null);
									
									if (types.length == 1) {										
										IType type = types[0];										
										IMethod method = type.getMethod(sourceMethod.getElementName());
										route = model.getRoute(type, method);										
									}
									
								}
							}
						} catch (ModelException e) {
							Logger.logException(e);
						}
					}
				}				
			}
		});
		
		
		if (route != null) {
			
			try {
				
				String URL = null;
				String base = "http://localhost/app_dev.php";
				
				if (route.hasParameters()) {					
					
					Map<String, RouteParameter> parameters = route.getParameters();

					Iterator it = parameters.keySet().iterator();
					
					while(it.hasNext()) {						
						String name = (String) it.next();
						RouteParameter param = parameters.get(name);
						param.setValue(param.getName());
					}					
					URL = base + route.getURL(parameters.values());
					
				} else {					
					URL = base + route.getURL(); 
				}
												
				ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
				wc.setAttribute("base_url", URL);				
				
				System.err.println("FILENAME " + configuration.getAttribute("file_name", ""));
				wc.doSave();
				
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
		
		return 0;
	}
	
	
	private SourceMethod getMethod(IModelElement element) {

		if (element instanceof SourceMethod)
			return (SourceMethod) element;
		
		while(element.getParent() != null) {

			if (element.getParent() instanceof SourceMethod)
				return (SourceMethod) element.getParent();
			
			element = element.getParent();
			
		}				
		return null;
	}	

}
