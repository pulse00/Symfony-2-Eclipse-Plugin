// $ANTLR 3.3 Nov 30, 2010 12:45:30 AnnotationParser.g 2012-02-23 09:34:56

package com.dubture.symfony.annotation.parser.antlr;

import com.dubture.symfony.annotation.parser.antlr.error.IAnnotationErrorReporter;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class AnnotationParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LOWER", "UPPER", "DIGIT", "UNDERSCORE", "QUOTE", "DOUBLE_QUOTE", "ESCAPE_QUOTE", "ESCAPE_DOUBLE_QUOTE", "LETTER", "ALPHANUM", "AT", "PARAM_START", "PARAM_END", "EQUAL", "COMMA", "BSLASH", "CURLY_START", "CURLY_END", "TRUE", "FALSE", "NULL", "IDENTIFIER", "STRING_LITERAL", "WHITESPACE", "COMMENT_CHAR", "ANNOTATION", "ARGUMENT", "ARGUMENT_NAME", "ARGUMENT_VALUE", "BOOLEAN_VALUE", "CLASS", "DECLARATION", "LITERAL", "NAMESPACE", "NAMESPACE_DEFAULT", "NAMESPACE_SEGMENT", "NULL_VALUE", "OBJECT_VALUE", "PAIR", "STRING_VALUE"
    };
    public static final int EOF=-1;
    public static final int LOWER=4;
    public static final int UPPER=5;
    public static final int DIGIT=6;
    public static final int UNDERSCORE=7;
    public static final int QUOTE=8;
    public static final int DOUBLE_QUOTE=9;
    public static final int ESCAPE_QUOTE=10;
    public static final int ESCAPE_DOUBLE_QUOTE=11;
    public static final int LETTER=12;
    public static final int ALPHANUM=13;
    public static final int AT=14;
    public static final int PARAM_START=15;
    public static final int PARAM_END=16;
    public static final int EQUAL=17;
    public static final int COMMA=18;
    public static final int BSLASH=19;
    public static final int CURLY_START=20;
    public static final int CURLY_END=21;
    public static final int TRUE=22;
    public static final int FALSE=23;
    public static final int NULL=24;
    public static final int IDENTIFIER=25;
    public static final int STRING_LITERAL=26;
    public static final int WHITESPACE=27;
    public static final int COMMENT_CHAR=28;
    public static final int ANNOTATION=29;
    public static final int ARGUMENT=30;
    public static final int ARGUMENT_NAME=31;
    public static final int ARGUMENT_VALUE=32;
    public static final int BOOLEAN_VALUE=33;
    public static final int CLASS=34;
    public static final int DECLARATION=35;
    public static final int LITERAL=36;
    public static final int NAMESPACE=37;
    public static final int NAMESPACE_DEFAULT=38;
    public static final int NAMESPACE_SEGMENT=39;
    public static final int NULL_VALUE=40;
    public static final int OBJECT_VALUE=41;
    public static final int PAIR=42;
    public static final int STRING_VALUE=43;

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
    // AnnotationParser.g:69:1: annotation : AT class_name ( declaration )? -> ^( ANNOTATION class_name ( declaration )? ) ;
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
            // AnnotationParser.g:70:3: ( AT class_name ( declaration )? -> ^( ANNOTATION class_name ( declaration )? ) )
            // AnnotationParser.g:71:6: AT class_name ( declaration )?
            {
            AT1=(Token)match(input,AT,FOLLOW_AT_in_annotation150);  
            stream_AT.add(AT1);

            pushFollow(FOLLOW_class_name_in_annotation152);
            class_name2=class_name();

            state._fsp--;

            stream_class_name.add(class_name2.getTree());
            // AnnotationParser.g:71:20: ( declaration )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==PARAM_START) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // AnnotationParser.g:71:20: declaration
                    {
                    pushFollow(FOLLOW_declaration_in_annotation154);
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
            // 72:7: -> ^( ANNOTATION class_name ( declaration )? )
            {
                // AnnotationParser.g:72:10: ^( ANNOTATION class_name ( declaration )? )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ANNOTATION, "ANNOTATION"), root_1);

                adaptor.addChild(root_1, stream_class_name.nextTree());
                // AnnotationParser.g:72:34: ( declaration )?
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
    // AnnotationParser.g:75:1: class_name : ( namespace name= IDENTIFIER ) -> ^( CLASS namespace $name) ;
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
            // AnnotationParser.g:76:3: ( ( namespace name= IDENTIFIER ) -> ^( CLASS namespace $name) )
            // AnnotationParser.g:76:5: ( namespace name= IDENTIFIER )
            {
            // AnnotationParser.g:76:5: ( namespace name= IDENTIFIER )
            // AnnotationParser.g:76:6: namespace name= IDENTIFIER
            {
            pushFollow(FOLLOW_namespace_in_class_name186);
            namespace4=namespace();

            state._fsp--;

            stream_namespace.add(namespace4.getTree());
            name=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_class_name190);  
            stream_IDENTIFIER.add(name);


            }



            // AST REWRITE
            // elements: name, namespace
            // token labels: name
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 77:7: -> ^( CLASS namespace $name)
            {
                // AnnotationParser.g:77:10: ^( CLASS namespace $name)
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
    // AnnotationParser.g:80:1: namespace : (start= BSLASH )? (segments+= IDENTIFIER BSLASH )* -> ^( NAMESPACE ( ^( NAMESPACE_DEFAULT $start) )? ( $segments)* ) ;
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
            // AnnotationParser.g:81:3: ( (start= BSLASH )? (segments+= IDENTIFIER BSLASH )* -> ^( NAMESPACE ( ^( NAMESPACE_DEFAULT $start) )? ( $segments)* ) )
            // AnnotationParser.g:81:5: (start= BSLASH )? (segments+= IDENTIFIER BSLASH )*
            {
            // AnnotationParser.g:81:10: (start= BSLASH )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==BSLASH) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // AnnotationParser.g:81:10: start= BSLASH
                    {
                    start=(Token)match(input,BSLASH,FOLLOW_BSLASH_in_namespace223);  
                    stream_BSLASH.add(start);


                    }
                    break;

            }

            // AnnotationParser.g:81:19: (segments+= IDENTIFIER BSLASH )*
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
            	    // AnnotationParser.g:81:20: segments+= IDENTIFIER BSLASH
            	    {
            	    segments=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_namespace229);  
            	    stream_IDENTIFIER.add(segments);

            	    if (list_segments==null) list_segments=new ArrayList();
            	    list_segments.add(segments);

            	    BSLASH5=(Token)match(input,BSLASH,FOLLOW_BSLASH_in_namespace231);  
            	    stream_BSLASH.add(BSLASH5);


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);



            // AST REWRITE
            // elements: segments, start
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
            // 82:7: -> ^( NAMESPACE ( ^( NAMESPACE_DEFAULT $start) )? ( $segments)* )
            {
                // AnnotationParser.g:82:10: ^( NAMESPACE ( ^( NAMESPACE_DEFAULT $start) )? ( $segments)* )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(NAMESPACE, "NAMESPACE"), root_1);

                // AnnotationParser.g:82:22: ( ^( NAMESPACE_DEFAULT $start) )?
                if ( stream_start.hasNext() ) {
                    // AnnotationParser.g:82:22: ^( NAMESPACE_DEFAULT $start)
                    {
                    AnnotationCommonTree root_2 = (AnnotationCommonTree)adaptor.nil();
                    root_2 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(NAMESPACE_DEFAULT, "NAMESPACE_DEFAULT"), root_2);

                    adaptor.addChild(root_2, stream_start.nextNode());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_start.reset();
                // AnnotationParser.g:82:51: ( $segments)*
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
    // AnnotationParser.g:85:1: declaration : PARAM_START (statements+= statement )? ( COMMA statements+= statement )* PARAM_END -> ^( DECLARATION ( $statements)* ) ;
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
            // AnnotationParser.g:86:3: ( PARAM_START (statements+= statement )? ( COMMA statements+= statement )* PARAM_END -> ^( DECLARATION ( $statements)* ) )
            // AnnotationParser.g:86:5: PARAM_START (statements+= statement )? ( COMMA statements+= statement )* PARAM_END
            {
            PARAM_START6=(Token)match(input,PARAM_START,FOLLOW_PARAM_START_in_declaration270);  
            stream_PARAM_START.add(PARAM_START6);

            // AnnotationParser.g:86:27: (statements+= statement )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0>=IDENTIFIER && LA4_0<=STRING_LITERAL)) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // AnnotationParser.g:86:27: statements+= statement
                    {
                    pushFollow(FOLLOW_statement_in_declaration274);
                    statements=statement();

                    state._fsp--;

                    stream_statement.add(statements.getTree());
                    if (list_statements==null) list_statements=new ArrayList();
                    list_statements.add(statements.getTree());


                    }
                    break;

            }

            // AnnotationParser.g:86:40: ( COMMA statements+= statement )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==COMMA) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // AnnotationParser.g:86:41: COMMA statements+= statement
            	    {
            	    COMMA7=(Token)match(input,COMMA,FOLLOW_COMMA_in_declaration278);  
            	    stream_COMMA.add(COMMA7);

            	    pushFollow(FOLLOW_statement_in_declaration282);
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

            PARAM_END8=(Token)match(input,PARAM_END,FOLLOW_PARAM_END_in_declaration286);  
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
            // 87:7: -> ^( DECLARATION ( $statements)* )
            {
                // AnnotationParser.g:87:10: ^( DECLARATION ( $statements)* )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(DECLARATION, "DECLARATION"), root_1);

                // AnnotationParser.g:87:24: ( $statements)*
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
    // AnnotationParser.g:90:1: statement : ( literal | argument );
    public final AnnotationParser.statement_return statement() throws RecognitionException {
        AnnotationParser.statement_return retval = new AnnotationParser.statement_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        AnnotationParser.literal_return literal9 = null;

        AnnotationParser.argument_return argument10 = null;



        try {
            // AnnotationParser.g:91:3: ( literal | argument )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==STRING_LITERAL) ) {
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
                    // AnnotationParser.g:91:5: literal
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_literal_in_statement315);
                    literal9=literal();

                    state._fsp--;

                    adaptor.addChild(root_0, literal9.getTree());

                    }
                    break;
                case 2 :
                    // AnnotationParser.g:91:15: argument
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_argument_in_statement319);
                    argument10=argument();

                    state._fsp--;

                    adaptor.addChild(root_0, argument10.getTree());

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

    public static class literal_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "literal"
    // AnnotationParser.g:94:1: literal : literal_value= STRING_LITERAL -> ^( LITERAL $literal_value) ;
    public final AnnotationParser.literal_return literal() throws RecognitionException {
        AnnotationParser.literal_return retval = new AnnotationParser.literal_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token literal_value=null;

        AnnotationCommonTree literal_value_tree=null;
        RewriteRuleTokenStream stream_STRING_LITERAL=new RewriteRuleTokenStream(adaptor,"token STRING_LITERAL");

        try {
            // AnnotationParser.g:95:3: (literal_value= STRING_LITERAL -> ^( LITERAL $literal_value) )
            // AnnotationParser.g:95:5: literal_value= STRING_LITERAL
            {
            literal_value=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_literal334);  
            stream_STRING_LITERAL.add(literal_value);



            // AST REWRITE
            // elements: literal_value
            // token labels: literal_value
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_literal_value=new RewriteRuleTokenStream(adaptor,"token literal_value",literal_value);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 96:7: -> ^( LITERAL $literal_value)
            {
                // AnnotationParser.g:96:10: ^( LITERAL $literal_value)
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(LITERAL, "LITERAL"), root_1);

                adaptor.addChild(root_1, stream_literal_value.nextNode());

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
    // $ANTLR end "literal"

    public static class argument_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argument"
    // AnnotationParser.g:99:1: argument : name= argumentName EQUAL argument_value= argumentValue -> ^( ARGUMENT $name $argument_value) ;
    public final AnnotationParser.argument_return argument() throws RecognitionException {
        AnnotationParser.argument_return retval = new AnnotationParser.argument_return();
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
            // AnnotationParser.g:100:3: (name= argumentName EQUAL argument_value= argumentValue -> ^( ARGUMENT $name $argument_value) )
            // AnnotationParser.g:100:5: name= argumentName EQUAL argument_value= argumentValue
            {
            pushFollow(FOLLOW_argumentName_in_argument364);
            name=argumentName();

            state._fsp--;

            stream_argumentName.add(name.getTree());
            EQUAL11=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_argument366);  
            stream_EQUAL.add(EQUAL11);

            pushFollow(FOLLOW_argumentValue_in_argument370);
            argument_value=argumentValue();

            state._fsp--;

            stream_argumentValue.add(argument_value.getTree());


            // AST REWRITE
            // elements: name, argument_value
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
            // 101:7: -> ^( ARGUMENT $name $argument_value)
            {
                // AnnotationParser.g:101:10: ^( ARGUMENT $name $argument_value)
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ARGUMENT, "ARGUMENT"), root_1);

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
    // $ANTLR end "argument"

    public static class argumentName_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argumentName"
    // AnnotationParser.g:104:1: argumentName : name= IDENTIFIER -> ^( ARGUMENT_NAME $name) ;
    public final AnnotationParser.argumentName_return argumentName() throws RecognitionException {
        AnnotationParser.argumentName_return retval = new AnnotationParser.argumentName_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token name=null;

        AnnotationCommonTree name_tree=null;
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");

        try {
            // AnnotationParser.g:105:3: (name= IDENTIFIER -> ^( ARGUMENT_NAME $name) )
            // AnnotationParser.g:105:5: name= IDENTIFIER
            {
            name=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_argumentName403);  
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
            // 106:7: -> ^( ARGUMENT_NAME $name)
            {
                // AnnotationParser.g:106:10: ^( ARGUMENT_NAME $name)
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
    // AnnotationParser.g:109:1: argumentValue : ( value -> ^( ARGUMENT_VALUE value ) | CURLY_START annotations= subAnnotation ( COMMA annotations= subAnnotation )* CURLY_END -> ^( ARGUMENT_VALUE ( $annotations)+ ) );
    public final AnnotationParser.argumentValue_return argumentValue() throws RecognitionException {
        AnnotationParser.argumentValue_return retval = new AnnotationParser.argumentValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token CURLY_START13=null;
        Token COMMA14=null;
        Token CURLY_END15=null;
        AnnotationParser.subAnnotation_return annotations = null;

        AnnotationParser.value_return value12 = null;


        AnnotationCommonTree CURLY_START13_tree=null;
        AnnotationCommonTree COMMA14_tree=null;
        AnnotationCommonTree CURLY_END15_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_CURLY_START=new RewriteRuleTokenStream(adaptor,"token CURLY_START");
        RewriteRuleTokenStream stream_CURLY_END=new RewriteRuleTokenStream(adaptor,"token CURLY_END");
        RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value");
        RewriteRuleSubtreeStream stream_subAnnotation=new RewriteRuleSubtreeStream(adaptor,"rule subAnnotation");
        try {
            // AnnotationParser.g:110:3: ( value -> ^( ARGUMENT_VALUE value ) | CURLY_START annotations= subAnnotation ( COMMA annotations= subAnnotation )* CURLY_END -> ^( ARGUMENT_VALUE ( $annotations)+ ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==CURLY_START) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==STRING_LITERAL) ) {
                    alt8=1;
                }
                else if ( (LA8_1==AT) ) {
                    alt8=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;
                }
            }
            else if ( ((LA8_0>=TRUE && LA8_0<=NULL)||LA8_0==STRING_LITERAL) ) {
                alt8=1;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // AnnotationParser.g:110:5: value
                    {
                    pushFollow(FOLLOW_value_in_argumentValue431);
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
                    // 111:7: -> ^( ARGUMENT_VALUE value )
                    {
                        // AnnotationParser.g:111:10: ^( ARGUMENT_VALUE value )
                        {
                        AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                        root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ARGUMENT_VALUE, "ARGUMENT_VALUE"), root_1);

                        adaptor.addChild(root_1, stream_value.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // AnnotationParser.g:112:5: CURLY_START annotations= subAnnotation ( COMMA annotations= subAnnotation )* CURLY_END
                    {
                    CURLY_START13=(Token)match(input,CURLY_START,FOLLOW_CURLY_START_in_argumentValue451);  
                    stream_CURLY_START.add(CURLY_START13);

                    pushFollow(FOLLOW_subAnnotation_in_argumentValue455);
                    annotations=subAnnotation();

                    state._fsp--;

                    stream_subAnnotation.add(annotations.getTree());
                    // AnnotationParser.g:112:43: ( COMMA annotations= subAnnotation )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==COMMA) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // AnnotationParser.g:112:44: COMMA annotations= subAnnotation
                    	    {
                    	    COMMA14=(Token)match(input,COMMA,FOLLOW_COMMA_in_argumentValue458);  
                    	    stream_COMMA.add(COMMA14);

                    	    pushFollow(FOLLOW_subAnnotation_in_argumentValue462);
                    	    annotations=subAnnotation();

                    	    state._fsp--;

                    	    stream_subAnnotation.add(annotations.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    CURLY_END15=(Token)match(input,CURLY_END,FOLLOW_CURLY_END_in_argumentValue466);  
                    stream_CURLY_END.add(CURLY_END15);



                    // AST REWRITE
                    // elements: annotations
                    // token labels: 
                    // rule labels: retval, annotations
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_annotations=new RewriteRuleSubtreeStream(adaptor,"rule annotations",annotations!=null?annotations.tree:null);

                    root_0 = (AnnotationCommonTree)adaptor.nil();
                    // 113:7: -> ^( ARGUMENT_VALUE ( $annotations)+ )
                    {
                        // AnnotationParser.g:113:10: ^( ARGUMENT_VALUE ( $annotations)+ )
                        {
                        AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                        root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ARGUMENT_VALUE, "ARGUMENT_VALUE"), root_1);

                        if ( !(stream_annotations.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_annotations.hasNext() ) {
                            adaptor.addChild(root_1, stream_annotations.nextTree());

                        }
                        stream_annotations.reset();

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
    // $ANTLR end "argumentValue"

    public static class subAnnotation_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "subAnnotation"
    // AnnotationParser.g:116:1: subAnnotation : AT class_name ( declaration )? -> ^( ANNOTATION class_name ( declaration )? ) ;
    public final AnnotationParser.subAnnotation_return subAnnotation() throws RecognitionException {
        AnnotationParser.subAnnotation_return retval = new AnnotationParser.subAnnotation_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token AT16=null;
        AnnotationParser.class_name_return class_name17 = null;

        AnnotationParser.declaration_return declaration18 = null;


        AnnotationCommonTree AT16_tree=null;
        RewriteRuleTokenStream stream_AT=new RewriteRuleTokenStream(adaptor,"token AT");
        RewriteRuleSubtreeStream stream_declaration=new RewriteRuleSubtreeStream(adaptor,"rule declaration");
        RewriteRuleSubtreeStream stream_class_name=new RewriteRuleSubtreeStream(adaptor,"rule class_name");
        try {
            // AnnotationParser.g:117:3: ( AT class_name ( declaration )? -> ^( ANNOTATION class_name ( declaration )? ) )
            // AnnotationParser.g:118:6: AT class_name ( declaration )?
            {
            AT16=(Token)match(input,AT,FOLLOW_AT_in_subAnnotation500);  
            stream_AT.add(AT16);

            pushFollow(FOLLOW_class_name_in_subAnnotation502);
            class_name17=class_name();

            state._fsp--;

            stream_class_name.add(class_name17.getTree());
            // AnnotationParser.g:118:20: ( declaration )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==PARAM_START) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // AnnotationParser.g:118:20: declaration
                    {
                    pushFollow(FOLLOW_declaration_in_subAnnotation504);
                    declaration18=declaration();

                    state._fsp--;

                    stream_declaration.add(declaration18.getTree());

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
            // 119:8: -> ^( ANNOTATION class_name ( declaration )? )
            {
                // AnnotationParser.g:119:11: ^( ANNOTATION class_name ( declaration )? )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ANNOTATION, "ANNOTATION"), root_1);

                adaptor.addChild(root_1, stream_class_name.nextTree());
                // AnnotationParser.g:119:35: ( declaration )?
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
    // $ANTLR end "subAnnotation"

    public static class value_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "value"
    // AnnotationParser.g:122:1: value : ( objectValue | stringValue | booleanValue | nullValue );
    public final AnnotationParser.value_return value() throws RecognitionException {
        AnnotationParser.value_return retval = new AnnotationParser.value_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        AnnotationParser.objectValue_return objectValue19 = null;

        AnnotationParser.stringValue_return stringValue20 = null;

        AnnotationParser.booleanValue_return booleanValue21 = null;

        AnnotationParser.nullValue_return nullValue22 = null;



        try {
            // AnnotationParser.g:123:3: ( objectValue | stringValue | booleanValue | nullValue )
            int alt10=4;
            switch ( input.LA(1) ) {
            case CURLY_START:
                {
                alt10=1;
                }
                break;
            case STRING_LITERAL:
                {
                alt10=2;
                }
                break;
            case TRUE:
            case FALSE:
                {
                alt10=3;
                }
                break;
            case NULL:
                {
                alt10=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // AnnotationParser.g:123:5: objectValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_objectValue_in_value536);
                    objectValue19=objectValue();

                    state._fsp--;

                    adaptor.addChild(root_0, objectValue19.getTree());

                    }
                    break;
                case 2 :
                    // AnnotationParser.g:124:5: stringValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_stringValue_in_value542);
                    stringValue20=stringValue();

                    state._fsp--;

                    adaptor.addChild(root_0, stringValue20.getTree());

                    }
                    break;
                case 3 :
                    // AnnotationParser.g:125:5: booleanValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_booleanValue_in_value548);
                    booleanValue21=booleanValue();

                    state._fsp--;

                    adaptor.addChild(root_0, booleanValue21.getTree());

                    }
                    break;
                case 4 :
                    // AnnotationParser.g:126:5: nullValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_nullValue_in_value554);
                    nullValue22=nullValue();

                    state._fsp--;

                    adaptor.addChild(root_0, nullValue22.getTree());

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

    public static class objectValue_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "objectValue"
    // AnnotationParser.g:129:1: objectValue : CURLY_START pairs+= pair ( COMMA pairs+= pair )* CURLY_END -> ^( OBJECT_VALUE ( $pairs)+ ) ;
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
            // AnnotationParser.g:130:3: ( CURLY_START pairs+= pair ( COMMA pairs+= pair )* CURLY_END -> ^( OBJECT_VALUE ( $pairs)+ ) )
            // AnnotationParser.g:130:5: CURLY_START pairs+= pair ( COMMA pairs+= pair )* CURLY_END
            {
            CURLY_START23=(Token)match(input,CURLY_START,FOLLOW_CURLY_START_in_objectValue567);  
            stream_CURLY_START.add(CURLY_START23);

            pushFollow(FOLLOW_pair_in_objectValue571);
            pairs=pair();

            state._fsp--;

            stream_pair.add(pairs.getTree());
            if (list_pairs==null) list_pairs=new ArrayList();
            list_pairs.add(pairs.getTree());

            // AnnotationParser.g:130:29: ( COMMA pairs+= pair )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==COMMA) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // AnnotationParser.g:130:30: COMMA pairs+= pair
            	    {
            	    COMMA24=(Token)match(input,COMMA,FOLLOW_COMMA_in_objectValue574);  
            	    stream_COMMA.add(COMMA24);

            	    pushFollow(FOLLOW_pair_in_objectValue578);
            	    pairs=pair();

            	    state._fsp--;

            	    stream_pair.add(pairs.getTree());
            	    if (list_pairs==null) list_pairs=new ArrayList();
            	    list_pairs.add(pairs.getTree());


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            CURLY_END25=(Token)match(input,CURLY_END,FOLLOW_CURLY_END_in_objectValue582);  
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
            // 131:7: -> ^( OBJECT_VALUE ( $pairs)+ )
            {
                // AnnotationParser.g:131:10: ^( OBJECT_VALUE ( $pairs)+ )
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

    public static class stringValue_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stringValue"
    // AnnotationParser.g:134:1: stringValue : string_value= STRING_LITERAL -> ^( STRING_VALUE $string_value) ;
    public final AnnotationParser.stringValue_return stringValue() throws RecognitionException {
        AnnotationParser.stringValue_return retval = new AnnotationParser.stringValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token string_value=null;

        AnnotationCommonTree string_value_tree=null;
        RewriteRuleTokenStream stream_STRING_LITERAL=new RewriteRuleTokenStream(adaptor,"token STRING_LITERAL");

        try {
            // AnnotationParser.g:135:3: (string_value= STRING_LITERAL -> ^( STRING_VALUE $string_value) )
            // AnnotationParser.g:135:5: string_value= STRING_LITERAL
            {
            string_value=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_stringValue613);  
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
            // 136:7: -> ^( STRING_VALUE $string_value)
            {
                // AnnotationParser.g:136:10: ^( STRING_VALUE $string_value)
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

    public static class booleanValue_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "booleanValue"
    // AnnotationParser.g:139:1: booleanValue : ( TRUE -> ^( BOOLEAN_VALUE TRUE ) | FALSE -> ^( BOOLEAN_VALUE FALSE ) );
    public final AnnotationParser.booleanValue_return booleanValue() throws RecognitionException {
        AnnotationParser.booleanValue_return retval = new AnnotationParser.booleanValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token TRUE26=null;
        Token FALSE27=null;

        AnnotationCommonTree TRUE26_tree=null;
        AnnotationCommonTree FALSE27_tree=null;
        RewriteRuleTokenStream stream_FALSE=new RewriteRuleTokenStream(adaptor,"token FALSE");
        RewriteRuleTokenStream stream_TRUE=new RewriteRuleTokenStream(adaptor,"token TRUE");

        try {
            // AnnotationParser.g:140:3: ( TRUE -> ^( BOOLEAN_VALUE TRUE ) | FALSE -> ^( BOOLEAN_VALUE FALSE ) )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==TRUE) ) {
                alt12=1;
            }
            else if ( (LA12_0==FALSE) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // AnnotationParser.g:140:5: TRUE
                    {
                    TRUE26=(Token)match(input,TRUE,FOLLOW_TRUE_in_booleanValue641);  
                    stream_TRUE.add(TRUE26);



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
                    // 141:7: -> ^( BOOLEAN_VALUE TRUE )
                    {
                        // AnnotationParser.g:141:10: ^( BOOLEAN_VALUE TRUE )
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
                    // AnnotationParser.g:142:5: FALSE
                    {
                    FALSE27=(Token)match(input,FALSE,FOLLOW_FALSE_in_booleanValue661);  
                    stream_FALSE.add(FALSE27);



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
                    // 143:7: -> ^( BOOLEAN_VALUE FALSE )
                    {
                        // AnnotationParser.g:143:10: ^( BOOLEAN_VALUE FALSE )
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
    // AnnotationParser.g:146:1: nullValue : NULL -> ^( NULL_VALUE NULL ) ;
    public final AnnotationParser.nullValue_return nullValue() throws RecognitionException {
        AnnotationParser.nullValue_return retval = new AnnotationParser.nullValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token NULL28=null;

        AnnotationCommonTree NULL28_tree=null;
        RewriteRuleTokenStream stream_NULL=new RewriteRuleTokenStream(adaptor,"token NULL");

        try {
            // AnnotationParser.g:147:3: ( NULL -> ^( NULL_VALUE NULL ) )
            // AnnotationParser.g:147:5: NULL
            {
            NULL28=(Token)match(input,NULL,FOLLOW_NULL_in_nullValue688);  
            stream_NULL.add(NULL28);



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
            // 148:7: -> ^( NULL_VALUE NULL )
            {
                // AnnotationParser.g:148:10: ^( NULL_VALUE NULL )
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

    public static class pair_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pair"
    // AnnotationParser.g:151:1: pair : name= stringValue EQUAL value -> ^( PAIR $name value ) ;
    public final AnnotationParser.pair_return pair() throws RecognitionException {
        AnnotationParser.pair_return retval = new AnnotationParser.pair_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token EQUAL29=null;
        AnnotationParser.stringValue_return name = null;

        AnnotationParser.value_return value30 = null;


        AnnotationCommonTree EQUAL29_tree=null;
        RewriteRuleTokenStream stream_EQUAL=new RewriteRuleTokenStream(adaptor,"token EQUAL");
        RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value");
        RewriteRuleSubtreeStream stream_stringValue=new RewriteRuleSubtreeStream(adaptor,"rule stringValue");
        try {
            // AnnotationParser.g:152:3: (name= stringValue EQUAL value -> ^( PAIR $name value ) )
            // AnnotationParser.g:152:5: name= stringValue EQUAL value
            {
            pushFollow(FOLLOW_stringValue_in_pair717);
            name=stringValue();

            state._fsp--;

            stream_stringValue.add(name.getTree());
            EQUAL29=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pair719);  
            stream_EQUAL.add(EQUAL29);

            pushFollow(FOLLOW_value_in_pair721);
            value30=value();

            state._fsp--;

            stream_value.add(value30.getTree());


            // AST REWRITE
            // elements: value, name
            // token labels: 
            // rule labels: retval, name
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name",name!=null?name.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 153:7: -> ^( PAIR $name value )
            {
                // AnnotationParser.g:153:10: ^( PAIR $name value )
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

    // Delegated rules


 

    public static final BitSet FOLLOW_AT_in_annotation150 = new BitSet(new long[]{0x0000000002080000L});
    public static final BitSet FOLLOW_class_name_in_annotation152 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_declaration_in_annotation154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_namespace_in_class_name186 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_class_name190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BSLASH_in_namespace223 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_namespace229 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_BSLASH_in_namespace231 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_PARAM_START_in_declaration270 = new BitSet(new long[]{0x0000000006050000L});
    public static final BitSet FOLLOW_statement_in_declaration274 = new BitSet(new long[]{0x0000000000050000L});
    public static final BitSet FOLLOW_COMMA_in_declaration278 = new BitSet(new long[]{0x0000000006000000L});
    public static final BitSet FOLLOW_statement_in_declaration282 = new BitSet(new long[]{0x0000000000050000L});
    public static final BitSet FOLLOW_PARAM_END_in_declaration286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_statement315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_argument_in_statement319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_literal334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_argumentName_in_argument364 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_EQUAL_in_argument366 = new BitSet(new long[]{0x0000000005D00000L});
    public static final BitSet FOLLOW_argumentValue_in_argument370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_argumentName403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_value_in_argumentValue431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CURLY_START_in_argumentValue451 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_subAnnotation_in_argumentValue455 = new BitSet(new long[]{0x0000000000240000L});
    public static final BitSet FOLLOW_COMMA_in_argumentValue458 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_subAnnotation_in_argumentValue462 = new BitSet(new long[]{0x0000000000240000L});
    public static final BitSet FOLLOW_CURLY_END_in_argumentValue466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_subAnnotation500 = new BitSet(new long[]{0x0000000002080000L});
    public static final BitSet FOLLOW_class_name_in_subAnnotation502 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_declaration_in_subAnnotation504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objectValue_in_value536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringValue_in_value542 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_booleanValue_in_value548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nullValue_in_value554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CURLY_START_in_objectValue567 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_pair_in_objectValue571 = new BitSet(new long[]{0x0000000000240000L});
    public static final BitSet FOLLOW_COMMA_in_objectValue574 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_pair_in_objectValue578 = new BitSet(new long[]{0x0000000000240000L});
    public static final BitSet FOLLOW_CURLY_END_in_objectValue582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_stringValue613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_booleanValue641 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_booleanValue661 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_nullValue688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringValue_in_pair717 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_EQUAL_in_pair719 = new BitSet(new long[]{0x0000000005D00000L});
    public static final BitSet FOLLOW_value_in_pair721 = new BitSet(new long[]{0x0000000000000002L});

}