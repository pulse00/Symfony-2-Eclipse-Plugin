package com.dubture.symfony.core.model;

import org.eclipse.dltk.core.ISourceModule;

/**
 *
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
	
	public AppKernel(String environment, ISourceModule sourceModule) {
		
		this.environment = environment;
		this.source = sourceModule;
		path = source.getPath().removeFirstSegments(1).toString();
		
	}

	public String getEnvironment() {
		return environment;
	}

	public String getPath() {
		
		return path;
		
	}

}
