package org.eclipse.symfony.core.parser.antlr;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
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
}
