package com.dubture.symfony.ui.actions;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.actions.ActionUtil;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.IBinding;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.actions.SelectionConverter;
import org.eclipse.php.internal.ui.actions.SelectionDispatchAction;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.IUpdate;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
@SuppressWarnings("restriction")
public class SwitchContextAction extends SelectionDispatchAction implements
IUpdate {
	
	private PHPStructuredEditor fEditor;

	public SwitchContextAction(IWorkbenchSite site) {
		super(site);
		setText("Open &Type Hierarchy");
		setToolTipText("Open &Type Hierarchy");
		setDescription("Open &Type Hierarchy");
		// HELP - PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
		// IPHPHelpContextIds.OPEN_TYPE_HIERARCHY_ACTION);
	}


	public SwitchContextAction(PHPStructuredEditor editor) {
		this(editor.getEditorSite());
		fEditor = editor;
		setEnabled(SelectionConverter.canOperateOn(fEditor));
	}


	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(ITextSelection selection) {
		IModelElement input = EditorUtility.getEditorInputModelElement(fEditor,
				true);
		if (input == null || !ActionUtil.isProcessable(getShell(), input)
				|| !(input instanceof ISourceModule)) {
			return;
		}
		final IModelElement selectionModelElement = getSelectionModelElement(
				selection.getOffset(), selection.getLength(),
				(ISourceModule) input);
		run(new IModelElement[] { selectionModelElement });
	}
	
	public void run(IModelElement[] elements) {
		if (elements.length == 0) {
			getShell().getDisplay().beep();
			return;
		}
//		open(elements, getSite().getWorkbenchWindow());
	}
	
	
	protected IModelElement getSelectionModelElement(int offset, int length,
			ISourceModule sourceModule) {
		IModelElement element = null;
		try {
			Program ast = SharedASTProvider.getAST(sourceModule,
					SharedASTProvider.WAIT_NO, null);
			if (ast != null) {
				ASTNode selectedNode = NodeFinder.perform(ast, offset, length);
				if (selectedNode != null
						&& selectedNode.getType() == ASTNode.IDENTIFIER) {
					IBinding binding = ((Identifier) selectedNode)
							.resolveBinding();
					if (binding != null) {
						element = binding.getPHPElement();
					}
				}
			}
		} catch (Exception e) {
			// Logger.logException(e);
		}
		if (element == null) {
			// try to get the top level
			try {
				IModelElement[] selected = sourceModule.codeSelect(offset, 1);
				if (selected.length > 0) {
					element = selected[0];
				}
			} catch (ModelException e) {
			}
		}
		return element;
	}
	


	@Override
	public void update() {
		setEnabled(fEditor != null);

	}
}