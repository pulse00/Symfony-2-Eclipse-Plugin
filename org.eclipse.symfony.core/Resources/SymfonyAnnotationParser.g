parser grammar SymfonyAnnotationParser;

options {
output = AST;
TokenLabelType=CommonToken;
ASTLabelType=AnnotationCommonTree;
superClass='AbstractAnnotationParser';
tokenVocab=SymfonyAnnotationLexer;
}

tokens {
ANNOTATION;
NAMED_ARG;
LITERAL_ARG;
RHTYPE;
}

@header {
package org.eclipse.symfony.core.parser.antlr;
}


// an annotation starts with @ followed by 
// the name of the annotation optionally followed 
// by argument list in parentheses
annotation
  : AT ann_class (PARAM_START arguments PARAM_END)
    -> ^(ANNOTATION ann_class arguments)
  ;


ann_class
  : (STRING BSLASH?)+
  ;

arguments
  : argument  (COMMA (argument))*
  ;

argument
: literal_argument | named_argument
;


literal_argument
  : param=STRING_LITERAL
    -> ^(LITERAL_ARG $param)
  ;

named_argument
  : lht=STRING ASIG rhtype
    -> ^(NAMED_ARG $lht rhtype)
  ;

rhtype
  : param=STRING
    -> ^(RHTYPE $param)
  | param=STRING_LITERAL
    -> ^(RHTYPE $param)
  ;