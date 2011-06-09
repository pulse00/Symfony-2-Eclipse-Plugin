lexer grammar SymfonyAnnotationLexer;

options{
superClass='AbstractAnnotationLexer';
}

@header {
package org.eclipse.symfony.core.parser.antlr;
}


                    
// control characters
AT			: '@';
PARAM_START	: '(';
PARAM_END	: ')';
ASIG 		: '=';
COMMA	: ( ' '* ',' ' '*);


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
fragment BSLASH	: '\\';
fragment UNDER : '_';
fragment SYMBOL: UNDER | '-' | '/' | ':' | '{' | '}';

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ { $channel = HIDDEN; };