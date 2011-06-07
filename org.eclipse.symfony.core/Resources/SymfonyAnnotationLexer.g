lexer grammar SymfonyAnnotationLexer;

@header {
package org.eclipse.symfony.core.parser.antlr;
}

ANNOTATIONNAME	:	ANN_START LABEL ;
ANNOTATIONPARAM	:	'\'' LABEL '\'';


WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ { $channel = HIDDEN; };

LABEL
: ('a'..'z'|'A'..'Z'|'_'|'*')('*'|'a'..'z'|'A'..'Z'|'_'|'0'..'9')*
;

//fragment WORD	: ('a'..'z'|'A'..'Z')+;

ANN_START	: '@';
PARAM_START	: '(';
PARAM_END	: ')';