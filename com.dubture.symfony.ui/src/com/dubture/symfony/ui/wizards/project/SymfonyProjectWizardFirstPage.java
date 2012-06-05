/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.wizards.project;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PHPProjectLayoutPreferencePage;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.wizards.CompositeData;
import org.eclipse.php.internal.ui.wizards.DetectGroup;
import org.eclipse.php.internal.ui.wizards.LocationGroup;
import org.eclipse.php.internal.ui.wizards.NameGroup;
import org.eclipse.php.internal.ui.wizards.PHPProjectWizardFirstPage;
import org.eclipse.php.internal.ui.wizards.WizardFragment;
import org.eclipse.php.internal.ui.wizards.fields.ComboDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.osgi.framework.Bundle;

import com.dubture.symfony.core.SymfonyCorePlugin;
import com.dubture.symfony.core.SymfonyVersion;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.preferences.SymfonyCoreConstants;
import com.dubture.symfony.ui.Messages;
import com.dubture.symfony.ui.SymfonyUiPlugin;
import com.dubture.symfony.ui.wizards.ISymfonyProjectWizardExtension;

@SuppressWarnings("restriction")
public class SymfonyProjectWizardFirstPage extends PHPProjectWizardFirstPage {

    public static final String WIZARDEXTENSION_ID = "com.dubture.symfony.ui.projectWizardExtension";

    private SymfonySupportGroup symfonySupportGroup;
    private SymfonyLayoutGroup fSymfonyLayoutGroup;

    protected Validator validator;

    private List<ISymfonyProjectWizardExtension> extensions = new ArrayList<ISymfonyProjectWizardExtension>();

    public SymfonyProjectWizardFirstPage() {

        setPageComplete(false);
        setTitle("New Symfony project");
        setDescription("Create a Symfony project in the workspace or in an external location.");
        fInitialName = ""; //$NON-NLS-1$$
    }

    public void createControl(Composite parent) {
        initializeDialogUnits(parent);
        final Composite composite = new Composite(parent, SWT.NULL);
        composite.setFont(parent.getFont());
        composite.setLayout(initGridLayout(new GridLayout(1, false), false));
        composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
        // create UI elements
        fNameGroup = new NameGroup(composite, fInitialName, getShell());
        fPHPLocationGroup = new LocationGroup(composite, fNameGroup, getShell());

        fPHPLocationGroup.addObserver(new Observer() {
            @Override
            public void update(Observable arg0, Object arg1) {
                fSymfonyLayoutGroup.setEnabled(!fPHPLocationGroup.isExistingLocation());
            }
        });

        CompositeData data = new CompositeData();
        data.setParetnt(composite);
        data.setSettings(getDialogSettings());
        data.setObserver(fPHPLocationGroup);
        fragment = (WizardFragment) Platform.getAdapterManager().loadAdapter(
                                                                             data,
                                                                             PHPProjectWizardFirstPage.class.getName());

        // don't really have a choice with Symfony2 ;)
        // fVersionGroup = new VersionGroup(composite);

        fSymfonyLayoutGroup = new SymfonyLayoutGroup(composite);
        fJavaScriptSupportGroup = new JavaScriptSupportGroup(composite, this);

        symfonySupportGroup = new SymfonySupportGroup(composite, this);
        fDetectGroup = new DetectGroup(composite, fPHPLocationGroup, fNameGroup);

        // establish connections
        fNameGroup.addObserver(fPHPLocationGroup);
        fDetectGroup.addObserver(fSymfonyLayoutGroup);

        fPHPLocationGroup.addObserver(fDetectGroup);
        // initialize all elements
        fNameGroup.notifyObservers();
        // create and connect validator
        validator = new Validator();

        fNameGroup.addObserver(validator);
        fPHPLocationGroup.addObserver(validator);
        fSymfonyLayoutGroup.addObserver(validator);

        setControl(composite);
        Dialog.applyDialogFont(composite);

        // set the focus to the project name
        fNameGroup.postSetFocus();

        setHelpContext(composite);
    }

    public boolean hasTwigSupport() {
        return symfonySupportGroup.getSelection();
    }

