/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui;

import java.io.IOException;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.pdtextensions.core.ui.preferences.PHPExecutableChangeListener;

import org.eclipse.php.composer.ui.ComposerUIPlugin;
import com.dubture.symfony.core.SymfonyCorePlugin;  
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.preferences.CorePreferenceConstants.Keys;
import com.dubture.symfony.ui.editor.template.CodeTemplateContextType;
import com.dubture.symfony.ui.preferences.SymfonyTemplateStore;
import com.dubture.symfony.ui.viewsupport.ImagesOnFileSystemRegistry;

/**
 * The activator class controls the plug-in life cycle
 */
@SuppressWarnings("restriction")
public class SymfonyUiPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.dubture.symfony.ui"; //$NON-NLS-1$

	// The shared instance
	private static SymfonyUiPlugin plugin;

	private ImagesOnFileSystemRegistry fImagesOnFSRegistry;

	private SymfonyTemplateStore fCodeTemplateStore;

	protected ContextTypeRegistry codeTypeRegistry = null;
	private IEclipseContext eclipseContext;

	/**
	 * The constructor
	 */
	public SymfonyUiPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@SuppressWarnings("deprecation")
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		eclipseContext = EclipseContextFactory.getServiceContext(context);

		try {
			PHPDebugPlugin
					.getDefault()
					.getPluginPreferences()
					.addPropertyChangeListener(new PHPExecutableChangeListener(SymfonyCorePlugin.ID, Keys.PHP_EXECUTABLE));
			
			// make sure composer loads the preference listener for the php executable
			ComposerUIPlugin.getDefault();
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static SymfonyUiPlugin getDefault() {
		return plugin;
	}

	public ImagesOnFileSystemRegistry getImagesOnFSRegistry() {
		if (fImagesOnFSRegistry == null) {
			fImagesOnFSRegistry = new ImagesOnFileSystemRegistry();
		}
		return fImagesOnFSRegistry;
	}

	public TemplateStore getCodeTemplateStore() {
		if (fCodeTemplateStore == null) {

			fCodeTemplateStore = new SymfonyTemplateStore(getCodeTemplateContextRegistry(), getPreferenceStore(),
					PreferenceConstants.CODE_TEMPLATES_KEY);
			try {
				fCodeTemplateStore.load();
			} catch (IOException e) {
				Logger.logException(e);
			}
		}

		return fCodeTemplateStore;
	}

	public ContextTypeRegistry getCodeTemplateContextRegistry() {
		if (codeTypeRegistry == null) {
			ContributionContextTypeRegistry registry = new ContributionContextTypeRegistry();

			CodeTemplateContextType.registerContextTypes(registry);

			codeTypeRegistry = registry;
		}

		return codeTypeRegistry;
	}

	public IEclipseContext getEclipseContext() {
		return eclipseContext;
	}
}
