package org.eclipse.symfony.core.parser.antlr;

import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;
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
	
	
}