    public List<ISymfonyProjectWizardExtension> getExtensions() {
        return extensions;
    }

    public class SymfonySupportGroup implements SelectionListener {

        private final Group fGroup;
        protected Button fEnableJavaScriptSupport;

        public boolean shouldSupportJavaScript() {
            return PHPUiPlugin.getDefault()
                              .getPreferenceStore()
                              .getBoolean((PreferenceConstants.JavaScriptSupportEnable));
        }

        public SymfonySupportGroup(Composite composite, WizardPage projectWizardFirstPage) {
            final int numColumns = 1;
            fGroup = new Group(composite, SWT.NONE);
            fGroup.setFont(composite.getFont());

            fGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            fGroup.setLayout(initGridLayout(new GridLayout(numColumns, false),
                                            true));
            fGroup.setText("Symfony"); //$NON-NLS-1$

            IConfigurationElement[] config = Platform.getExtensionRegistry()
                                                     .getConfigurationElementsFor(WIZARDEXTENSION_ID);
            extensions = new ArrayList<ISymfonyProjectWizardExtension>();

            try {
                for (IConfigurationElement e : config) {
                    final Object object = e.createExecutableExtension("class");
                    if (object instanceof ISymfonyProjectWizardExtension) {
                        ISymfonyProjectWizardExtension extension = (ISymfonyProjectWizardExtension) object;
                        extension.addElements(fGroup);
                        extensions.add(extension);
                    }
                }
            } catch (Exception e) {
                Logger.logException(e);
            }

            // hide the symfony group if no extensions is filling it up
            if (config.length == 0)
                fGroup.setVisible(false);
        }

        public void widgetDefaultSelected(SelectionEvent e) {
        }

        public void widgetSelected(SelectionEvent e) {
            PHPUiPlugin.getDefault().getPreferenceStore().setValue((PreferenceConstants.JavaScriptSupportEnable),
                                                                   fEnableJavaScriptSupport.getSelection());
        }

        public boolean getSelection() {
            return fEnableJavaScriptSupport.getSelection();
        }
    }

    /**
     * Request a project layout.
     */
    public class SymfonyLayoutGroup extends Observable implements Observer, SelectionListener, IDialogFieldListener {

        private Group fGroup;
        // TODO: link to preference page
        // private Link fPreferenceLink;
        private ComboDialogField versionSelector;
        private ComboDialogField layoutSelector;

        protected void fireEvent() {
            setChanged();
            notifyObservers();
        }

        public SymfonyLayoutGroup(Composite composite) {
            final int numColumns = 4;

            String[] layouts = new String[] {"Standard Edition (vendors)",
                                             "Standard Edition (no vendors)",
                                             "Custom project layout"};

            final Map<String, String[]> available = new HashMap<String, String[]>();

            available.put(layouts[0], new String[] {SymfonyVersion.Symfony2_0_15.getAlias()});
            available.put(layouts[1], new String[] {SymfonyVersion.Symfony2_0_15.getAlias()});

            IPreferenceStore store = SymfonyUiPlugin.getDefault().getPreferenceStore();

            String thing = store.getString(Messages.LibraryPreferencePage_1);
            String[] paths = thing.split(":");

            available.put(layouts[2], paths);

            versionSelector = new ComboDialogField(SWT.READ_ONLY);
            versionSelector.setLabelText("");
            versionSelector.setItems(layouts);

            layoutSelector = new ComboDialogField(SWT.READ_ONLY);
            layoutSelector.setLabelText("Select layout:");
            layoutSelector.setItems(layouts);

            layoutSelector.setDialogFieldListener(new org.eclipse.php.internal.ui.wizards.fields.IDialogFieldListener() {
                @Override
                public void dialogFieldChanged(org.eclipse.php.internal.ui.wizards.fields.DialogField field) {
                    String[] items = layoutSelector.getItems();

                    String index = items[layoutSelector.getSelectionIndex()];
                    String[] selection = available.get(index);

                    versionSelector.setItems(selection);
                    versionSelector.selectItem(0);

                    fireEvent();
                }
            });

            versionSelector.setDialogFieldListener(new org.eclipse.php.internal.ui.wizards.fields.IDialogFieldListener() {
                @Override
                public void dialogFieldChanged(org.eclipse.php.internal.ui.wizards.fields.DialogField field) {
                    fireEvent();
                }
            });

            // createContent
            fGroup = new Group(composite, SWT.NONE);
            fGroup.setFont(composite.getFont());
            fGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            fGroup.setLayout(initGridLayout(new GridLayout(numColumns, false), true));
            fGroup.setText(PHPUIMessages.LayoutGroup_OptionBlock_Title); //$NON-NLS-1$

            layoutSelector.doFillIntoGrid(fGroup, 2);

            versionSelector.doFillIntoGrid(fGroup, 2);
            layoutSelector.selectItem(0);
            versionSelector.selectItem(0);

            // fPreferenceLink = new Link(fGroup, SWT.NONE);
            // fPreferenceLink
            //                    .setText(PHPUIMessages.ToggleLinkingAction_link_description); //$NON-NLS-1$
            // fPreferenceLink.setLayoutData(new GridData(SWT.END, SWT.BEGINNING,
            // true, false));
            // fPreferenceLink.addSelectionListener(this);
            // fPreferenceLink.setEnabled(true);
        }

