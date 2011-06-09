// $ANTLR 3.3 Nov 30, 2010 12:45:30 SymfonyAnnotationLexer.g 2011-06-09 00:40:04

package org.eclipse.symfony.core.parser.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class SymfonyAnnotationLexer extends Lexer {
    public static final int EOF=-1;
    public static final int ANNOTATIONNAME=4;
    public static final int ARGUMENTS=5;
    public static final int ANNOTATION=6;
    public static final int ANN_START=7;
    public static final int ANN_CLASS=8;
    public static final int PARAM_START=9;
    public static final int STRING_LITERAL=10;
    public static final int COMMA=11;
    public static final int PARAM_END=12;
    public static final int LETTER=13;
    public static final int EQUALS=14;
    public static final int KEY_VAL=15;
    public static final int NONCONTROL_CHAR=16;
    public static final int ANN_CHAR=17;
    public static final int BSLASH=18;
    public static final int DIGIT=19;
    public static final int SYMBOL=20;
    public static final int LOWER=21;
    public static final int UPPER=22;
    public static final int SPACE=23;
    public static final int WHITESPACE=24;
    public static final int LT=25;

    // delegates
    // delegators

    public SymfonyAnnotationLexer() {;} 
    public SymfonyAnnotationLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public SymfonyAnnotationLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "SymfonyAnnotationLexer.g"; }

    // $ANTLR start "ANNOTATION"
    public final void mANNOTATION() throws RecognitionException {
        try {
            int _type = ANNOTATION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:9:2: ( ANNOTATIONNAME ARGUMENTS )
            // SymfonyAnnotationLexer.g:9:4: ANNOTATIONNAME ARGUMENTS
            {
            mANNOTATIONNAME(); 
            mARGUMENTS(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ANNOTATION"

    // $ANTLR start "ANNOTATIONNAME"
    public final void mANNOTATIONNAME() throws RecognitionException {
        try {
            int _type = ANNOTATIONNAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:13:2: ( ANN_START ANN_CLASS )
            // SymfonyAnnotationLexer.g:13:4: ANN_START ANN_CLASS
            {
            mANN_START(); 
            mANN_CLASS(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ANNOTATIONNAME"

    // $ANTLR start "ARGUMENTS"
    public final void mARGUMENTS() throws RecognitionException {
        try {
            int _type = ARGUMENTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:17:2: ( PARAM_START ( STRING_LITERAL ( COMMA STRING_LITERAL )* )? PARAM_END )
            // SymfonyAnnotationLexer.g:17:4: PARAM_START ( STRING_LITERAL ( COMMA STRING_LITERAL )* )? PARAM_END
            {
            mPARAM_START(); 
            // SymfonyAnnotationLexer.g:17:16: ( STRING_LITERAL ( COMMA STRING_LITERAL )* )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='\"'||LA2_0=='\'') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // SymfonyAnnotationLexer.g:17:17: STRING_LITERAL ( COMMA STRING_LITERAL )*
                    {
                    mSTRING_LITERAL(); 
                    // SymfonyAnnotationLexer.g:17:32: ( COMMA STRING_LITERAL )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==' '||LA1_0==',') ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // SymfonyAnnotationLexer.g:17:33: COMMA STRING_LITERAL
                    	    {
                    	    mCOMMA(); 
                    	    mSTRING_LITERAL(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);


                    }
                    break;

            }

            mPARAM_END(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ARGUMENTS"

    // $ANTLR start "KEY_VAL"
    public final void mKEY_VAL() throws RecognitionException {
        try {
            int _type = KEY_VAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:21:2: ( LETTER EQUALS STRING_LITERAL )
            // SymfonyAnnotationLexer.g:21:4: LETTER EQUALS STRING_LITERAL
            {
            mLETTER(); 
            mEQUALS(); 
            mSTRING_LITERAL(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEY_VAL"

    // $ANTLR start "STRING_LITERAL"
    public final void mSTRING_LITERAL() throws RecognitionException {
        try {
            int _type = STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:25:2: ( '\"' ( NONCONTROL_CHAR )* '\"' | '\\'' ( NONCONTROL_CHAR )* '\\'' )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='\"') ) {
                alt5=1;
            }
            else if ( (LA5_0=='\'') ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // SymfonyAnnotationLexer.g:25:4: '\"' ( NONCONTROL_CHAR )* '\"'
                    {
                    match('\"'); 
                    // SymfonyAnnotationLexer.g:25:8: ( NONCONTROL_CHAR )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0=='-'||(LA3_0>='/' && LA3_0<=':')||(LA3_0>='A' && LA3_0<='Z')||LA3_0=='_'||(LA3_0>='a' && LA3_0<='{')||LA3_0=='}') ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // SymfonyAnnotationLexer.g:25:8: NONCONTROL_CHAR
                    	    {
                    	    mNONCONTROL_CHAR(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // SymfonyAnnotationLexer.g:26:4: '\\'' ( NONCONTROL_CHAR )* '\\''
                    {
                    match('\''); 
                    // SymfonyAnnotationLexer.g:26:9: ( NONCONTROL_CHAR )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0=='-'||(LA4_0>='/' && LA4_0<=':')||(LA4_0>='A' && LA4_0<='Z')||LA4_0=='_'||(LA4_0>='a' && LA4_0<='{')||LA4_0=='}') ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // SymfonyAnnotationLexer.g:26:9: NONCONTROL_CHAR
                    	    {
                    	    mNONCONTROL_CHAR(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    match('\''); 

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

    // $ANTLR start "ANN_CLASS"
    public final void mANN_CLASS() throws RecognitionException {
        try {
            int _type = ANN_CLASS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:30:2: ( ( ANN_CHAR )+ )
            // SymfonyAnnotationLexer.g:30:4: ( ANN_CHAR )+
            {
            // SymfonyAnnotationLexer.g:30:4: ( ANN_CHAR )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='A' && LA6_0<='Z')||LA6_0=='\\'||(LA6_0>='a' && LA6_0<='z')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // SymfonyAnnotationLexer.g:30:4: ANN_CHAR
            	    {
            	    mANN_CHAR(); 

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
    // $ANTLR end "ANN_CLASS"

    // $ANTLR start "ANN_CHAR"
    public final void mANN_CHAR() throws RecognitionException {
        try {
            // SymfonyAnnotationLexer.g:33:18: ( LETTER | BSLASH )
            // SymfonyAnnotationLexer.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='\\'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
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
    // $ANTLR end "ANN_CHAR"

    // $ANTLR start "NONCONTROL_CHAR"
    public final void mNONCONTROL_CHAR() throws RecognitionException {
        try {
            // SymfonyAnnotationLexer.g:34:25: ( LETTER | DIGIT | SYMBOL )
            // SymfonyAnnotationLexer.g:
            {
            if ( input.LA(1)=='-'||(input.LA(1)>='/' && input.LA(1)<=':')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='{')||input.LA(1)=='}' ) {
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
    // $ANTLR end "NONCONTROL_CHAR"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // SymfonyAnnotationLexer.g:35:16: ( LOWER | UPPER )
            // SymfonyAnnotationLexer.g:
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

    // $ANTLR start "LOWER"
    public final void mLOWER() throws RecognitionException {
        try {
            // SymfonyAnnotationLexer.g:36:15: ( 'a' .. 'z' )
            // SymfonyAnnotationLexer.g:36:17: 'a' .. 'z'
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
            // SymfonyAnnotationLexer.g:37:15: ( 'A' .. 'Z' )
            // SymfonyAnnotationLexer.g:37:17: 'A' .. 'Z'
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
            // SymfonyAnnotationLexer.g:38:15: ( '0' .. '9' )
            // SymfonyAnnotationLexer.g:38:17: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "SPACE"
    public final void mSPACE() throws RecognitionException {
        try {
            // SymfonyAnnotationLexer.g:39:15: ( ' ' | '\\t' )
            // SymfonyAnnotationLexer.g:
            {
            if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
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
    // $ANTLR end "SPACE"

    // $ANTLR start "BSLASH"
    public final void mBSLASH() throws RecognitionException {
        try {
            // SymfonyAnnotationLexer.g:40:17: ( '\\\\' )
            // SymfonyAnnotationLexer.g:40:19: '\\\\'
            {
            match('\\'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "BSLASH"

    // $ANTLR start "EQUALS"
    public final void mEQUALS() throws RecognitionException {
        try {
            // SymfonyAnnotationLexer.g:41:17: ( '=' )
            // SymfonyAnnotationLexer.g:41:19: '='
            {
            match('='); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "EQUALS"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            // SymfonyAnnotationLexer.g:42:16: ( ( ( ' ' )* ',' ( ' ' )* ) )
            // SymfonyAnnotationLexer.g:42:18: ( ( ' ' )* ',' ( ' ' )* )
            {
            // SymfonyAnnotationLexer.g:42:18: ( ( ' ' )* ',' ( ' ' )* )
            // SymfonyAnnotationLexer.g:42:20: ( ' ' )* ',' ( ' ' )*
            {
            // SymfonyAnnotationLexer.g:42:20: ( ' ' )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==' ') ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // SymfonyAnnotationLexer.g:42:20: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            match(','); 
            // SymfonyAnnotationLexer.g:42:29: ( ' ' )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==' ') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // SymfonyAnnotationLexer.g:42:29: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "SYMBOL"
    public final void mSYMBOL() throws RecognitionException {
        try {
            // SymfonyAnnotationLexer.g:43:16: ( '_' | '-' | '/' | ':' | '{' | '}' )
            // SymfonyAnnotationLexer.g:
            {
            if ( input.LA(1)=='-'||input.LA(1)=='/'||input.LA(1)==':'||input.LA(1)=='_'||input.LA(1)=='{'||input.LA(1)=='}' ) {
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
    // $ANTLR end "SYMBOL"

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:45:12: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+ )
            // SymfonyAnnotationLexer.g:45:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            {
            // SymfonyAnnotationLexer.g:45:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='\t' && LA9_0<='\n')||(LA9_0>='\f' && LA9_0<='\r')||LA9_0==' ') ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // SymfonyAnnotationLexer.g:
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
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
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

    // $ANTLR start "LT"
    public final void mLT() throws RecognitionException {
        try {
            int _type = LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:48:2: ( '\\n' | '\\r' | '\\u2028' | '\\u2029' )
            // SymfonyAnnotationLexer.g:
            {
            if ( input.LA(1)=='\n'||input.LA(1)=='\r'||(input.LA(1)>='\u2028' && input.LA(1)<='\u2029') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LT"

    // $ANTLR start "ANN_START"
    public final void mANN_START() throws RecognitionException {
        try {
            int _type = ANN_START;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:54:11: ( '@' )
            // SymfonyAnnotationLexer.g:54:13: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ANN_START"

    // $ANTLR start "PARAM_START"
    public final void mPARAM_START() throws RecognitionException {
        try {
            int _type = PARAM_START;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:55:13: ( '(' )
            // SymfonyAnnotationLexer.g:55:15: '('
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
            // SymfonyAnnotationLexer.g:56:11: ( ')' )
            // SymfonyAnnotationLexer.g:56:13: ')'
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

    public void mTokens() throws RecognitionException {
        // SymfonyAnnotationLexer.g:1:8: ( ANNOTATION | ANNOTATIONNAME | ARGUMENTS | KEY_VAL | STRING_LITERAL | ANN_CLASS | WHITESPACE | LT | ANN_START | PARAM_START | PARAM_END )
        int alt10=11;
        alt10 = dfa10.predict(input);
        switch (alt10) {
            case 1 :
                // SymfonyAnnotationLexer.g:1:10: ANNOTATION
                {
                mANNOTATION(); 

                }
                break;
            case 2 :
                // SymfonyAnnotationLexer.g:1:21: ANNOTATIONNAME
                {
                mANNOTATIONNAME(); 

                }
                break;
            case 3 :
                // SymfonyAnnotationLexer.g:1:36: ARGUMENTS
                {
                mARGUMENTS(); 

                }
                break;
            case 4 :
                // SymfonyAnnotationLexer.g:1:46: KEY_VAL
                {
                mKEY_VAL(); 

                }
                break;
            case 5 :
                // SymfonyAnnotationLexer.g:1:54: STRING_LITERAL
                {
                mSTRING_LITERAL(); 

                }
                break;
            case 6 :
                // SymfonyAnnotationLexer.g:1:69: ANN_CLASS
                {
                mANN_CLASS(); 

                }
                break;
            case 7 :
                // SymfonyAnnotationLexer.g:1:79: WHITESPACE
                {
                mWHITESPACE(); 

                }
                break;
            case 8 :
                // SymfonyAnnotationLexer.g:1:90: LT
                {
                mLT(); 

                }
                break;
            case 9 :
                // SymfonyAnnotationLexer.g:1:93: ANN_START
                {
                mANN_START(); 

                }
                break;
            case 10 :
                // SymfonyAnnotationLexer.g:1:103: PARAM_START
                {
                mPARAM_START(); 

                }
                break;
            case 11 :
                // SymfonyAnnotationLexer.g:1:115: PARAM_END
                {
                mPARAM_END(); 

                }
                break;

        }

    }


    protected DFA10 dfa10 = new DFA10(this);
    static final String DFA10_eotS =
        "\1\uffff\1\12\1\14\1\5\7\uffff\1\17\5\uffff";
    static final String DFA10_eofS =
        "\21\uffff";
    static final String DFA10_minS =
        "\1\11\1\101\1\42\1\75\7\uffff\1\50\5\uffff";
    static final String DFA10_maxS =
        "\1\u2029\1\172\1\51\1\75\7\uffff\1\172\5\uffff";
    static final String DFA10_acceptS =
        "\4\uffff\1\5\1\6\2\7\1\10\1\13\1\11\1\uffff\1\12\1\3\1\4\1\2\1\1";
    static final String DFA10_specialS =
        "\21\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\7\1\6\1\uffff\1\7\1\6\22\uffff\1\7\1\uffff\1\4\4\uffff\1"+
            "\4\1\2\1\11\26\uffff\1\1\32\3\1\uffff\1\5\4\uffff\32\3\u1fad"+
            "\uffff\2\10",
            "\32\13\1\uffff\1\13\4\uffff\32\13",
            "\1\15\4\uffff\1\15\1\uffff\1\15",
            "\1\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\20\30\uffff\32\13\1\uffff\1\13\4\uffff\32\13",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( ANNOTATION | ANNOTATIONNAME | ARGUMENTS | KEY_VAL | STRING_LITERAL | ANN_CLASS | WHITESPACE | LT | ANN_START | PARAM_START | PARAM_END );";
        }
    }
 

}