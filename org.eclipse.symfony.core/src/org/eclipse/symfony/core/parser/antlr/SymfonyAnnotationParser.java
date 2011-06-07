// $ANTLR 3.3 Nov 30, 2010 12:45:30 SymfonyAnnotationParser.g 2011-06-07 00:50:01

package org.eclipse.symfony.core.parser.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class SymfonyAnnotationParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ANN_START", "LABEL", "ANNOTATIONNAME", "ANNOTATIONPARAM", "WHITESPACE", "PARAM_START", "PARAM_END"
    };
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


        public SymfonyAnnotationParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public SymfonyAnnotationParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return SymfonyAnnotationParser.tokenNames; }
    public String getGrammarFileName() { return "SymfonyAnnotationParser.g"; }



    // $ANTLR start "name"
    // SymfonyAnnotationParser.g:15:1: name returns [String result] : (a= ANNOTATIONNAME | );
    public final String name() throws RecognitionException {
        String result = null;

        Token a=null;

        try {
            // SymfonyAnnotationParser.g:16:2: (a= ANNOTATIONNAME | )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==ANNOTATIONNAME) ) {
                alt1=1;
            }
            else if ( (LA1_0==EOF) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // SymfonyAnnotationParser.g:16:4: a= ANNOTATIONNAME
                    {
                    a=(Token)match(input,ANNOTATIONNAME,FOLLOW_ANNOTATIONNAME_in_name43); 
                     result = (a!=null?a.getText():null);

                    }
                    break;
                case 2 :
                    // SymfonyAnnotationParser.g:18:2: 
                    {
                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "name"


    // $ANTLR start "annotation"
    // SymfonyAnnotationParser.g:21:1: annotation : ANNOTATIONNAME PARAM_START ( ANNOTATIONPARAM )+ PARAM_END ;
    public final void annotation() throws RecognitionException {
        try {
            // SymfonyAnnotationParser.g:21:12: ( ANNOTATIONNAME PARAM_START ( ANNOTATIONPARAM )+ PARAM_END )
            // SymfonyAnnotationParser.g:21:14: ANNOTATIONNAME PARAM_START ( ANNOTATIONPARAM )+ PARAM_END
            {
            match(input,ANNOTATIONNAME,FOLLOW_ANNOTATIONNAME_in_annotation62); 
            match(input,PARAM_START,FOLLOW_PARAM_START_in_annotation64); 
            // SymfonyAnnotationParser.g:21:41: ( ANNOTATIONPARAM )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==ANNOTATIONPARAM) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // SymfonyAnnotationParser.g:21:41: ANNOTATIONPARAM
            	    {
            	    match(input,ANNOTATIONPARAM,FOLLOW_ANNOTATIONPARAM_in_annotation66); 

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);

            match(input,PARAM_END,FOLLOW_PARAM_END_in_annotation70); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "annotation"

    // Delegated rules


 

    public static final BitSet FOLLOW_ANNOTATIONNAME_in_name43 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANNOTATIONNAME_in_annotation62 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_PARAM_START_in_annotation64 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ANNOTATIONPARAM_in_annotation66 = new BitSet(new long[]{0x0000000000000480L});
    public static final BitSet FOLLOW_PARAM_END_in_annotation70 = new BitSet(new long[]{0x0000000000000002L});

}