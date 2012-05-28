/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.actions;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
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
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.part.MultiPageEditorPart;
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
    
    protected IScriptProject getProject()
    {
        IEditorPart  editorPart =PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();

        if(editorPart  != null)
        {
            IFileEditorInput input = (IFileEditorInput)editorPart.getEditorInput() ;
            IFile file = input.getFile();
            IProject activeProject = file.getProject();
            return DLTKCore.create(activeProject);
        }
        return null;
    }
    
    
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException
    {
        project = getProject();
        Assert.isNotNull(project);
        
        final Shell p = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
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
    
    protected void doInsert(ITextEditor editor, String text)
    {
        ISelectionProvider selectionProvider = editor.getSelectionProvider();
        ISelection selection = selectionProvider.getSelection();
        
        if (selection instanceof ITextSelection) {
            ITextSelection textSelection = (ITextSelection) selection;
            
            
            int offset = textSelection.getOffset();
            IDocumentProvider dp = editor.getDocumentProvider();
            IDocument doc = dp.getDocument(editor.getEditorInput());
            try {
                doc.replace(offset, 0, text);
            } catch (BadLocationException e) {
                Logger.logException(e);
            }
        }

    }
    
        
    protected void insertText(String text)
    {
        IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
        
        if (editor instanceof MultiPageEditorPart) {
            MultiPageEditorPart multiEditor = (MultiPageEditorPart) editor;
            if (multiEditor.getSelectedPage() instanceof ITextEditor) {
                doInsert((ITextEditor) multiEditor.getSelectedPage() , text);
            }
        } else if (editor instanceof ITextEditor) {
            doInsert((ITextEditor) editor, text);
        }
    }        
}
