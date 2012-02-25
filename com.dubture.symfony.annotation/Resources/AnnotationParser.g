parser grammar AnnotationParser;

options {
  language = Java;
  output = AST;
  ASTLabelType = AnnotationCommonTree;
  tokenVocab = AnnotationLexer;
}

tokens {
  ANNOTATION;
  ANNOTATION_VALUE;
  ARGUMENT;
  ARGUMENT_NAME;
  ARGUMENT_VALUE;
  ARRAY_VALUE;
  BOOLEAN_VALUE;
  CLASS;
  DECLARATION;
  NAMED_ARGUMENT;
  NAMESPACE;
  NAMESPACE_DEFAULT;
  NULL_VALUE;
  NUMBER_VALUE;
  OBJECT_VALUE;
  PAIR;
  STRING_VALUE;
}

@header {
package com.dubture.symfony.annotation.parser.antlr;

import com.dubture.symfony.annotation.parser.antlr.error.IAnnotationErrorReporter;
import com.dubture.symfony.annotation.parser.tree.AnnotationCommonTree;
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
  : AT class_name declaration?
      -> ^(ANNOTATION AT class_name declaration?)
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
      -> ^(DECLARATION PARAM_START $statements* PARAM_END)
  ;

statement
  : argument | namedArgument
  ;

argument
  : argument_value=value
      -> ^(ARGUMENT $argument_value)
  ;

namedArgument
  : name=argumentName EQUAL argument_value=argumentValue
      -> ^(NAMED_ARGUMENT $name $argument_value)
  ;

argumentName
  : name=IDENTIFIER
      -> ^(ARGUMENT_NAME $name)
  ;

argumentValue
  : value
      -> ^(ARGUMENT_VALUE value)
  ;

value
  : annotationValue
  | objectValue
  | arrayValue
  | stringValue
  | numberValue
  | booleanValue
  | nullValue
  ;

annotationValue
  :
     AT class_name declaration?
       -> ^(ANNOTATION_VALUE AT class_name declaration?)
  ;

objectValue
  : CURLY_START pairs+=pair (COMMA pairs+=pair)* CURLY_END
      -> ^(OBJECT_VALUE $pairs+)
  ;

pair
  : name=stringValue EQUAL value
      -> ^(PAIR $name value)
  ;

arrayValue
  : CURLY_START values+=value? (COMMA values+=value)* CURLY_END
      -> ^(ARRAY_VALUE $values*)
  ;

stringValue
  : string_value=STRING_LITERAL
      -> ^(STRING_VALUE $string_value)
  ;

numberValue
  : interger_value=INTEGER_LITERAL
      -> ^(NUMBER_VALUE $interger_value)
  | float_value=FLOAT_LITERAL
      -> ^(NUMBER_VALUE $float_value)
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


