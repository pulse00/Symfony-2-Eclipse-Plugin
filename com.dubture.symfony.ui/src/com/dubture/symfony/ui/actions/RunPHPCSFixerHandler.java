package com.dubture.symfony.ui.actions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.handlers.HandlerUtil;

import com.dubture.symfony.core.SymfonyCorePlugin;
import com.dubture.symfony.core.log.Logger;

/**
 * 
 * TODO: refactor the php-execution/console stuff to more generalized classes.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class RunPHPCSFixerHandler extends AbstractHandler {

	final public static URL CS_FIXER = SymfonyCorePlugin.getDefault().getBundle().getEntry("Resources/csfixer/php-cs-fixer.phar");
	
	private String phpExe;
	private String fixerName;

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		try {
			
			phpExe = getPHPExecutable();
			fixerName = getFilename();
			
			if (phpExe == null || fixerName == null) {
				writeToConsole("Unable to locate php executable or php-cs-fixer.phar!");
				return null; 
			}
			
			ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
			
			if (selection != null && selection instanceof IStructuredSelection) {
				
				IStructuredSelection strucSelection = (IStructuredSelection) selection;
				
				for (Iterator<Object> iterator = strucSelection.iterator(); iterator.hasNext();) {
					
					Object element = iterator.next();
					if (element instanceof IScriptFolder) {
						IScriptFolder folder = (IScriptFolder) element;
						processFolder(folder);
					} else if (element instanceof ISourceModule) {
						runFixer((ISourceModule) element);
					}
				}
			}
		} catch (FileNotFoundException fe) {
			writeToConsole("Unable to locate phpExecutable. Please configure a phpexecutable in the eclipse preferences");
		} catch (Exception e) {
			Logger.logException(e);
		}
		
		return null;
	}
	
	protected void processFolder(IScriptFolder folder) throws IOException, InterruptedException, CoreException {
		
		try {
			for (IModelElement child : folder.getChildren()) {
				if (child instanceof IScriptFolder) {
					processFolder((IScriptFolder) child);
				} else if (child instanceof ISourceModule) {
					runFixer((ISourceModule) child);
				}
			}
		} catch (ModelException e) {
			Logger.logException(e);
		}
	}

	private String  runFixer(ISourceModule source) throws IOException, InterruptedException, CoreException {

		Assert.isNotNull(source);
		IResource resource = source.getUnderlyingResource();
		String fileToFix =  resource.getLocation().toOSString();
		
		String[] args= { phpExe, fixerName, "fix", fileToFix };
		Process p=Runtime.getRuntime().exec(args);
		p.waitFor();

		BufferedReader output=new BufferedReader(new InputStreamReader(p
				.getInputStream()));
		String result="";
		String temp=output.readLine();
		while (temp != null) {
			if (temp.trim().length() > 0) {
				result=result + "\n" + temp;
			}
			temp=output.readLine();
		}
		result=result.trim();
		
		resource.refreshLocal(IResource.DEPTH_ZERO, null);

		writeToConsole(result);
		return result;
	}
	
	protected void writeToConsole(String message)
	{
		MessageConsole console = getConsole();
		MessageConsoleStream out = console.newMessageStream();
		out.println(message);
	}
	
	protected MessageConsole getConsole()
	{

		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		String name = "Symfony";

		for (int i = 0; i < existing.length; i++) {
			if (name.equals(existing[i].getName())) {
				return (MessageConsole) existing[i];
			}
		}
		
		//no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[]{myConsole});
		return myConsole;
	}

	protected String getPHPExecutable() throws Exception
	{
		PHPexeItem phpExe = null;
		
		phpExe = PHPexes.getInstance().getDefaultItem("com.dubture.symfony.debug.xdebugger");
		
		if (phpExe != null) {
			return phpExe.getExecutable().toString();
		}
		
		phpExe = PHPexes.getInstance().getDefaultItem("org.eclipse.php.debug.core.xdebugDebugger");
		
		if (phpExe != null) {
			return phpExe.getExecutable().toString();
		}
		
		phpExe = PHPexes.getInstance().getDefaultItem("org.eclipse.php.debug.core.zendDebugger");

		if (phpExe != null) {
			return phpExe.getExecutable().toString();
		}

		throw new FileNotFoundException("Unable to find php executable");
	}

	protected String getFilename() throws Exception
	{
		final URL resolve=FileLocator.resolve(CS_FIXER);

		if (resolve == null) {
			throw new Exception("Unable to load php-cs-fixer.phar");
		}

		IPath path = new Path(resolve.getFile());
		return path.toOSString();
	}
}
