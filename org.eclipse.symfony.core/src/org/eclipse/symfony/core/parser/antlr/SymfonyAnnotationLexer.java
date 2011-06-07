// $ANTLR 3.3 Nov 30, 2010 12:45:30 SymfonyAnnotationLexer.g 2011-06-07 00:50:01

package org.eclipse.symfony.core.parser.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class SymfonyAnnotationLexer extends Lexer {
    public static final int EOF=-1;
    public static final int ANN_START=4;
    public static final int LABEL=5;
    public static final int ANNOTATIONNAME=6;
    public static final int ANNOTATIONPARAM=7;
    public static final int WHITESPACE=8;
    public static final int PARAM_START=9;
    public static final int PARAM_END=10;

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

    // $ANTLR start "ANNOTATIONNAME"
    public final void mANNOTATIONNAME() throws RecognitionException {
        try {
            int _type = ANNOTATIONNAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:7:16: ( ANN_START LABEL )
            // SymfonyAnnotationLexer.g:7:18: ANN_START LABEL
            {
            mANN_START(); 
            mLABEL(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ANNOTATIONNAME"

    // $ANTLR start "ANNOTATIONPARAM"
    public final void mANNOTATIONPARAM() throws RecognitionException {
        try {
            int _type = ANNOTATIONPARAM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:8:17: ( '\\'' LABEL '\\'' )
            // SymfonyAnnotationLexer.g:8:19: '\\'' LABEL '\\''
            {
            match('\''); 
            mLABEL(); 
            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ANNOTATIONPARAM"

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:11:12: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+ )
            // SymfonyAnnotationLexer.g:11:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            {
            // SymfonyAnnotationLexer.g:11:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='\t' && LA1_0<='\n')||(LA1_0>='\f' && LA1_0<='\r')||LA1_0==' ') ) {
                    alt1=1;
                }


                switch (alt1) {
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
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
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

    // $ANTLR start "LABEL"
    public final void mLABEL() throws RecognitionException {
        try {
            int _type = LABEL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:14:1: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '*' ) ( '*' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // SymfonyAnnotationLexer.g:14:3: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '*' ) ( '*' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            if ( input.LA(1)=='*'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // SymfonyAnnotationLexer.g:14:30: ( '*' | 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='*'||(LA2_0>='0' && LA2_0<='9')||(LA2_0>='A' && LA2_0<='Z')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // SymfonyAnnotationLexer.g:
            	    {
            	    if ( input.LA(1)=='*'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LABEL"

    // $ANTLR start "ANN_START"
    public final void mANN_START() throws RecognitionException {
        try {
            int _type = ANN_START;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // SymfonyAnnotationLexer.g:19:11: ( '@' )
            // SymfonyAnnotationLexer.g:19:13: '@'
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
            // SymfonyAnnotationLexer.g:20:13: ( '(' )
            // SymfonyAnnotationLexer.g:20:15: '('
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
            // SymfonyAnnotationLexer.g:21:11: ( ')' )
            // SymfonyAnnotationLexer.g:21:13: ')'
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
        // SymfonyAnnotationLexer.g:1:8: ( ANNOTATIONNAME | ANNOTATIONPARAM | WHITESPACE | LABEL | ANN_START | PARAM_START | PARAM_END )
        int alt3=7;
        switch ( input.LA(1) ) {
        case '@':
            {
            int LA3_1 = input.LA(2);

            if ( (LA3_1=='*'||(LA3_1>='A' && LA3_1<='Z')||LA3_1=='_'||(LA3_1>='a' && LA3_1<='z')) ) {
                alt3=1;
            }
            else {
                alt3=5;}
            }
            break;
        case '\'':
            {
            alt3=2;
            }
            break;
        case '\t':
        case '\n':
        case '\f':
        case '\r':
        case ' ':
            {
            alt3=3;
            }
            break;
        case '*':
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case '_':
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt3=4;
            }
            break;
        case '(':
            {
            alt3=6;
            }
            break;
        case ')':
            {
            alt3=7;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 3, 0, input);

            throw nvae;
        }

        switch (alt3) {
            case 1 :
                // SymfonyAnnotationLexer.g:1:10: ANNOTATIONNAME
                {
                mANNOTATIONNAME(); 

                }
                break;
            case 2 :
                // SymfonyAnnotationLexer.g:1:25: ANNOTATIONPARAM
                {
                mANNOTATIONPARAM(); 

                }
                break;
            case 3 :
                // SymfonyAnnotationLexer.g:1:41: WHITESPACE
                {
                mWHITESPACE(); 

                }
                break;
            case 4 :
                // SymfonyAnnotationLexer.g:1:52: LABEL
                {
                mLABEL(); 

                }
                break;
            case 5 :
                // SymfonyAnnotationLexer.g:1:58: ANN_START
                {
                mANN_START(); 

                }
                break;
            case 6 :
                // SymfonyAnnotationLexer.g:1:68: PARAM_START
                {
                mPARAM_START(); 

                }
                break;
            case 7 :
                // SymfonyAnnotationLexer.g:1:80: PARAM_END
                {
                mPARAM_END(); 

                }
                break;

        }

    }


 

}