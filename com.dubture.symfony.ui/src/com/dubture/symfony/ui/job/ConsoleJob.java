package com.dubture.symfony.ui.job;

import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.swt.widgets.Display;
import org.eclipse.php.composer.core.launch.ExecutableNotFoundException;
import org.eclipse.php.composer.core.launch.ScriptLauncher;
import org.eclipse.php.composer.core.launch.ScriptLauncherManager;
import org.eclipse.php.composer.core.launch.ScriptNotFoundException;
import org.eclipse.php.composer.core.launch.execution.ExecutionResponseAdapter;

import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.ui.handler.ConsoleResponseHandler;
import org.eclipse.php.composer.ui.job.runner.ComposerFailureMessageRunner;
import org.eclipse.php.composer.ui.job.runner.MissingExecutableRunner;
import com.dubture.symfony.core.launch.SymfonyEnvironmentFactory;
import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.ui.SymfonyUiPlugin;

abstract public class ConsoleJob extends Job {

	@Inject
	public ScriptLauncherManager manager;
	private IProject project;
	private IProgressMonitor monitor;
	private boolean cancelling = false;
	private ScriptLauncher launcher;

	protected static final IStatus ERROR_STATUS = new Status(Status.ERROR, ComposerPlugin.ID,
			"Error running symfony console, see log for details");

	public ConsoleJob(String name) {
		super(name);
		ContextInjectionFactory.inject(this, SymfonyUiPlugin.getDefault().getEclipseContext());
	}

	@Override
	protected void canceling() {

		if (cancelling || launcher == null || !monitor.isCanceled()) {
			return;
		}

		launcher.abort();
		monitor.done();
		cancelling = true;
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		try {

			this.monitor = monitor;

			try {
				launcher = manager.getLauncher(SymfonyEnvironmentFactory.FACTORY_ID, getProject());
			} catch (ExecutableNotFoundException e) {
				// inform the user of the missing executable
				Display.getDefault().asyncExec(new MissingExecutableRunner());
				return Status.OK_STATUS;
			} catch (ScriptNotFoundException e) {
				// run the downloader
				// Display.getDefault().asyncExec(new DownloadRunner());
				return Status.OK_STATUS;
			}

			launcher.addResponseListener(new ConsoleResponseHandler());
			launcher.addResponseListener(new ExecutionResponseAdapter() {
				public void executionFailed(final String response, final Exception exception) {

					// TODO: write a dialog for symfony console launcher
					Display.getDefault().asyncExec(new ComposerFailureMessageRunner(response, monitor));
				}

				@Override
				public void executionMessage(String message) {
					if (monitor != null && message != null) {
						monitor.subTask(message);
						monitor.worked(1);
					}
				}
			});

			monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
			monitor.worked(1);
			launch(launcher);
			monitor.worked(1);

			// refresh project
			if (getProject() != null) {
				getProject().refreshLocal(IProject.DEPTH_INFINITE, null);
				monitor.worked(1);
			}
		} catch (Exception e) {
			Logger.logException(e);
			return ERROR_STATUS;
		} finally {
			monitor.done();
		}

		return Status.OK_STATUS;
	}

	abstract protected void launch(ScriptLauncher launcher) throws IOException, InterruptedException;

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}
}
