package com.dubture.symfony.ui.wizards.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.ComboDialogField;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.RepositoryPackage;
import org.eclipse.php.composer.api.collection.Versions;
import org.eclipse.php.composer.api.packages.AsyncPackagistDownloader;
import org.eclipse.php.composer.api.packages.PackageListenerInterface;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.ui.wizard.AbstractWizardFirstPage;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.ui.wizards.PHPProjectWizardFirstPage.VersionGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;

import com.dubture.symfony.core.preferences.SymfonyCoreConstants;

@SuppressWarnings("restriction")
public class SymfonyVersionGroup extends VersionGroup {

	protected ComboDialogField symfonyVersionSelector;
	
	protected AbstractWizardFirstPage firstPage;
	
	public SymfonyVersionGroup(AbstractWizardFirstPage composerProjectWizardFirstPage, Composite composite) {
		super(composerProjectWizardFirstPage, composite, PHPVersion.PHP5_3);
		firstPage = composerProjectWizardFirstPage;
		
		final Group group = new Group(composite, SWT.NONE);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new GridLayout(2, false));
		group.setText("Symfony Version");
		createSubComponents(group);
	}
	
	protected void createSubComponents(Composite group) {
		symfonyVersionSelector = new ComboDialogField(SWT.READ_ONLY);
		symfonyVersionSelector.setLabelText("Please select:");
		symfonyVersionSelector.doFillIntoGrid(group, 2);
		symfonyVersionSelector.setDialogFieldListener(this);
		fConfigurationBlock.setMinimumVersion(PHPVersion.PHP5_3);
		loadVersionCombo();
	}
	
	protected void loadVersionCombo() {

		symfonyVersionSelector.setItems(new String[]{"Loading versions..."});
		symfonyVersionSelector.selectItem(0);
		
		AsyncPackagistDownloader dl = new AsyncPackagistDownloader();
		dl.addPackageListener(new PackageListenerInterface() {

			@Override
			public void errorOccured(Exception e) {
				Logger.logException(e);
			}

			@Override
			public void aborted(String url) {
				
			}

			@Override
			public void packageLoaded(RepositoryPackage repositoryPackage) {
				Versions versions = repositoryPackage.getVersions();
				final List<String> versionNames = new ArrayList<String>();
				for (Entry<String, ComposerPackage> version : versions) {
					versionNames.add(version.getValue().getVersion());
				}

				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						symfonyVersionSelector.setItems(versionNames.toArray(new String[versionNames.size()]));
						symfonyVersionSelector.selectItem(0);
					}
				});
			}
		});

		dl.loadPackage(SymfonyCoreConstants.SYMFONY_STANDARD_EDITION);
	}
	
	public String getSymfonyVersion() {
		return symfonyVersionSelector.getText();
	}

	@Override
	public IEnvironment getEnvironment() {
		return firstPage.getEnvironment();
	}
}
