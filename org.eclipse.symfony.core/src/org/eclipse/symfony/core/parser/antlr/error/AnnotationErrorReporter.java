package org.eclipse.symfony.core.parser.antlr.error;

import org.antlr.runtime.RecognitionException;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.ProblemSeverities;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.symfony.core.SymfonyCorePlugin;

/**
 * 
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public class AnnotationErrorReporter implements IAnnotationErrorReporter {

	private IBuildContext context;	
	private int sourceStart;
	private int lineNo = 1;

	public AnnotationErrorReporter(IBuildContext context) {

		this.context = context;

	}


	@Override
	@SuppressWarnings("deprecation")	
	public void reportError(String header, String message, RecognitionException e) {

		if (context == null || e.token == null)  {

			return;
		}

		if(lineNo == 1)
		{
			char[] content = context.getContents();
			for(int i=0; i < sourceStart; i++)
			{
				if(content[i] == '\n')
				{
					lineNo++;
				}
			}
		}
		
		String filename = context.getFile().getName();		
		int start = sourceStart+e.token.getCharPositionInLine();
		int end = start+e.token.getText().length();

		
		
		//IPreferenceStore store = SymfonyUiPlugin.getDefault().getPreferenceStore();
		//String warnType = store.getString(PreferenceConstants.ANNOTATION_PROBLEMS);
		
		IProblem problem = new DefaultProblem(filename, message, IProblem.Syntax,
				new String[0], ProblemSeverities.Warning, start+1, end+1, lineNo);
		context.getProblemReporter().reportProblem(problem);

	}

	public IBuildContext getContext() {
		return context;
	}

}
