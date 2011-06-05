package org.eclipse.symfony.core.builder;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.ScriptNature;
import org.eclipse.symfony.core.SymfonyCorePlugin;

public class SymfonyNature extends ScriptNature {

	/**
	 * ID of this project nature
	 */
	public static final String NATURE_ID = SymfonyCorePlugin.ID + ".symfonyNature";

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#configure()
	 */
	public void configure() throws CoreException {
		
		super.configure();
		
		IProjectDescription desc = getProject().getDescription();
		ICommand[] commands = desc.getBuildSpec();

		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(SymfonyBuilder.BUILDER_ID)) {
				return;
			}
		}

		ICommand[] newCommands = new ICommand[commands.length + 1];
		System.arraycopy(commands, 0, newCommands, 0, commands.length);
		ICommand command = desc.newCommand();
		command.setBuilderName(SymfonyBuilder.BUILDER_ID);
		newCommands[newCommands.length - 1] = command;
		desc.setBuildSpec(newCommands);
		getProject().setDescription(desc, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#deconfigure()
	 */
	public void deconfigure() throws CoreException {
		
		super.deconfigure();
		
		IProjectDescription description = getProject().getDescription();
		ICommand[] commands = description.getBuildSpec();
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(SymfonyBuilder.BUILDER_ID)) {
				ICommand[] newCommands = new ICommand[commands.length - 1];
				System.arraycopy(commands, 0, newCommands, 0, i);
				System.arraycopy(commands, i + 1, newCommands, i,
						commands.length - i - 1);
				description.setBuildSpec(newCommands);
				getProject().setDescription(description, null);			
				return;
			}
		}
	}



}
