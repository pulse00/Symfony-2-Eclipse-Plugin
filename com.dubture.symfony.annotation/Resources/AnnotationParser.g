parser grammar AnnotationParser;

options {
  language = Java;
  output = AST;
  ASTLabelType=AnnotationCommonTree;
  tokenVocab = AnnotationLexer;
}

tokens {
  ANNOTATION;
  ARGUMENT;
  ARGUMENT_NAME;
  ARGUMENT_VALUE;
  BOOLEAN_VALUE;
  CLASS;
  DECLARATION;
  LITERAL;
  NAMESPACE;
  NAMESPACE_DEFAULT;
  NAMESPACE_SEGMENT;
  NULL_VALUE;
  OBJECT_VALUE;
  PAIR;
  STRING_VALUE;
}

@header {
package com.dubture.symfony.annotation.parser.antlr;

import com.dubture.symfony.annotation.parser.antlr.error.IAnnotationErrorReporter;
}

@members {

    private IAnnotationErrorReporter errorReporter = null;

    public AnnotationParser(TokenStream input, IAnnotationErrorReporter errorReporter) {
        this(input, new RecognizerSharedState());
        this.errorReporter = errorReporter;
    }

    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
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

annotation
  :
     AT class_name declaration?
      -> ^(ANNOTATION class_name declaration?)
  ;

class_name
  : (namespace name=IDENTIFIER)
      -> ^(CLASS namespace $name)
  ;

namespace
  : start=BSLASH? (segments+=IDENTIFIER BSLASH)*
      -> ^(NAMESPACE ^(NAMESPACE_DEFAULT $start)? $segments*)
  ;

declaration
  : PARAM_START statements+=statement? (COMMA statements+=statement)* PARAM_END
      -> ^(DECLARATION $statements*)
  ;

statement
  : literal | argument
  ;

literal
  : literal_value=STRING_LITERAL
      -> ^(LITERAL $literal_value)
  ;

argument
  : name=argumentName EQUAL argument_value=argumentValue
      -> ^(ARGUMENT $name $argument_value)
  ;

argumentName
  : name=IDENTIFIER
      -> ^(ARGUMENT_NAME $name)
  ;

argumentValue
  : value
      -> ^(ARGUMENT_VALUE value)
  | CURLY_START annotations=subAnnotation (COMMA annotations=subAnnotation)* CURLY_END
      -> ^(ARGUMENT_VALUE $annotations+)
  ;

subAnnotation
  :
     AT class_name declaration?
       -> ^(ANNOTATION class_name declaration?)
  ;

value
  : objectValue
  | stringValue
  | booleanValue
  | nullValue
  ;

objectValue
  : CURLY_START pairs+=pair (COMMA pairs+=pair)* CURLY_END
      -> ^(OBJECT_VALUE $pairs+)
  ;

stringValue
  : string_value=STRING_LITERAL
      -> ^(STRING_VALUE $string_value)
  ;

booleanValue
  : TRUE
      -> ^(BOOLEAN_VALUE TRUE)
  | FALSE
      -> ^(BOOLEAN_VALUE FALSE)
  ;

nullValue
  : NULL
      -> ^(NULL_VALUE NULL)
  ;

pair
  : name=stringValue EQUAL value
      -> ^(PAIR $name value)
  ;


