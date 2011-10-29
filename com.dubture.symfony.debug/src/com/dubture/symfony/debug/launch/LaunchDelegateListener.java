package com.dubture.symfony.debug.launch;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.php.debug.core.debugger.launching.ILaunchDelegateListener;

import com.dubture.symfony.debug.util.ServerUtils;

/**
 * 
 * A PHP Debug launchDelegate that injects the correct Launch URL
 * for a corresponding Symfony route.
 *  
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class LaunchDelegateListener implements ILaunchDelegateListener {


	@Override
	public int preLaunch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) {
		
		ServerUtils.injectRoutingURL(configuration);
		return 0;
	}
}
