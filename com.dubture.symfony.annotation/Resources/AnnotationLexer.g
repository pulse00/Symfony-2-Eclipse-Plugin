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

// fragments
fragment LOWER               : 'a'..'z';
fragment UPPER               : 'A'..'Z';
fragment DIGIT               : '0'..'9';
fragment DIGIT_NOZERO        : '1'..'9';
fragment UNDERSCORE          : '_';
fragment QUOTE               : '\'';
fragment DOUBLE_QUOTE        : '"';
fragment ESCAPE_QUOTE        : '\\' '\'';
fragment ESCAPE_DOUBLE_QUOTE : '\\' '"';
//fragment ACCENTUED         : bytes from 127 through 255 (0x7f-0xff).
fragment LETTER              : LOWER | UPPER; //| ACCENTUED;
fragment ALPHANUM            : LETTER | DIGIT;
fragment POSITIVE            : '+';
fragment NEGATIVE            : '-';
fragment DOT                 : '\.';
fragment WHITESPACE_CHAR     : ( '\t' | ' ' | '\r' | '\n'| '\u000C' );

// control characters
AT          : '@';
PARAM_START : '(';
PARAM_END   : ')';
EQUAL       : '=';
COMMA       : ',';
BSLASH      : '\\';
CURLY_START : '{' ;
CURLY_END   : '}' ;

// keyword tokens
TRUE  : 'true';
FALSE : 'false';
NULL  : 'null';

// identifier
IDENTIFIER  : (LETTER | UNDERSCORE) (ALPHANUM | UNDERSCORE)*;

// string literals (either single quoted or double quoted)
STRING_LITERAL
  : QUOTE
    { StringBuilder builder = new StringBuilder(); }
    (
      character=~(QUOTE)      { builder.appendCodePoint(character); }
    | ESCAPE_QUOTE            { builder.appendCodePoint('\''); }
    )*
    QUOTE
    { setText(builder.toString()); }
  |
    DOUBLE_QUOTE
    { StringBuilder builder = new StringBuilder(); }
    (
      character=~(DOUBLE_QUOTE)      { builder.appendCodePoint(character); }
    | ESCAPE_DOUBLE_QUOTE            { builder.appendCodePoint('"'); }
    )*
    DOUBLE_QUOTE
    { setText(builder.toString()); }
  ;

INTEGER_LITERAL
  : NEGATIVE? DIGIT+
  ;

FLOAT_LITERAL
  : NEGATIVE? DIGIT* DOT DIGIT+
  ;

// hidden characters
WHITESPACE    : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ { $channel = HIDDEN; };
COMMENT_START : ('\\' '*') { $channel = HIDDEN; };
COMMENT_END   : ('*' '/') { $channel = HIDDEN; };
COMMENT_CHAR  : ('*')+ { $channel = HIDDEN; };
//REST          : ('.')+ { $channel = HIDDEN; };
