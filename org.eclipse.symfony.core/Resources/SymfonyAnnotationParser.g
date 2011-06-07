parser grammar SymfonyAnnotationParser;

options {
tokenVocab=SymfonyAnnotationLexer;
}

tokens {
ANNOTATIONNAME;
}

@header {
package org.eclipse.symfony.core.parser.antlr;
}

name returns [String result]
	:	a=ANNOTATIONNAME { $result = $a.text;}
	|	// nothing
	;
	
	
annotation	: ANNOTATIONNAME PARAM_START ANNOTATIONPARAM + PARAM_END;