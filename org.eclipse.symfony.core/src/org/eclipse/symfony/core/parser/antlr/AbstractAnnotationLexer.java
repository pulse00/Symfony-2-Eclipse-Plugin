package org.eclipse.symfony.core.parser.antlr;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.ProblemSeverities;
import org.eclipse.dltk.core.builder.IBuildContext;


/**
 * 
 * Baseclass for the auto-generated {@link SymfonyAnnotationLexer}.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public abstract class AbstractAnnotationLexer extends Lexer {

	private IBuildContext context;
	private int sourceStart;
	
	private int _sErrors = 0;
	private int lineNo = 1;
	
    public AbstractAnnotationLexer() {
    	
    	
    }
    
    public AbstractAnnotationLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
	
	abstract public void mTokens() throws RecognitionException;
	
	public void setContext(IBuildContext context, int start) {

		this.context = context;
		sourceStart = start;
		
	}

	public IBuildContext getContext() {
		return context;
	}

	public int getSourceStart() {
		return sourceStart;
	}
	
	
	@Override
	public void reportError(RecognitionException e) {

		
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
			int start = sourceStart+e.charPositionInLine;
			int end = start+1;
			IProblem problem = new DefaultProblem(filename, message, IProblem.Syntax,
					new String[0], ProblemSeverities.Error, start+1, end+1, lineNo);
			context.getProblemReporter().reportProblem(problem);
		} else {
			super.reportError(e);
		}		
		

	}
	
	public boolean hasErrors() {
				
		System.out.println("has errors " + _sErrors);
		return _sErrors > 0;
		
		
	}
}
