package com.dubture.symfony.ui.preferences;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

public class LibraryEditor extends ListEditor {

    /**
     * The last path, or <code>null</code> if none.
     */
    private String lastPath;

    /**
     * The special label text for directory chooser, 
     * or <code>null</code> if none.
     */
    private String dirChooserLabelText;

    public LibraryEditor(String name, String labelText,
            String dirChooserLabelText, Composite parent) {
        init(name, labelText);
        this.dirChooserLabelText = dirChooserLabelText;
        createControl(parent);
    }

    /* (non-Javadoc)
     * Method declared on ListEditor.
     * Creates a single string from the given array by separating each
     * string with the appropriate OS-specific path separator.
     */
    protected String createList(String[] items) {
        StringBuffer path = new StringBuffer("");//$NON-NLS-1$

        for (int i = 0; i < items.length; i++) {
            path.append(items[i]);
            path.append(File.pathSeparator);
        }
        return path.toString();
    }
    

	@Override
	protected String getNewInputObject() {
		
        FileDialog dialog = new FileDialog(getShell(), SWT.SHEET);
        
        dialog.setFilterExtensions(new String[]{".tgz"});
        
        if (dirChooserLabelText != null) {
			dialog.setText(dirChooserLabelText);
		}
        
        if (lastPath != null) {
            if (new File(lastPath).exists()) {
				dialog.setFilterPath(lastPath);
			}
        }
        String dir = dialog.open();
        if (dir != null) {
            dir = dir.trim();
            if (dir.length() == 0) {
				return null;
			}
            lastPath = dir;
        }
        return dir;		
	}

	@Override
	protected String[] parseString(String stringList) {
		
        StringTokenizer st = new StringTokenizer(stringList, File.pathSeparator
                + "\n\r");//$NON-NLS-1$
        List<String> v = new LinkedList<String>();
        while (st.hasMoreTokens()) {
            v.add(st.nextToken());
        }
        return (String[]) v.toArray(new String[v.size()]);
		
	}
}
