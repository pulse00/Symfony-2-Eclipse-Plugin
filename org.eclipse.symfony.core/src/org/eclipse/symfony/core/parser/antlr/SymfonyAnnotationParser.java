// $ANTLR 3.3 Nov 30, 2010 12:45:30 SymfonyAnnotationParser.g 2011-06-09 22:09:05

package org.eclipse.symfony.core.parser.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class SymfonyAnnotationParser extends AbstractAnnotationParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AT", "PARAM_START", "PARAM_END", "ASIG", "COMMA", "BSLASH", "STRING_CHAR", "STRING", "NONCONTROL_CHAR", "STRING_LITERAL", "LOWER", "UPPER", "DIGIT", "UNDER", "LETTER", "SYMBOL", "WHITESPACE", "ANNOTATION", "NAMED_ARG", "LITERAL_ARG", "NSPART", "CLASSNAME", "FQCN", "RHTYPE"
    };
    public static final int EOF=-1;
    public static final int AT=4;
    public static final int PARAM_START=5;
    public static final int PARAM_END=6;
    public static final int ASIG=7;
    public static final int COMMA=8;
    public static final int BSLASH=9;
    public static final int STRING_CHAR=10;
    public static final int STRING=11;
    public static final int NONCONTROL_CHAR=12;
    public static final int STRING_LITERAL=13;
    public static final int LOWER=14;
    public static final int UPPER=15;
    public static final int DIGIT=16;
    public static final int UNDER=17;
    public static final int LETTER=18;
    public static final int SYMBOL=19;
    public static final int WHITESPACE=20;
    public static final int ANNOTATION=21;
    public static final int NAMED_ARG=22;
    public static final int LITERAL_ARG=23;
    public static final int NSPART=24;
    public static final int CLASSNAME=25;
    public static final int FQCN=26;
    public static final int RHTYPE=27;

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
    // SymfonyAnnotationParser.g:29:1: annotation : AT ann_class ( PARAM_START arguments PARAM_END ) -> ^( ANNOTATION ann_class arguments ) ;
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
            // SymfonyAnnotationParser.g:30:3: ( AT ann_class ( PARAM_START arguments PARAM_END ) -> ^( ANNOTATION ann_class arguments ) )
            // SymfonyAnnotationParser.g:30:5: AT ann_class ( PARAM_START arguments PARAM_END )
            {
            AT1=(CommonToken)match(input,AT,FOLLOW_AT_in_annotation82);  
            stream_AT.add(AT1);

            pushFollow(FOLLOW_ann_class_in_annotation84);
            ann_class2=ann_class();

            state._fsp--;

            stream_ann_class.add(ann_class2.getTree());
            // SymfonyAnnotationParser.g:30:18: ( PARAM_START arguments PARAM_END )
            // SymfonyAnnotationParser.g:30:19: PARAM_START arguments PARAM_END
            {
            PARAM_START3=(CommonToken)match(input,PARAM_START,FOLLOW_PARAM_START_in_annotation87);  
            stream_PARAM_START.add(PARAM_START3);

            pushFollow(FOLLOW_arguments_in_annotation89);
            arguments4=arguments();

            state._fsp--;

            stream_arguments.add(arguments4.getTree());
            PARAM_END5=(CommonToken)match(input,PARAM_END,FOLLOW_PARAM_END_in_annotation91);  
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
            // 31:5: -> ^( ANNOTATION ann_class arguments )
            {
                // SymfonyAnnotationParser.g:31:7: ^( ANNOTATION ann_class arguments )
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
    // SymfonyAnnotationParser.g:35:1: ann_class : ( namespace )* classname ;
    public final SymfonyAnnotationParser.ann_class_return ann_class() throws RecognitionException {
        SymfonyAnnotationParser.ann_class_return retval = new SymfonyAnnotationParser.ann_class_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        SymfonyAnnotationParser.namespace_return namespace6 = null;

        SymfonyAnnotationParser.classname_return classname7 = null;



        try {
            // SymfonyAnnotationParser.g:36:3: ( ( namespace )* classname )
            // SymfonyAnnotationParser.g:36:5: ( namespace )* classname
            {
            root_0 = (AnnotationCommonTree)adaptor.nil();

            // SymfonyAnnotationParser.g:36:5: ( namespace )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==STRING) ) {
                    int LA1_1 = input.LA(2);

                    if ( (LA1_1==BSLASH) ) {
                        alt1=1;
                    }


                }


                switch (alt1) {
            	case 1 :
            	    // SymfonyAnnotationParser.g:36:5: namespace
            	    {
            	    pushFollow(FOLLOW_namespace_in_ann_class119);
            	    namespace6=namespace();

            	    state._fsp--;

            	    adaptor.addChild(root_0, namespace6.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            pushFollow(FOLLOW_classname_in_ann_class122);
            classname7=classname();

            state._fsp--;

            adaptor.addChild(root_0, classname7.getTree());

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

    public static class namespace_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "namespace"
    // SymfonyAnnotationParser.g:39:1: namespace : ns= STRING BSLASH -> ^( NSPART $ns) ;
    public final SymfonyAnnotationParser.namespace_return namespace() throws RecognitionException {
        SymfonyAnnotationParser.namespace_return retval = new SymfonyAnnotationParser.namespace_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken ns=null;
        CommonToken BSLASH8=null;

        AnnotationCommonTree ns_tree=null;
        AnnotationCommonTree BSLASH8_tree=null;
        RewriteRuleTokenStream stream_BSLASH=new RewriteRuleTokenStream(adaptor,"token BSLASH");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // SymfonyAnnotationParser.g:40:3: (ns= STRING BSLASH -> ^( NSPART $ns) )
            // SymfonyAnnotationParser.g:40:5: ns= STRING BSLASH
            {
            ns=(CommonToken)match(input,STRING,FOLLOW_STRING_in_namespace139);  
            stream_STRING.add(ns);

            BSLASH8=(CommonToken)match(input,BSLASH,FOLLOW_BSLASH_in_namespace141);  
            stream_BSLASH.add(BSLASH8);



            // AST REWRITE
            // elements: ns
            // token labels: ns
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_ns=new RewriteRuleTokenStream(adaptor,"token ns",ns);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 41:4: -> ^( NSPART $ns)
            {
                // SymfonyAnnotationParser.g:41:6: ^( NSPART $ns)
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(NSPART, "NSPART"), root_1);

                adaptor.addChild(root_1, stream_ns.nextNode());

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
    // $ANTLR end "namespace"

    public static class classname_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "classname"
    // SymfonyAnnotationParser.g:44:1: classname : cn= STRING -> ^( CLASSNAME $cn) ;
    public final SymfonyAnnotationParser.classname_return classname() throws RecognitionException {
        SymfonyAnnotationParser.classname_return retval = new SymfonyAnnotationParser.classname_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken cn=null;

        AnnotationCommonTree cn_tree=null;
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // SymfonyAnnotationParser.g:45:3: (cn= STRING -> ^( CLASSNAME $cn) )
            // SymfonyAnnotationParser.g:45:5: cn= STRING
            {
            cn=(CommonToken)match(input,STRING,FOLLOW_STRING_in_classname169);  
            stream_STRING.add(cn);



            // AST REWRITE
            // elements: cn
            // token labels: cn
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_cn=new RewriteRuleTokenStream(adaptor,"token cn",cn);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 46:5: -> ^( CLASSNAME $cn)
            {
                // SymfonyAnnotationParser.g:46:7: ^( CLASSNAME $cn)
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(CLASSNAME, "CLASSNAME"), root_1);

                adaptor.addChild(root_1, stream_cn.nextNode());

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
    // $ANTLR end "classname"

    public static class arguments_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arguments"
    // SymfonyAnnotationParser.g:49:1: arguments : argument ( COMMA ( argument ) )* ;
    public final SymfonyAnnotationParser.arguments_return arguments() throws RecognitionException {
        SymfonyAnnotationParser.arguments_return retval = new SymfonyAnnotationParser.arguments_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken COMMA10=null;
        SymfonyAnnotationParser.argument_return argument9 = null;

        SymfonyAnnotationParser.argument_return argument11 = null;


        AnnotationCommonTree COMMA10_tree=null;

        try {
            // SymfonyAnnotationParser.g:50:3: ( argument ( COMMA ( argument ) )* )
            // SymfonyAnnotationParser.g:50:5: argument ( COMMA ( argument ) )*
            {
            root_0 = (AnnotationCommonTree)adaptor.nil();

            pushFollow(FOLLOW_argument_in_arguments194);
            argument9=argument();

            state._fsp--;

            adaptor.addChild(root_0, argument9.getTree());
            // SymfonyAnnotationParser.g:50:15: ( COMMA ( argument ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==COMMA) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // SymfonyAnnotationParser.g:50:16: COMMA ( argument )
            	    {
            	    COMMA10=(CommonToken)match(input,COMMA,FOLLOW_COMMA_in_arguments198); 
            	    COMMA10_tree = (AnnotationCommonTree)adaptor.create(COMMA10);
            	    adaptor.addChild(root_0, COMMA10_tree);

            	    // SymfonyAnnotationParser.g:50:22: ( argument )
            	    // SymfonyAnnotationParser.g:50:23: argument
            	    {
            	    pushFollow(FOLLOW_argument_in_arguments201);
            	    argument11=argument();

            	    state._fsp--;

            	    adaptor.addChild(root_0, argument11.getTree());

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
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
    // SymfonyAnnotationParser.g:53:1: argument : ( literal_argument | named_argument );
    public final SymfonyAnnotationParser.argument_return argument() throws RecognitionException {
        SymfonyAnnotationParser.argument_return retval = new SymfonyAnnotationParser.argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        SymfonyAnnotationParser.literal_argument_return literal_argument12 = null;

        SymfonyAnnotationParser.named_argument_return named_argument13 = null;



        try {
            // SymfonyAnnotationParser.g:54:1: ( literal_argument | named_argument )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==STRING_LITERAL) ) {
                alt3=1;
            }
            else if ( (LA3_0==STRING) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // SymfonyAnnotationParser.g:54:3: literal_argument
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_literal_argument_in_argument215);
                    literal_argument12=literal_argument();

                    state._fsp--;

                    adaptor.addChild(root_0, literal_argument12.getTree());

                    }
                    break;
                case 2 :
                    // SymfonyAnnotationParser.g:54:22: named_argument
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_named_argument_in_argument219);
                    named_argument13=named_argument();

                    state._fsp--;

                    adaptor.addChild(root_0, named_argument13.getTree());

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
    // SymfonyAnnotationParser.g:58:1: literal_argument : param= STRING_LITERAL -> ^( LITERAL_ARG $param) ;
    public final SymfonyAnnotationParser.literal_argument_return literal_argument() throws RecognitionException {
        SymfonyAnnotationParser.literal_argument_return retval = new SymfonyAnnotationParser.literal_argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken param=null;

        AnnotationCommonTree param_tree=null;
        RewriteRuleTokenStream stream_STRING_LITERAL=new RewriteRuleTokenStream(adaptor,"token STRING_LITERAL");

        try {
            // SymfonyAnnotationParser.g:59:3: (param= STRING_LITERAL -> ^( LITERAL_ARG $param) )
            // SymfonyAnnotationParser.g:59:5: param= STRING_LITERAL
            {
            param=(CommonToken)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_literal_argument233);  
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
            // 60:5: -> ^( LITERAL_ARG $param)
            {
                // SymfonyAnnotationParser.g:60:8: ^( LITERAL_ARG $param)
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
    // SymfonyAnnotationParser.g:63:1: named_argument : lht= STRING ASIG rhtype -> ^( NAMED_ARG $lht rhtype ) ;
    public final SymfonyAnnotationParser.named_argument_return named_argument() throws RecognitionException {
        SymfonyAnnotationParser.named_argument_return retval = new SymfonyAnnotationParser.named_argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken lht=null;
        CommonToken ASIG14=null;
        SymfonyAnnotationParser.rhtype_return rhtype15 = null;


        AnnotationCommonTree lht_tree=null;
        AnnotationCommonTree ASIG14_tree=null;
        RewriteRuleTokenStream stream_ASIG=new RewriteRuleTokenStream(adaptor,"token ASIG");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_rhtype=new RewriteRuleSubtreeStream(adaptor,"rule rhtype");
        try {
            // SymfonyAnnotationParser.g:64:3: (lht= STRING ASIG rhtype -> ^( NAMED_ARG $lht rhtype ) )
            // SymfonyAnnotationParser.g:64:5: lht= STRING ASIG rhtype
            {
            lht=(CommonToken)match(input,STRING,FOLLOW_STRING_in_named_argument261);  
            stream_STRING.add(lht);

            ASIG14=(CommonToken)match(input,ASIG,FOLLOW_ASIG_in_named_argument263);  
            stream_ASIG.add(ASIG14);

            pushFollow(FOLLOW_rhtype_in_named_argument265);
            rhtype15=rhtype();

            state._fsp--;

            stream_rhtype.add(rhtype15.getTree());


            // AST REWRITE
            // elements: rhtype, lht
            // token labels: lht
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_lht=new RewriteRuleTokenStream(adaptor,"token lht",lht);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 65:5: -> ^( NAMED_ARG $lht rhtype )
            {
                // SymfonyAnnotationParser.g:65:8: ^( NAMED_ARG $lht rhtype )
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
    // SymfonyAnnotationParser.g:68:1: rhtype : (param= STRING -> ^( RHTYPE $param) | param= STRING_LITERAL -> ^( RHTYPE $param) );
    public final SymfonyAnnotationParser.rhtype_return rhtype() throws RecognitionException {
        SymfonyAnnotationParser.rhtype_return retval = new SymfonyAnnotationParser.rhtype_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken param=null;

        AnnotationCommonTree param_tree=null;
        RewriteRuleTokenStream stream_STRING_LITERAL=new RewriteRuleTokenStream(adaptor,"token STRING_LITERAL");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // SymfonyAnnotationParser.g:69:3: (param= STRING -> ^( RHTYPE $param) | param= STRING_LITERAL -> ^( RHTYPE $param) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==STRING) ) {
                alt4=1;
            }
            else if ( (LA4_0==STRING_LITERAL) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // SymfonyAnnotationParser.g:69:5: param= STRING
                    {
                    param=(CommonToken)match(input,STRING,FOLLOW_STRING_in_rhtype295);  
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
                    // 70:5: -> ^( RHTYPE $param)
                    {
                        // SymfonyAnnotationParser.g:70:8: ^( RHTYPE $param)
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
                    // SymfonyAnnotationParser.g:71:5: param= STRING_LITERAL
                    {
                    param=(CommonToken)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_rhtype316);  
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
                    // 72:5: -> ^( RHTYPE $param)
                    {
                        // SymfonyAnnotationParser.g:72:8: ^( RHTYPE $param)
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


 

    public static final BitSet FOLLOW_AT_in_annotation82 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_ann_class_in_annotation84 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_PARAM_START_in_annotation87 = new BitSet(new long[]{0x0000000000002800L});
    public static final BitSet FOLLOW_arguments_in_annotation89 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_PARAM_END_in_annotation91 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_namespace_in_ann_class119 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_classname_in_ann_class122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_namespace139 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_BSLASH_in_namespace141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_classname169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_argument_in_arguments194 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_arguments198 = new BitSet(new long[]{0x0000000000002800L});
    public static final BitSet FOLLOW_argument_in_arguments201 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_literal_argument_in_argument215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_named_argument_in_argument219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_literal_argument233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_named_argument261 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ASIG_in_named_argument263 = new BitSet(new long[]{0x0000000000002800L});
    public static final BitSet FOLLOW_rhtype_in_named_argument265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_rhtype295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_rhtype316 = new BitSet(new long[]{0x0000000000000002L});

}