        public void setEnabled(boolean b) {
            layoutSelector.setEnabled(b);
            versionSelector.setEnabled(b);
        }

        /*
         * (non-Javadoc)
         *
         * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse
         * .swt.events.SelectionEvent)
         */
        public void widgetSelected(SelectionEvent e) {
            widgetDefaultSelected(e);
        }

        public void widgetDefaultSelected(SelectionEvent e) {
            String prefID = PHPProjectLayoutPreferencePage.PREF_ID;

            Object data = null;
            PreferencesUtil.createPreferenceDialogOn(getShell(), prefID,
                                                     new String[] {prefID}, data).open();
        }

        public boolean hasSymfonyStandardEdition() {
            
            System.err.println("selected index " + layoutSelector.getSelectionIndex());
            return layoutSelector.getSelectionIndex() <= 1;
        }

        public SymfonyVersion getSymfonyVersion() {
            if (versionSelector.getSelectionIndex() == 0) {
                return SymfonyVersion.Symfony2_0_15;
            }

            return null;
        }

        @Override
        public void dialogFieldChanged(DialogField field) {
            // Do nothing
        }

        @Override
        public void update(Observable o, Object arg) {
            // Do nothing
        }
    }

    public boolean shouldSupportJavascript() {
        return fJavaScriptSupportGroup.shouldSupportJavaScript();
    }

    // Symfony requires php 5.3
    public PHPVersion getPHPVersionValue() {
        return PHPVersion.PHP5_3;
    }

    public boolean hasSymfonyStandardEdition() {
        return fSymfonyLayoutGroup.hasSymfonyStandardEdition();
    }

    public SymfonyVersion getSymfonyVersion() {
        return fSymfonyLayoutGroup.getSymfonyVersion();
    }

    public String getLibraryPath() throws Exception {

        String path = null;

        try {
            // built in standard edition is selected
            if (hasSymfonyStandardEdition()) {

                String entry = SymfonyCoreConstants.BUILTIN_SYMFONY;

                String selection = fSymfonyLayoutGroup.versionSelector.getText();
                String parts[] = selection.split(" ");
                String version = parts[1];

                int index = fSymfonyLayoutGroup.layoutSelector.getSelectionIndex();

                if (index == 0) {
                    entry += version + "/" + SymfonyCoreConstants.BUILTIN_VENDOR + ".tgz";
                } else if (index == 1) {
                    entry += version + "/" + SymfonyCoreConstants.BUILTIN_NO_VENDOR + ".tgz";
                } else {
                    throw new Exception("Invalid library selection");
                }

                Bundle bundle = Platform.getBundle(SymfonyCorePlugin.ID);

                // for some weird reason the symfony files can't be resolved
                // if the root of the bundle is not accessed initially

                URL fileURL = bundle.getEntry(entry);
                path = FileLocator.toFileURL(fileURL).getPath().toString();

                // custom symfony installation is selected
            } else {
                path = fSymfonyLayoutGroup.versionSelector.getText();
            }
        } catch (Exception e) {
            Logger.logException(e);
        }

        return path;

    }

