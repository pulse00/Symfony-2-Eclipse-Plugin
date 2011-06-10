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
ARGUMENT_LIST;
NAMED_ARG;
LITERAL_ARG;
NSPART;
CLASSNAME;
FQCN;
RHTYPE;
}

@header {
package org.eclipse.symfony.core.parser.antlr;
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