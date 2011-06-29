parser grammar AnnotationParser;

options {
output = AST;
TokenLabelType=CommonToken;
ASTLabelType=AnnotationCommonTree;
tokenVocab=AnnotationLexer;
}

tokens {
ANNOTATION;
ARGUMENT_LIST;
ARGS;
NAMED_ARG;
LITERAL_ARG;
NSPART;
CLASSNAME;
FQCN;
RHTYPE;
}

@header {
package org.eclipse.symfony.core.parser.antlr;

import org.eclipse.symfony.core.parser.antlr.error.IAnnotationErrorReporter;
}


@members {

    private IAnnotationErrorReporter errorReporter = null;
    
    public AnnotationParser(TokenStream input, IAnnotationErrorReporter errorReporter) {
        this(input, new RecognizerSharedState());
        this.errorReporter = errorReporter;
    }

	public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
        
    		if(errorReporter != null) {
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


// an annotation starts with @ followed by 
// the name of the annotation optionally followed 
// by argument list in parentheses
annotation
  : AT ann_class argument_list
    ->^(ANNOTATION ann_class argument_list)
  ;


argument_list
  : (PARAM_START arguments? PARAM_END)?
    -> ^(ARGUMENT_LIST arguments?)
  ;

ann_class
  : namespace* classname
  ;
  
namespace
  : ns=STRING BSLASH
  	->^(NSPART $ns)
  ;
  
classname
  : cn=STRING
    ->^(CLASSNAME $cn)
  ;

arguments
  : argument  (COMMA arguments)?
    -> argument arguments?
  ;

argument
: literal_argument | named_argument | json
;


literal_argument
  : param=STRING_LITERAL
    -> ^(LITERAL_ARG $param)
  ;

named_argument
  : param=STRING ASIG rhtype
    -> ^(NAMED_ARG $param rhtype)
  ;

json
  : JSON_START json_arguments? JSON_END
  ;
  
json_arguments
  : json_argument (COMMA (json_argument))*
  ;
  
json_argument
  : STRING_LITERAL ASIG STRING_LITERAL
  ;

rhtype
  : param=STRING
    -> ^(RHTYPE $param)
  | param=STRING_LITERAL
    -> ^(RHTYPE $param)
  | json
  ;