package com.dubture.symfony.debug;

import org.eclipse.php.internal.debug.core.xdebug.dbgp.XDebugDebuggerConfiguration;

import com.dubture.symfony.debug.launch.SymfonyWebLaunchConfigurationDelegate;

/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class DebuggerConfiguration extends XDebugDebuggerConfiguration {
	
	public String getWebLaunchDelegateClass() {
		return SymfonyWebLaunchConfigurationDelegate.class.getName();
	}
	
}
