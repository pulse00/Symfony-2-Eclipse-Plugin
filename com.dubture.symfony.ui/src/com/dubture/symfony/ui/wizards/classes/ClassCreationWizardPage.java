package com.dubture.symfony.ui.wizards.classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.ui.dialogs.OpenTypeSelectionDialog2;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.ui.PHPUILanguageToolkit;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.dubture.symfony.ui.SymfonyPluginImages;
import com.dubture.symfony.ui.wizards.CodeTemplateWizardPage;

@SuppressWarnings("restriction")
public class ClassCreationWizardPage extends CodeTemplateWizardPage {


	protected Text fileText;
	protected Text superClassText;
	
	protected ISelection selection;

	protected Label targetResourceLabel;
	protected Label superClassLabel;
	
	private List<String> interfaces = new ArrayList<String>();
	
	private TableViewer interfaceTable;
	
	private Button abstractCheckbox;
	private Button finalCheckbox;
	
	private String[] cities = new String[] { "Aachen", "Berlin", "Bremen", "Bochum" };	

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public ClassCreationWizardPage(final ISelection selection, String initialFileName) {
		super("wizardPage", initialFileName); //$NON-NLS-1$
		setTitle("PHP Class"); //$NON-NLS-1$
		setDescription("Create a new PHP class"); //$NON-NLS-1$
		setImageDescriptor(SymfonyPluginImages.DESC_WIZBAN_ADD_SYMFONY_FILE);
		this.selection = selection;
	}

	
	private OpenTypeSelectionDialog2 getDialog(int type, String title, String message) {
		
		final Shell p = DLTKUIPlugin.getActiveWorkbenchShell();
		OpenTypeSelectionDialog2 dialog = new OpenTypeSelectionDialog2(p,
				true, PlatformUI.getWorkbench().getProgressService(), null,
				type, PHPUILanguageToolkit.getInstance());

		dialog.setTitle(title);
		dialog.setMessage(message);

		return dialog;
		
	}
	
	
	private SelectionListener changeListener = new SelectionListener() {
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			dialogChanged();			
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

			
		}
	};
	
	
	private SelectionListener interfaceRemoveListener = new SelectionListener() {
		
		@SuppressWarnings("rawtypes")
		@Override
		public void widgetSelected(SelectionEvent e) {
			
			ISelection select = interfaceTable.getSelection();
			
			if (select instanceof StructuredSelection) {
				
				StructuredSelection selection = (StructuredSelection) select;				
				Iterator it = selection.iterator();
				
				while(it.hasNext()) {					
					String next = (String) it.next();					
					interfaces.remove(next);					
				}
				
				interfaceTable.setInput(interfaces);
			}
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			
		}
	};
	
	
	private SelectionListener superClassSelectionListener  = new SelectionListener() {
		
		@Override
		public void widgetSelected(SelectionEvent e) {

			OpenTypeSelectionDialog2 dialog = getDialog(IDLTKSearchConstants.TYPE, "Superclass selection", "Select superclass");

			int result = dialog.open();
			if (result != IDialogConstants.OK_ID)
				return;
			
			Object[] types = dialog.getResult();
			if (types != null && types.length > 0) {
				IModelElement type = null;
				for (int i = 0; i < types.length; i++) {
					type = (IModelElement) types[i];
					try {
													
						String superclass = "";
						
						if (type.getParent() == null)
							return;
						
						superclass += type.getParent().getElementName() + "\\";
						superclass += type.getElementName();
						
						superClassText.setText(superclass);

					} catch (Exception x) {

					}
				}
			}				
			
			
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	private SelectionListener interfaceSelectionListener = new SelectionListener() {
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			
			OpenTypeSelectionDialog2 dialog = getDialog(IDLTKSearchConstants.TYPE, "Interface selection", "Select interface");

			int result = dialog.open();
			if (result != IDialogConstants.OK_ID)
				return;
			
			Object[] types = dialog.getResult();
			if (types != null && types.length > 0) {
				IModelElement type = null;
				for (int i = 0; i < types.length; i++) {
					type = (IModelElement) types[i];
					try {
													
						String _interface = "";
						
						if (type.getParent() == null)
							return;
						
						_interface += type.getParent().getElementName() + "\\";
						_interface += type.getElementName();
						
						interfaces.add(_interface);						
						interfaceTable.setInput(interfaces);

					} catch (Exception x) {

					}
				}
			}				
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			
		}
	};
	private AutoCompleteField acField;
	
	
	private class SuperclassProvider implements IContentProposalProvider {

		@Override
		public IContentProposal[] getProposals(String contents, int position) {

			
		         ArrayList list = new ArrayList();
		         
		         for (String city : cities) {
 
		               list.add(makeContentProposal(city, city));

		         }
		         return (IContentProposal[]) list.toArray(new IContentProposal[list.size()]);


		}
		
		private IContentProposal makeContentProposal(final String proposal, final String label) {
			
			return new IContentProposal() {
				
				@Override
				public String getLabel() {
					return proposal + " - " + label;
				}
				
				@Override
				public String getDescription() {
					return null;
				}
				
				@Override
				public int getCursorPosition() {
					return proposal.length();
				}
				
				@Override
				public String getContent() {
					return proposal;
				}
			};
		}
		
	}
	
	private KeyListener acListener = new KeyListener() {
		
		@Override
		public void keyReleased(KeyEvent e) {

			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {

			List<String> props = new ArrayList<String>();
			
			IScriptProject project = DLTKCore.create(getProject());
			IDLTKSearchScope scope = SearchEngine.createSearchScope(project);
			IType[] types = PhpModelAccess.getDefault().findTypes(superClassText.getText(), MatchRule.PREFIX, 0, 0, scope, null);
			
			for (IType type : types) {					
				props.add(type.getElementName());					
			}
										
			acField.setProposals((String[]) props.toArray(new String[props.size()]));			
			
		}
	};
	private ControlDecoration decoration;
	private Button commentCheckbox;

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(final Composite parent) {
		
		final Composite container = new Composite(parent, SWT.NULL);
		
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 400;
		
		superClassLabel = new Label(container, SWT.NULL);
		superClassLabel.setText("Superclass:");
		
		superClassText = new Text(container, SWT.BORDER | SWT.SINGLE);
		superClassText.setLayoutData(gd);
		superClassText.addKeyListener(acListener);
		superClassText.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {				
				decoration.hide();				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				decoration.show();				
			}
		});


		acField = new AutoCompleteField(superClassText, new TextContentAdapter(), null);
		
		decoration = new ControlDecoration(superClassLabel, SWT.RIGHT | SWT.TOP);
		Image errorImage = FieldDecorationRegistry.getDefault()
		        .getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL).getImage();
		decoration.setImage(errorImage);
		decoration.setDescriptionText("Content assist available.");
		decoration.setShowHover(true);
		decoration.hide();
		
		Button button = new Button(container, SWT.NULL);
		button.setText("Browse...");
				
		button.addSelectionListener(superClassSelectionListener);	

		targetResourceLabel = new Label(container, SWT.NULL);
		targetResourceLabel.setText("Class name:");

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		fileText.setFocus();
		gd = new GridData(GridData.FILL_HORIZONTAL);
				
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				dialogChanged();
			}
		});
		
		Label empty = new Label(container, SWT.None);
		empty.setText("");

		Label modifierLabel = new Label(container, SWT.NULL);
		modifierLabel.setText("Modifiers:");

		gd = new GridData();
		gd.verticalAlignment = SWT.LEFT;		
		
		RowLayout modifierLayout = new RowLayout(SWT.HORIZONTAL);
		
		Composite modifierContainer = new Composite(container, SWT.NULL);
		modifierContainer.setLayout(modifierLayout);
		
		abstractCheckbox = new Button(modifierContainer, SWT.CHECK | SWT.LEFT);	
		abstractCheckbox.setText("abstract");
		abstractCheckbox.addSelectionListener(changeListener);
		
	    finalCheckbox = new Button(modifierContainer, SWT.CHECK | SWT.LEFT);
	    finalCheckbox.setText("final");		
	    finalCheckbox.addSelectionListener(changeListener);
		
	    Label dummyLabel = new Label(container, SWT.NULL);
	    dummyLabel.setText("");
	    
		gd = new GridData();
		gd.verticalAlignment = SWT.TOP;
		
		Label interfaceLabel = new Label(container, SWT.NULL);
		interfaceLabel.setText("Interfaces:");
		interfaceLabel.setLayoutData(gd);
		
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;		
		
		interfaceTable = new TableViewer(container, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
		interfaceTable.setContentProvider(ArrayContentProvider.getInstance());
		interfaceTable.setInput(interfaces);
		interfaceTable.getControl().setLayoutData(gridData);
		
		FillLayout buttonLayout = new FillLayout();
		buttonLayout.type = SWT.VERTICAL;
		
		gd = new GridData();
		gd.verticalAlignment = SWT.TOP;
		
		Composite buttonContainer = new Composite(container, SWT.NULL);
		buttonContainer.setLayout(buttonLayout);
		buttonContainer.setLayoutData(gd);
		
		
		Button addInterface = new Button(buttonContainer, SWT.NULL);
		addInterface.setText("Add...");
		addInterface.addSelectionListener(interfaceSelectionListener);

		Button removeInterface = new Button(buttonContainer, SWT.NULL);
		removeInterface.setText("Remove");
		
		removeInterface.addSelectionListener(interfaceRemoveListener);
		
		
		Label commentLabel = new Label(container, SWT.NONE);
		commentLabel.setText("Generate element comments:");
		
		commentCheckbox = new Button(container, SWT.CHECK);


		initialize();
		dialogChanged();
		setControl(container);
		
//		PlatformUI
//				.getWorkbench()
//				.getHelpSystem()
//				.setHelp(parent,
//						IPHPHelpContextIds.CREATING_A_PHP_FILE_WITHIN_A_PROJECT);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */
	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1) {
				return;
			}

			Object obj = ssel.getFirstElement();
			if (obj instanceof IAdaptable) {
				obj = ((IAdaptable) obj).getAdapter(IResource.class);
			}

			IContainer container = null;
			if (obj instanceof IResource) {
				if (obj instanceof IContainer) {
					container = (IContainer) obj;
				} else {
					container = ((IResource) obj).getParent();
				}
			}

			if (container != null) {
				containerName = container.getFullPath().toString();
			}
		}
		setInitialFileName(initialFileName); //$NON-NLS-1$
	}

	protected void setInitialFileName(final String fileName) {
		
		fileText.setFocus();
		fileText.setText(fileName);
		fileText.setSelection(0, fileName.length());
	}


	/**
	 * Ensures that both text fields are set.
	 */
	protected void dialogChanged() {
		final String container = getContainerName();
		final String fileName = getFileName();

		if (abstractCheckbox.getSelection() && finalCheckbox.getSelection()) {			
			updateStatus("A class cannot be abstract and final at the same time");
			return;
		}
		if (container.length() == 0) {
			updateStatus(PHPUIMessages.PHPFileCreationWizardPage_10); //$NON-NLS-1$
			return;
		}
		final IContainer containerFolder = getContainer(container);
		if (containerFolder == null || !containerFolder.exists()) {
			updateStatus(PHPUIMessages.PHPFileCreationWizardPage_11); //$NON-NLS-1$
			return;
		}
		if (!containerFolder.getProject().isOpen()) {
			updateStatus(PHPUIMessages.PHPFileCreationWizardPage_12); //$NON-NLS-1$
			return;
		}
		if (fileName != null
				&& !fileName.equals("") && containerFolder.getFile(new Path(fileName)).exists()) { //$NON-NLS-1$
			updateStatus("The specified class already exists"); //$NON-NLS-1$
			return;
		}
		
		

		int dotIndex = fileName.lastIndexOf('.');
		if (fileName.length() == 0 || dotIndex == 0) {
			updateStatus(PHPUIMessages.PHPFileCreationWizardPage_15); //$NON-NLS-1$
			return;
		}

		if (dotIndex != -1) {
			String fileNameWithoutExtention = fileName.substring(0, dotIndex);
			for (int i = 0; i < fileNameWithoutExtention.length(); i++) {
				char ch = fileNameWithoutExtention.charAt(i);
				if (!(Character.isJavaIdentifierPart(ch) || ch == '.' || ch == '-')) {
					updateStatus(PHPUIMessages.PHPFileCreationWizardPage_16); //$NON-NLS-1$
					return;
				}
			}
		}
				
		String text = fileText.getText();
		
		if (text.length() > 0 && Character.isLowerCase(fileText.getText().charAt(0))) {						
			setMessage("Classes starting with lowercase letters are discouraged", IMessageProvider.WARNING);						
		} else {
			setMessage("");
		}
		
		updateStatus(null);
	}

	protected void updateStatus(final String message) {
		
		setErrorMessage(message);
		setPageComplete(message == null);
	}


	public String getFileName() {
		return fileText.getText() + ".php";
	}

	public String getSuperclass() {

		return superClassText.getText();

	}
	
	public List<String> getInterfaces() {
		
		return interfaces;
		
	}


	public String getModifiers() {

		if (abstractCheckbox.getSelection())
			return "abstract ";

		if (finalCheckbox.getSelection())
			return "final ";
			
		return "";
		
	}
	
	public boolean shouldGenerateComments() {

		return commentCheckbox.getSelection();
		
	}
}
