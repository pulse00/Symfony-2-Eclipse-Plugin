// $ANTLR 3.3 Nov 30, 2010 12:45:30 SymfonyAnnotationParser.g 2011-06-10 11:08:31

package org.eclipse.symfony.core.parser.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class SymfonyAnnotationParser extends AbstractAnnotationParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AT", "PARAM_START", "PARAM_END", "ASIG", "COMMA", "BSLASH", "STRING_CHAR", "STRING", "NONCONTROL_CHAR", "STRING_LITERAL", "LOWER", "UPPER", "DIGIT", "UNDER", "LETTER", "SYMBOL", "WHITESPACE", "ANNOTATION", "ARGUMENT_LIST", "NAMED_ARG", "LITERAL_ARG", "NSPART", "CLASSNAME", "FQCN", "RHTYPE"
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
    public static final int ARGUMENT_LIST=22;
    public static final int NAMED_ARG=23;
    public static final int LITERAL_ARG=24;
    public static final int NSPART=25;
    public static final int CLASSNAME=26;
    public static final int FQCN=27;
    public static final int RHTYPE=28;

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
    // SymfonyAnnotationParser.g:30:1: annotation : AT ann_class argument_list -> ^( ANNOTATION ann_class argument_list ) ;
    public final SymfonyAnnotationParser.annotation_return annotation() throws RecognitionException {
        SymfonyAnnotationParser.annotation_return retval = new SymfonyAnnotationParser.annotation_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken AT1=null;
        SymfonyAnnotationParser.ann_class_return ann_class2 = null;

        SymfonyAnnotationParser.argument_list_return argument_list3 = null;


        AnnotationCommonTree AT1_tree=null;
        RewriteRuleTokenStream stream_AT=new RewriteRuleTokenStream(adaptor,"token AT");
        RewriteRuleSubtreeStream stream_argument_list=new RewriteRuleSubtreeStream(adaptor,"rule argument_list");
        RewriteRuleSubtreeStream stream_ann_class=new RewriteRuleSubtreeStream(adaptor,"rule ann_class");
        try {
            // SymfonyAnnotationParser.g:31:3: ( AT ann_class argument_list -> ^( ANNOTATION ann_class argument_list ) )
            // SymfonyAnnotationParser.g:31:5: AT ann_class argument_list
            {
            AT1=(CommonToken)match(input,AT,FOLLOW_AT_in_annotation85);  
            stream_AT.add(AT1);

            pushFollow(FOLLOW_ann_class_in_annotation87);
            ann_class2=ann_class();

            state._fsp--;

            stream_ann_class.add(ann_class2.getTree());
            pushFollow(FOLLOW_argument_list_in_annotation89);
            argument_list3=argument_list();

            state._fsp--;

            stream_argument_list.add(argument_list3.getTree());


            // AST REWRITE
            // elements: argument_list, ann_class
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 32:5: -> ^( ANNOTATION ann_class argument_list )
            {
                // SymfonyAnnotationParser.g:32:7: ^( ANNOTATION ann_class argument_list )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ANNOTATION, "ANNOTATION"), root_1);

                adaptor.addChild(root_1, stream_ann_class.nextTree());
                adaptor.addChild(root_1, stream_argument_list.nextTree());

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

    public static class argument_list_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argument_list"
    // SymfonyAnnotationParser.g:36:1: argument_list : ( PARAM_START ( arguments )? PARAM_END )? -> ^( ARGUMENT_LIST ( arguments )? ) ;
    public final SymfonyAnnotationParser.argument_list_return argument_list() throws RecognitionException {
        SymfonyAnnotationParser.argument_list_return retval = new SymfonyAnnotationParser.argument_list_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken PARAM_START4=null;
        CommonToken PARAM_END6=null;
        SymfonyAnnotationParser.arguments_return arguments5 = null;


        AnnotationCommonTree PARAM_START4_tree=null;
        AnnotationCommonTree PARAM_END6_tree=null;
        RewriteRuleTokenStream stream_PARAM_START=new RewriteRuleTokenStream(adaptor,"token PARAM_START");
        RewriteRuleTokenStream stream_PARAM_END=new RewriteRuleTokenStream(adaptor,"token PARAM_END");
        RewriteRuleSubtreeStream stream_arguments=new RewriteRuleSubtreeStream(adaptor,"rule arguments");
        try {
            // SymfonyAnnotationParser.g:37:3: ( ( PARAM_START ( arguments )? PARAM_END )? -> ^( ARGUMENT_LIST ( arguments )? ) )
            // SymfonyAnnotationParser.g:37:5: ( PARAM_START ( arguments )? PARAM_END )?
            {
            // SymfonyAnnotationParser.g:37:5: ( PARAM_START ( arguments )? PARAM_END )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==PARAM_START) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // SymfonyAnnotationParser.g:37:6: PARAM_START ( arguments )? PARAM_END
                    {
                    PARAM_START4=(CommonToken)match(input,PARAM_START,FOLLOW_PARAM_START_in_argument_list117);  
                    stream_PARAM_START.add(PARAM_START4);

                    // SymfonyAnnotationParser.g:37:18: ( arguments )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==STRING||LA1_0==STRING_LITERAL) ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // SymfonyAnnotationParser.g:37:18: arguments
                            {
                            pushFollow(FOLLOW_arguments_in_argument_list119);
                            arguments5=arguments();

                            state._fsp--;

                            stream_arguments.add(arguments5.getTree());

                            }
                            break;

                    }

                    PARAM_END6=(CommonToken)match(input,PARAM_END,FOLLOW_PARAM_END_in_argument_list122);  
                    stream_PARAM_END.add(PARAM_END6);


                    }
                    break;

            }



            // AST REWRITE
            // elements: arguments
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 38:5: -> ^( ARGUMENT_LIST ( arguments )? )
            {
                // SymfonyAnnotationParser.g:38:8: ^( ARGUMENT_LIST ( arguments )? )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ARGUMENT_LIST, "ARGUMENT_LIST"), root_1);

                // SymfonyAnnotationParser.g:38:24: ( arguments )?
                if ( stream_arguments.hasNext() ) {
                    adaptor.addChild(root_1, stream_arguments.nextTree());

                }
                stream_arguments.reset();

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
    // $ANTLR end "argument_list"

    public static class ann_class_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ann_class"
    // SymfonyAnnotationParser.g:41:1: ann_class : ( namespace )* classname ;
    public final SymfonyAnnotationParser.ann_class_return ann_class() throws RecognitionException {
        SymfonyAnnotationParser.ann_class_return retval = new SymfonyAnnotationParser.ann_class_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        SymfonyAnnotationParser.namespace_return namespace7 = null;

        SymfonyAnnotationParser.classname_return classname8 = null;



        try {
            // SymfonyAnnotationParser.g:42:3: ( ( namespace )* classname )
            // SymfonyAnnotationParser.g:42:5: ( namespace )* classname
            {
            root_0 = (AnnotationCommonTree)adaptor.nil();

            // SymfonyAnnotationParser.g:42:5: ( namespace )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==STRING) ) {
                    int LA3_1 = input.LA(2);

                    if ( (LA3_1==BSLASH) ) {
                        alt3=1;
                    }


                }


                switch (alt3) {
            	case 1 :
            	    // SymfonyAnnotationParser.g:42:5: namespace
            	    {
            	    pushFollow(FOLLOW_namespace_in_ann_class150);
            	    namespace7=namespace();

            	    state._fsp--;

            	    adaptor.addChild(root_0, namespace7.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            pushFollow(FOLLOW_classname_in_ann_class153);
            classname8=classname();

            state._fsp--;

            adaptor.addChild(root_0, classname8.getTree());

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
    // SymfonyAnnotationParser.g:45:1: namespace : ns= STRING BSLASH -> ^( NSPART $ns) ;
    public final SymfonyAnnotationParser.namespace_return namespace() throws RecognitionException {
        SymfonyAnnotationParser.namespace_return retval = new SymfonyAnnotationParser.namespace_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken ns=null;
        CommonToken BSLASH9=null;

        AnnotationCommonTree ns_tree=null;
        AnnotationCommonTree BSLASH9_tree=null;
        RewriteRuleTokenStream stream_BSLASH=new RewriteRuleTokenStream(adaptor,"token BSLASH");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // SymfonyAnnotationParser.g:46:3: (ns= STRING BSLASH -> ^( NSPART $ns) )
            // SymfonyAnnotationParser.g:46:5: ns= STRING BSLASH
            {
            ns=(CommonToken)match(input,STRING,FOLLOW_STRING_in_namespace170);  
            stream_STRING.add(ns);

            BSLASH9=(CommonToken)match(input,BSLASH,FOLLOW_BSLASH_in_namespace172);  
            stream_BSLASH.add(BSLASH9);



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
            // 47:4: -> ^( NSPART $ns)
            {
                // SymfonyAnnotationParser.g:47:6: ^( NSPART $ns)
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
    // SymfonyAnnotationParser.g:50:1: classname : cn= STRING -> ^( CLASSNAME $cn) ;
    public final SymfonyAnnotationParser.classname_return classname() throws RecognitionException {
        SymfonyAnnotationParser.classname_return retval = new SymfonyAnnotationParser.classname_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken cn=null;

        AnnotationCommonTree cn_tree=null;
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // SymfonyAnnotationParser.g:51:3: (cn= STRING -> ^( CLASSNAME $cn) )
            // SymfonyAnnotationParser.g:51:5: cn= STRING
            {
            cn=(CommonToken)match(input,STRING,FOLLOW_STRING_in_classname200);  
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
            // 52:5: -> ^( CLASSNAME $cn)
            {
                // SymfonyAnnotationParser.g:52:7: ^( CLASSNAME $cn)
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
    // SymfonyAnnotationParser.g:55:1: arguments : argument ( COMMA ( argument ) )* ;
    public final SymfonyAnnotationParser.arguments_return arguments() throws RecognitionException {
        SymfonyAnnotationParser.arguments_return retval = new SymfonyAnnotationParser.arguments_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken COMMA11=null;
        SymfonyAnnotationParser.argument_return argument10 = null;

        SymfonyAnnotationParser.argument_return argument12 = null;


        AnnotationCommonTree COMMA11_tree=null;

        try {
            // SymfonyAnnotationParser.g:56:3: ( argument ( COMMA ( argument ) )* )
            // SymfonyAnnotationParser.g:56:5: argument ( COMMA ( argument ) )*
            {
            root_0 = (AnnotationCommonTree)adaptor.nil();

            pushFollow(FOLLOW_argument_in_arguments225);
            argument10=argument();

            state._fsp--;

            adaptor.addChild(root_0, argument10.getTree());
            // SymfonyAnnotationParser.g:56:15: ( COMMA ( argument ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==COMMA) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // SymfonyAnnotationParser.g:56:16: COMMA ( argument )
            	    {
            	    COMMA11=(CommonToken)match(input,COMMA,FOLLOW_COMMA_in_arguments229); 
            	    COMMA11_tree = (AnnotationCommonTree)adaptor.create(COMMA11);
            	    adaptor.addChild(root_0, COMMA11_tree);

            	    // SymfonyAnnotationParser.g:56:22: ( argument )
            	    // SymfonyAnnotationParser.g:56:23: argument
            	    {
            	    pushFollow(FOLLOW_argument_in_arguments232);
            	    argument12=argument();

            	    state._fsp--;

            	    adaptor.addChild(root_0, argument12.getTree());

            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
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
    // SymfonyAnnotationParser.g:59:1: argument : ( literal_argument | named_argument );
    public final SymfonyAnnotationParser.argument_return argument() throws RecognitionException {
        SymfonyAnnotationParser.argument_return retval = new SymfonyAnnotationParser.argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        SymfonyAnnotationParser.literal_argument_return literal_argument13 = null;

        SymfonyAnnotationParser.named_argument_return named_argument14 = null;



        try {
            // SymfonyAnnotationParser.g:60:1: ( literal_argument | named_argument )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==STRING_LITERAL) ) {
                alt5=1;
            }
            else if ( (LA5_0==STRING) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // SymfonyAnnotationParser.g:60:3: literal_argument
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_literal_argument_in_argument246);
                    literal_argument13=literal_argument();

                    state._fsp--;

                    adaptor.addChild(root_0, literal_argument13.getTree());

                    }
                    break;
                case 2 :
                    // SymfonyAnnotationParser.g:60:22: named_argument
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_named_argument_in_argument250);
                    named_argument14=named_argument();

                    state._fsp--;

                    adaptor.addChild(root_0, named_argument14.getTree());

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
    // SymfonyAnnotationParser.g:64:1: literal_argument : param= STRING_LITERAL -> ^( LITERAL_ARG $param) ;
    public final SymfonyAnnotationParser.literal_argument_return literal_argument() throws RecognitionException {
        SymfonyAnnotationParser.literal_argument_return retval = new SymfonyAnnotationParser.literal_argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken param=null;

        AnnotationCommonTree param_tree=null;
        RewriteRuleTokenStream stream_STRING_LITERAL=new RewriteRuleTokenStream(adaptor,"token STRING_LITERAL");

        try {
            // SymfonyAnnotationParser.g:65:3: (param= STRING_LITERAL -> ^( LITERAL_ARG $param) )
            // SymfonyAnnotationParser.g:65:5: param= STRING_LITERAL
            {
            param=(CommonToken)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_literal_argument264);  
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
            // 66:5: -> ^( LITERAL_ARG $param)
            {
                // SymfonyAnnotationParser.g:66:8: ^( LITERAL_ARG $param)
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
    // SymfonyAnnotationParser.g:69:1: named_argument : lht= STRING ASIG rhtype -> ^( NAMED_ARG $lht rhtype ) ;
    public final SymfonyAnnotationParser.named_argument_return named_argument() throws RecognitionException {
        SymfonyAnnotationParser.named_argument_return retval = new SymfonyAnnotationParser.named_argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken lht=null;
        CommonToken ASIG15=null;
        SymfonyAnnotationParser.rhtype_return rhtype16 = null;


        AnnotationCommonTree lht_tree=null;
        AnnotationCommonTree ASIG15_tree=null;
        RewriteRuleTokenStream stream_ASIG=new RewriteRuleTokenStream(adaptor,"token ASIG");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_rhtype=new RewriteRuleSubtreeStream(adaptor,"rule rhtype");
        try {
            // SymfonyAnnotationParser.g:70:3: (lht= STRING ASIG rhtype -> ^( NAMED_ARG $lht rhtype ) )
            // SymfonyAnnotationParser.g:70:5: lht= STRING ASIG rhtype
            {
            lht=(CommonToken)match(input,STRING,FOLLOW_STRING_in_named_argument292);  
            stream_STRING.add(lht);

            ASIG15=(CommonToken)match(input,ASIG,FOLLOW_ASIG_in_named_argument294);  
            stream_ASIG.add(ASIG15);

            pushFollow(FOLLOW_rhtype_in_named_argument296);
            rhtype16=rhtype();

            state._fsp--;

            stream_rhtype.add(rhtype16.getTree());


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
            // 71:5: -> ^( NAMED_ARG $lht rhtype )
            {
                // SymfonyAnnotationParser.g:71:8: ^( NAMED_ARG $lht rhtype )
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
    // SymfonyAnnotationParser.g:74:1: rhtype : (param= STRING -> ^( RHTYPE $param) | param= STRING_LITERAL -> ^( RHTYPE $param) );
    public final SymfonyAnnotationParser.rhtype_return rhtype() throws RecognitionException {
        SymfonyAnnotationParser.rhtype_return retval = new SymfonyAnnotationParser.rhtype_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken param=null;

        AnnotationCommonTree param_tree=null;
        RewriteRuleTokenStream stream_STRING_LITERAL=new RewriteRuleTokenStream(adaptor,"token STRING_LITERAL");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // SymfonyAnnotationParser.g:75:3: (param= STRING -> ^( RHTYPE $param) | param= STRING_LITERAL -> ^( RHTYPE $param) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==STRING) ) {
                alt6=1;
            }
            else if ( (LA6_0==STRING_LITERAL) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // SymfonyAnnotationParser.g:75:5: param= STRING
                    {
                    param=(CommonToken)match(input,STRING,FOLLOW_STRING_in_rhtype326);  
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
                    // 76:5: -> ^( RHTYPE $param)
                    {
                        // SymfonyAnnotationParser.g:76:8: ^( RHTYPE $param)
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
                    // SymfonyAnnotationParser.g:77:5: param= STRING_LITERAL
                    {
                    param=(CommonToken)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_rhtype347);  
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
                    // 78:5: -> ^( RHTYPE $param)
                    {
                        // SymfonyAnnotationParser.g:78:8: ^( RHTYPE $param)
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


 

    public static final BitSet FOLLOW_AT_in_annotation85 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_ann_class_in_annotation87 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_argument_list_in_annotation89 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PARAM_START_in_argument_list117 = new BitSet(new long[]{0x0000000000002840L});
    public static final BitSet FOLLOW_arguments_in_argument_list119 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_PARAM_END_in_argument_list122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_namespace_in_ann_class150 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_classname_in_ann_class153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_namespace170 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_BSLASH_in_namespace172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_classname200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_argument_in_arguments225 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_arguments229 = new BitSet(new long[]{0x0000000000002800L});
    public static final BitSet FOLLOW_argument_in_arguments232 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_literal_argument_in_argument246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_named_argument_in_argument250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_literal_argument264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_named_argument292 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ASIG_in_named_argument294 = new BitSet(new long[]{0x0000000000002800L});
    public static final BitSet FOLLOW_rhtype_in_named_argument296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_rhtype326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_rhtype347 = new BitSet(new long[]{0x0000000000000002L});

}