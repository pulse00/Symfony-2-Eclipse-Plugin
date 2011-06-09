// $ANTLR 3.3 Nov 30, 2010 12:45:30 SymfonyAnnotationParser.g 2011-06-09 00:40:04

package org.eclipse.symfony.core.parser.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class SymfonyAnnotationParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ANNOTATIONNAME", "ARGUMENTS", "ANNOTATION", "ANN_START", "ANN_CLASS", "PARAM_START", "STRING_LITERAL", "COMMA", "PARAM_END", "LETTER", "EQUALS", "KEY_VAL", "NONCONTROL_CHAR", "ANN_CHAR", "BSLASH", "DIGIT", "SYMBOL", "LOWER", "UPPER", "SPACE", "WHITESPACE", "LT"
    };
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


        public SymfonyAnnotationParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public SymfonyAnnotationParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return SymfonyAnnotationParser.tokenNames; }
    public String getGrammarFileName() { return "SymfonyAnnotationParser.g"; }



    // $ANTLR start "name"
    // SymfonyAnnotationParser.g:17:1: name returns [String aname] : ANNOTATIONNAME ;
    public final String name() throws RecognitionException {
        String aname = null;


        	aname = new String();

        try {
            // SymfonyAnnotationParser.g:21:2: ( ANNOTATIONNAME )
            // SymfonyAnnotationParser.g:21:4: ANNOTATIONNAME
            {
            match(input,ANNOTATIONNAME,FOLLOW_ANNOTATIONNAME_in_name44); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return aname;
    }
    // $ANTLR end "name"

    // Delegated rules


 

    public static final BitSet FOLLOW_ANNOTATIONNAME_in_name44 = new BitSet(new long[]{0x0000000000000002L});

}