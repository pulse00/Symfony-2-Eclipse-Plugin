// $ANTLR 3.3 Nov 30, 2010 12:45:30 C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g 2012-02-27 09:01:47

package com.dubture.symfony.annotation.parser.antlr;

import com.dubture.symfony.annotation.parser.antlr.error.IAnnotationErrorReporter;
import com.dubture.symfony.annotation.parser.antlr.AnnotationToken;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class AnnotationLexer extends Lexer {
    public static final int EOF=-1;
    public static final int LOWER=4;
    public static final int UPPER=5;
    public static final int DIGIT=6;
    public static final int AT=7;
    public static final int UNDERSCORE=8;
    public static final int BSLASH=9;
    public static final int QUOTE=10;
    public static final int DOUBLE_QUOTE=11;
    public static final int ESCAPE_QUOTE=12;
    public static final int ESCAPE_DOUBLE_QUOTE=13;
    public static final int NEGATIVE=14;
    public static final int DOT=15;
    public static final int LETTER=16;
    public static final int ALPHANUM=17;
    public static final int IDENTIFIER_FRAG=18;
    public static final int PARAM_START=19;
    public static final int PARAM_END=20;
    public static final int EQUAL=21;
    public static final int COMMA=22;
    public static final int CURLY_START=23;
    public static final int CURLY_END=24;
    public static final int TRUE=25;
    public static final int FALSE=26;
    public static final int NULL=27;
    public static final int ANNOTATION=28;
    public static final int IDENTIFIER=29;
    public static final int STRING_LITERAL=30;
    public static final int INTEGER_LITERAL=31;
    public static final int FLOAT_LITERAL=32;
    public static final int WHITESPACE=33;


        private IAnnotationErrorReporter errorReporter = null;

        public AnnotationLexer(CharStream input, IAnnotationErrorReporter errorReporter) {
            this(input, new RecognizerSharedState());
            this.errorReporter = errorReporter;
        }

        public Token emit() {
            AnnotationToken token = new AnnotationToken(input,
                                                        state.type,
                                                        state.channel,
                                                        state.tokenStartCharIndex,
                                                        getCharIndex() - 1);
            token.setLine(state.tokenStartLine);
            token.setText(state.text);
            token.setCharPositionInLine(state.tokenStartCharPositionInLine);

            emit(token);
            return token;
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
    public String getGrammarFileName() { return "C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g"; }

    // $ANTLR start "LOWER"
    public final void mLOWER() throws RecognitionException {
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:62:30: ( 'a' .. 'z' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:62:32: 'a' .. 'z'
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:63:30: ( 'A' .. 'Z' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:63:32: 'A' .. 'Z'
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:64:30: ( '0' .. '9' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:64:32: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:65:30: ( '@' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:65:32: '@'
            {
            match('@'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "AT"

    // $ANTLR start "UNDERSCORE"
    public final void mUNDERSCORE() throws RecognitionException {
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:66:30: ( '_' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:66:32: '_'
            {
            match('_'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UNDERSCORE"

    // $ANTLR start "BSLASH"
    public final void mBSLASH() throws RecognitionException {
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:67:30: ( '\\\\' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:67:32: '\\\\'
            {
            match('\\'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "BSLASH"

    // $ANTLR start "QUOTE"
    public final void mQUOTE() throws RecognitionException {
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:68:30: ( '\\'' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:68:32: '\\''
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:69:30: ( '\"' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:69:32: '\"'
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:70:30: ( '\\\\' '\\'' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:70:32: '\\\\' '\\''
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:71:30: ( '\\\\' '\"' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:71:32: '\\\\' '\"'
            {
            match('\\'); 
            match('\"'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "ESCAPE_DOUBLE_QUOTE"

    // $ANTLR start "NEGATIVE"
    public final void mNEGATIVE() throws RecognitionException {
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:72:30: ( '-' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:72:32: '-'
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:73:30: ( '\\.' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:73:32: '\\.'
            {
            match('.'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:75:30: ( LOWER | UPPER )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:76:30: ( LETTER | DIGIT )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:
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

    // $ANTLR start "IDENTIFIER_FRAG"
    public final void mIDENTIFIER_FRAG() throws RecognitionException {
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:77:30: ( ( LETTER | UNDERSCORE ) ( ALPHANUM | UNDERSCORE )* )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:77:32: ( LETTER | UNDERSCORE ) ( ALPHANUM | UNDERSCORE )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:77:54: ( ALPHANUM | UNDERSCORE )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:
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

        }
        finally {
        }
    }
    // $ANTLR end "IDENTIFIER_FRAG"

    // $ANTLR start "PARAM_START"
    public final void mPARAM_START() throws RecognitionException {
        try {
            int _type = PARAM_START;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:80:13: ( '(' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:80:15: '('
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:81:13: ( ')' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:81:15: ')'
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:82:13: ( '=' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:82:15: '='
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:83:13: ( ',' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:83:15: ','
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

    // $ANTLR start "CURLY_START"
    public final void mCURLY_START() throws RecognitionException {
        try {
            int _type = CURLY_START;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:84:13: ( '{' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:84:15: '{'
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:85:13: ( '}' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:85:15: '}'
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:88:7: ( 'true' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:88:9: 'true'
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:89:7: ( 'false' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:89:9: 'false'
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:90:7: ( 'null' )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:90:9: 'null'
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

    // $ANTLR start "ANNOTATION"
    public final void mANNOTATION() throws RecognitionException {
        try {
            int _type = ANNOTATION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:93:12: ( AT ( BSLASH )? IDENTIFIER_FRAG ( BSLASH IDENTIFIER_FRAG )* )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:93:14: AT ( BSLASH )? IDENTIFIER_FRAG ( BSLASH IDENTIFIER_FRAG )*
            {
            mAT(); 
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:93:17: ( BSLASH )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='\\') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:93:17: BSLASH
                    {
                    mBSLASH(); 

                    }
                    break;

            }

            mIDENTIFIER_FRAG(); 
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:93:41: ( BSLASH IDENTIFIER_FRAG )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='\\') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:93:42: BSLASH IDENTIFIER_FRAG
            	    {
            	    mBSLASH(); 
            	    mIDENTIFIER_FRAG(); 

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ANNOTATION"

    // $ANTLR start "IDENTIFIER"
    public final void mIDENTIFIER() throws RecognitionException {
        try {
            int _type = IDENTIFIER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:96:12: ( IDENTIFIER_FRAG )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:96:14: IDENTIFIER_FRAG
            {
            mIDENTIFIER_FRAG(); 

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

            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:100:3: ( QUOTE (character=~ ( QUOTE ) | ESCAPE_QUOTE )* QUOTE | DOUBLE_QUOTE (character=~ ( DOUBLE_QUOTE ) | ESCAPE_DOUBLE_QUOTE )* DOUBLE_QUOTE )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='\'') ) {
                alt6=1;
            }
            else if ( (LA6_0=='\"') ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:100:5: QUOTE (character=~ ( QUOTE ) | ESCAPE_QUOTE )* QUOTE
                    {
                    mQUOTE(); 
                     StringBuilder builder = new StringBuilder(); 
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:102:5: (character=~ ( QUOTE ) | ESCAPE_QUOTE )*
                    loop4:
                    do {
                        int alt4=3;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0=='\\') ) {
                            int LA4_2 = input.LA(2);

                            if ( (LA4_2=='\'') ) {
                                int LA4_4 = input.LA(3);

                                if ( ((LA4_4>='\u0000' && LA4_4<='\uFFFF')) ) {
                                    alt4=2;
                                }

                                else {
                                    alt4=1;
                                }

                            }
                            else if ( ((LA4_2>='\u0000' && LA4_2<='&')||(LA4_2>='(' && LA4_2<='\uFFFF')) ) {
                                alt4=1;
                            }


                        }
                        else if ( ((LA4_0>='\u0000' && LA4_0<='&')||(LA4_0>='(' && LA4_0<='[')||(LA4_0>=']' && LA4_0<='\uFFFF')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:103:7: character=~ ( QUOTE )
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
                    	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:104:7: ESCAPE_QUOTE
                    	    {
                    	    mESCAPE_QUOTE(); 
                    	     builder.appendCodePoint('\''); 

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    mQUOTE(); 
                     setText(builder.toString()); 

                    }
                    break;
                case 2 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:109:5: DOUBLE_QUOTE (character=~ ( DOUBLE_QUOTE ) | ESCAPE_DOUBLE_QUOTE )* DOUBLE_QUOTE
                    {
                    mDOUBLE_QUOTE(); 
                     StringBuilder builder = new StringBuilder(); 
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:111:5: (character=~ ( DOUBLE_QUOTE ) | ESCAPE_DOUBLE_QUOTE )*
                    loop5:
                    do {
                        int alt5=3;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0=='\\') ) {
                            int LA5_2 = input.LA(2);

                            if ( (LA5_2=='\"') ) {
                                int LA5_4 = input.LA(3);

                                if ( ((LA5_4>='\u0000' && LA5_4<='\uFFFF')) ) {
                                    alt5=2;
                                }

                                else {
                                    alt5=1;
                                }

                            }
                            else if ( ((LA5_2>='\u0000' && LA5_2<='!')||(LA5_2>='#' && LA5_2<='\uFFFF')) ) {
                                alt5=1;
                            }


                        }
                        else if ( ((LA5_0>='\u0000' && LA5_0<='!')||(LA5_0>='#' && LA5_0<='[')||(LA5_0>=']' && LA5_0<='\uFFFF')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:112:7: character=~ ( DOUBLE_QUOTE )
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
                    	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:113:7: ESCAPE_DOUBLE_QUOTE
                    	    {
                    	    mESCAPE_DOUBLE_QUOTE(); 
                    	     builder.appendCodePoint('"'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:120:3: ( ( NEGATIVE )? ( DIGIT )+ )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:120:5: ( NEGATIVE )? ( DIGIT )+
            {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:120:5: ( NEGATIVE )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='-') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:120:5: NEGATIVE
                    {
                    mNEGATIVE(); 

                    }
                    break;

            }

            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:120:15: ( DIGIT )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:120:15: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:124:3: ( ( NEGATIVE )? ( DIGIT )* DOT ( DIGIT )+ )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:124:5: ( NEGATIVE )? ( DIGIT )* DOT ( DIGIT )+
            {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:124:5: ( NEGATIVE )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='-') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:124:5: NEGATIVE
                    {
                    mNEGATIVE(); 

                    }
                    break;

            }

            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:124:15: ( DIGIT )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='0' && LA10_0<='9')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:124:15: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            mDOT(); 
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:124:26: ( DIGIT )+
            int cnt11=0;
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:124:26: DIGIT
            	    {
            	    mDIGIT(); 

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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:128:12: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' | '\\\\' '*' | '*' '/' | '*' )+ )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:128:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' | '\\\\' '*' | '*' '/' | '*' )+
            {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:128:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' | '\\\\' '*' | '*' '/' | '*' )+
            int cnt12=0;
            loop12:
            do {
                int alt12=9;
                alt12 = dfa12.predict(input);
                switch (alt12) {
            	case 1 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:128:16: '\\t'
            	    {
            	    match('\t'); 

            	    }
            	    break;
            	case 2 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:128:23: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;
            	case 3 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:128:29: '\\r'
            	    {
            	    match('\r'); 

            	    }
            	    break;
            	case 4 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:128:36: '\\n'
            	    {
            	    match('\n'); 

            	    }
            	    break;
            	case 5 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:128:42: '\\u000C'
            	    {
            	    match('\f'); 

            	    }
            	    break;
            	case 6 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:128:53: '\\\\' '*'
            	    {
            	    match('\\'); 
            	    match('*'); 

            	    }
            	    break;
            	case 7 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:128:64: '*' '/'
            	    {
            	    match('*'); 
            	    match('/'); 

            	    }
            	    break;
            	case 8 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:128:74: '*'
            	    {
            	    match('*'); 

            	    }
            	    break;

            	default :
            	    if ( cnt12 >= 1 ) break loop12;
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        throw eee;
                }
                cnt12++;
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

    public void mTokens() throws RecognitionException {
        // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:8: ( PARAM_START | PARAM_END | EQUAL | COMMA | CURLY_START | CURLY_END | TRUE | FALSE | NULL | ANNOTATION | IDENTIFIER | STRING_LITERAL | INTEGER_LITERAL | FLOAT_LITERAL | WHITESPACE )
        int alt13=15;
        alt13 = dfa13.predict(input);
        switch (alt13) {
            case 1 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:10: PARAM_START
                {
                mPARAM_START(); 

                }
                break;
            case 2 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:22: PARAM_END
                {
                mPARAM_END(); 

                }
                break;
            case 3 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:32: EQUAL
                {
                mEQUAL(); 

                }
                break;
            case 4 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:38: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 5 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:44: CURLY_START
                {
                mCURLY_START(); 

                }
                break;
            case 6 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:56: CURLY_END
                {
                mCURLY_END(); 

                }
                break;
            case 7 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:66: TRUE
                {
                mTRUE(); 

                }
                break;
            case 8 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:71: FALSE
                {
                mFALSE(); 

                }
                break;
            case 9 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:77: NULL
                {
                mNULL(); 

                }
                break;
            case 10 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:82: ANNOTATION
                {
                mANNOTATION(); 

                }
                break;
            case 11 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:93: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 12 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:104: STRING_LITERAL
                {
                mSTRING_LITERAL(); 

                }
                break;
            case 13 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:119: INTEGER_LITERAL
                {
                mINTEGER_LITERAL(); 

                }
                break;
            case 14 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:135: FLOAT_LITERAL
                {
                mFLOAT_LITERAL(); 

                }
                break;
            case 15 :
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationLexer.g:1:149: WHITESPACE
                {
                mWHITESPACE(); 

                }
                break;

        }

    }


    protected DFA12 dfa12 = new DFA12(this);
    protected DFA13 dfa13 = new DFA13(this);
    static final String DFA12_eotS =
        "\1\1\7\uffff\1\12\2\uffff";
    static final String DFA12_eofS =
        "\13\uffff";
    static final String DFA12_minS =
        "\1\11\7\uffff\1\57\2\uffff";
    static final String DFA12_maxS =
        "\1\134\7\uffff\1\57\2\uffff";
    static final String DFA12_acceptS =
        "\1\uffff\1\11\1\1\1\2\1\3\1\4\1\5\1\6\1\uffff\1\7\1\10";
    static final String DFA12_specialS =
        "\13\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\2\1\5\1\uffff\1\6\1\4\22\uffff\1\3\11\uffff\1\10\61\uffff"+
            "\1\7",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\11",
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
            return "()+ loopback of 128:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' | '\\\\' '*' | '*' '/' | '*' )+";
        }
    }
    static final String DFA13_eotS =
        "\7\uffff\3\13\4\uffff\1\24\2\uffff\3\13\1\uffff\3\13\1\33\1\13"+
        "\1\35\1\uffff\1\36\2\uffff";
    static final String DFA13_eofS =
        "\37\uffff";
    static final String DFA13_minS =
        "\1\11\6\uffff\1\162\1\141\1\165\3\uffff\2\56\2\uffff\1\165\2\154"+
        "\1\uffff\1\145\1\163\1\154\1\60\1\145\1\60\1\uffff\1\60\2\uffff";
    static final String DFA13_maxS =
        "\1\175\6\uffff\1\162\1\141\1\165\3\uffff\2\71\2\uffff\1\165\2\154"+
        "\1\uffff\1\145\1\163\1\154\1\172\1\145\1\172\1\uffff\1\172\2\uffff";
    static final String DFA13_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\3\uffff\1\12\1\13\1\14\2\uffff"+
        "\1\16\1\17\3\uffff\1\15\6\uffff\1\7\1\uffff\1\11\1\10";
    static final String DFA13_specialS =
        "\37\uffff}>";
    static final String[] DFA13_transitionS = {
            "\2\20\1\uffff\2\20\22\uffff\1\20\1\uffff\1\14\4\uffff\1\14"+
            "\1\1\1\2\1\20\1\uffff\1\4\1\15\1\17\1\uffff\12\16\3\uffff\1"+
            "\3\2\uffff\1\12\32\13\1\uffff\1\20\2\uffff\1\13\1\uffff\5\13"+
            "\1\10\7\13\1\11\5\13\1\7\6\13\1\5\1\uffff\1\6",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\21",
            "\1\22",
            "\1\23",
            "",
            "",
            "",
            "\1\17\1\uffff\12\16",
            "\1\17\1\uffff\12\16",
            "",
            "",
            "\1\25",
            "\1\26",
            "\1\27",
            "",
            "\1\30",
            "\1\31",
            "\1\32",
            "\12\13\7\uffff\32\13\4\uffff\1\13\1\uffff\32\13",
            "\1\34",
            "\12\13\7\uffff\32\13\4\uffff\1\13\1\uffff\32\13",
            "",
            "\12\13\7\uffff\32\13\4\uffff\1\13\1\uffff\32\13",
            "",
            ""
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( PARAM_START | PARAM_END | EQUAL | COMMA | CURLY_START | CURLY_END | TRUE | FALSE | NULL | ANNOTATION | IDENTIFIER | STRING_LITERAL | INTEGER_LITERAL | FLOAT_LITERAL | WHITESPACE );";
        }
    }
 

}