    public final class Validator implements Observer {

        @Override
        public void update(Observable arg0, Object arg1) {

            final IWorkspace workspace = DLTKUIPlugin.getWorkspace();
            final String name = fNameGroup.getName();
            // Check whether the project name field is empty
            if (name.length() == 0) {
                setErrorMessage(null);
                setMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_enterProjectName);
                setPageComplete(false);
                return;
            }

            // Check whether the project name is valid
            final IStatus nameStatus = workspace.validateName(name, IResource.PROJECT);
            if (!nameStatus.isOK()) {
                setErrorMessage(nameStatus.getMessage());
                setPageComplete(false);
                return;
            }

            // Check whether project already exists
            final IProject handle = getProjectHandle();
            if (!isInLocalServer()) {
                if (handle.exists()) {
                    setErrorMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_projectAlreadyExists);
                    setPageComplete(false);
                    return;
                }
            }

            IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
            String newProjectNameLowerCase = name.toLowerCase();
            for (IProject currentProject : projects) {
                String existingProjectName = currentProject.getName();
                if (existingProjectName.toLowerCase().equals(
                                                             newProjectNameLowerCase)) {
                    setErrorMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_projectAlreadyExists);
                    setPageComplete(false);
                    return;
                }
            }

            // Check whether location is empty
            final String location = fPHPLocationGroup.getLocation().toOSString();
            if (location.length() == 0) {
                setErrorMessage(null);
                setMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_enterLocation);
                setPageComplete(false);
                return;
            }

            // Check whether the location is a syntactically correct path
            if (!Path.EMPTY.isValidPath(location)) {
                setErrorMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_invalidDirectory);
                setPageComplete(false);
                return;
            }

            // Check whether the location has the workspace as prefix
            IPath projectPath = Path.fromOSString(location);
            if (!fPHPLocationGroup.isInWorkspace()
                && Platform.getLocation().isPrefixOf(projectPath)) {
                setErrorMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_cannotCreateInWorkspace);
                setPageComplete(false);
                return;
            }

            // If we do not place the contents in the workspace validate the location.
            if (!fPHPLocationGroup.isInWorkspace()) {
                IEnvironment environment = getEnvironment();
                if (EnvironmentManager.isLocal(environment)) {
                    final IStatus locationStatus = workspace
                                                            .validateProjectLocation(handle, projectPath);

                    if (!locationStatus.isOK()) {
                        setErrorMessage(locationStatus.getMessage());
                        setPageComplete(false);
                        return;
                    }

                    // File file = projectPath.toFile();
                    // if (!canCreate(projectPath.toFile())) {
                    // setErrorMessage(NewWizardMessages.ScriptProjectWizardFirstPage_Message_invalidDirectory);
                    // setPageComplete(false);
                    // return;
                    // }
                }
            }

            if (fragment != null) {
                fragment.getWizardModel().putObject("ProjectName", fNameGroup.getName());
                if (!fragment.isComplete()) {
                    setErrorMessage((String) fragment.getWizardModel().getObject(ERROR_MESSAGE));
                    setPageComplete(false);
                    return;
                }
            }

            // TODO: This check never works because when layoutSelector == 2,
            // returned LibraryPath is always null, hence the test
            // will never by true. Don't know what is is suppose to
            // check ...
            // if (fSymfonyLayoutGroup.layoutSelector.getSelectionIndex() == 2) {
            //
            // String path;
            // try {
            // path = getLibraryPath();
            // File file = new File(path);
            //
            // if (!file.exists()) {
            // setErrorMessage("Directory for custom project layout does not exist.");
            // setPageComplete(false);
            // return;
            // }
            // } catch (Exception e) {
            // Logger.logException(e);
            // setErrorMessage("Directory for custom project layout does not exist.");
            // setPageComplete(false);
            // return;
            // }
            //
            // }

            setPageComplete(true);
            setErrorMessage(null);
            setMessage(null);
        }
    }
}
