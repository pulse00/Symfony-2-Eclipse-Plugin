// $ANTLR 3.3 Nov 30, 2010 12:45:30 AnnotationParser.g 2011-07-07 12:30:26

package org.eclipse.symfony.core.parser.antlr;

import org.eclipse.symfony.core.parser.antlr.error.IAnnotationErrorReporter;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class AnnotationParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AT", "PARAM_START", "PARAM_END", "ASIG", "COMMA", "BSLASH", "JSON_START", "JSON_END", "STRING_CHAR", "STRING", "NONCONTROL_CHAR", "STRING_LITERAL", "LOWER", "UPPER", "DIGIT", "UNDER", "LETTER", "SYMBOL", "WHITESPACE", "ANNOTATION", "ARGUMENT_LIST", "ARGS", "NAMED_ARG", "LITERAL_ARG", "NSPART", "CLASSNAME", "FQCN", "RHTYPE"
    };
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
    public static final int ANNOTATION=23;
    public static final int ARGUMENT_LIST=24;
    public static final int ARGS=25;
    public static final int NAMED_ARG=26;
    public static final int LITERAL_ARG=27;
    public static final int NSPART=28;
    public static final int CLASSNAME=29;
    public static final int FQCN=30;
    public static final int RHTYPE=31;

    // delegates
    // delegators


        public AnnotationParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public AnnotationParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return AnnotationParser.tokenNames; }
    public String getGrammarFileName() { return "AnnotationParser.g"; }



        private IAnnotationErrorReporter errorReporter = null;
        
        public AnnotationParser(TokenStream input, IAnnotationErrorReporter errorReporter) {
            this(input, new RecognizerSharedState());
            this.errorReporter = errorReporter;
        }

    	public void displayRecognitionError(String[] tokenNames,
                                            RecognitionException e) {
            
        		if(errorReporter != null) {
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
    	


    public static class annotation_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "annotation"
    // AnnotationParser.g:72:1: annotation : AT ann_class argument_list -> ^( ANNOTATION ann_class argument_list ) ;
    public final AnnotationParser.annotation_return annotation() throws RecognitionException {
        AnnotationParser.annotation_return retval = new AnnotationParser.annotation_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken AT1=null;
        AnnotationParser.ann_class_return ann_class2 = null;

        AnnotationParser.argument_list_return argument_list3 = null;


        AnnotationCommonTree AT1_tree=null;
        RewriteRuleTokenStream stream_AT=new RewriteRuleTokenStream(adaptor,"token AT");
        RewriteRuleSubtreeStream stream_argument_list=new RewriteRuleSubtreeStream(adaptor,"rule argument_list");
        RewriteRuleSubtreeStream stream_ann_class=new RewriteRuleSubtreeStream(adaptor,"rule ann_class");
        try {
            // AnnotationParser.g:73:3: ( AT ann_class argument_list -> ^( ANNOTATION ann_class argument_list ) )
            // AnnotationParser.g:73:5: AT ann_class argument_list
            {
            AT1=(CommonToken)match(input,AT,FOLLOW_AT_in_annotation90);  
            stream_AT.add(AT1);

            pushFollow(FOLLOW_ann_class_in_annotation92);
            ann_class2=ann_class();

            state._fsp--;

            stream_ann_class.add(ann_class2.getTree());
            pushFollow(FOLLOW_argument_list_in_annotation94);
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
            // 74:5: -> ^( ANNOTATION ann_class argument_list )
            {
                // AnnotationParser.g:74:7: ^( ANNOTATION ann_class argument_list )
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
    // AnnotationParser.g:78:1: argument_list : ( PARAM_START ( arguments )? PARAM_END )? -> ^( ARGUMENT_LIST ( arguments )? ) ;
    public final AnnotationParser.argument_list_return argument_list() throws RecognitionException {
        AnnotationParser.argument_list_return retval = new AnnotationParser.argument_list_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken PARAM_START4=null;
        CommonToken PARAM_END6=null;
        AnnotationParser.arguments_return arguments5 = null;


        AnnotationCommonTree PARAM_START4_tree=null;
        AnnotationCommonTree PARAM_END6_tree=null;
        RewriteRuleTokenStream stream_PARAM_START=new RewriteRuleTokenStream(adaptor,"token PARAM_START");
        RewriteRuleTokenStream stream_PARAM_END=new RewriteRuleTokenStream(adaptor,"token PARAM_END");
        RewriteRuleSubtreeStream stream_arguments=new RewriteRuleSubtreeStream(adaptor,"rule arguments");
        try {
            // AnnotationParser.g:79:3: ( ( PARAM_START ( arguments )? PARAM_END )? -> ^( ARGUMENT_LIST ( arguments )? ) )
            // AnnotationParser.g:79:5: ( PARAM_START ( arguments )? PARAM_END )?
            {
            // AnnotationParser.g:79:5: ( PARAM_START ( arguments )? PARAM_END )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==PARAM_START) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // AnnotationParser.g:79:6: PARAM_START ( arguments )? PARAM_END
                    {
                    PARAM_START4=(CommonToken)match(input,PARAM_START,FOLLOW_PARAM_START_in_argument_list122);  
                    stream_PARAM_START.add(PARAM_START4);

                    // AnnotationParser.g:79:18: ( arguments )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==JSON_START||LA1_0==STRING||LA1_0==STRING_LITERAL) ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // AnnotationParser.g:79:18: arguments
                            {
                            pushFollow(FOLLOW_arguments_in_argument_list124);
                            arguments5=arguments();

                            state._fsp--;

                            stream_arguments.add(arguments5.getTree());

                            }
                            break;

                    }

                    PARAM_END6=(CommonToken)match(input,PARAM_END,FOLLOW_PARAM_END_in_argument_list127);  
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
            // 80:5: -> ^( ARGUMENT_LIST ( arguments )? )
            {
                // AnnotationParser.g:80:8: ^( ARGUMENT_LIST ( arguments )? )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ARGUMENT_LIST, "ARGUMENT_LIST"), root_1);

                // AnnotationParser.g:80:24: ( arguments )?
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
    // AnnotationParser.g:83:1: ann_class : ( namespace )* classname ;
    public final AnnotationParser.ann_class_return ann_class() throws RecognitionException {
        AnnotationParser.ann_class_return retval = new AnnotationParser.ann_class_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        AnnotationParser.namespace_return namespace7 = null;

        AnnotationParser.classname_return classname8 = null;



        try {
            // AnnotationParser.g:84:3: ( ( namespace )* classname )
            // AnnotationParser.g:84:5: ( namespace )* classname
            {
            root_0 = (AnnotationCommonTree)adaptor.nil();

            // AnnotationParser.g:84:5: ( namespace )*
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
            	    // AnnotationParser.g:84:5: namespace
            	    {
            	    pushFollow(FOLLOW_namespace_in_ann_class155);
            	    namespace7=namespace();

            	    state._fsp--;

            	    adaptor.addChild(root_0, namespace7.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            pushFollow(FOLLOW_classname_in_ann_class158);
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
    // AnnotationParser.g:87:1: namespace : ns= STRING BSLASH -> ^( NSPART $ns) ;
    public final AnnotationParser.namespace_return namespace() throws RecognitionException {
        AnnotationParser.namespace_return retval = new AnnotationParser.namespace_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken ns=null;
        CommonToken BSLASH9=null;

        AnnotationCommonTree ns_tree=null;
        AnnotationCommonTree BSLASH9_tree=null;
        RewriteRuleTokenStream stream_BSLASH=new RewriteRuleTokenStream(adaptor,"token BSLASH");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // AnnotationParser.g:88:3: (ns= STRING BSLASH -> ^( NSPART $ns) )
            // AnnotationParser.g:88:5: ns= STRING BSLASH
            {
            ns=(CommonToken)match(input,STRING,FOLLOW_STRING_in_namespace175);  
            stream_STRING.add(ns);

            BSLASH9=(CommonToken)match(input,BSLASH,FOLLOW_BSLASH_in_namespace177);  
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
            // 89:4: -> ^( NSPART $ns)
            {
                // AnnotationParser.g:89:6: ^( NSPART $ns)
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
    // AnnotationParser.g:92:1: classname : cn= STRING -> ^( CLASSNAME $cn) ;
    public final AnnotationParser.classname_return classname() throws RecognitionException {
        AnnotationParser.classname_return retval = new AnnotationParser.classname_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken cn=null;

        AnnotationCommonTree cn_tree=null;
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // AnnotationParser.g:93:3: (cn= STRING -> ^( CLASSNAME $cn) )
            // AnnotationParser.g:93:5: cn= STRING
            {
            cn=(CommonToken)match(input,STRING,FOLLOW_STRING_in_classname205);  
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
            // 94:5: -> ^( CLASSNAME $cn)
            {
                // AnnotationParser.g:94:7: ^( CLASSNAME $cn)
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
    // AnnotationParser.g:97:1: arguments : argument ( COMMA arguments )? -> argument ( arguments )? ;
    public final AnnotationParser.arguments_return arguments() throws RecognitionException {
        AnnotationParser.arguments_return retval = new AnnotationParser.arguments_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken COMMA11=null;
        AnnotationParser.argument_return argument10 = null;

        AnnotationParser.arguments_return arguments12 = null;


        AnnotationCommonTree COMMA11_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_argument=new RewriteRuleSubtreeStream(adaptor,"rule argument");
        RewriteRuleSubtreeStream stream_arguments=new RewriteRuleSubtreeStream(adaptor,"rule arguments");
        try {
            // AnnotationParser.g:98:3: ( argument ( COMMA arguments )? -> argument ( arguments )? )
            // AnnotationParser.g:98:5: argument ( COMMA arguments )?
            {
            pushFollow(FOLLOW_argument_in_arguments230);
            argument10=argument();

            state._fsp--;

            stream_argument.add(argument10.getTree());
            // AnnotationParser.g:98:15: ( COMMA arguments )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==COMMA) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // AnnotationParser.g:98:16: COMMA arguments
                    {
                    COMMA11=(CommonToken)match(input,COMMA,FOLLOW_COMMA_in_arguments234);  
                    stream_COMMA.add(COMMA11);

                    pushFollow(FOLLOW_arguments_in_arguments236);
                    arguments12=arguments();

                    state._fsp--;

                    stream_arguments.add(arguments12.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: arguments, argument
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 99:5: -> argument ( arguments )?
            {
                adaptor.addChild(root_0, stream_argument.nextTree());
                // AnnotationParser.g:99:17: ( arguments )?
                if ( stream_arguments.hasNext() ) {
                    adaptor.addChild(root_0, stream_arguments.nextTree());

                }
                stream_arguments.reset();

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
    // $ANTLR end "arguments"

    public static class argument_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argument"
    // AnnotationParser.g:102:1: argument : ( literal_argument | named_argument | json );
    public final AnnotationParser.argument_return argument() throws RecognitionException {
        AnnotationParser.argument_return retval = new AnnotationParser.argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        AnnotationParser.literal_argument_return literal_argument13 = null;

        AnnotationParser.named_argument_return named_argument14 = null;

        AnnotationParser.json_return json15 = null;



        try {
            // AnnotationParser.g:103:1: ( literal_argument | named_argument | json )
            int alt5=3;
            switch ( input.LA(1) ) {
            case STRING_LITERAL:
                {
                alt5=1;
                }
                break;
            case STRING:
                {
                alt5=2;
                }
                break;
            case JSON_START:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // AnnotationParser.g:103:3: literal_argument
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_literal_argument_in_argument260);
                    literal_argument13=literal_argument();

                    state._fsp--;

                    adaptor.addChild(root_0, literal_argument13.getTree());

                    }
                    break;
                case 2 :
                    // AnnotationParser.g:103:22: named_argument
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_named_argument_in_argument264);
                    named_argument14=named_argument();

                    state._fsp--;

                    adaptor.addChild(root_0, named_argument14.getTree());

                    }
                    break;
                case 3 :
                    // AnnotationParser.g:103:39: json
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_json_in_argument268);
                    json15=json();

                    state._fsp--;

                    adaptor.addChild(root_0, json15.getTree());

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
    // AnnotationParser.g:107:1: literal_argument : param= STRING_LITERAL -> ^( LITERAL_ARG $param) ;
    public final AnnotationParser.literal_argument_return literal_argument() throws RecognitionException {
        AnnotationParser.literal_argument_return retval = new AnnotationParser.literal_argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken param=null;

        AnnotationCommonTree param_tree=null;
        RewriteRuleTokenStream stream_STRING_LITERAL=new RewriteRuleTokenStream(adaptor,"token STRING_LITERAL");

        try {
            // AnnotationParser.g:108:3: (param= STRING_LITERAL -> ^( LITERAL_ARG $param) )
            // AnnotationParser.g:108:5: param= STRING_LITERAL
            {
            param=(CommonToken)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_literal_argument282);  
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
            // 109:5: -> ^( LITERAL_ARG $param)
            {
                // AnnotationParser.g:109:8: ^( LITERAL_ARG $param)
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
    // AnnotationParser.g:112:1: named_argument : param= STRING ASIG rhtype -> ^( NAMED_ARG $param rhtype ) ;
    public final AnnotationParser.named_argument_return named_argument() throws RecognitionException {
        AnnotationParser.named_argument_return retval = new AnnotationParser.named_argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken param=null;
        CommonToken ASIG16=null;
        AnnotationParser.rhtype_return rhtype17 = null;


        AnnotationCommonTree param_tree=null;
        AnnotationCommonTree ASIG16_tree=null;
        RewriteRuleTokenStream stream_ASIG=new RewriteRuleTokenStream(adaptor,"token ASIG");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_rhtype=new RewriteRuleSubtreeStream(adaptor,"rule rhtype");
        try {
            // AnnotationParser.g:113:3: (param= STRING ASIG rhtype -> ^( NAMED_ARG $param rhtype ) )
            // AnnotationParser.g:113:5: param= STRING ASIG rhtype
            {
            param=(CommonToken)match(input,STRING,FOLLOW_STRING_in_named_argument310);  
            stream_STRING.add(param);

            ASIG16=(CommonToken)match(input,ASIG,FOLLOW_ASIG_in_named_argument312);  
            stream_ASIG.add(ASIG16);

            pushFollow(FOLLOW_rhtype_in_named_argument314);
            rhtype17=rhtype();

            state._fsp--;

            stream_rhtype.add(rhtype17.getTree());


            // AST REWRITE
            // elements: rhtype, param
            // token labels: param
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_param=new RewriteRuleTokenStream(adaptor,"token param",param);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 114:5: -> ^( NAMED_ARG $param rhtype )
            {
                // AnnotationParser.g:114:8: ^( NAMED_ARG $param rhtype )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(NAMED_ARG, "NAMED_ARG"), root_1);

                adaptor.addChild(root_1, stream_param.nextNode());
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

    public static class json_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "json"
    // AnnotationParser.g:117:1: json : JSON_START ( json_arguments )? JSON_END ;
    public final AnnotationParser.json_return json() throws RecognitionException {
        AnnotationParser.json_return retval = new AnnotationParser.json_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken JSON_START18=null;
        CommonToken JSON_END20=null;
        AnnotationParser.json_arguments_return json_arguments19 = null;


        AnnotationCommonTree JSON_START18_tree=null;
        AnnotationCommonTree JSON_END20_tree=null;

        try {
            // AnnotationParser.g:118:3: ( JSON_START ( json_arguments )? JSON_END )
            // AnnotationParser.g:118:5: JSON_START ( json_arguments )? JSON_END
            {
            root_0 = (AnnotationCommonTree)adaptor.nil();

            JSON_START18=(CommonToken)match(input,JSON_START,FOLLOW_JSON_START_in_json342); 
            JSON_START18_tree = (AnnotationCommonTree)adaptor.create(JSON_START18);
            adaptor.addChild(root_0, JSON_START18_tree);

            // AnnotationParser.g:118:16: ( json_arguments )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==STRING_LITERAL) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // AnnotationParser.g:118:16: json_arguments
                    {
                    pushFollow(FOLLOW_json_arguments_in_json344);
                    json_arguments19=json_arguments();

                    state._fsp--;

                    adaptor.addChild(root_0, json_arguments19.getTree());

                    }
                    break;

            }

            JSON_END20=(CommonToken)match(input,JSON_END,FOLLOW_JSON_END_in_json347); 
            JSON_END20_tree = (AnnotationCommonTree)adaptor.create(JSON_END20);
            adaptor.addChild(root_0, JSON_END20_tree);


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
    // $ANTLR end "json"

    public static class json_arguments_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "json_arguments"
    // AnnotationParser.g:121:1: json_arguments : json_argument ( COMMA ( json_argument ) )* ;
    public final AnnotationParser.json_arguments_return json_arguments() throws RecognitionException {
        AnnotationParser.json_arguments_return retval = new AnnotationParser.json_arguments_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken COMMA22=null;
        AnnotationParser.json_argument_return json_argument21 = null;

        AnnotationParser.json_argument_return json_argument23 = null;


        AnnotationCommonTree COMMA22_tree=null;

        try {
            // AnnotationParser.g:122:3: ( json_argument ( COMMA ( json_argument ) )* )
            // AnnotationParser.g:122:5: json_argument ( COMMA ( json_argument ) )*
            {
            root_0 = (AnnotationCommonTree)adaptor.nil();

            pushFollow(FOLLOW_json_argument_in_json_arguments362);
            json_argument21=json_argument();

            state._fsp--;

            adaptor.addChild(root_0, json_argument21.getTree());
            // AnnotationParser.g:122:19: ( COMMA ( json_argument ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==COMMA) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // AnnotationParser.g:122:20: COMMA ( json_argument )
            	    {
            	    COMMA22=(CommonToken)match(input,COMMA,FOLLOW_COMMA_in_json_arguments365); 
            	    COMMA22_tree = (AnnotationCommonTree)adaptor.create(COMMA22);
            	    adaptor.addChild(root_0, COMMA22_tree);

            	    // AnnotationParser.g:122:26: ( json_argument )
            	    // AnnotationParser.g:122:27: json_argument
            	    {
            	    pushFollow(FOLLOW_json_argument_in_json_arguments368);
            	    json_argument23=json_argument();

            	    state._fsp--;

            	    adaptor.addChild(root_0, json_argument23.getTree());

            	    }


            	    }
            	    break;

            	default :
            	    break loop7;
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
    // $ANTLR end "json_arguments"

    public static class json_argument_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "json_argument"
    // AnnotationParser.g:125:1: json_argument : STRING_LITERAL ASIG ( STRING_LITERAL | STRING ) ;
    public final AnnotationParser.json_argument_return json_argument() throws RecognitionException {
        AnnotationParser.json_argument_return retval = new AnnotationParser.json_argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken STRING_LITERAL24=null;
        CommonToken ASIG25=null;
        CommonToken set26=null;

        AnnotationCommonTree STRING_LITERAL24_tree=null;
        AnnotationCommonTree ASIG25_tree=null;
        AnnotationCommonTree set26_tree=null;

        try {
            // AnnotationParser.g:126:3: ( STRING_LITERAL ASIG ( STRING_LITERAL | STRING ) )
            // AnnotationParser.g:126:5: STRING_LITERAL ASIG ( STRING_LITERAL | STRING )
            {
            root_0 = (AnnotationCommonTree)adaptor.nil();

            STRING_LITERAL24=(CommonToken)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_json_argument386); 
            STRING_LITERAL24_tree = (AnnotationCommonTree)adaptor.create(STRING_LITERAL24);
            adaptor.addChild(root_0, STRING_LITERAL24_tree);

            ASIG25=(CommonToken)match(input,ASIG,FOLLOW_ASIG_in_json_argument388); 
            ASIG25_tree = (AnnotationCommonTree)adaptor.create(ASIG25);
            adaptor.addChild(root_0, ASIG25_tree);

            set26=(CommonToken)input.LT(1);
            if ( input.LA(1)==STRING||input.LA(1)==STRING_LITERAL ) {
                input.consume();
                adaptor.addChild(root_0, (AnnotationCommonTree)adaptor.create(set26));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


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
    // $ANTLR end "json_argument"

    public static class rhtype_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rhtype"
    // AnnotationParser.g:129:1: rhtype : (param= STRING -> ^( RHTYPE $param) | param= STRING_LITERAL -> ^( RHTYPE $param) | json );
    public final AnnotationParser.rhtype_return rhtype() throws RecognitionException {
        AnnotationParser.rhtype_return retval = new AnnotationParser.rhtype_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        CommonToken param=null;
        AnnotationParser.json_return json27 = null;


        AnnotationCommonTree param_tree=null;
        RewriteRuleTokenStream stream_STRING_LITERAL=new RewriteRuleTokenStream(adaptor,"token STRING_LITERAL");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // AnnotationParser.g:130:3: (param= STRING -> ^( RHTYPE $param) | param= STRING_LITERAL -> ^( RHTYPE $param) | json )
            int alt8=3;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt8=1;
                }
                break;
            case STRING_LITERAL:
                {
                alt8=2;
                }
                break;
            case JSON_START:
                {
                alt8=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // AnnotationParser.g:130:5: param= STRING
                    {
                    param=(CommonToken)match(input,STRING,FOLLOW_STRING_in_rhtype411);  
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
                    // 131:5: -> ^( RHTYPE $param)
                    {
                        // AnnotationParser.g:131:8: ^( RHTYPE $param)
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
                    // AnnotationParser.g:132:5: param= STRING_LITERAL
                    {
                    param=(CommonToken)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_rhtype432);  
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
                    // 133:5: -> ^( RHTYPE $param)
                    {
                        // AnnotationParser.g:133:8: ^( RHTYPE $param)
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
                case 3 :
                    // AnnotationParser.g:134:5: json
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_json_in_rhtype451);
                    json27=json();

                    state._fsp--;

                    adaptor.addChild(root_0, json27.getTree());

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


 

    public static final BitSet FOLLOW_AT_in_annotation90 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_ann_class_in_annotation92 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_argument_list_in_annotation94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PARAM_START_in_argument_list122 = new BitSet(new long[]{0x000000000000A440L});
    public static final BitSet FOLLOW_arguments_in_argument_list124 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_PARAM_END_in_argument_list127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_namespace_in_ann_class155 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_classname_in_ann_class158 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_namespace175 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_BSLASH_in_namespace177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_classname205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_argument_in_arguments230 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_arguments234 = new BitSet(new long[]{0x000000000000A400L});
    public static final BitSet FOLLOW_arguments_in_arguments236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_argument_in_argument260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_named_argument_in_argument264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_json_in_argument268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_literal_argument282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_named_argument310 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ASIG_in_named_argument312 = new BitSet(new long[]{0x000000000000A400L});
    public static final BitSet FOLLOW_rhtype_in_named_argument314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_JSON_START_in_json342 = new BitSet(new long[]{0x0000000000008800L});
    public static final BitSet FOLLOW_json_arguments_in_json344 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_JSON_END_in_json347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_json_argument_in_json_arguments362 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_json_arguments365 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_json_argument_in_json_arguments368 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_json_argument386 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ASIG_in_json_argument388 = new BitSet(new long[]{0x000000000000A000L});
    public static final BitSet FOLLOW_set_in_json_argument390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_rhtype411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_rhtype432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_json_in_rhtype451 = new BitSet(new long[]{0x0000000000000002L});

}