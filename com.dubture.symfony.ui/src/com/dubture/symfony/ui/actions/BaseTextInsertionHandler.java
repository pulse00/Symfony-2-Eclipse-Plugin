package com.dubture.symfony.ui.actions;

import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.internal.ui.actions.SelectionHandler;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import com.dubture.symfony.core.log.Logger;


@SuppressWarnings("restriction")
abstract public class BaseTextInsertionHandler extends SelectionHandler implements IHandler
{
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
