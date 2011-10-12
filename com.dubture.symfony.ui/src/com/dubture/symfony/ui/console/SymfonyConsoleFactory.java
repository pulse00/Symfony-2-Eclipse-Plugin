package com.dubture.symfony.ui.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;

public class SymfonyConsoleFactory implements IConsoleFactory {

	private static MessageConsole console;


	@Override
	public void openConsole() {

		MessageConsole console = getConsole();
		
		if (console != null) {
			
			IConsoleManager manager = ConsolePlugin.getDefault().getConsoleManager();
			IConsole[] existing = manager.getConsoles();
			boolean exists = false;
			
			for (int i = 0; i < existing.length; i++) {
				if (console == existing[i]) {
					exists = true;
					break;
				}				
			}
			
			if (!exists) {
				manager.addConsoles(new IConsole[] {console});
				manager.showConsoleView(console);
				
			}
		}

	}
	
	
	public static MessageConsole getConsole() {
		
		
		if (console == null) {
			console = new MessageConsole("Symfony", null);
		}
		
		
		return console;
		
	}

}
