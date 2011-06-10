package org.eclipse.symfony.core.parser.antlr;

import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.ProblemSeverities;
import org.eclipse.dltk.core.builder.IBuildContext;

/**
 * 
 * Baseclass for the auto-generated {@link SymfonyAnnotationParser}.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public abstract class AbstractAnnotationParser extends Parser {

	
	private IBuildContext context;
	private int sourceStart;
	private int lineNo = 1;
	
	public AbstractAnnotationParser(TokenStream input) {
		super(input);
	
	}
	
    public AbstractAnnotationParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
         
    }
	
	public IBuildContext getContext() {
		return context;
	}

	public int getSourceStart() {
		return sourceStart;
	}

	public void setContext(IBuildContext context, int start) {

		this.context = context;
		sourceStart = start;
		
	}
	
	@Override
	public void reportError(RecognitionException e) {
		
		state.syntaxErrors++;
		
		if(context != null)
		{
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
			String message = getErrorMessage(e, getTokenNames());
			int start = sourceStart+e.token.getCharPositionInLine();
			int end = start+e.token.getText().length();
			IProblem problem = new DefaultProblem(filename, message, IProblem.Syntax,
					new String[0], ProblemSeverities.Warning, start+1, end+1, lineNo);
			context.getProblemReporter().reportProblem(problem);
		} else {
			super.reportError(e);
		}
	}	
	
	
}
