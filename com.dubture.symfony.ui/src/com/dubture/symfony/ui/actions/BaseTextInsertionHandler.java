package com.dubture.symfony.ui.actions;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.actions.SelectionHandler;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import com.dubture.symfony.core.log.Logger;


@SuppressWarnings("restriction")
abstract public class BaseTextInsertionHandler extends SelectionHandler implements IHandler
{
    protected IScriptProject project;
    
    
    protected static class ArrayLabelProvider extends LabelProvider{
        public String getText(Object element) {
            return ((String[]) element)[0].toString();
        }
    }

    abstract protected List<String[]> getInput();
    abstract protected String getTitle();
    
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException
    {
        IModelElement modelElement = getCurrentModelElement(event);
        Assert.isNotNull(modelElement);
        
        project = modelElement.getScriptProject();
        Assert.isNotNull(project);
        
        final Shell p = DLTKUIPlugin.getActiveWorkbenchShell();
        ILabelProvider labelRenderer  = new ArrayLabelProvider();
        ElementListSelectionDialog dialog = new ElementListSelectionDialog(p, labelRenderer);
        
        List<String[]> input = getInput(); 
        dialog.setElements(input.toArray());
        dialog.setMultipleSelection(true);
        dialog.setTitle(getTitle());
        dialog.setMessage("Wildcardsearch enabled");
        
        if (dialog.open() == Window.OK) {
            Object[] result = dialog.getResult();
            for (int i = 0; i < result.length; i++) {
                String[] ss = (String[])result[i];
                insertText(ss[1]);
            }
        }
        
        return null;
    }
    
        
    protected void insertText(String text)
    {
        IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
        
        if (editor instanceof ITextEditor) {
            
            ITextEditor textEditor = (ITextEditor) editor;
            ISelectionProvider selectionProvider = textEditor.getSelectionProvider();
            ISelection selection = selectionProvider.getSelection();
            
            if (selection instanceof ITextSelection) {
                ITextSelection textSelection = (ITextSelection) selection;
                int offset = textSelection.getOffset();
                IDocumentProvider dp = textEditor.getDocumentProvider();
                IDocument doc = dp.getDocument(textEditor.getEditorInput());
                try {
                    doc.replace(offset, 0, text);
                } catch (BadLocationException e) {
                    Logger.logException(e);
                }
            }
        }
    }        
}
