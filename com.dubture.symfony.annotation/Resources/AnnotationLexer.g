lexer grammar AnnotationLexer;


@header {
package com.dubture.symfony.annotation.parser.antlr;

import com.dubture.symfony.annotation.parser.antlr.error.IAnnotationErrorReporter;
}


// add custom fields to the generated code
@members {

    private IAnnotationErrorReporter errorReporter = null;

    public AnnotationLexer(CharStream input, IAnnotationErrorReporter errorReporter) {
        this(input, new RecognizerSharedState());
        this.errorReporter = errorReporter;
    }
    
	public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
        
    		if (errorReporter != null) {
        		String hdr = getErrorHeader(e);
                String msg = getErrorMessage(e, tokenNames);        
                errorReporter.reportError(hdr,msg,e);
    		}
        
    }    
        
    public void setErrorReporter(IAnnotationErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }
    
	protected Object recoverFromMismatchedToken(IntStream input,
				int ttype, BitSet follow) throws RecognitionException
	{   
	    throw new MismatchedTokenException(ttype, input);
	}       
	
    public Object recoverFromMismatchedSet(IntStream input,
    			RecognitionException e, BitSet follow) throws RecognitionException 
    { 
		throw new MismatchedSetException(follow, input);
   	}
	
}


                    
// control characters
AT			: '@';
PARAM_START	: '(';
PARAM_END	: ')';
ASIG 		: '=';
COMMA		: ( ' '* ',' ' '*);
BSLASH		: '\\';
JSON_START  : '{' ;
JSON_END	: '}' ;

// strings


STRING : STRING_CHAR+;




STRING_LITERAL
: '"' NONCONTROL_CHAR* '"'
| '\'' NONCONTROL_CHAR* '\''
;

fragment STRING_CHAR  : LOWER | UPPER | DIGIT | UNDER ;
fragment NONCONTROL_CHAR: LETTER | DIGIT | SYMBOL;
fragment LETTER	: LOWER | UPPER;
fragment LOWER	: 'a'..'z';
fragment UPPER	: 'A'..'Z';
fragment DIGIT	: '0'..'9';
fragment UNDER : '_';
fragment SYMBOL: UNDER | '-' | '/' | ':' | '.' | '{' | '}' | '|'| '\\';

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ { $channel = HIDDEN; };