package com.dubture.symfony.ui.quickfix;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.php.internal.ui.text.correction.IInvocationContext;
import org.eclipse.php.internal.ui.text.correction.IProblemLocation;
import org.eclipse.php.internal.ui.text.correction.IQuickFixProcessor;

import com.dubture.symfony.core.compiler.ISymfonyProblem;
import com.dubture.symfony.ui.contentassist.InterfaceMethodCompletionProposal;


@SuppressWarnings("restriction")
public class InterfaceMethodQuickFixProcessor implements IQuickFixProcessor {

	public InterfaceMethodQuickFixProcessor() {

	}

	@Override
	public boolean hasCorrections(ISourceModule unit, int problemId) {

		if (problemId == ISymfonyProblem.InterfaceRelated) {
			System.err.println("can correct");
			return true;
		}

		return false;
	}

	@Override
	public IScriptCompletionProposal[] getCorrections(
			IInvocationContext context, IProblemLocation[] locations)
			throws CoreException {

		InterfaceMethodCompletionProposal prop = new InterfaceMethodCompletionProposal("foo", 0, 100, null, "some", 100);
		
		return new IScriptCompletionProposal[] { prop };

	}

}
