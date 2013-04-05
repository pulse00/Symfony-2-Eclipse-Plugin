package com.dubture.symfony.ui.wizards.project;

import java.util.ArrayList;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.ui.wizards.ISymfonyProjectWizardExtension;

@SuppressWarnings("restriction")
public class SymfonySupportGroup implements SelectionListener {

	private final SymfonyProjectWizardFirstPage symfonyProjectWizardFirstPage;
	private final Group fGroup;
    protected Button fEnableTwigSupport;

    public boolean shouldSupportJavaScript() {
        return PHPUiPlugin.getDefault()
                          .getPreferenceStore()
                          .getBoolean((PreferenceConstants.JavaScriptSupportEnable));
    }

    public SymfonySupportGroup(SymfonyProjectWizardFirstPage symfonyProjectWizardFirstPage, Composite composite, WizardPage projectWizardFirstPage) {
        this.symfonyProjectWizardFirstPage = symfonyProjectWizardFirstPage;
		final int numColumns = 1;
        fGroup = new Group(composite, SWT.NONE);
        fGroup.setFont(composite.getFont());

        fGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        fGroup.setLayout(this.symfonyProjectWizardFirstPage.initGridLayout(new GridLayout(numColumns, false),
                                        true));
        fGroup.setText("Symfony"); //$NON-NLS-1$

        IConfigurationElement[] config = Platform.getExtensionRegistry()
                                                 .getConfigurationElementsFor(SymfonyProjectWizardFirstPage.WIZARDEXTENSION_ID);
        this.symfonyProjectWizardFirstPage.extensions = new ArrayList<ISymfonyProjectWizardExtension>();

        try {
            for (IConfigurationElement e : config) {
                final Object object = e.createExecutableExtension("class");
                if (object instanceof ISymfonyProjectWizardExtension) {
                    ISymfonyProjectWizardExtension extension = (ISymfonyProjectWizardExtension) object;
                    extension.addElements(fGroup);
                    this.symfonyProjectWizardFirstPage.extensions.add(extension);
                }
            }
        } catch (Exception e) {
            Logger.logException(e);
        }

        // hide the symfony group if no extensions is filling it up
        if (config.length == 0) {
            fGroup.setVisible(false);
        }
    }

    public void widgetDefaultSelected(SelectionEvent e) {
    }

    public void widgetSelected(SelectionEvent e) {
        PHPUiPlugin.getDefault().getPreferenceStore().setValue((PreferenceConstants.JavaScriptSupportEnable),
                                                               fEnableTwigSupport.getSelection());
    }

    public boolean getSelection() {
        return fEnableTwigSupport.getSelection();
    }
}