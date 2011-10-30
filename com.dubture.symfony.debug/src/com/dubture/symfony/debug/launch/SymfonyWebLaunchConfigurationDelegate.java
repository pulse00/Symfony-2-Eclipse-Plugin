package com.dubture.symfony.debug.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.php.internal.debug.core.launching.XDebugWebLaunchConfigurationDelegate;

/**
 * 
 * A ConfigurationDelegate for XDebug to inject the correct URL
 * depending on the current cursor position in the editor.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * 
 */
@SuppressWarnings("restriction")
public class SymfonyWebLaunchConfigurationDelegate extends
		XDebugWebLaunchConfigurationDelegate {
	
	
	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {

		super.launch(configuration, mode, launch, monitor);
	}
}