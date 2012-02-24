// $ANTLR 3.3 Nov 30, 2010 12:45:30 AnnotationLexer.g 2012-02-24 15:23:12

package com.dubture.symfony.annotation.parser.antlr;

import com.dubture.symfony.annotation.parser.antlr.error.IAnnotationErrorReporter;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class AnnotationLexer extends Lexer {
    public static final int EOF=-1;
    public static final int LOWER=4;
    public static final int UPPER=5;
    public static final int DIGIT=6;
    public static final int DIGIT_NOZERO=7;
    public static final int UNDERSCORE=8;
    public static final int QUOTE=9;
    public static final int DOUBLE_QUOTE=10;
    public static final int ESCAPE_QUOTE=11;
    public static final int ESCAPE_DOUBLE_QUOTE=12;
    public static final int LETTER=13;
    public static final int ALPHANUM=14;
    public static final int POSITIVE=15;
    public static final int NEGATIVE=16;
    public static final int DOT=17;
    public static final int WHITESPACE_CHAR=18;
    public static final int AT=19;
    public static final int PARAM_START=20;
    public static final int PARAM_END=21;
    public static final int EQUAL=22;
    public static final int COMMA=23;
    public static final int BSLASH=24;
    public static final int CURLY_START=25;
    public static final int CURLY_END=26;
    public static final int TRUE=27;
    public static final int FALSE=28;
    public static final int NULL=29;
    public static final int IDENTIFIER=30;
    public static final int STRING_LITERAL=31;
    public static final int INTEGER_LITERAL=32;
    public static final int FLOAT_LITERAL=33;
    public static final int WHITESPACE=34;
    public static final int COMMENT_START=35;
    public static final int COMMENT_END=36;
    public static final int COMMENT_CHAR=37;


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



    // delegates
    // delegators

    public AnnotationLexer() {;} 
    public AnnotationLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public AnnotationLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "AnnotationLexer.g"; }

    // $ANTLR start "LOWER"
    public final void mLOWER() throws RecognitionException {
        try {
            // AnnotationLexer.g:48:30: ( 'a' .. 'z' )
            // AnnotationLexer.g:48:32: 'a' .. 'z'
            {
            matchRange('a','z'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "LOWER"

    // $ANTLR start "UPPER"
    public final void mUPPER() throws RecognitionException {
        try {
            // AnnotationLexer.g:49:30: ( 'A' .. 'Z' )
            // AnnotationLexer.g:49:32: 'A' .. 'Z'
            {
            matchRange('A','Z'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UPPER"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // AnnotationLexer.g:50:30: ( '0' .. '9' )
            // AnnotationLexer.g:50:32: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "DIGIT_NOZERO"
    public final void mDIGIT_NOZERO() throws RecognitionException {
        try {
            // AnnotationLexer.g:51:30: ( '1' .. '9' )
            // AnnotationLexer.g:51:32: '1' .. '9'
            {
            matchRange('1','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT_NOZERO"

    // $ANTLR start "UNDERSCORE"
    public final void mUNDERSCORE() throws RecognitionException {
        try {
            // AnnotationLexer.g:52:30: ( '_' )
            // AnnotationLexer.g:52:32: '_'
            {
            match('_'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UNDERSCORE"

    // $ANTLR start "QUOTE"
    public final void mQUOTE() throws RecognitionException {
        try {
            // AnnotationLexer.g:53:30: ( '\\'' )
            // AnnotationLexer.g:53:32: '\\''
            {
            match('\''); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "QUOTE"

    // $ANTLR start "DOUBLE_QUOTE"
    public final void mDOUBLE_QUOTE() throws RecognitionException {
        try {
            // AnnotationLexer.g:54:30: ( '\"' )
            // AnnotationLexer.g:54:32: '\"'
            {
            match('\"'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DOUBLE_QUOTE"

    // $ANTLR start "ESCAPE_QUOTE"
    public final void mESCAPE_QUOTE() throws RecognitionException {
        try {
            // AnnotationLexer.g:55:30: ( '\\\\' '\\'' )
            // AnnotationLexer.g:55:32: '\\\\' '\\''
            {
            match('\\'); 
            match('\''); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "ESCAPE_QUOTE"

    // $ANTLR start "ESCAPE_DOUBLE_QUOTE"
    public final void mESCAPE_DOUBLE_QUOTE() throws RecognitionException {
        try {
            // AnnotationLexer.g:56:30: ( '\\\\' '\"' )
            // AnnotationLexer.g:56:32: '\\\\' '\"'
            {
            match('\\'); 
            match('\"'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "ESCAPE_DOUBLE_QUOTE"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // AnnotationLexer.g:58:30: ( LOWER | UPPER )
            // AnnotationLexer.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "ALPHANUM"
    public final void mALPHANUM() throws RecognitionException {
        try {
            // AnnotationLexer.g:59:30: ( LETTER | DIGIT )
            // AnnotationLexer.g:
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "ALPHANUM"

    // $ANTLR start "POSITIVE"
    public final void mPOSITIVE() throws RecognitionException {
        try {
            // AnnotationLexer.g:60:30: ( '+' )
            // AnnotationLexer.g:60:32: '+'
            {
            match('+'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "POSITIVE"

    // $ANTLR start "NEGATIVE"
    public final void mNEGATIVE() throws RecognitionException {
        try {
            // AnnotationLexer.g:61:30: ( '-' )
            // AnnotationLexer.g:61:32: '-'
            {
            match('-'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "NEGATIVE"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            // AnnotationLexer.g:62:30: ( '\\.' )
            // AnnotationLexer.g:62:32: '\\.'
            {
            match('.'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "WHITESPACE_CHAR"
    public final void mWHITESPACE_CHAR() throws RecognitionException {
        try {
            // AnnotationLexer.g:63:30: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' ) )
            // AnnotationLexer.g:63:32: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "WHITESPACE_CHAR"

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:66:13: ( '@' )
            // AnnotationLexer.g:66:15: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AT"

    // $ANTLR start "PARAM_START"
    public final void mPARAM_START() throws RecognitionException {
        try {
            int _type = PARAM_START;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:67:13: ( '(' )
            // AnnotationLexer.g:67:15: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PARAM_START"

    // $ANTLR start "PARAM_END"
    public final void mPARAM_END() throws RecognitionException {
        try {
            int _type = PARAM_END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:68:13: ( ')' )
            // AnnotationLexer.g:68:15: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PARAM_END"

    // $ANTLR start "EQUAL"
    public final void mEQUAL() throws RecognitionException {
        try {
            int _type = EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:69:13: ( '=' )
            // AnnotationLexer.g:69:15: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUAL"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:70:13: ( ',' )
            // AnnotationLexer.g:70:15: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "BSLASH"
    public final void mBSLASH() throws RecognitionException {
        try {
            int _type = BSLASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:71:13: ( '\\\\' )
            // AnnotationLexer.g:71:15: '\\\\'
            {
            match('\\'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BSLASH"

    // $ANTLR start "CURLY_START"
    public final void mCURLY_START() throws RecognitionException {
        try {
            int _type = CURLY_START;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:72:13: ( '{' )
            // AnnotationLexer.g:72:15: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CURLY_START"

    // $ANTLR start "CURLY_END"
    public final void mCURLY_END() throws RecognitionException {
        try {
            int _type = CURLY_END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:73:13: ( '}' )
            // AnnotationLexer.g:73:15: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CURLY_END"

    // $ANTLR start "TRUE"
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:76:7: ( 'true' )
            // AnnotationLexer.g:76:9: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRUE"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:77:7: ( 'false' )
            // AnnotationLexer.g:77:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FALSE"

    // $ANTLR start "NULL"
    public final void mNULL() throws RecognitionException {
        try {
            int _type = NULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:78:7: ( 'null' )
            // AnnotationLexer.g:78:9: 'null'
            {
            match("null"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NULL"

    // $ANTLR start "IDENTIFIER"
    public final void mIDENTIFIER() throws RecognitionException {
        try {
            int _type = IDENTIFIER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:81:13: ( ( LETTER | UNDERSCORE ) ( ALPHANUM | UNDERSCORE )* )
            // AnnotationLexer.g:81:15: ( LETTER | UNDERSCORE ) ( ALPHANUM | UNDERSCORE )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // AnnotationLexer.g:81:37: ( ALPHANUM | UNDERSCORE )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // AnnotationLexer.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IDENTIFIER"

    // $ANTLR start "STRING_LITERAL"
    public final void mSTRING_LITERAL() throws RecognitionException {
        try {
            int _type = STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            int character;

            // AnnotationLexer.g:85:3: ( QUOTE (character=~ ( QUOTE ) | ESCAPE_QUOTE )* QUOTE | DOUBLE_QUOTE (character=~ ( DOUBLE_QUOTE ) | ESCAPE_DOUBLE_QUOTE )* DOUBLE_QUOTE )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\'') ) {
                alt4=1;
            }
            else if ( (LA4_0=='\"') ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // AnnotationLexer.g:85:5: QUOTE (character=~ ( QUOTE ) | ESCAPE_QUOTE )* QUOTE
                    {
                    mQUOTE(); 
                     StringBuilder builder = new StringBuilder(); 
                    // AnnotationLexer.g:87:5: (character=~ ( QUOTE ) | ESCAPE_QUOTE )*
                    loop2:
                    do {
                        int alt2=3;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0=='\\') ) {
                            int LA2_2 = input.LA(2);

                            if ( (LA2_2=='\'') ) {
                                int LA2_4 = input.LA(3);

                                if ( ((LA2_4>='\u0000' && LA2_4<='\uFFFF')) ) {
                                    alt2=2;
                                }

                                else {
                                    alt2=1;
                                }

                            }
                            else if ( ((LA2_2>='\u0000' && LA2_2<='&')||(LA2_2>='(' && LA2_2<='\uFFFF')) ) {
                                alt2=1;
                            }


                        }
                        else if ( ((LA2_0>='\u0000' && LA2_0<='&')||(LA2_0>='(' && LA2_0<='[')||(LA2_0>=']' && LA2_0<='\uFFFF')) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // AnnotationLexer.g:88:7: character=~ ( QUOTE )
                    	    {
                    	    character= input.LA(1);
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}

                    	     builder.appendCodePoint(character); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // AnnotationLexer.g:89:7: ESCAPE_QUOTE
                    	    {
                    	    mESCAPE_QUOTE(); 
                    	     builder.appendCodePoint('\''); 

                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);

                    mQUOTE(); 
                     setText(builder.toString()); 

                    }
                    break;
                case 2 :
                    // AnnotationLexer.g:94:5: DOUBLE_QUOTE (character=~ ( DOUBLE_QUOTE ) | ESCAPE_DOUBLE_QUOTE )* DOUBLE_QUOTE
                    {
                    mDOUBLE_QUOTE(); 
                     StringBuilder builder = new StringBuilder(); 
                    // AnnotationLexer.g:96:5: (character=~ ( DOUBLE_QUOTE ) | ESCAPE_DOUBLE_QUOTE )*
                    loop3:
                    do {
                        int alt3=3;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0=='\\') ) {
                            int LA3_2 = input.LA(2);

                            if ( (LA3_2=='\"') ) {
                                int LA3_4 = input.LA(3);

                                if ( ((LA3_4>='\u0000' && LA3_4<='\uFFFF')) ) {
                                    alt3=2;
                                }

                                else {
                                    alt3=1;
                                }

                            }
                            else if ( ((LA3_2>='\u0000' && LA3_2<='!')||(LA3_2>='#' && LA3_2<='\uFFFF')) ) {
                                alt3=1;
                            }


                        }
                        else if ( ((LA3_0>='\u0000' && LA3_0<='!')||(LA3_0>='#' && LA3_0<='[')||(LA3_0>=']' && LA3_0<='\uFFFF')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // AnnotationLexer.g:97:7: character=~ ( DOUBLE_QUOTE )
                    	    {
                    	    character= input.LA(1);
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}

                    	     builder.appendCodePoint(character); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // AnnotationLexer.g:98:7: ESCAPE_DOUBLE_QUOTE
                    	    {
                    	    mESCAPE_DOUBLE_QUOTE(); 
                    	     builder.appendCodePoint('"'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    mDOUBLE_QUOTE(); 
                     setText(builder.toString()); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING_LITERAL"

    // $ANTLR start "INTEGER_LITERAL"
    public final void mINTEGER_LITERAL() throws RecognitionException {
        try {
            int _type = INTEGER_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:105:3: ( ( NEGATIVE )? ( DIGIT )+ )
            // AnnotationLexer.g:105:5: ( NEGATIVE )? ( DIGIT )+
            {
            // AnnotationLexer.g:105:5: ( NEGATIVE )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='-') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // AnnotationLexer.g:105:5: NEGATIVE
                    {
                    mNEGATIVE(); 

                    }
                    break;

            }

            // AnnotationLexer.g:105:15: ( DIGIT )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // AnnotationLexer.g:105:15: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTEGER_LITERAL"

    // $ANTLR start "FLOAT_LITERAL"
    public final void mFLOAT_LITERAL() throws RecognitionException {
        try {
            int _type = FLOAT_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:109:3: ( ( NEGATIVE )? ( DIGIT )* DOT ( DIGIT )+ )
            // AnnotationLexer.g:109:5: ( NEGATIVE )? ( DIGIT )* DOT ( DIGIT )+
            {
            // AnnotationLexer.g:109:5: ( NEGATIVE )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='-') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // AnnotationLexer.g:109:5: NEGATIVE
                    {
                    mNEGATIVE(); 

                    }
                    break;

            }

            // AnnotationLexer.g:109:15: ( DIGIT )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // AnnotationLexer.g:109:15: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            mDOT(); 
            // AnnotationLexer.g:109:26: ( DIGIT )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // AnnotationLexer.g:109:26: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FLOAT_LITERAL"

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:113:15: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+ )
            // AnnotationLexer.g:113:17: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            {
            // AnnotationLexer.g:113:17: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\t' && LA10_0<='\n')||(LA10_0>='\f' && LA10_0<='\r')||LA10_0==' ') ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // AnnotationLexer.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);

             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHITESPACE"

    // $ANTLR start "COMMENT_START"
    public final void mCOMMENT_START() throws RecognitionException {
        try {
            int _type = COMMENT_START;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:114:15: ( ( '\\\\' '*' ) )
            // AnnotationLexer.g:114:17: ( '\\\\' '*' )
            {
            // AnnotationLexer.g:114:17: ( '\\\\' '*' )
            // AnnotationLexer.g:114:18: '\\\\' '*'
            {
            match('\\'); 
            match('*'); 

            }

             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT_START"

    // $ANTLR start "COMMENT_END"
    public final void mCOMMENT_END() throws RecognitionException {
        try {
            int _type = COMMENT_END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:115:15: ( ( '*' '/' ) )
            // AnnotationLexer.g:115:17: ( '*' '/' )
            {
            // AnnotationLexer.g:115:17: ( '*' '/' )
            // AnnotationLexer.g:115:18: '*' '/'
            {
            match('*'); 
            match('/'); 

            }

             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT_END"

    // $ANTLR start "COMMENT_CHAR"
    public final void mCOMMENT_CHAR() throws RecognitionException {
        try {
            int _type = COMMENT_CHAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:116:15: ( ( '*' )+ )
            // AnnotationLexer.g:116:17: ( '*' )+
            {
            // AnnotationLexer.g:116:17: ( '*' )+
            int cnt11=0;
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0=='*') ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // AnnotationLexer.g:116:18: '*'
            	    {
            	    match('*'); 

            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
            } while (true);

             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT_CHAR"

    public void mTokens() throws RecognitionException {
        // AnnotationLexer.g:1:8: ( AT | PARAM_START | PARAM_END | EQUAL | COMMA | BSLASH | CURLY_START | CURLY_END | TRUE | FALSE | NULL | IDENTIFIER | STRING_LITERAL | INTEGER_LITERAL | FLOAT_LITERAL | WHITESPACE | COMMENT_START | COMMENT_END | COMMENT_CHAR )
        int alt12=19;
        alt12 = dfa12.predict(input);
        switch (alt12) {
            case 1 :
                // AnnotationLexer.g:1:10: AT
                {
                mAT(); 

                }
                break;
            case 2 :
                // AnnotationLexer.g:1:13: PARAM_START
                {
                mPARAM_START(); 

                }
                break;
            case 3 :
                // AnnotationLexer.g:1:25: PARAM_END
                {
                mPARAM_END(); 

                }
                break;
            case 4 :
                // AnnotationLexer.g:1:35: EQUAL
                {
                mEQUAL(); 

                }
                break;
            case 5 :
                // AnnotationLexer.g:1:41: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 6 :
                // AnnotationLexer.g:1:47: BSLASH
                {
                mBSLASH(); 

                }
                break;
            case 7 :
                // AnnotationLexer.g:1:54: CURLY_START
                {
                mCURLY_START(); 

                }
                break;
            case 8 :
                // AnnotationLexer.g:1:66: CURLY_END
                {
                mCURLY_END(); 

                }
                break;
            case 9 :
                // AnnotationLexer.g:1:76: TRUE
                {
                mTRUE(); 

                }
                break;
            case 10 :
                // AnnotationLexer.g:1:81: FALSE
                {
                mFALSE(); 

                }
                break;
            case 11 :
                // AnnotationLexer.g:1:87: NULL
                {
                mNULL(); 

                }
                break;
            case 12 :
                // AnnotationLexer.g:1:92: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 13 :
                // AnnotationLexer.g:1:103: STRING_LITERAL
                {
                mSTRING_LITERAL(); 

                }
                break;
            case 14 :
                // AnnotationLexer.g:1:118: INTEGER_LITERAL
                {
                mINTEGER_LITERAL(); 

                }
                break;
            case 15 :
                // AnnotationLexer.g:1:134: FLOAT_LITERAL
                {
                mFLOAT_LITERAL(); 

                }
                break;
            case 16 :
                // AnnotationLexer.g:1:148: WHITESPACE
                {
                mWHITESPACE(); 

                }
                break;
            case 17 :
                // AnnotationLexer.g:1:159: COMMENT_START
                {
                mCOMMENT_START(); 

                }
                break;
            case 18 :
                // AnnotationLexer.g:1:173: COMMENT_END
                {
                mCOMMENT_END(); 

                }
                break;
            case 19 :
                // AnnotationLexer.g:1:185: COMMENT_CHAR
                {
                mCOMMENT_CHAR(); 

                }
                break;

        }

    }


    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA12_eotS =
        "\6\uffff\1\24\2\uffff\3\14\3\uffff\1\30\2\uffff\1\32\2\uffff\3"+
        "\14\3\uffff\3\14\1\41\1\14\1\43\1\uffff\1\44\2\uffff";
    static final String DFA12_eofS =
        "\45\uffff";
    static final String DFA12_minS =
        "\1\11\5\uffff\1\52\2\uffff\1\162\1\141\1\165\2\uffff\2\56\2\uffff"+
        "\1\57\2\uffff\1\165\2\154\3\uffff\1\145\1\163\1\154\1\60\1\145\1"+
        "\60\1\uffff\1\60\2\uffff";
    static final String DFA12_maxS =
        "\1\175\5\uffff\1\52\2\uffff\1\162\1\141\1\165\2\uffff\2\71\2\uffff"+
        "\1\57\2\uffff\1\165\2\154\3\uffff\1\145\1\163\1\154\1\172\1\145"+
        "\1\172\1\uffff\1\172\2\uffff";
    static final String DFA12_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\uffff\1\7\1\10\3\uffff\1\14\1\15"+
        "\2\uffff\1\17\1\20\1\uffff\1\21\1\6\3\uffff\1\16\1\22\1\23\6\uffff"+
        "\1\11\1\uffff\1\13\1\12";
    static final String DFA12_specialS =
        "\45\uffff}>";
    static final String[] DFA12_transitionS = {
            "\2\21\1\uffff\2\21\22\uffff\1\21\1\uffff\1\15\4\uffff\1\15"+
            "\1\2\1\3\1\22\1\uffff\1\5\1\16\1\20\1\uffff\12\17\3\uffff\1"+
            "\4\2\uffff\1\1\32\14\1\uffff\1\6\2\uffff\1\14\1\uffff\5\14\1"+
            "\12\7\14\1\13\5\14\1\11\6\14\1\7\1\uffff\1\10",
            "",
            "",
            "",
            "",
            "",
            "\1\23",
            "",
            "",
            "\1\25",
            "\1\26",
            "\1\27",
            "",
            "",
            "\1\20\1\uffff\12\17",
            "\1\20\1\uffff\12\17",
            "",
            "",
            "\1\31",
            "",
            "",
            "\1\33",
            "\1\34",
            "\1\35",
            "",
            "",
            "",
            "\1\36",
            "\1\37",
            "\1\40",
            "\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32\14",
            "\1\42",
            "\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32\14",
            "",
            "\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32\14",
            "",
            ""
    };

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( AT | PARAM_START | PARAM_END | EQUAL | COMMA | BSLASH | CURLY_START | CURLY_END | TRUE | FALSE | NULL | IDENTIFIER | STRING_LITERAL | INTEGER_LITERAL | FLOAT_LITERAL | WHITESPACE | COMMENT_START | COMMENT_END | COMMENT_CHAR );";
        }
    }
 

}