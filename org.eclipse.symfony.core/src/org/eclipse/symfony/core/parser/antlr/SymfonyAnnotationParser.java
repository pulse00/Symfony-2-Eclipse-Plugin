// $ANTLR 3.3 Nov 30, 2010 12:45:30 SymfonyAnnotationParser.g 2011-06-09 20:11:02

package org.eclipse.symfony.core.parser.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class SymfonyAnnotationParser extends AbstractAnnotationParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AT", "PARAM_START", "PARAM_END", "ASIG", "COMMA", "STRING_CHAR", "STRING", "NONCONTROL_CHAR", "STRING_LITERAL", "LOWER", "UPPER", "DIGIT", "UNDER", "LETTER", "SYMBOL", "BSLASH", "WHITESPACE", "ANNOTATION", "NAMED_ARG", "LITERAL_ARG", "RHTYPE"
    };
    public static final int EOF=-1;
    public static final int AT=4;
    public static final int PARAM_START=5;
    public static final int PARAM_END=6;
    public static final int ASIG=7;
    public static final int COMMA=8;
    public static final int STRING_CHAR=9;
    public static final int STRING=10;
    public static final int NONCONTROL_CHAR=11;
    public static final int STRING_LITERAL=12;
    public static final int LOWER=13;
    public static final int UPPER=14;
    public static final int DIGIT=15;
    public static final int UNDER=16;
    public static final int LETTER=17;
    public static final int SYMBOL=18;
    public static final int BSLASH=19;
    public static final int WHITESPACE=20;
    public static final int ANNOTATION=21;
    public static final int NAMED_ARG=22;
    public static final int LITERAL_ARG=23;
    public static final int RHTYPE=24;

    // delegates
    // delegators


        public SymfonyAnnotationParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public SymfonyAnnotationParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return SymfonyAnnotationParser.tokenNames; }
    public String getGrammarFileName() { return "SymfonyAnnotationParser.g"; }


    public static class annotation_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "annotation"
    // SymfonyAnnotationParser.g:26:1: annotation : AT ann_class ( PARAM_START arguments PARAM_END ) -> ^( ANNOTATION ann_class arguments ) ;
    public final SymfonyAnnotationParser.annotation_return annotation() throws RecognitionException {
        SymfonyAnnotationParser.annotation_return retval = new SymfonyAnnotationParser.annotation_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken AT1=null;
        CommonToken PARAM_START3=null;
        CommonToken PARAM_END5=null;
        SymfonyAnnotationParser.ann_class_return ann_class2 = null;

        SymfonyAnnotationParser.arguments_return arguments4 = null;


        AnnotationCommonTree AT1_tree=null;
        AnnotationCommonTree PARAM_START3_tree=null;
        AnnotationCommonTree PARAM_END5_tree=null;
        RewriteRuleTokenStream stream_AT=new RewriteRuleTokenStream(adaptor,"token AT");
        RewriteRuleTokenStream stream_PARAM_START=new RewriteRuleTokenStream(adaptor,"token PARAM_START");
        RewriteRuleTokenStream stream_PARAM_END=new RewriteRuleTokenStream(adaptor,"token PARAM_END");
        RewriteRuleSubtreeStream stream_arguments=new RewriteRuleSubtreeStream(adaptor,"rule arguments");
        RewriteRuleSubtreeStream stream_ann_class=new RewriteRuleSubtreeStream(adaptor,"rule ann_class");
        try {
            // SymfonyAnnotationParser.g:27:3: ( AT ann_class ( PARAM_START arguments PARAM_END ) -> ^( ANNOTATION ann_class arguments ) )
            // SymfonyAnnotationParser.g:27:5: AT ann_class ( PARAM_START arguments PARAM_END )
            {
            AT1=(CommonToken)match(input,AT,FOLLOW_AT_in_annotation73);  
            stream_AT.add(AT1);

            pushFollow(FOLLOW_ann_class_in_annotation75);
            ann_class2=ann_class();

            state._fsp--;

            stream_ann_class.add(ann_class2.getTree());
            // SymfonyAnnotationParser.g:27:18: ( PARAM_START arguments PARAM_END )
            // SymfonyAnnotationParser.g:27:19: PARAM_START arguments PARAM_END
            {
            PARAM_START3=(CommonToken)match(input,PARAM_START,FOLLOW_PARAM_START_in_annotation78);  
            stream_PARAM_START.add(PARAM_START3);

            pushFollow(FOLLOW_arguments_in_annotation80);
            arguments4=arguments();

            state._fsp--;

            stream_arguments.add(arguments4.getTree());
            PARAM_END5=(CommonToken)match(input,PARAM_END,FOLLOW_PARAM_END_in_annotation82);  
            stream_PARAM_END.add(PARAM_END5);


            }



            // AST REWRITE
            // elements: arguments, ann_class
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 28:5: -> ^( ANNOTATION ann_class arguments )
            {
                // SymfonyAnnotationParser.g:28:8: ^( ANNOTATION ann_class arguments )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ANNOTATION, "ANNOTATION"), root_1);

                adaptor.addChild(root_1, stream_ann_class.nextTree());
                adaptor.addChild(root_1, stream_arguments.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (AnnotationCommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (AnnotationCommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "annotation"

    public static class ann_class_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ann_class"
    // SymfonyAnnotationParser.g:32:1: ann_class : ( STRING ( BSLASH )? )+ ;
    public final SymfonyAnnotationParser.ann_class_return ann_class() throws RecognitionException {
        SymfonyAnnotationParser.ann_class_return retval = new SymfonyAnnotationParser.ann_class_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken STRING6=null;
        CommonToken BSLASH7=null;

        AnnotationCommonTree STRING6_tree=null;
        AnnotationCommonTree BSLASH7_tree=null;

        try {
            // SymfonyAnnotationParser.g:33:3: ( ( STRING ( BSLASH )? )+ )
            // SymfonyAnnotationParser.g:33:5: ( STRING ( BSLASH )? )+
            {
            root_0 = (AnnotationCommonTree)adaptor.nil();

            // SymfonyAnnotationParser.g:33:5: ( STRING ( BSLASH )? )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==STRING) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // SymfonyAnnotationParser.g:33:6: STRING ( BSLASH )?
            	    {
            	    STRING6=(CommonToken)match(input,STRING,FOLLOW_STRING_in_ann_class112); 
            	    STRING6_tree = (AnnotationCommonTree)adaptor.create(STRING6);
            	    adaptor.addChild(root_0, STRING6_tree);

            	    // SymfonyAnnotationParser.g:33:13: ( BSLASH )?
            	    int alt1=2;
            	    int LA1_0 = input.LA(1);

            	    if ( (LA1_0==BSLASH) ) {
            	        alt1=1;
            	    }
            	    switch (alt1) {
            	        case 1 :
            	            // SymfonyAnnotationParser.g:33:13: BSLASH
            	            {
            	            BSLASH7=(CommonToken)match(input,BSLASH,FOLLOW_BSLASH_in_ann_class114); 
            	            BSLASH7_tree = (AnnotationCommonTree)adaptor.create(BSLASH7);
            	            adaptor.addChild(root_0, BSLASH7_tree);


            	            }
            	            break;

            	    }


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


            }

            retval.stop = input.LT(-1);

            retval.tree = (AnnotationCommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (AnnotationCommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "ann_class"

    public static class arguments_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arguments"
    // SymfonyAnnotationParser.g:36:1: arguments : argument ( COMMA ( argument ) )* ;
    public final SymfonyAnnotationParser.arguments_return arguments() throws RecognitionException {
        SymfonyAnnotationParser.arguments_return retval = new SymfonyAnnotationParser.arguments_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken COMMA9=null;
        SymfonyAnnotationParser.argument_return argument8 = null;

        SymfonyAnnotationParser.argument_return argument10 = null;


        AnnotationCommonTree COMMA9_tree=null;

        try {
            // SymfonyAnnotationParser.g:37:3: ( argument ( COMMA ( argument ) )* )
            // SymfonyAnnotationParser.g:37:5: argument ( COMMA ( argument ) )*
            {
            root_0 = (AnnotationCommonTree)adaptor.nil();

            pushFollow(FOLLOW_argument_in_arguments130);
            argument8=argument();

            state._fsp--;

            adaptor.addChild(root_0, argument8.getTree());
            // SymfonyAnnotationParser.g:37:15: ( COMMA ( argument ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==COMMA) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // SymfonyAnnotationParser.g:37:16: COMMA ( argument )
            	    {
            	    COMMA9=(CommonToken)match(input,COMMA,FOLLOW_COMMA_in_arguments134); 
            	    COMMA9_tree = (AnnotationCommonTree)adaptor.create(COMMA9);
            	    adaptor.addChild(root_0, COMMA9_tree);

            	    // SymfonyAnnotationParser.g:37:22: ( argument )
            	    // SymfonyAnnotationParser.g:37:23: argument
            	    {
            	    pushFollow(FOLLOW_argument_in_arguments137);
            	    argument10=argument();

            	    state._fsp--;

            	    adaptor.addChild(root_0, argument10.getTree());

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (AnnotationCommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (AnnotationCommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "arguments"

    public static class argument_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argument"
    // SymfonyAnnotationParser.g:40:1: argument : ( literal_argument | named_argument );
    public final SymfonyAnnotationParser.argument_return argument() throws RecognitionException {
        SymfonyAnnotationParser.argument_return retval = new SymfonyAnnotationParser.argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        SymfonyAnnotationParser.literal_argument_return literal_argument11 = null;

        SymfonyAnnotationParser.named_argument_return named_argument12 = null;



        try {
            // SymfonyAnnotationParser.g:41:1: ( literal_argument | named_argument )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==STRING_LITERAL) ) {
                alt4=1;
            }
            else if ( (LA4_0==STRING) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // SymfonyAnnotationParser.g:41:3: literal_argument
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_literal_argument_in_argument151);
                    literal_argument11=literal_argument();

                    state._fsp--;

                    adaptor.addChild(root_0, literal_argument11.getTree());

                    }
                    break;
                case 2 :
                    // SymfonyAnnotationParser.g:41:22: named_argument
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_named_argument_in_argument155);
                    named_argument12=named_argument();

                    state._fsp--;

                    adaptor.addChild(root_0, named_argument12.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (AnnotationCommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (AnnotationCommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "argument"

    public static class literal_argument_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "literal_argument"
    // SymfonyAnnotationParser.g:45:1: literal_argument : param= STRING_LITERAL -> ^( LITERAL_ARG $param) ;
    public final SymfonyAnnotationParser.literal_argument_return literal_argument() throws RecognitionException {
        SymfonyAnnotationParser.literal_argument_return retval = new SymfonyAnnotationParser.literal_argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken param=null;

        AnnotationCommonTree param_tree=null;
        RewriteRuleTokenStream stream_STRING_LITERAL=new RewriteRuleTokenStream(adaptor,"token STRING_LITERAL");

        try {
            // SymfonyAnnotationParser.g:46:3: (param= STRING_LITERAL -> ^( LITERAL_ARG $param) )
            // SymfonyAnnotationParser.g:46:5: param= STRING_LITERAL
            {
            param=(CommonToken)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_literal_argument169);  
            stream_STRING_LITERAL.add(param);



            // AST REWRITE
            // elements: param
            // token labels: param
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_param=new RewriteRuleTokenStream(adaptor,"token param",param);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 47:5: -> ^( LITERAL_ARG $param)
            {
                // SymfonyAnnotationParser.g:47:8: ^( LITERAL_ARG $param)
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(LITERAL_ARG, "LITERAL_ARG"), root_1);

                adaptor.addChild(root_1, stream_param.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (AnnotationCommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (AnnotationCommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "literal_argument"

    public static class named_argument_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "named_argument"
    // SymfonyAnnotationParser.g:50:1: named_argument : lht= STRING ASIG rhtype -> ^( NAMED_ARG $lht rhtype ) ;
    public final SymfonyAnnotationParser.named_argument_return named_argument() throws RecognitionException {
        SymfonyAnnotationParser.named_argument_return retval = new SymfonyAnnotationParser.named_argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken lht=null;
        CommonToken ASIG13=null;
        SymfonyAnnotationParser.rhtype_return rhtype14 = null;


        AnnotationCommonTree lht_tree=null;
        AnnotationCommonTree ASIG13_tree=null;
        RewriteRuleTokenStream stream_ASIG=new RewriteRuleTokenStream(adaptor,"token ASIG");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_rhtype=new RewriteRuleSubtreeStream(adaptor,"rule rhtype");
        try {
            // SymfonyAnnotationParser.g:51:3: (lht= STRING ASIG rhtype -> ^( NAMED_ARG $lht rhtype ) )
            // SymfonyAnnotationParser.g:51:5: lht= STRING ASIG rhtype
            {
            lht=(CommonToken)match(input,STRING,FOLLOW_STRING_in_named_argument197);  
            stream_STRING.add(lht);

            ASIG13=(CommonToken)match(input,ASIG,FOLLOW_ASIG_in_named_argument199);  
            stream_ASIG.add(ASIG13);

            pushFollow(FOLLOW_rhtype_in_named_argument201);
            rhtype14=rhtype();

            state._fsp--;

            stream_rhtype.add(rhtype14.getTree());


            // AST REWRITE
            // elements: lht, rhtype
            // token labels: lht
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_lht=new RewriteRuleTokenStream(adaptor,"token lht",lht);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 52:5: -> ^( NAMED_ARG $lht rhtype )
            {
                // SymfonyAnnotationParser.g:52:8: ^( NAMED_ARG $lht rhtype )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(NAMED_ARG, "NAMED_ARG"), root_1);

                adaptor.addChild(root_1, stream_lht.nextNode());
                adaptor.addChild(root_1, stream_rhtype.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (AnnotationCommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (AnnotationCommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "named_argument"

    public static class rhtype_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rhtype"
    // SymfonyAnnotationParser.g:55:1: rhtype : (param= STRING -> ^( RHTYPE $param) | param= STRING_LITERAL -> ^( RHTYPE $param) );
    public final SymfonyAnnotationParser.rhtype_return rhtype() throws RecognitionException {
        SymfonyAnnotationParser.rhtype_return retval = new SymfonyAnnotationParser.rhtype_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken param=null;

        AnnotationCommonTree param_tree=null;
        RewriteRuleTokenStream stream_STRING_LITERAL=new RewriteRuleTokenStream(adaptor,"token STRING_LITERAL");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // SymfonyAnnotationParser.g:56:3: (param= STRING -> ^( RHTYPE $param) | param= STRING_LITERAL -> ^( RHTYPE $param) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==STRING) ) {
                alt5=1;
            }
            else if ( (LA5_0==STRING_LITERAL) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // SymfonyAnnotationParser.g:56:5: param= STRING
                    {
                    param=(CommonToken)match(input,STRING,FOLLOW_STRING_in_rhtype231);  
                    stream_STRING.add(param);



                    // AST REWRITE
                    // elements: param
                    // token labels: param
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_param=new RewriteRuleTokenStream(adaptor,"token param",param);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (AnnotationCommonTree)adaptor.nil();
                    // 57:5: -> ^( RHTYPE $param)
                    {
                        // SymfonyAnnotationParser.g:57:8: ^( RHTYPE $param)
                        {
                        AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                        root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(RHTYPE, "RHTYPE"), root_1);

                        adaptor.addChild(root_1, stream_param.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // SymfonyAnnotationParser.g:58:5: param= STRING_LITERAL
                    {
                    param=(CommonToken)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_rhtype252);  
                    stream_STRING_LITERAL.add(param);



                    // AST REWRITE
                    // elements: param
                    // token labels: param
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_param=new RewriteRuleTokenStream(adaptor,"token param",param);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (AnnotationCommonTree)adaptor.nil();
                    // 59:5: -> ^( RHTYPE $param)
                    {
                        // SymfonyAnnotationParser.g:59:8: ^( RHTYPE $param)
                        {
                        AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                        root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(RHTYPE, "RHTYPE"), root_1);

                        adaptor.addChild(root_1, stream_param.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (AnnotationCommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (AnnotationCommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "rhtype"

    // Delegated rules


 

    public static final BitSet FOLLOW_AT_in_annotation73 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_ann_class_in_annotation75 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_PARAM_START_in_annotation78 = new BitSet(new long[]{0x0000000000001400L});
    public static final BitSet FOLLOW_arguments_in_annotation80 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_PARAM_END_in_annotation82 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_ann_class112 = new BitSet(new long[]{0x0000000000080402L});
    public static final BitSet FOLLOW_BSLASH_in_ann_class114 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_argument_in_arguments130 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_arguments134 = new BitSet(new long[]{0x0000000000001400L});
    public static final BitSet FOLLOW_argument_in_arguments137 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_literal_argument_in_argument151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_named_argument_in_argument155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_literal_argument169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_named_argument197 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ASIG_in_named_argument199 = new BitSet(new long[]{0x0000000000001400L});
    public static final BitSet FOLLOW_rhtype_in_named_argument201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_rhtype231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_rhtype252 = new BitSet(new long[]{0x0000000000000002L});

}