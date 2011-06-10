package org.eclipse.symfony.ui;

import java.util.List;

import org.eclipse.dltk.ui.IModelContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class SymfonyModelContentProvider implements IModelContentProvider {

	@Override
	public void provideModelChanges(Object parentElement, List children,
			ITreeContentProvider iTreeContentProvider) {

	}

	@Override
	public Object getParentElement(Object element,
			ITreeContentProvider iTreeContentProvider) {
		// TODO Auto-generated method stub
		return null;
	}

}
