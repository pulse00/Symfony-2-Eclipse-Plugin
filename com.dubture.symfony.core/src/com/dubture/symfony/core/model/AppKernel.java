package com.dubture.symfony.core.model;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.ISourceModule;

/**
 *
 * Represents a Symfony2 AppKernel instance.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class AppKernel {
	
	
	public static final String DEV = "dev";
	public static final String PROD = "prod";
	
	private String environment;
	private String path;
	private ISourceModule source;
	private IPath rawPath;
	private String script;
	
	public AppKernel(String environment, ISourceModule sourceModule) {
		
		this.environment = environment;
		this.source = sourceModule;
		rawPath = source.getPath();
		path = source.getPath().removeFirstSegments(1).toString();		
		script = rawPath.removeFirstSegments(rawPath.segmentCount()-1).toString();		
		
	}

	/**
	 * Returns the environment this Kernel is instantiated with.
	 * 
	 * @return
	 */
	public String getEnvironment() {
		
		return environment;
		
	}
	
	/**
	 * Returns the complete path to the appKernel script.
	 * 
	 * @return
	 */
	public IPath getRawPath() {
		
		return rawPath;
		
	}

	/**
	 * Returns the relative path to the script where the AppKernel is instantiated.
	 * 
	 */
	public String getPath() {
		
		return path;
		
	}
	
	
	/**
	 * 
	 * Returns the script name the appKernel is instantiated in (ie. app_dev.php)
	 * 
	 */
	public String getScript() {
		
		return script;
		
	}

}
