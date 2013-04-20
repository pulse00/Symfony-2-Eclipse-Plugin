package com.dubture.symfony.core.launch;

import org.apache.commons.exec.CommandLine;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.pdtextensions.core.launch.ScriptNotFoundException;
import org.pdtextensions.core.launch.environment.PrjPharEnvironment;

import com.dubture.symfony.core.preferences.SymfonyCoreConstants;

public class SysPhpProjectConsole extends PrjPharEnvironment {

	private String php;
	
	public SysPhpProjectConsole(String executable) {
		php = executable;
	}

	public boolean isAvailable() {
		return php != null;
	}

	public CommandLine getCommand() {
		CommandLine cmd = new CommandLine(php.trim());
		cmd.addArgument(phar.trim());
		
		return cmd;
	}
	@Override
	protected IResource getScript(IProject project) {
		return project.findMember(new Path(SymfonyCoreConstants.DEFAULT_CONSOLE));
	}
	
	@Override
	public void setUp(IProject project) throws ScriptNotFoundException {
		
		IResource script = getScript(project);
		
		if (script == null) {
			throw new ScriptNotFoundException("No script found in project " + project.getName());
		}
		
		this.phar = script.getFullPath().removeFirstSegments(1).toOSString();
	}
}
