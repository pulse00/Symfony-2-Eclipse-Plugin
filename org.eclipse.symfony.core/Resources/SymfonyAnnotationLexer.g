lexer grammar SymfonyAnnotationLexer;


@header {
package org.eclipse.symfony.core.parser.antlr;
}

ANNOTATION			
	: ANNOTATIONNAME ARGUMENTS 
	;
	
ANNOTATIONNAME		
	: ANN_START ANN_CLASS 
	;
	
ARGUMENTS
	: PARAM_START (STRING_LITERAL (COMMA STRING_LITERAL)*)? PARAM_END
	;
	
KEY_VAL
	: LETTER EQUALS STRING_LITERAL
	;

STRING_LITERAL
	: '"' NONCONTROL_CHAR* '"'
	| '\'' NONCONTROL_CHAR* '\''
	;

ANN_CLASS
	: ANN_CHAR+
	;

fragment ANN_CHAR: LETTER | BSLASH; 
fragment NONCONTROL_CHAR: LETTER | DIGIT | SYMBOL;
fragment LETTER: LOWER | UPPER;
fragment LOWER: 'a'..'z';
fragment UPPER: 'A'..'Z';
fragment DIGIT: '0'..'9';
fragment SPACE: ' ' | '\t';
fragment BSLASH : '\\';
fragment EQUALS : '=';
fragment COMMA	:	( ' '* ',' ' '*);
fragment SYMBOL: '_' | '-' | '/' | ':' | '{' | '}';

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ { $channel = HIDDEN; };

LT
	: '\n'		// Line feed.
	| '\r'		// Carriage return.
	| '\u2028'	// Line separator.
	| '\u2029'	// Paragraph separator.
	;

ANN_START	: '@';
PARAM_START	: '(';
PARAM_END	: ')';