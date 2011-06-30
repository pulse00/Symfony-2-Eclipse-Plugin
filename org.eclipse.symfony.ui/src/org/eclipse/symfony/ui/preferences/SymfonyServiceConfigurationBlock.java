package org.eclipse.symfony.ui.preferences;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.php.internal.ui.preferences.IStatusChangeListener;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.symfony.ui.CorePreferenceConstants.Keys;
import org.eclipse.symfony.ui.Messages;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * 
 *
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyServiceConfigurationBlock extends
		SymfonyCoreOptionsConfigurationBlock {

	private static final Key SYNTHETIC_SERVICES= getSymfonyCoreKey(Keys.SYNTHETIC_SERVICES);
	
	
	private Button dummyButton; 
	
	private IStatus fTaskTagsStatus;
	
	public SymfonyServiceConfigurationBlock(IStatusChangeListener context,
			IProject project, Key[] allKeys,
			IWorkbenchPreferenceContainer container) {
		super(context, project, allKeys, container);

	}

	public SymfonyServiceConfigurationBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {

			this(newStatusChangedListener, project, getKeys(), container);		
		
	}
	
	private static Key[] getKeys() {
		return new Key[] { SYNTHETIC_SERVICES };
	}	

	@Override
	protected Control createContents(Composite parent) {

		setShell(parent.getShell());
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		
		
		dummyButton = new Button(composite, SWT.CHECK | SWT.RIGHT);
		dummyButton
				.setText("dummy label");
		dummyButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				setDummyValue(Boolean.toString(dummyButton
						.getSelection()));
			}
		});		
		
		
		validateSettings(null, null, null);
		return composite;

	}
	
	private void setDummyValue(String value) {
		setValue(SYNTHETIC_SERVICES, value);
		validateSettings(SYNTHETIC_SERVICES, null, null);
	}
	

	@Override
	protected void validateSettings(Key changedKey, String oldValue,
			String newValue) {
		
		if (changedKey != null) {
			if (SYNTHETIC_SERVICES.equals(changedKey)) {
				fTaskTagsStatus = validateDummyValue();
			} else {
				return;
			}
		} else {
			fTaskTagsStatus = validateDummyValue();
		}
		fContext.statusChanged(fTaskTagsStatus);		


	}
	
	private IStatus validateDummyValue() {
		return new StatusInfo();
	}
	

	@Override
	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {

		System.err.println("full build");
		String title = Messages.SymfonyServiceConfigurationBlock_0;
		
		String message;
		if (workspaceSettings) {
			message = Messages.SymfonyServiceConfigurationBlock_1;
		} else {
			message = Messages.SymfonyServiceConfigurationBlock_2;
		}
		return new String[] { title, message };
		
	}

}
