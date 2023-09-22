package com.dubture.symfony.core.launch;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.composer.core.launch.ScriptNotFoundException;
import org.eclipse.php.composer.core.launch.environment.PrjPharEnvironment;

import com.dubture.symfony.core.preferences.SymfonyCoreConstants;

public class SysPhpProjectConsole extends PrjPharEnvironment {

	private String php;
	
	public SysPhpProjectConsole(String executable) {
		php = executable;
	}

	public boolean isAvailable() {
		return php != null;
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

	@Override
	public ProcessBuilder getCommand() throws CoreException {
		return new ProcessBuilder(php.trim(), phar.trim());
	}
}
