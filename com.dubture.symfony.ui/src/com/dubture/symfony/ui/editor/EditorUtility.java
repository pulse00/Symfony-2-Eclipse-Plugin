package com.dubture.symfony.ui.editor;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.SourceMethod;
import org.eclipse.dltk.internal.core.SourceType;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
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

@SuppressWarnings("restriction")
public class EditorUtility {
	
	private Route route = null;
	private IScriptProject project;
	
	private SourceMethod sourceMethod;
	
	public EditorUtility() {
		
		
		
	}
	
	public Route getRouteAtCursor() {
		
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {

				IWorkbench wb = PlatformUI.getWorkbench();
				IWorkbenchWindow window = wb.getActiveWorkbenchWindow();				
				IWorkbenchPage page = window.getActivePage();				
				IEditorPart part = page.getActiveEditor();
				
				if (part instanceof PHPStructuredEditor) {
					
					PHPStructuredEditor editor = (PHPStructuredEditor) part;
					ISourceModule source = org.eclipse.dltk.internal.ui.editor.EditorUtility.getEditorInputModelElement(editor, false);		
					project = source.getScriptProject();
					ISelectionProvider provider = editor.getSelectionProvider();					
					ISelection selection = provider.getSelection();
					
					if (selection instanceof ITextSelection) {
						
						ITextSelection textSelection = (ITextSelection) selection;						
						int offset = textSelection.getOffset();
						
						try {
							
							IModelElement element = source.getElementAt(offset);
							
							if (element != null) {															
								sourceMethod = getMethod(element);								
								
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

		
		return route;
	}
	
	public SourceMethod getMethod(IModelElement element) {

		if (element instanceof SourceMethod)
			return (SourceMethod) element;
		
		while(element.getParent() != null) {

			if (element.getParent() instanceof SourceMethod)
				return (SourceMethod) element.getParent();
			
			element = element.getParent();
			
		}				
		return null;
	}

	public IScriptProject getProject() {
		return project;
	}

	
	public SourceMethod getMethod() {
				
		return sourceMethod;
		
	}
}
