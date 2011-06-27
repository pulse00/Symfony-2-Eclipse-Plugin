// $ANTLR 3.3 Nov 30, 2010 12:45:30 AnnotationLexer.g 2011-06-27 21:02:24

package org.eclipse.symfony.core.parser.antlr;

import org.eclipse.symfony.core.parser.antlr.error.IAnnotationErrorReporter;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class AnnotationLexer extends Lexer {
    public static final int EOF=-1;
    public static final int AT=4;
    public static final int PARAM_START=5;
    public static final int PARAM_END=6;
    public static final int ASIG=7;
    public static final int COMMA=8;
    public static final int BSLASH=9;
    public static final int JSON_START=10;
    public static final int JSON_END=11;
    public static final int STRING_CHAR=12;
    public static final int STRING=13;
    public static final int NONCONTROL_CHAR=14;
    public static final int STRING_LITERAL=15;
    public static final int LOWER=16;
    public static final int UPPER=17;
    public static final int DIGIT=18;
    public static final int UNDER=19;
    public static final int LETTER=20;
    public static final int SYMBOL=21;
    public static final int WHITESPACE=22;


        private IAnnotationErrorReporter errorReporter = null;

        public AnnotationLexer(CharStream input, IAnnotationErrorReporter errorReporter) {
            this(input, new RecognizerSharedState());
            this.errorReporter = errorReporter;
        }
        
    	public void displayRecognitionError(String[] tokenNames,
                                            RecognitionException e) {
            
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

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:53:6: ( '@' )
            // AnnotationLexer.g:53:8: '@'
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
            // AnnotationLexer.g:54:13: ( '(' )
            // AnnotationLexer.g:54:15: '('
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
            // AnnotationLexer.g:55:11: ( ')' )
            // AnnotationLexer.g:55:13: ')'
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

    // $ANTLR start "ASIG"
    public final void mASIG() throws RecognitionException {
        try {
            int _type = ASIG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:56:8: ( '=' )
            // AnnotationLexer.g:56:10: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASIG"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:57:8: ( ( ( ' ' )* ',' ( ' ' )* ) )
            // AnnotationLexer.g:57:10: ( ( ' ' )* ',' ( ' ' )* )
            {
            // AnnotationLexer.g:57:10: ( ( ' ' )* ',' ( ' ' )* )
            // AnnotationLexer.g:57:12: ( ' ' )* ',' ( ' ' )*
            {
            // AnnotationLexer.g:57:12: ( ' ' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==' ') ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // AnnotationLexer.g:57:12: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match(','); 
            // AnnotationLexer.g:57:21: ( ' ' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==' ') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // AnnotationLexer.g:57:21: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }


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
            // AnnotationLexer.g:58:9: ( '\\\\' )
            // AnnotationLexer.g:58:11: '\\\\'
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

    // $ANTLR start "JSON_START"
    public final void mJSON_START() throws RecognitionException {
        try {
            int _type = JSON_START;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:59:13: ( '{' )
            // AnnotationLexer.g:59:15: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "JSON_START"

    // $ANTLR start "JSON_END"
    public final void mJSON_END() throws RecognitionException {
        try {
            int _type = JSON_END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:60:10: ( '}' )
            // AnnotationLexer.g:60:12: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "JSON_END"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:65:8: ( ( STRING_CHAR )+ )
            // AnnotationLexer.g:65:10: ( STRING_CHAR )+
            {
            // AnnotationLexer.g:65:10: ( STRING_CHAR )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='Z')||LA3_0=='_'||(LA3_0>='a' && LA3_0<='z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // AnnotationLexer.g:65:10: STRING_CHAR
            	    {
            	    mSTRING_CHAR(); 

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "STRING_LITERAL"
    public final void mSTRING_LITERAL() throws RecognitionException {
        try {
            int _type = STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // AnnotationLexer.g:71:1: ( '\"' ( NONCONTROL_CHAR )* '\"' | '\\'' ( NONCONTROL_CHAR )* '\\'' )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='\"') ) {
                alt6=1;
            }
            else if ( (LA6_0=='\'') ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // AnnotationLexer.g:71:3: '\"' ( NONCONTROL_CHAR )* '\"'
                    {
                    match('\"'); 
                    // AnnotationLexer.g:71:7: ( NONCONTROL_CHAR )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0=='-'||(LA4_0>='/' && LA4_0<=':')||(LA4_0>='A' && LA4_0<='Z')||LA4_0=='_'||(LA4_0>='a' && LA4_0<='{')||LA4_0=='}') ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // AnnotationLexer.g:71:7: NONCONTROL_CHAR
                    	    {
                    	    mNONCONTROL_CHAR(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // AnnotationLexer.g:72:3: '\\'' ( NONCONTROL_CHAR )* '\\''
                    {
                    match('\''); 
                    // AnnotationLexer.g:72:8: ( NONCONTROL_CHAR )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0=='-'||(LA5_0>='/' && LA5_0<=':')||(LA5_0>='A' && LA5_0<='Z')||LA5_0=='_'||(LA5_0>='a' && LA5_0<='{')||LA5_0=='}') ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // AnnotationLexer.g:72:8: NONCONTROL_CHAR
                    	    {
                    	    mNONCONTROL_CHAR(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
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

    // $ANTLR start "STRING_CHAR"
    public final void mSTRING_CHAR() throws RecognitionException {
        try {
            // AnnotationLexer.g:75:23: ( LOWER | UPPER | DIGIT | UNDER )
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

        }
        finally {
        }
    }
    // $ANTLR end "STRING_CHAR"

    // $ANTLR start "NONCONTROL_CHAR"
    public final void mNONCONTROL_CHAR() throws RecognitionException {
        try {
            // AnnotationLexer.g:76:25: ( LETTER | DIGIT | SYMBOL )
            // AnnotationLexer.g:
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
            // AnnotationLexer.g:77:17: ( LOWER | UPPER )
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

    // $ANTLR start "LOWER"
    public final void mLOWER() throws RecognitionException {
        try {
            // AnnotationLexer.g:78:16: ( 'a' .. 'z' )
            // AnnotationLexer.g:78:18: 'a' .. 'z'
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
            // AnnotationLexer.g:79:16: ( 'A' .. 'Z' )
            // AnnotationLexer.g:79:18: 'A' .. 'Z'
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
            // AnnotationLexer.g:80:16: ( '0' .. '9' )
            // AnnotationLexer.g:80:18: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "UNDER"
    public final void mUNDER() throws RecognitionException {
        try {
            // AnnotationLexer.g:81:16: ( '_' )
            // AnnotationLexer.g:81:18: '_'
            {
            match('_'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UNDER"

    // $ANTLR start "SYMBOL"
    public final void mSYMBOL() throws RecognitionException {
        try {
            // AnnotationLexer.g:82:16: ( UNDER | '-' | '/' | ':' | '{' | '}' )
            // AnnotationLexer.g:
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
            // AnnotationLexer.g:84:12: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+ )
            // AnnotationLexer.g:84:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            {
            // AnnotationLexer.g:84:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='\t' && LA7_0<='\n')||(LA7_0>='\f' && LA7_0<='\r')||LA7_0==' ') ) {
                    alt7=1;
                }


                switch (alt7) {
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
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
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
        // AnnotationLexer.g:1:8: ( AT | PARAM_START | PARAM_END | ASIG | COMMA | BSLASH | JSON_START | JSON_END | STRING | STRING_LITERAL | WHITESPACE )
        int alt8=11;
        alt8 = dfa8.predict(input);
        switch (alt8) {
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
                // AnnotationLexer.g:1:35: ASIG
                {
                mASIG(); 

                }
                break;
            case 5 :
                // AnnotationLexer.g:1:40: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 6 :
                // AnnotationLexer.g:1:46: BSLASH
                {
                mBSLASH(); 

                }
                break;
            case 7 :
                // AnnotationLexer.g:1:53: JSON_START
                {
                mJSON_START(); 

                }
                break;
            case 8 :
                // AnnotationLexer.g:1:64: JSON_END
                {
                mJSON_END(); 

                }
                break;
            case 9 :
                // AnnotationLexer.g:1:73: STRING
                {
                mSTRING(); 

                }
                break;
            case 10 :
                // AnnotationLexer.g:1:80: STRING_LITERAL
                {
                mSTRING_LITERAL(); 

                }
                break;
            case 11 :
                // AnnotationLexer.g:1:95: WHITESPACE
                {
                mWHITESPACE(); 

                }
                break;

        }

    }


    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA8_eotS =
        "\5\uffff\1\14\7\uffff";
    static final String DFA8_eofS =
        "\15\uffff";
    static final String DFA8_minS =
        "\1\11\4\uffff\1\40\7\uffff";
    static final String DFA8_maxS =
        "\1\175\4\uffff\1\54\7\uffff";
    static final String DFA8_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\uffff\1\5\1\6\1\7\1\10\1\11\1\12\1\13";
    static final String DFA8_specialS =
        "\15\uffff}>";
    static final String[] DFA8_transitionS = {
            "\2\14\1\uffff\2\14\22\uffff\1\5\1\uffff\1\13\4\uffff\1\13\1"+
            "\2\1\3\2\uffff\1\6\3\uffff\12\12\3\uffff\1\4\2\uffff\1\1\32"+
            "\12\1\uffff\1\7\2\uffff\1\12\1\uffff\32\12\1\10\1\uffff\1\11",
            "",
            "",
            "",
            "",
            "\1\5\13\uffff\1\6",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( AT | PARAM_START | PARAM_END | ASIG | COMMA | BSLASH | JSON_START | JSON_END | STRING | STRING_LITERAL | WHITESPACE );";
        }
    }
 

}