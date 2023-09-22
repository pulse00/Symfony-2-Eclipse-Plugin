package com.dubture.symfony.ui.job;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.composer.core.launch.ScriptLauncher;

public class NopJob extends ConsoleJob {

	public NopJob() {
		super("Refreshing symfony project");
	}

	@Override
	protected void launch(ScriptLauncher launcher) throws IOException, InterruptedException {
		try {
			launcher.launch("");
		} catch (CoreException e) {
			throw new InterruptedException(e.getMessage());
		}
	}
}
