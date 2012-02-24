// $ANTLR 3.3 Nov 30, 2010 12:45:30 AnnotationParser.g 2012-02-24 15:23:12

package com.dubture.symfony.annotation.parser.antlr;

import com.dubture.symfony.annotation.parser.antlr.error.IAnnotationErrorReporter;
import com.dubture.symfony.annotation.parser.tree.AnnotationCommonTree;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class AnnotationParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LOWER", "UPPER", "DIGIT", "DIGIT_NOZERO", "UNDERSCORE", "QUOTE", "DOUBLE_QUOTE", "ESCAPE_QUOTE", "ESCAPE_DOUBLE_QUOTE", "LETTER", "ALPHANUM", "POSITIVE", "NEGATIVE", "DOT", "WHITESPACE_CHAR", "AT", "PARAM_START", "PARAM_END", "EQUAL", "COMMA", "BSLASH", "CURLY_START", "CURLY_END", "TRUE", "FALSE", "NULL", "IDENTIFIER", "STRING_LITERAL", "INTEGER_LITERAL", "FLOAT_LITERAL", "WHITESPACE", "COMMENT_START", "COMMENT_END", "COMMENT_CHAR", "ANNOTATION", "ANNOTATION_LIST", "ANNOTATION_VALUE", "ARGUMENT", "ARGUMENT_NAME", "ARGUMENT_VALUE", "ARRAY_VALUE", "BOOLEAN_VALUE", "CLASS", "DECLARATION", "NAMED_ARGUMENT", "NAMESPACE", "NAMESPACE_DEFAULT", "NAMESPACE_SEGMENT", "NULL_VALUE", "NUMBER_VALUE", "OBJECT_VALUE", "PAIR", "STRING_VALUE"
    };
    public static final int EOF=-1;
    public static final int LOWER=4;
    public static final int UPPER=5;
    public static final int DIGIT=6;
    public static final int DIGIT_NOZERO=7;
    public static final int UNDERSCORE=8;
    public static final int QUOTE=9;
    public static final int DOUBLE_QUOTE=10;
    public static final int ESCAPE_QUOTE=11;
    public static final int ESCAPE_DOUBLE_QUOTE=12;
    public static final int LETTER=13;
    public static final int ALPHANUM=14;
    public static final int POSITIVE=15;
    public static final int NEGATIVE=16;
    public static final int DOT=17;
    public static final int WHITESPACE_CHAR=18;
    public static final int AT=19;
    public static final int PARAM_START=20;
    public static final int PARAM_END=21;
    public static final int EQUAL=22;
    public static final int COMMA=23;
    public static final int BSLASH=24;
    public static final int CURLY_START=25;
    public static final int CURLY_END=26;
    public static final int TRUE=27;
    public static final int FALSE=28;
    public static final int NULL=29;
    public static final int IDENTIFIER=30;
    public static final int STRING_LITERAL=31;
    public static final int INTEGER_LITERAL=32;
    public static final int FLOAT_LITERAL=33;
    public static final int WHITESPACE=34;
    public static final int COMMENT_START=35;
    public static final int COMMENT_END=36;
    public static final int COMMENT_CHAR=37;
    public static final int ANNOTATION=38;
    public static final int ANNOTATION_LIST=39;
    public static final int ANNOTATION_VALUE=40;
    public static final int ARGUMENT=41;
    public static final int ARGUMENT_NAME=42;
    public static final int ARGUMENT_VALUE=43;
    public static final int ARRAY_VALUE=44;
    public static final int BOOLEAN_VALUE=45;
    public static final int CLASS=46;
    public static final int DECLARATION=47;
    public static final int NAMED_ARGUMENT=48;
    public static final int NAMESPACE=49;
    public static final int NAMESPACE_DEFAULT=50;
    public static final int NAMESPACE_SEGMENT=51;
    public static final int NULL_VALUE=52;
    public static final int NUMBER_VALUE=53;
    public static final int OBJECT_VALUE=54;
    public static final int PAIR=55;
    public static final int STRING_VALUE=56;

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



    public static class annotation_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "annotation"
    // AnnotationParser.g:79:1: annotation : AT class_name ( declaration )? -> ^( ANNOTATION class_name ( declaration )? ) ;
    public final AnnotationParser.annotation_return annotation() throws RecognitionException {
        AnnotationParser.annotation_return retval = new AnnotationParser.annotation_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token AT1=null;
        AnnotationParser.class_name_return class_name2 = null;

        AnnotationParser.declaration_return declaration3 = null;


        AnnotationCommonTree AT1_tree=null;
        RewriteRuleTokenStream stream_AT=new RewriteRuleTokenStream(adaptor,"token AT");
        RewriteRuleSubtreeStream stream_declaration=new RewriteRuleSubtreeStream(adaptor,"rule declaration");
        RewriteRuleSubtreeStream stream_class_name=new RewriteRuleSubtreeStream(adaptor,"rule class_name");
        try {
            // AnnotationParser.g:80:3: ( AT class_name ( declaration )? -> ^( ANNOTATION class_name ( declaration )? ) )
            // AnnotationParser.g:80:5: AT class_name ( declaration )?
            {
            AT1=(Token)match(input,AT,FOLLOW_AT_in_annotation172);  
            stream_AT.add(AT1);

            pushFollow(FOLLOW_class_name_in_annotation174);
            class_name2=class_name();

            state._fsp--;

            stream_class_name.add(class_name2.getTree());
            // AnnotationParser.g:80:19: ( declaration )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==PARAM_START) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // AnnotationParser.g:80:19: declaration
                    {
                    pushFollow(FOLLOW_declaration_in_annotation176);
                    declaration3=declaration();

                    state._fsp--;

                    stream_declaration.add(declaration3.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: class_name, declaration
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 81:7: -> ^( ANNOTATION class_name ( declaration )? )
            {
                // AnnotationParser.g:81:10: ^( ANNOTATION class_name ( declaration )? )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ANNOTATION, "ANNOTATION"), root_1);

                adaptor.addChild(root_1, stream_class_name.nextTree());
                // AnnotationParser.g:81:34: ( declaration )?
                if ( stream_declaration.hasNext() ) {
                    adaptor.addChild(root_1, stream_declaration.nextTree());

                }
                stream_declaration.reset();

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

    public static class class_name_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "class_name"
    // AnnotationParser.g:84:1: class_name : ( namespace name= IDENTIFIER ) -> ^( CLASS namespace $name) ;
    public final AnnotationParser.class_name_return class_name() throws RecognitionException {
        AnnotationParser.class_name_return retval = new AnnotationParser.class_name_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token name=null;
        AnnotationParser.namespace_return namespace4 = null;


        AnnotationCommonTree name_tree=null;
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleSubtreeStream stream_namespace=new RewriteRuleSubtreeStream(adaptor,"rule namespace");
        try {
            // AnnotationParser.g:85:3: ( ( namespace name= IDENTIFIER ) -> ^( CLASS namespace $name) )
            // AnnotationParser.g:85:5: ( namespace name= IDENTIFIER )
            {
            // AnnotationParser.g:85:5: ( namespace name= IDENTIFIER )
            // AnnotationParser.g:85:6: namespace name= IDENTIFIER
            {
            pushFollow(FOLLOW_namespace_in_class_name208);
            namespace4=namespace();

            state._fsp--;

            stream_namespace.add(namespace4.getTree());
            name=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_class_name212);  
            stream_IDENTIFIER.add(name);


            }



            // AST REWRITE
            // elements: namespace, name
            // token labels: name
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 86:7: -> ^( CLASS namespace $name)
            {
                // AnnotationParser.g:86:10: ^( CLASS namespace $name)
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(CLASS, "CLASS"), root_1);

                adaptor.addChild(root_1, stream_namespace.nextTree());
                adaptor.addChild(root_1, stream_name.nextNode());

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
    // $ANTLR end "class_name"

    public static class namespace_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "namespace"
    // AnnotationParser.g:89:1: namespace : (start= BSLASH )? (segments+= IDENTIFIER BSLASH )* -> ^( NAMESPACE ( ^( NAMESPACE_DEFAULT $start) )? ( $segments)* ) ;
    public final AnnotationParser.namespace_return namespace() throws RecognitionException {
        AnnotationParser.namespace_return retval = new AnnotationParser.namespace_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token start=null;
        Token BSLASH5=null;
        Token segments=null;
        List list_segments=null;

        AnnotationCommonTree start_tree=null;
        AnnotationCommonTree BSLASH5_tree=null;
        AnnotationCommonTree segments_tree=null;
        RewriteRuleTokenStream stream_BSLASH=new RewriteRuleTokenStream(adaptor,"token BSLASH");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");

        try {
            // AnnotationParser.g:90:3: ( (start= BSLASH )? (segments+= IDENTIFIER BSLASH )* -> ^( NAMESPACE ( ^( NAMESPACE_DEFAULT $start) )? ( $segments)* ) )
            // AnnotationParser.g:90:5: (start= BSLASH )? (segments+= IDENTIFIER BSLASH )*
            {
            // AnnotationParser.g:90:10: (start= BSLASH )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==BSLASH) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // AnnotationParser.g:90:10: start= BSLASH
                    {
                    start=(Token)match(input,BSLASH,FOLLOW_BSLASH_in_namespace245);  
                    stream_BSLASH.add(start);


                    }
                    break;

            }

            // AnnotationParser.g:90:19: (segments+= IDENTIFIER BSLASH )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==IDENTIFIER) ) {
                    int LA3_1 = input.LA(2);

                    if ( (LA3_1==BSLASH) ) {
                        alt3=1;
                    }


                }


                switch (alt3) {
            	case 1 :
            	    // AnnotationParser.g:90:20: segments+= IDENTIFIER BSLASH
            	    {
            	    segments=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_namespace251);  
            	    stream_IDENTIFIER.add(segments);

            	    if (list_segments==null) list_segments=new ArrayList();
            	    list_segments.add(segments);

            	    BSLASH5=(Token)match(input,BSLASH,FOLLOW_BSLASH_in_namespace253);  
            	    stream_BSLASH.add(BSLASH5);


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);



            // AST REWRITE
            // elements: start, segments
            // token labels: start
            // rule labels: retval
            // token list labels: segments
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_start=new RewriteRuleTokenStream(adaptor,"token start",start);
            RewriteRuleTokenStream stream_segments=new RewriteRuleTokenStream(adaptor,"token segments", list_segments);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 91:7: -> ^( NAMESPACE ( ^( NAMESPACE_DEFAULT $start) )? ( $segments)* )
            {
                // AnnotationParser.g:91:10: ^( NAMESPACE ( ^( NAMESPACE_DEFAULT $start) )? ( $segments)* )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(NAMESPACE, "NAMESPACE"), root_1);

                // AnnotationParser.g:91:22: ( ^( NAMESPACE_DEFAULT $start) )?
                if ( stream_start.hasNext() ) {
                    // AnnotationParser.g:91:22: ^( NAMESPACE_DEFAULT $start)
                    {
                    AnnotationCommonTree root_2 = (AnnotationCommonTree)adaptor.nil();
                    root_2 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(NAMESPACE_DEFAULT, "NAMESPACE_DEFAULT"), root_2);

                    adaptor.addChild(root_2, stream_start.nextNode());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_start.reset();
                // AnnotationParser.g:91:51: ( $segments)*
                while ( stream_segments.hasNext() ) {
                    adaptor.addChild(root_1, stream_segments.nextNode());

                }
                stream_segments.reset();

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

    public static class declaration_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declaration"
    // AnnotationParser.g:94:1: declaration : PARAM_START (statements+= statement )? ( COMMA statements+= statement )* PARAM_END -> ^( DECLARATION ( $statements)* ) ;
    public final AnnotationParser.declaration_return declaration() throws RecognitionException {
        AnnotationParser.declaration_return retval = new AnnotationParser.declaration_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token PARAM_START6=null;
        Token COMMA7=null;
        Token PARAM_END8=null;
        List list_statements=null;
        RuleReturnScope statements = null;
        AnnotationCommonTree PARAM_START6_tree=null;
        AnnotationCommonTree COMMA7_tree=null;
        AnnotationCommonTree PARAM_END8_tree=null;
        RewriteRuleTokenStream stream_PARAM_START=new RewriteRuleTokenStream(adaptor,"token PARAM_START");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_PARAM_END=new RewriteRuleTokenStream(adaptor,"token PARAM_END");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // AnnotationParser.g:95:3: ( PARAM_START (statements+= statement )? ( COMMA statements+= statement )* PARAM_END -> ^( DECLARATION ( $statements)* ) )
            // AnnotationParser.g:95:5: PARAM_START (statements+= statement )? ( COMMA statements+= statement )* PARAM_END
            {
            PARAM_START6=(Token)match(input,PARAM_START,FOLLOW_PARAM_START_in_declaration292);  
            stream_PARAM_START.add(PARAM_START6);

            // AnnotationParser.g:95:27: (statements+= statement )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==AT||LA4_0==CURLY_START||(LA4_0>=TRUE && LA4_0<=FLOAT_LITERAL)) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // AnnotationParser.g:95:27: statements+= statement
                    {
                    pushFollow(FOLLOW_statement_in_declaration296);
                    statements=statement();

                    state._fsp--;

                    stream_statement.add(statements.getTree());
                    if (list_statements==null) list_statements=new ArrayList();
                    list_statements.add(statements.getTree());


                    }
                    break;

            }

            // AnnotationParser.g:95:40: ( COMMA statements+= statement )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==COMMA) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // AnnotationParser.g:95:41: COMMA statements+= statement
            	    {
            	    COMMA7=(Token)match(input,COMMA,FOLLOW_COMMA_in_declaration300);  
            	    stream_COMMA.add(COMMA7);

            	    pushFollow(FOLLOW_statement_in_declaration304);
            	    statements=statement();

            	    state._fsp--;

            	    stream_statement.add(statements.getTree());
            	    if (list_statements==null) list_statements=new ArrayList();
            	    list_statements.add(statements.getTree());


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            PARAM_END8=(Token)match(input,PARAM_END,FOLLOW_PARAM_END_in_declaration308);  
            stream_PARAM_END.add(PARAM_END8);



            // AST REWRITE
            // elements: statements
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: statements
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_statements=new RewriteRuleSubtreeStream(adaptor,"token statements",list_statements);
            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 96:7: -> ^( DECLARATION ( $statements)* )
            {
                // AnnotationParser.g:96:10: ^( DECLARATION ( $statements)* )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(DECLARATION, "DECLARATION"), root_1);

                // AnnotationParser.g:96:24: ( $statements)*
                while ( stream_statements.hasNext() ) {
                    adaptor.addChild(root_1, stream_statements.nextTree());

                }
                stream_statements.reset();

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
    // $ANTLR end "declaration"

    public static class statement_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statement"
    // AnnotationParser.g:99:1: statement : ( argument | namedArgument );
    public final AnnotationParser.statement_return statement() throws RecognitionException {
        AnnotationParser.statement_return retval = new AnnotationParser.statement_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        AnnotationParser.argument_return argument9 = null;

        AnnotationParser.namedArgument_return namedArgument10 = null;



        try {
            // AnnotationParser.g:100:3: ( argument | namedArgument )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==AT||LA6_0==CURLY_START||(LA6_0>=TRUE && LA6_0<=NULL)||(LA6_0>=STRING_LITERAL && LA6_0<=FLOAT_LITERAL)) ) {
                alt6=1;
            }
            else if ( (LA6_0==IDENTIFIER) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // AnnotationParser.g:100:5: argument
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_argument_in_statement337);
                    argument9=argument();

                    state._fsp--;

                    adaptor.addChild(root_0, argument9.getTree());

                    }
                    break;
                case 2 :
                    // AnnotationParser.g:100:16: namedArgument
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_namedArgument_in_statement341);
                    namedArgument10=namedArgument();

                    state._fsp--;

                    adaptor.addChild(root_0, namedArgument10.getTree());

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
    // $ANTLR end "statement"

    public static class argument_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argument"
    // AnnotationParser.g:103:1: argument : argument_value= value -> ^( ARGUMENT $argument_value) ;
    public final AnnotationParser.argument_return argument() throws RecognitionException {
        AnnotationParser.argument_return retval = new AnnotationParser.argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        AnnotationParser.value_return argument_value = null;


        RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value");
        try {
            // AnnotationParser.g:104:3: (argument_value= value -> ^( ARGUMENT $argument_value) )
            // AnnotationParser.g:104:5: argument_value= value
            {
            pushFollow(FOLLOW_value_in_argument356);
            argument_value=value();

            state._fsp--;

            stream_value.add(argument_value.getTree());


            // AST REWRITE
            // elements: argument_value
            // token labels: 
            // rule labels: retval, argument_value
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_argument_value=new RewriteRuleSubtreeStream(adaptor,"rule argument_value",argument_value!=null?argument_value.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 105:7: -> ^( ARGUMENT $argument_value)
            {
                // AnnotationParser.g:105:10: ^( ARGUMENT $argument_value)
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ARGUMENT, "ARGUMENT"), root_1);

                adaptor.addChild(root_1, stream_argument_value.nextTree());

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
    // $ANTLR end "argument"

    public static class namedArgument_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "namedArgument"
    // AnnotationParser.g:108:1: namedArgument : name= argumentName EQUAL argument_value= argumentValue -> ^( NAMED_ARGUMENT $name $argument_value) ;
    public final AnnotationParser.namedArgument_return namedArgument() throws RecognitionException {
        AnnotationParser.namedArgument_return retval = new AnnotationParser.namedArgument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token EQUAL11=null;
        AnnotationParser.argumentName_return name = null;

        AnnotationParser.argumentValue_return argument_value = null;


        AnnotationCommonTree EQUAL11_tree=null;
        RewriteRuleTokenStream stream_EQUAL=new RewriteRuleTokenStream(adaptor,"token EQUAL");
        RewriteRuleSubtreeStream stream_argumentValue=new RewriteRuleSubtreeStream(adaptor,"rule argumentValue");
        RewriteRuleSubtreeStream stream_argumentName=new RewriteRuleSubtreeStream(adaptor,"rule argumentName");
        try {
            // AnnotationParser.g:109:3: (name= argumentName EQUAL argument_value= argumentValue -> ^( NAMED_ARGUMENT $name $argument_value) )
            // AnnotationParser.g:109:5: name= argumentName EQUAL argument_value= argumentValue
            {
            pushFollow(FOLLOW_argumentName_in_namedArgument386);
            name=argumentName();

            state._fsp--;

            stream_argumentName.add(name.getTree());
            EQUAL11=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_namedArgument388);  
            stream_EQUAL.add(EQUAL11);

            pushFollow(FOLLOW_argumentValue_in_namedArgument392);
            argument_value=argumentValue();

            state._fsp--;

            stream_argumentValue.add(argument_value.getTree());


            // AST REWRITE
            // elements: argument_value, name
            // token labels: 
            // rule labels: retval, name, argument_value
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name",name!=null?name.tree:null);
            RewriteRuleSubtreeStream stream_argument_value=new RewriteRuleSubtreeStream(adaptor,"rule argument_value",argument_value!=null?argument_value.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 110:7: -> ^( NAMED_ARGUMENT $name $argument_value)
            {
                // AnnotationParser.g:110:10: ^( NAMED_ARGUMENT $name $argument_value)
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(NAMED_ARGUMENT, "NAMED_ARGUMENT"), root_1);

                adaptor.addChild(root_1, stream_name.nextTree());
                adaptor.addChild(root_1, stream_argument_value.nextTree());

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
    // $ANTLR end "namedArgument"

    public static class argumentName_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argumentName"
    // AnnotationParser.g:113:1: argumentName : name= IDENTIFIER -> ^( ARGUMENT_NAME $name) ;
    public final AnnotationParser.argumentName_return argumentName() throws RecognitionException {
        AnnotationParser.argumentName_return retval = new AnnotationParser.argumentName_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token name=null;

        AnnotationCommonTree name_tree=null;
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");

        try {
            // AnnotationParser.g:114:3: (name= IDENTIFIER -> ^( ARGUMENT_NAME $name) )
            // AnnotationParser.g:114:5: name= IDENTIFIER
            {
            name=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_argumentName425);  
            stream_IDENTIFIER.add(name);



            // AST REWRITE
            // elements: name
            // token labels: name
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 115:7: -> ^( ARGUMENT_NAME $name)
            {
                // AnnotationParser.g:115:10: ^( ARGUMENT_NAME $name)
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ARGUMENT_NAME, "ARGUMENT_NAME"), root_1);

                adaptor.addChild(root_1, stream_name.nextNode());

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
    // $ANTLR end "argumentName"

    public static class argumentValue_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argumentValue"
    // AnnotationParser.g:118:1: argumentValue : value -> ^( ARGUMENT_VALUE value ) ;
    public final AnnotationParser.argumentValue_return argumentValue() throws RecognitionException {
        AnnotationParser.argumentValue_return retval = new AnnotationParser.argumentValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        AnnotationParser.value_return value12 = null;


        RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value");
        try {
            // AnnotationParser.g:119:3: ( value -> ^( ARGUMENT_VALUE value ) )
            // AnnotationParser.g:119:5: value
            {
            pushFollow(FOLLOW_value_in_argumentValue453);
            value12=value();

            state._fsp--;

            stream_value.add(value12.getTree());


            // AST REWRITE
            // elements: value
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 120:7: -> ^( ARGUMENT_VALUE value )
            {
                // AnnotationParser.g:120:10: ^( ARGUMENT_VALUE value )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ARGUMENT_VALUE, "ARGUMENT_VALUE"), root_1);

                adaptor.addChild(root_1, stream_value.nextTree());

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
    // $ANTLR end "argumentValue"

    public static class value_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "value"
    // AnnotationParser.g:123:1: value : ( annotationValue | objectValue | arrayValue | stringValue | numberValue | booleanValue | nullValue );
    public final AnnotationParser.value_return value() throws RecognitionException {
        AnnotationParser.value_return retval = new AnnotationParser.value_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        AnnotationParser.annotationValue_return annotationValue13 = null;

        AnnotationParser.objectValue_return objectValue14 = null;

        AnnotationParser.arrayValue_return arrayValue15 = null;

        AnnotationParser.stringValue_return stringValue16 = null;

        AnnotationParser.numberValue_return numberValue17 = null;

        AnnotationParser.booleanValue_return booleanValue18 = null;

        AnnotationParser.nullValue_return nullValue19 = null;



        try {
            // AnnotationParser.g:124:3: ( annotationValue | objectValue | arrayValue | stringValue | numberValue | booleanValue | nullValue )
            int alt7=7;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // AnnotationParser.g:124:5: annotationValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_annotationValue_in_value480);
                    annotationValue13=annotationValue();

                    state._fsp--;

                    adaptor.addChild(root_0, annotationValue13.getTree());

                    }
                    break;
                case 2 :
                    // AnnotationParser.g:125:5: objectValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_objectValue_in_value486);
                    objectValue14=objectValue();

                    state._fsp--;

                    adaptor.addChild(root_0, objectValue14.getTree());

                    }
                    break;
                case 3 :
                    // AnnotationParser.g:126:5: arrayValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_arrayValue_in_value492);
                    arrayValue15=arrayValue();

                    state._fsp--;

                    adaptor.addChild(root_0, arrayValue15.getTree());

                    }
                    break;
                case 4 :
                    // AnnotationParser.g:127:5: stringValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_stringValue_in_value498);
                    stringValue16=stringValue();

                    state._fsp--;

                    adaptor.addChild(root_0, stringValue16.getTree());

                    }
                    break;
                case 5 :
                    // AnnotationParser.g:128:5: numberValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_numberValue_in_value504);
                    numberValue17=numberValue();

                    state._fsp--;

                    adaptor.addChild(root_0, numberValue17.getTree());

                    }
                    break;
                case 6 :
                    // AnnotationParser.g:129:5: booleanValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_booleanValue_in_value510);
                    booleanValue18=booleanValue();

                    state._fsp--;

                    adaptor.addChild(root_0, booleanValue18.getTree());

                    }
                    break;
                case 7 :
                    // AnnotationParser.g:130:5: nullValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_nullValue_in_value516);
                    nullValue19=nullValue();

                    state._fsp--;

                    adaptor.addChild(root_0, nullValue19.getTree());

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
    // $ANTLR end "value"

    public static class annotationValue_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "annotationValue"
    // AnnotationParser.g:133:1: annotationValue : AT class_name ( declaration )? -> ^( ANNOTATION_VALUE class_name ( declaration )? ) ;
    public final AnnotationParser.annotationValue_return annotationValue() throws RecognitionException {
        AnnotationParser.annotationValue_return retval = new AnnotationParser.annotationValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token AT20=null;
        AnnotationParser.class_name_return class_name21 = null;

        AnnotationParser.declaration_return declaration22 = null;


        AnnotationCommonTree AT20_tree=null;
        RewriteRuleTokenStream stream_AT=new RewriteRuleTokenStream(adaptor,"token AT");
        RewriteRuleSubtreeStream stream_declaration=new RewriteRuleSubtreeStream(adaptor,"rule declaration");
        RewriteRuleSubtreeStream stream_class_name=new RewriteRuleSubtreeStream(adaptor,"rule class_name");
        try {
            // AnnotationParser.g:134:3: ( AT class_name ( declaration )? -> ^( ANNOTATION_VALUE class_name ( declaration )? ) )
            // AnnotationParser.g:135:6: AT class_name ( declaration )?
            {
            AT20=(Token)match(input,AT,FOLLOW_AT_in_annotationValue534);  
            stream_AT.add(AT20);

            pushFollow(FOLLOW_class_name_in_annotationValue536);
            class_name21=class_name();

            state._fsp--;

            stream_class_name.add(class_name21.getTree());
            // AnnotationParser.g:135:20: ( declaration )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==PARAM_START) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // AnnotationParser.g:135:20: declaration
                    {
                    pushFollow(FOLLOW_declaration_in_annotationValue538);
                    declaration22=declaration();

                    state._fsp--;

                    stream_declaration.add(declaration22.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: class_name, declaration
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 136:8: -> ^( ANNOTATION_VALUE class_name ( declaration )? )
            {
                // AnnotationParser.g:136:11: ^( ANNOTATION_VALUE class_name ( declaration )? )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ANNOTATION_VALUE, "ANNOTATION_VALUE"), root_1);

                adaptor.addChild(root_1, stream_class_name.nextTree());
                // AnnotationParser.g:136:41: ( declaration )?
                if ( stream_declaration.hasNext() ) {
                    adaptor.addChild(root_1, stream_declaration.nextTree());

                }
                stream_declaration.reset();

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
    // $ANTLR end "annotationValue"

    public static class objectValue_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "objectValue"
    // AnnotationParser.g:139:1: objectValue : CURLY_START pairs+= pair ( COMMA pairs+= pair )* CURLY_END -> ^( OBJECT_VALUE ( $pairs)+ ) ;
    public final AnnotationParser.objectValue_return objectValue() throws RecognitionException {
        AnnotationParser.objectValue_return retval = new AnnotationParser.objectValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token CURLY_START23=null;
        Token COMMA24=null;
        Token CURLY_END25=null;
        List list_pairs=null;
        RuleReturnScope pairs = null;
        AnnotationCommonTree CURLY_START23_tree=null;
        AnnotationCommonTree COMMA24_tree=null;
        AnnotationCommonTree CURLY_END25_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_CURLY_START=new RewriteRuleTokenStream(adaptor,"token CURLY_START");
        RewriteRuleTokenStream stream_CURLY_END=new RewriteRuleTokenStream(adaptor,"token CURLY_END");
        RewriteRuleSubtreeStream stream_pair=new RewriteRuleSubtreeStream(adaptor,"rule pair");
        try {
            // AnnotationParser.g:140:3: ( CURLY_START pairs+= pair ( COMMA pairs+= pair )* CURLY_END -> ^( OBJECT_VALUE ( $pairs)+ ) )
            // AnnotationParser.g:140:5: CURLY_START pairs+= pair ( COMMA pairs+= pair )* CURLY_END
            {
            CURLY_START23=(Token)match(input,CURLY_START,FOLLOW_CURLY_START_in_objectValue570);  
            stream_CURLY_START.add(CURLY_START23);

            pushFollow(FOLLOW_pair_in_objectValue574);
            pairs=pair();

            state._fsp--;

            stream_pair.add(pairs.getTree());
            if (list_pairs==null) list_pairs=new ArrayList();
            list_pairs.add(pairs.getTree());

            // AnnotationParser.g:140:29: ( COMMA pairs+= pair )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==COMMA) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // AnnotationParser.g:140:30: COMMA pairs+= pair
            	    {
            	    COMMA24=(Token)match(input,COMMA,FOLLOW_COMMA_in_objectValue577);  
            	    stream_COMMA.add(COMMA24);

            	    pushFollow(FOLLOW_pair_in_objectValue581);
            	    pairs=pair();

            	    state._fsp--;

            	    stream_pair.add(pairs.getTree());
            	    if (list_pairs==null) list_pairs=new ArrayList();
            	    list_pairs.add(pairs.getTree());


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            CURLY_END25=(Token)match(input,CURLY_END,FOLLOW_CURLY_END_in_objectValue585);  
            stream_CURLY_END.add(CURLY_END25);



            // AST REWRITE
            // elements: pairs
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: pairs
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_pairs=new RewriteRuleSubtreeStream(adaptor,"token pairs",list_pairs);
            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 141:7: -> ^( OBJECT_VALUE ( $pairs)+ )
            {
                // AnnotationParser.g:141:10: ^( OBJECT_VALUE ( $pairs)+ )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(OBJECT_VALUE, "OBJECT_VALUE"), root_1);

                if ( !(stream_pairs.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_pairs.hasNext() ) {
                    adaptor.addChild(root_1, stream_pairs.nextTree());

                }
                stream_pairs.reset();

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
    // $ANTLR end "objectValue"

    public static class pair_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pair"
    // AnnotationParser.g:144:1: pair : name= stringValue EQUAL value -> ^( PAIR $name value ) ;
    public final AnnotationParser.pair_return pair() throws RecognitionException {
        AnnotationParser.pair_return retval = new AnnotationParser.pair_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token EQUAL26=null;
        AnnotationParser.stringValue_return name = null;

        AnnotationParser.value_return value27 = null;


        AnnotationCommonTree EQUAL26_tree=null;
        RewriteRuleTokenStream stream_EQUAL=new RewriteRuleTokenStream(adaptor,"token EQUAL");
        RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value");
        RewriteRuleSubtreeStream stream_stringValue=new RewriteRuleSubtreeStream(adaptor,"rule stringValue");
        try {
            // AnnotationParser.g:145:3: (name= stringValue EQUAL value -> ^( PAIR $name value ) )
            // AnnotationParser.g:145:5: name= stringValue EQUAL value
            {
            pushFollow(FOLLOW_stringValue_in_pair616);
            name=stringValue();

            state._fsp--;

            stream_stringValue.add(name.getTree());
            EQUAL26=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pair618);  
            stream_EQUAL.add(EQUAL26);

            pushFollow(FOLLOW_value_in_pair620);
            value27=value();

            state._fsp--;

            stream_value.add(value27.getTree());


            // AST REWRITE
            // elements: name, value
            // token labels: 
            // rule labels: retval, name
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name",name!=null?name.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 146:7: -> ^( PAIR $name value )
            {
                // AnnotationParser.g:146:10: ^( PAIR $name value )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(PAIR, "PAIR"), root_1);

                adaptor.addChild(root_1, stream_name.nextTree());
                adaptor.addChild(root_1, stream_value.nextTree());

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
    // $ANTLR end "pair"

    public static class arrayValue_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arrayValue"
    // AnnotationParser.g:149:1: arrayValue : CURLY_START (values+= value )? ( COMMA values+= value )* CURLY_END -> ^( ARRAY_VALUE ( $values)* ) ;
    public final AnnotationParser.arrayValue_return arrayValue() throws RecognitionException {
        AnnotationParser.arrayValue_return retval = new AnnotationParser.arrayValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token CURLY_START28=null;
        Token COMMA29=null;
        Token CURLY_END30=null;
        List list_values=null;
        RuleReturnScope values = null;
        AnnotationCommonTree CURLY_START28_tree=null;
        AnnotationCommonTree COMMA29_tree=null;
        AnnotationCommonTree CURLY_END30_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_CURLY_START=new RewriteRuleTokenStream(adaptor,"token CURLY_START");
        RewriteRuleTokenStream stream_CURLY_END=new RewriteRuleTokenStream(adaptor,"token CURLY_END");
        RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value");
        try {
            // AnnotationParser.g:150:3: ( CURLY_START (values+= value )? ( COMMA values+= value )* CURLY_END -> ^( ARRAY_VALUE ( $values)* ) )
            // AnnotationParser.g:150:5: CURLY_START (values+= value )? ( COMMA values+= value )* CURLY_END
            {
            CURLY_START28=(Token)match(input,CURLY_START,FOLLOW_CURLY_START_in_arrayValue650);  
            stream_CURLY_START.add(CURLY_START28);

            // AnnotationParser.g:150:23: (values+= value )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==AT||LA10_0==CURLY_START||(LA10_0>=TRUE && LA10_0<=NULL)||(LA10_0>=STRING_LITERAL && LA10_0<=FLOAT_LITERAL)) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // AnnotationParser.g:150:23: values+= value
                    {
                    pushFollow(FOLLOW_value_in_arrayValue654);
                    values=value();

                    state._fsp--;

                    stream_value.add(values.getTree());
                    if (list_values==null) list_values=new ArrayList();
                    list_values.add(values.getTree());


                    }
                    break;

            }

            // AnnotationParser.g:150:32: ( COMMA values+= value )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==COMMA) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // AnnotationParser.g:150:33: COMMA values+= value
            	    {
            	    COMMA29=(Token)match(input,COMMA,FOLLOW_COMMA_in_arrayValue658);  
            	    stream_COMMA.add(COMMA29);

            	    pushFollow(FOLLOW_value_in_arrayValue662);
            	    values=value();

            	    state._fsp--;

            	    stream_value.add(values.getTree());
            	    if (list_values==null) list_values=new ArrayList();
            	    list_values.add(values.getTree());


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            CURLY_END30=(Token)match(input,CURLY_END,FOLLOW_CURLY_END_in_arrayValue666);  
            stream_CURLY_END.add(CURLY_END30);



            // AST REWRITE
            // elements: values
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: values
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_values=new RewriteRuleSubtreeStream(adaptor,"token values",list_values);
            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 151:7: -> ^( ARRAY_VALUE ( $values)* )
            {
                // AnnotationParser.g:151:10: ^( ARRAY_VALUE ( $values)* )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ARRAY_VALUE, "ARRAY_VALUE"), root_1);

                // AnnotationParser.g:151:24: ( $values)*
                while ( stream_values.hasNext() ) {
                    adaptor.addChild(root_1, stream_values.nextTree());

                }
                stream_values.reset();

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
    // $ANTLR end "arrayValue"

    public static class stringValue_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stringValue"
    // AnnotationParser.g:154:1: stringValue : string_value= STRING_LITERAL -> ^( STRING_VALUE $string_value) ;
    public final AnnotationParser.stringValue_return stringValue() throws RecognitionException {
        AnnotationParser.stringValue_return retval = new AnnotationParser.stringValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token string_value=null;

        AnnotationCommonTree string_value_tree=null;
        RewriteRuleTokenStream stream_STRING_LITERAL=new RewriteRuleTokenStream(adaptor,"token STRING_LITERAL");

        try {
            // AnnotationParser.g:155:3: (string_value= STRING_LITERAL -> ^( STRING_VALUE $string_value) )
            // AnnotationParser.g:155:5: string_value= STRING_LITERAL
            {
            string_value=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_stringValue697);  
            stream_STRING_LITERAL.add(string_value);



            // AST REWRITE
            // elements: string_value
            // token labels: string_value
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_string_value=new RewriteRuleTokenStream(adaptor,"token string_value",string_value);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 156:7: -> ^( STRING_VALUE $string_value)
            {
                // AnnotationParser.g:156:10: ^( STRING_VALUE $string_value)
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(STRING_VALUE, "STRING_VALUE"), root_1);

                adaptor.addChild(root_1, stream_string_value.nextNode());

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
    // $ANTLR end "stringValue"

    public static class numberValue_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "numberValue"
    // AnnotationParser.g:159:1: numberValue : (interger_value= INTEGER_LITERAL -> ^( NUMBER_VALUE $interger_value) | float_value= FLOAT_LITERAL -> ^( NUMBER_VALUE $float_value) );
    public final AnnotationParser.numberValue_return numberValue() throws RecognitionException {
        AnnotationParser.numberValue_return retval = new AnnotationParser.numberValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token interger_value=null;
        Token float_value=null;

        AnnotationCommonTree interger_value_tree=null;
        AnnotationCommonTree float_value_tree=null;
        RewriteRuleTokenStream stream_FLOAT_LITERAL=new RewriteRuleTokenStream(adaptor,"token FLOAT_LITERAL");
        RewriteRuleTokenStream stream_INTEGER_LITERAL=new RewriteRuleTokenStream(adaptor,"token INTEGER_LITERAL");

        try {
            // AnnotationParser.g:160:3: (interger_value= INTEGER_LITERAL -> ^( NUMBER_VALUE $interger_value) | float_value= FLOAT_LITERAL -> ^( NUMBER_VALUE $float_value) )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==INTEGER_LITERAL) ) {
                alt12=1;
            }
            else if ( (LA12_0==FLOAT_LITERAL) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // AnnotationParser.g:160:5: interger_value= INTEGER_LITERAL
                    {
                    interger_value=(Token)match(input,INTEGER_LITERAL,FOLLOW_INTEGER_LITERAL_in_numberValue727);  
                    stream_INTEGER_LITERAL.add(interger_value);



                    // AST REWRITE
                    // elements: interger_value
                    // token labels: interger_value
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_interger_value=new RewriteRuleTokenStream(adaptor,"token interger_value",interger_value);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (AnnotationCommonTree)adaptor.nil();
                    // 161:7: -> ^( NUMBER_VALUE $interger_value)
                    {
                        // AnnotationParser.g:161:10: ^( NUMBER_VALUE $interger_value)
                        {
                        AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                        root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(NUMBER_VALUE, "NUMBER_VALUE"), root_1);

                        adaptor.addChild(root_1, stream_interger_value.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // AnnotationParser.g:162:5: float_value= FLOAT_LITERAL
                    {
                    float_value=(Token)match(input,FLOAT_LITERAL,FOLLOW_FLOAT_LITERAL_in_numberValue750);  
                    stream_FLOAT_LITERAL.add(float_value);



                    // AST REWRITE
                    // elements: float_value
                    // token labels: float_value
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_float_value=new RewriteRuleTokenStream(adaptor,"token float_value",float_value);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (AnnotationCommonTree)adaptor.nil();
                    // 163:7: -> ^( NUMBER_VALUE $float_value)
                    {
                        // AnnotationParser.g:163:10: ^( NUMBER_VALUE $float_value)
                        {
                        AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                        root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(NUMBER_VALUE, "NUMBER_VALUE"), root_1);

                        adaptor.addChild(root_1, stream_float_value.nextNode());

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
    // $ANTLR end "numberValue"

    public static class booleanValue_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "booleanValue"
    // AnnotationParser.g:166:1: booleanValue : ( TRUE -> ^( BOOLEAN_VALUE TRUE ) | FALSE -> ^( BOOLEAN_VALUE FALSE ) );
    public final AnnotationParser.booleanValue_return booleanValue() throws RecognitionException {
        AnnotationParser.booleanValue_return retval = new AnnotationParser.booleanValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token TRUE31=null;
        Token FALSE32=null;

        AnnotationCommonTree TRUE31_tree=null;
        AnnotationCommonTree FALSE32_tree=null;
        RewriteRuleTokenStream stream_FALSE=new RewriteRuleTokenStream(adaptor,"token FALSE");
        RewriteRuleTokenStream stream_TRUE=new RewriteRuleTokenStream(adaptor,"token TRUE");

        try {
            // AnnotationParser.g:167:3: ( TRUE -> ^( BOOLEAN_VALUE TRUE ) | FALSE -> ^( BOOLEAN_VALUE FALSE ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==TRUE) ) {
                alt13=1;
            }
            else if ( (LA13_0==FALSE) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // AnnotationParser.g:167:5: TRUE
                    {
                    TRUE31=(Token)match(input,TRUE,FOLLOW_TRUE_in_booleanValue778);  
                    stream_TRUE.add(TRUE31);



                    // AST REWRITE
                    // elements: TRUE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (AnnotationCommonTree)adaptor.nil();
                    // 168:7: -> ^( BOOLEAN_VALUE TRUE )
                    {
                        // AnnotationParser.g:168:10: ^( BOOLEAN_VALUE TRUE )
                        {
                        AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                        root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(BOOLEAN_VALUE, "BOOLEAN_VALUE"), root_1);

                        adaptor.addChild(root_1, stream_TRUE.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // AnnotationParser.g:169:5: FALSE
                    {
                    FALSE32=(Token)match(input,FALSE,FOLLOW_FALSE_in_booleanValue798);  
                    stream_FALSE.add(FALSE32);



                    // AST REWRITE
                    // elements: FALSE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (AnnotationCommonTree)adaptor.nil();
                    // 170:7: -> ^( BOOLEAN_VALUE FALSE )
                    {
                        // AnnotationParser.g:170:10: ^( BOOLEAN_VALUE FALSE )
                        {
                        AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                        root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(BOOLEAN_VALUE, "BOOLEAN_VALUE"), root_1);

                        adaptor.addChild(root_1, stream_FALSE.nextNode());

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
    // $ANTLR end "booleanValue"

    public static class nullValue_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "nullValue"
    // AnnotationParser.g:173:1: nullValue : NULL -> ^( NULL_VALUE NULL ) ;
    public final AnnotationParser.nullValue_return nullValue() throws RecognitionException {
        AnnotationParser.nullValue_return retval = new AnnotationParser.nullValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token NULL33=null;

        AnnotationCommonTree NULL33_tree=null;
        RewriteRuleTokenStream stream_NULL=new RewriteRuleTokenStream(adaptor,"token NULL");

        try {
            // AnnotationParser.g:174:3: ( NULL -> ^( NULL_VALUE NULL ) )
            // AnnotationParser.g:174:5: NULL
            {
            NULL33=(Token)match(input,NULL,FOLLOW_NULL_in_nullValue825);  
            stream_NULL.add(NULL33);



            // AST REWRITE
            // elements: NULL
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 175:7: -> ^( NULL_VALUE NULL )
            {
                // AnnotationParser.g:175:10: ^( NULL_VALUE NULL )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(NULL_VALUE, "NULL_VALUE"), root_1);

                adaptor.addChild(root_1, stream_NULL.nextNode());

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
    // $ANTLR end "nullValue"

    // Delegated rules


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\12\uffff";
    static final String DFA7_eofS =
        "\12\uffff";
    static final String DFA7_minS =
        "\1\23\1\uffff\1\23\4\uffff\1\26\2\uffff";
    static final String DFA7_maxS =
        "\1\41\1\uffff\1\41\4\uffff\1\32\2\uffff";
    static final String DFA7_acceptS =
        "\1\uffff\1\1\1\uffff\1\4\1\5\1\6\1\7\1\uffff\1\3\1\2";
    static final String DFA7_specialS =
        "\12\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\1\5\uffff\1\2\1\uffff\2\5\1\6\1\uffff\1\3\2\4",
            "",
            "\1\10\3\uffff\1\10\1\uffff\5\10\1\uffff\1\7\2\10",
            "",
            "",
            "",
            "",
            "\1\11\1\10\2\uffff\1\10",
            "",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "123:1: value : ( annotationValue | objectValue | arrayValue | stringValue | numberValue | booleanValue | nullValue );";
        }
    }
 

    public static final BitSet FOLLOW_AT_in_annotation172 = new BitSet(new long[]{0x0000000041000000L});
    public static final BitSet FOLLOW_class_name_in_annotation174 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_declaration_in_annotation176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_namespace_in_class_name208 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_class_name212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BSLASH_in_namespace245 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_namespace251 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_BSLASH_in_namespace253 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_PARAM_START_in_declaration292 = new BitSet(new long[]{0x00000003FAA80000L});
    public static final BitSet FOLLOW_statement_in_declaration296 = new BitSet(new long[]{0x0000000000A00000L});
    public static final BitSet FOLLOW_COMMA_in_declaration300 = new BitSet(new long[]{0x00000003FA080000L});
    public static final BitSet FOLLOW_statement_in_declaration304 = new BitSet(new long[]{0x0000000000A00000L});
    public static final BitSet FOLLOW_PARAM_END_in_declaration308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_argument_in_statement337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_namedArgument_in_statement341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_value_in_argument356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_argumentName_in_namedArgument386 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_EQUAL_in_namedArgument388 = new BitSet(new long[]{0x00000003BA080000L});
    public static final BitSet FOLLOW_argumentValue_in_namedArgument392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_argumentName425 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_value_in_argumentValue453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotationValue_in_value480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objectValue_in_value486 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayValue_in_value492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringValue_in_value498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numberValue_in_value504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_booleanValue_in_value510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nullValue_in_value516 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_annotationValue534 = new BitSet(new long[]{0x0000000041000000L});
    public static final BitSet FOLLOW_class_name_in_annotationValue536 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_declaration_in_annotationValue538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CURLY_START_in_objectValue570 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_pair_in_objectValue574 = new BitSet(new long[]{0x0000000004800000L});
    public static final BitSet FOLLOW_COMMA_in_objectValue577 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_pair_in_objectValue581 = new BitSet(new long[]{0x0000000004800000L});
    public static final BitSet FOLLOW_CURLY_END_in_objectValue585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringValue_in_pair616 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_EQUAL_in_pair618 = new BitSet(new long[]{0x00000003BA080000L});
    public static final BitSet FOLLOW_value_in_pair620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CURLY_START_in_arrayValue650 = new BitSet(new long[]{0x00000003BE880000L});
    public static final BitSet FOLLOW_value_in_arrayValue654 = new BitSet(new long[]{0x0000000004800000L});
    public static final BitSet FOLLOW_COMMA_in_arrayValue658 = new BitSet(new long[]{0x00000003BA080000L});
    public static final BitSet FOLLOW_value_in_arrayValue662 = new BitSet(new long[]{0x0000000004800000L});
    public static final BitSet FOLLOW_CURLY_END_in_arrayValue666 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_stringValue697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_LITERAL_in_numberValue727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_LITERAL_in_numberValue750 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_booleanValue778 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_booleanValue798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_nullValue825 = new BitSet(new long[]{0x0000000000000002L});

}