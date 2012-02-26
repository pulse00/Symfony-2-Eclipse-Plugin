// $ANTLR 3.3 Nov 30, 2010 12:45:30 C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g 2012-02-26 10:25:05

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LOWER", "UPPER", "DIGIT", "AT", "UNDERSCORE", "BSLASH", "QUOTE", "DOUBLE_QUOTE", "ESCAPE_QUOTE", "ESCAPE_DOUBLE_QUOTE", "NEGATIVE", "DOT", "LETTER", "ALPHANUM", "IDENTIFIER_FRAG", "PARAM_START", "PARAM_END", "EQUAL", "COMMA", "CURLY_START", "CURLY_END", "TRUE", "FALSE", "NULL", "ANNOTATION", "IDENTIFIER", "STRING_LITERAL", "INTEGER_LITERAL", "FLOAT_LITERAL", "WHITESPACE", "ANNOTATION_VALUE", "ARGUMENT", "ARGUMENT_NAME", "ARGUMENT_VALUE", "ARRAY_VALUE", "BOOLEAN_VALUE", "CLASS", "DECLARATION", "NAMED_ARGUMENT", "NAMESPACE", "NAMESPACE_DEFAULT", "NULL_VALUE", "NUMBER_VALUE", "OBJECT_VALUE", "PAIR", "STRING_VALUE"
    };
    public static final int EOF=-1;
    public static final int LOWER=4;
    public static final int UPPER=5;
    public static final int DIGIT=6;
    public static final int AT=7;
    public static final int UNDERSCORE=8;
    public static final int BSLASH=9;
    public static final int QUOTE=10;
    public static final int DOUBLE_QUOTE=11;
    public static final int ESCAPE_QUOTE=12;
    public static final int ESCAPE_DOUBLE_QUOTE=13;
    public static final int NEGATIVE=14;
    public static final int DOT=15;
    public static final int LETTER=16;
    public static final int ALPHANUM=17;
    public static final int IDENTIFIER_FRAG=18;
    public static final int PARAM_START=19;
    public static final int PARAM_END=20;
    public static final int EQUAL=21;
    public static final int COMMA=22;
    public static final int CURLY_START=23;
    public static final int CURLY_END=24;
    public static final int TRUE=25;
    public static final int FALSE=26;
    public static final int NULL=27;
    public static final int ANNOTATION=28;
    public static final int IDENTIFIER=29;
    public static final int STRING_LITERAL=30;
    public static final int INTEGER_LITERAL=31;
    public static final int FLOAT_LITERAL=32;
    public static final int WHITESPACE=33;
    public static final int ANNOTATION_VALUE=34;
    public static final int ARGUMENT=35;
    public static final int ARGUMENT_NAME=36;
    public static final int ARGUMENT_VALUE=37;
    public static final int ARRAY_VALUE=38;
    public static final int BOOLEAN_VALUE=39;
    public static final int CLASS=40;
    public static final int DECLARATION=41;
    public static final int NAMED_ARGUMENT=42;
    public static final int NAMESPACE=43;
    public static final int NAMESPACE_DEFAULT=44;
    public static final int NULL_VALUE=45;
    public static final int NUMBER_VALUE=46;
    public static final int OBJECT_VALUE=47;
    public static final int PAIR=48;
    public static final int STRING_VALUE=49;

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
    public String getGrammarFileName() { return "C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g"; }



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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:72:1: annotation : annotation_class= ANNOTATION ( declaration )? -> ^( ANNOTATION $annotation_class ( declaration )? ) ;
    public final AnnotationParser.annotation_return annotation() throws RecognitionException {
        AnnotationParser.annotation_return retval = new AnnotationParser.annotation_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token annotation_class=null;
        AnnotationParser.declaration_return declaration1 = null;


        AnnotationCommonTree annotation_class_tree=null;
        RewriteRuleTokenStream stream_ANNOTATION=new RewriteRuleTokenStream(adaptor,"token ANNOTATION");
        RewriteRuleSubtreeStream stream_declaration=new RewriteRuleSubtreeStream(adaptor,"rule declaration");
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:73:3: (annotation_class= ANNOTATION ( declaration )? -> ^( ANNOTATION $annotation_class ( declaration )? ) )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:73:5: annotation_class= ANNOTATION ( declaration )?
            {
            annotation_class=(Token)match(input,ANNOTATION,FOLLOW_ANNOTATION_in_annotation159);  
            stream_ANNOTATION.add(annotation_class);

            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:73:33: ( declaration )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==PARAM_START) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:73:33: declaration
                    {
                    pushFollow(FOLLOW_declaration_in_annotation161);
                    declaration1=declaration();

                    state._fsp--;

                    stream_declaration.add(declaration1.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: annotation_class, ANNOTATION, declaration
            // token labels: annotation_class
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_annotation_class=new RewriteRuleTokenStream(adaptor,"token annotation_class",annotation_class);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 74:7: -> ^( ANNOTATION $annotation_class ( declaration )? )
            {
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:74:10: ^( ANNOTATION $annotation_class ( declaration )? )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot(stream_ANNOTATION.nextNode(), root_1);

                adaptor.addChild(root_1, stream_annotation_class.nextNode());
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:74:41: ( declaration )?
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

    public static class declaration_return extends ParserRuleReturnScope {
        AnnotationCommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declaration"
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:77:1: declaration : PARAM_START (statements+= statement )? ( COMMA statements+= statement )* PARAM_END -> ^( DECLARATION PARAM_START ( $statements)* PARAM_END ) ;
    public final AnnotationParser.declaration_return declaration() throws RecognitionException {
        AnnotationParser.declaration_return retval = new AnnotationParser.declaration_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token PARAM_START2=null;
        Token COMMA3=null;
        Token PARAM_END4=null;
        List list_statements=null;
        RuleReturnScope statements = null;
        AnnotationCommonTree PARAM_START2_tree=null;
        AnnotationCommonTree COMMA3_tree=null;
        AnnotationCommonTree PARAM_END4_tree=null;
        RewriteRuleTokenStream stream_PARAM_START=new RewriteRuleTokenStream(adaptor,"token PARAM_START");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_PARAM_END=new RewriteRuleTokenStream(adaptor,"token PARAM_END");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:78:3: ( PARAM_START (statements+= statement )? ( COMMA statements+= statement )* PARAM_END -> ^( DECLARATION PARAM_START ( $statements)* PARAM_END ) )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:78:5: PARAM_START (statements+= statement )? ( COMMA statements+= statement )* PARAM_END
            {
            PARAM_START2=(Token)match(input,PARAM_START,FOLLOW_PARAM_START_in_declaration193);  
            stream_PARAM_START.add(PARAM_START2);

            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:78:27: (statements+= statement )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==CURLY_START||(LA2_0>=TRUE && LA2_0<=FLOAT_LITERAL)) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:78:27: statements+= statement
                    {
                    pushFollow(FOLLOW_statement_in_declaration197);
                    statements=statement();

                    state._fsp--;

                    stream_statement.add(statements.getTree());
                    if (list_statements==null) list_statements=new ArrayList();
                    list_statements.add(statements.getTree());


                    }
                    break;

            }

            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:78:40: ( COMMA statements+= statement )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==COMMA) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:78:41: COMMA statements+= statement
            	    {
            	    COMMA3=(Token)match(input,COMMA,FOLLOW_COMMA_in_declaration201);  
            	    stream_COMMA.add(COMMA3);

            	    pushFollow(FOLLOW_statement_in_declaration205);
            	    statements=statement();

            	    state._fsp--;

            	    stream_statement.add(statements.getTree());
            	    if (list_statements==null) list_statements=new ArrayList();
            	    list_statements.add(statements.getTree());


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            PARAM_END4=(Token)match(input,PARAM_END,FOLLOW_PARAM_END_in_declaration209);  
            stream_PARAM_END.add(PARAM_END4);



            // AST REWRITE
            // elements: statements, PARAM_START, PARAM_END
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: statements
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_statements=new RewriteRuleSubtreeStream(adaptor,"token statements",list_statements);
            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 79:7: -> ^( DECLARATION PARAM_START ( $statements)* PARAM_END )
            {
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:79:10: ^( DECLARATION PARAM_START ( $statements)* PARAM_END )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(DECLARATION, "DECLARATION"), root_1);

                adaptor.addChild(root_1, stream_PARAM_START.nextNode());
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:79:36: ( $statements)*
                while ( stream_statements.hasNext() ) {
                    adaptor.addChild(root_1, stream_statements.nextTree());

                }
                stream_statements.reset();
                adaptor.addChild(root_1, stream_PARAM_END.nextNode());

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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:82:1: statement : ( argument | namedArgument );
    public final AnnotationParser.statement_return statement() throws RecognitionException {
        AnnotationParser.statement_return retval = new AnnotationParser.statement_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        AnnotationParser.argument_return argument5 = null;

        AnnotationParser.namedArgument_return namedArgument6 = null;



        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:83:3: ( argument | namedArgument )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==CURLY_START||(LA4_0>=TRUE && LA4_0<=ANNOTATION)||(LA4_0>=STRING_LITERAL && LA4_0<=FLOAT_LITERAL)) ) {
                alt4=1;
            }
            else if ( (LA4_0==IDENTIFIER) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:83:5: argument
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_argument_in_statement242);
                    argument5=argument();

                    state._fsp--;

                    adaptor.addChild(root_0, argument5.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:83:16: namedArgument
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_namedArgument_in_statement246);
                    namedArgument6=namedArgument();

                    state._fsp--;

                    adaptor.addChild(root_0, namedArgument6.getTree());

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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:86:1: argument : argument_value= value -> ^( ARGUMENT $argument_value) ;
    public final AnnotationParser.argument_return argument() throws RecognitionException {
        AnnotationParser.argument_return retval = new AnnotationParser.argument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        AnnotationParser.value_return argument_value = null;


        RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value");
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:87:3: (argument_value= value -> ^( ARGUMENT $argument_value) )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:87:5: argument_value= value
            {
            pushFollow(FOLLOW_value_in_argument261);
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
            // 88:7: -> ^( ARGUMENT $argument_value)
            {
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:88:10: ^( ARGUMENT $argument_value)
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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:91:1: namedArgument : name= argumentName EQUAL argument_value= argumentValue -> ^( NAMED_ARGUMENT $name $argument_value) ;
    public final AnnotationParser.namedArgument_return namedArgument() throws RecognitionException {
        AnnotationParser.namedArgument_return retval = new AnnotationParser.namedArgument_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token EQUAL7=null;
        AnnotationParser.argumentName_return name = null;

        AnnotationParser.argumentValue_return argument_value = null;


        AnnotationCommonTree EQUAL7_tree=null;
        RewriteRuleTokenStream stream_EQUAL=new RewriteRuleTokenStream(adaptor,"token EQUAL");
        RewriteRuleSubtreeStream stream_argumentValue=new RewriteRuleSubtreeStream(adaptor,"rule argumentValue");
        RewriteRuleSubtreeStream stream_argumentName=new RewriteRuleSubtreeStream(adaptor,"rule argumentName");
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:92:3: (name= argumentName EQUAL argument_value= argumentValue -> ^( NAMED_ARGUMENT $name $argument_value) )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:92:5: name= argumentName EQUAL argument_value= argumentValue
            {
            pushFollow(FOLLOW_argumentName_in_namedArgument291);
            name=argumentName();

            state._fsp--;

            stream_argumentName.add(name.getTree());
            EQUAL7=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_namedArgument293);  
            stream_EQUAL.add(EQUAL7);

            pushFollow(FOLLOW_argumentValue_in_namedArgument297);
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
            // 93:7: -> ^( NAMED_ARGUMENT $name $argument_value)
            {
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:93:10: ^( NAMED_ARGUMENT $name $argument_value)
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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:96:1: argumentName : name= IDENTIFIER -> ^( ARGUMENT_NAME $name) ;
    public final AnnotationParser.argumentName_return argumentName() throws RecognitionException {
        AnnotationParser.argumentName_return retval = new AnnotationParser.argumentName_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token name=null;

        AnnotationCommonTree name_tree=null;
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");

        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:97:3: (name= IDENTIFIER -> ^( ARGUMENT_NAME $name) )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:97:5: name= IDENTIFIER
            {
            name=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_argumentName330);  
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
            // 98:7: -> ^( ARGUMENT_NAME $name)
            {
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:98:10: ^( ARGUMENT_NAME $name)
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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:101:1: argumentValue : value -> ^( ARGUMENT_VALUE value ) ;
    public final AnnotationParser.argumentValue_return argumentValue() throws RecognitionException {
        AnnotationParser.argumentValue_return retval = new AnnotationParser.argumentValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        AnnotationParser.value_return value8 = null;


        RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value");
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:102:3: ( value -> ^( ARGUMENT_VALUE value ) )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:102:5: value
            {
            pushFollow(FOLLOW_value_in_argumentValue358);
            value8=value();

            state._fsp--;

            stream_value.add(value8.getTree());


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
            // 103:7: -> ^( ARGUMENT_VALUE value )
            {
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:103:10: ^( ARGUMENT_VALUE value )
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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:106:1: value : ( annotationValue | objectValue | arrayValue | stringValue | numberValue | booleanValue | nullValue );
    public final AnnotationParser.value_return value() throws RecognitionException {
        AnnotationParser.value_return retval = new AnnotationParser.value_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        AnnotationParser.annotationValue_return annotationValue9 = null;

        AnnotationParser.objectValue_return objectValue10 = null;

        AnnotationParser.arrayValue_return arrayValue11 = null;

        AnnotationParser.stringValue_return stringValue12 = null;

        AnnotationParser.numberValue_return numberValue13 = null;

        AnnotationParser.booleanValue_return booleanValue14 = null;

        AnnotationParser.nullValue_return nullValue15 = null;



        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:107:3: ( annotationValue | objectValue | arrayValue | stringValue | numberValue | booleanValue | nullValue )
            int alt5=7;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:107:5: annotationValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_annotationValue_in_value385);
                    annotationValue9=annotationValue();

                    state._fsp--;

                    adaptor.addChild(root_0, annotationValue9.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:108:5: objectValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_objectValue_in_value391);
                    objectValue10=objectValue();

                    state._fsp--;

                    adaptor.addChild(root_0, objectValue10.getTree());

                    }
                    break;
                case 3 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:109:5: arrayValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_arrayValue_in_value397);
                    arrayValue11=arrayValue();

                    state._fsp--;

                    adaptor.addChild(root_0, arrayValue11.getTree());

                    }
                    break;
                case 4 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:110:5: stringValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_stringValue_in_value403);
                    stringValue12=stringValue();

                    state._fsp--;

                    adaptor.addChild(root_0, stringValue12.getTree());

                    }
                    break;
                case 5 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:111:5: numberValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_numberValue_in_value409);
                    numberValue13=numberValue();

                    state._fsp--;

                    adaptor.addChild(root_0, numberValue13.getTree());

                    }
                    break;
                case 6 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:112:5: booleanValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_booleanValue_in_value415);
                    booleanValue14=booleanValue();

                    state._fsp--;

                    adaptor.addChild(root_0, booleanValue14.getTree());

                    }
                    break;
                case 7 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:113:5: nullValue
                    {
                    root_0 = (AnnotationCommonTree)adaptor.nil();

                    pushFollow(FOLLOW_nullValue_in_value421);
                    nullValue15=nullValue();

                    state._fsp--;

                    adaptor.addChild(root_0, nullValue15.getTree());

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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:116:1: annotationValue : annotation_class= ANNOTATION ( declaration )? -> ^( ANNOTATION_VALUE $annotation_class ( declaration )? ) ;
    public final AnnotationParser.annotationValue_return annotationValue() throws RecognitionException {
        AnnotationParser.annotationValue_return retval = new AnnotationParser.annotationValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token annotation_class=null;
        AnnotationParser.declaration_return declaration16 = null;


        AnnotationCommonTree annotation_class_tree=null;
        RewriteRuleTokenStream stream_ANNOTATION=new RewriteRuleTokenStream(adaptor,"token ANNOTATION");
        RewriteRuleSubtreeStream stream_declaration=new RewriteRuleSubtreeStream(adaptor,"rule declaration");
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:117:3: (annotation_class= ANNOTATION ( declaration )? -> ^( ANNOTATION_VALUE $annotation_class ( declaration )? ) )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:118:6: annotation_class= ANNOTATION ( declaration )?
            {
            annotation_class=(Token)match(input,ANNOTATION,FOLLOW_ANNOTATION_in_annotationValue441);  
            stream_ANNOTATION.add(annotation_class);

            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:118:34: ( declaration )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==PARAM_START) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:118:34: declaration
                    {
                    pushFollow(FOLLOW_declaration_in_annotationValue443);
                    declaration16=declaration();

                    state._fsp--;

                    stream_declaration.add(declaration16.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: declaration, annotation_class
            // token labels: annotation_class
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_annotation_class=new RewriteRuleTokenStream(adaptor,"token annotation_class",annotation_class);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 119:8: -> ^( ANNOTATION_VALUE $annotation_class ( declaration )? )
            {
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:119:11: ^( ANNOTATION_VALUE $annotation_class ( declaration )? )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ANNOTATION_VALUE, "ANNOTATION_VALUE"), root_1);

                adaptor.addChild(root_1, stream_annotation_class.nextNode());
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:119:48: ( declaration )?
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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:122:1: objectValue : CURLY_START pairs+= pair ( COMMA pairs+= pair )* CURLY_END -> ^( OBJECT_VALUE CURLY_START ( $pairs)+ CURLY_END ) ;
    public final AnnotationParser.objectValue_return objectValue() throws RecognitionException {
        AnnotationParser.objectValue_return retval = new AnnotationParser.objectValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token CURLY_START17=null;
        Token COMMA18=null;
        Token CURLY_END19=null;
        List list_pairs=null;
        RuleReturnScope pairs = null;
        AnnotationCommonTree CURLY_START17_tree=null;
        AnnotationCommonTree COMMA18_tree=null;
        AnnotationCommonTree CURLY_END19_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_CURLY_START=new RewriteRuleTokenStream(adaptor,"token CURLY_START");
        RewriteRuleTokenStream stream_CURLY_END=new RewriteRuleTokenStream(adaptor,"token CURLY_END");
        RewriteRuleSubtreeStream stream_pair=new RewriteRuleSubtreeStream(adaptor,"rule pair");
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:123:3: ( CURLY_START pairs+= pair ( COMMA pairs+= pair )* CURLY_END -> ^( OBJECT_VALUE CURLY_START ( $pairs)+ CURLY_END ) )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:123:5: CURLY_START pairs+= pair ( COMMA pairs+= pair )* CURLY_END
            {
            CURLY_START17=(Token)match(input,CURLY_START,FOLLOW_CURLY_START_in_objectValue476);  
            stream_CURLY_START.add(CURLY_START17);

            pushFollow(FOLLOW_pair_in_objectValue480);
            pairs=pair();

            state._fsp--;

            stream_pair.add(pairs.getTree());
            if (list_pairs==null) list_pairs=new ArrayList();
            list_pairs.add(pairs.getTree());

            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:123:29: ( COMMA pairs+= pair )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==COMMA) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:123:30: COMMA pairs+= pair
            	    {
            	    COMMA18=(Token)match(input,COMMA,FOLLOW_COMMA_in_objectValue483);  
            	    stream_COMMA.add(COMMA18);

            	    pushFollow(FOLLOW_pair_in_objectValue487);
            	    pairs=pair();

            	    state._fsp--;

            	    stream_pair.add(pairs.getTree());
            	    if (list_pairs==null) list_pairs=new ArrayList();
            	    list_pairs.add(pairs.getTree());


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            CURLY_END19=(Token)match(input,CURLY_END,FOLLOW_CURLY_END_in_objectValue491);  
            stream_CURLY_END.add(CURLY_END19);



            // AST REWRITE
            // elements: CURLY_END, pairs, CURLY_START
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: pairs
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_pairs=new RewriteRuleSubtreeStream(adaptor,"token pairs",list_pairs);
            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 124:7: -> ^( OBJECT_VALUE CURLY_START ( $pairs)+ CURLY_END )
            {
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:124:10: ^( OBJECT_VALUE CURLY_START ( $pairs)+ CURLY_END )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(OBJECT_VALUE, "OBJECT_VALUE"), root_1);

                adaptor.addChild(root_1, stream_CURLY_START.nextNode());
                if ( !(stream_pairs.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_pairs.hasNext() ) {
                    adaptor.addChild(root_1, stream_pairs.nextTree());

                }
                stream_pairs.reset();
                adaptor.addChild(root_1, stream_CURLY_END.nextNode());

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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:127:1: pair : name= stringValue EQUAL value -> ^( PAIR $name value ) ;
    public final AnnotationParser.pair_return pair() throws RecognitionException {
        AnnotationParser.pair_return retval = new AnnotationParser.pair_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token EQUAL20=null;
        AnnotationParser.stringValue_return name = null;

        AnnotationParser.value_return value21 = null;


        AnnotationCommonTree EQUAL20_tree=null;
        RewriteRuleTokenStream stream_EQUAL=new RewriteRuleTokenStream(adaptor,"token EQUAL");
        RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value");
        RewriteRuleSubtreeStream stream_stringValue=new RewriteRuleSubtreeStream(adaptor,"rule stringValue");
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:128:3: (name= stringValue EQUAL value -> ^( PAIR $name value ) )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:128:5: name= stringValue EQUAL value
            {
            pushFollow(FOLLOW_stringValue_in_pair526);
            name=stringValue();

            state._fsp--;

            stream_stringValue.add(name.getTree());
            EQUAL20=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pair528);  
            stream_EQUAL.add(EQUAL20);

            pushFollow(FOLLOW_value_in_pair530);
            value21=value();

            state._fsp--;

            stream_value.add(value21.getTree());


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
            // 129:7: -> ^( PAIR $name value )
            {
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:129:10: ^( PAIR $name value )
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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:132:1: arrayValue : CURLY_START (values+= value )? ( COMMA values+= value )* CURLY_END -> ^( ARRAY_VALUE CURLY_START ( $values)* CURLY_END ) ;
    public final AnnotationParser.arrayValue_return arrayValue() throws RecognitionException {
        AnnotationParser.arrayValue_return retval = new AnnotationParser.arrayValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token CURLY_START22=null;
        Token COMMA23=null;
        Token CURLY_END24=null;
        List list_values=null;
        RuleReturnScope values = null;
        AnnotationCommonTree CURLY_START22_tree=null;
        AnnotationCommonTree COMMA23_tree=null;
        AnnotationCommonTree CURLY_END24_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_CURLY_START=new RewriteRuleTokenStream(adaptor,"token CURLY_START");
        RewriteRuleTokenStream stream_CURLY_END=new RewriteRuleTokenStream(adaptor,"token CURLY_END");
        RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value");
        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:133:3: ( CURLY_START (values+= value )? ( COMMA values+= value )* CURLY_END -> ^( ARRAY_VALUE CURLY_START ( $values)* CURLY_END ) )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:133:5: CURLY_START (values+= value )? ( COMMA values+= value )* CURLY_END
            {
            CURLY_START22=(Token)match(input,CURLY_START,FOLLOW_CURLY_START_in_arrayValue560);  
            stream_CURLY_START.add(CURLY_START22);

            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:133:23: (values+= value )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==CURLY_START||(LA8_0>=TRUE && LA8_0<=ANNOTATION)||(LA8_0>=STRING_LITERAL && LA8_0<=FLOAT_LITERAL)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:133:23: values+= value
                    {
                    pushFollow(FOLLOW_value_in_arrayValue564);
                    values=value();

                    state._fsp--;

                    stream_value.add(values.getTree());
                    if (list_values==null) list_values=new ArrayList();
                    list_values.add(values.getTree());


                    }
                    break;

            }

            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:133:32: ( COMMA values+= value )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==COMMA) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:133:33: COMMA values+= value
            	    {
            	    COMMA23=(Token)match(input,COMMA,FOLLOW_COMMA_in_arrayValue568);  
            	    stream_COMMA.add(COMMA23);

            	    pushFollow(FOLLOW_value_in_arrayValue572);
            	    values=value();

            	    state._fsp--;

            	    stream_value.add(values.getTree());
            	    if (list_values==null) list_values=new ArrayList();
            	    list_values.add(values.getTree());


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            CURLY_END24=(Token)match(input,CURLY_END,FOLLOW_CURLY_END_in_arrayValue576);  
            stream_CURLY_END.add(CURLY_END24);



            // AST REWRITE
            // elements: CURLY_END, CURLY_START, values
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: values
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_values=new RewriteRuleSubtreeStream(adaptor,"token values",list_values);
            root_0 = (AnnotationCommonTree)adaptor.nil();
            // 134:7: -> ^( ARRAY_VALUE CURLY_START ( $values)* CURLY_END )
            {
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:134:10: ^( ARRAY_VALUE CURLY_START ( $values)* CURLY_END )
                {
                AnnotationCommonTree root_1 = (AnnotationCommonTree)adaptor.nil();
                root_1 = (AnnotationCommonTree)adaptor.becomeRoot((AnnotationCommonTree)adaptor.create(ARRAY_VALUE, "ARRAY_VALUE"), root_1);

                adaptor.addChild(root_1, stream_CURLY_START.nextNode());
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:134:36: ( $values)*
                while ( stream_values.hasNext() ) {
                    adaptor.addChild(root_1, stream_values.nextTree());

                }
                stream_values.reset();
                adaptor.addChild(root_1, stream_CURLY_END.nextNode());

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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:137:1: stringValue : string_value= STRING_LITERAL -> ^( STRING_VALUE $string_value) ;
    public final AnnotationParser.stringValue_return stringValue() throws RecognitionException {
        AnnotationParser.stringValue_return retval = new AnnotationParser.stringValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token string_value=null;

        AnnotationCommonTree string_value_tree=null;
        RewriteRuleTokenStream stream_STRING_LITERAL=new RewriteRuleTokenStream(adaptor,"token STRING_LITERAL");

        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:138:3: (string_value= STRING_LITERAL -> ^( STRING_VALUE $string_value) )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:138:5: string_value= STRING_LITERAL
            {
            string_value=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_stringValue611);  
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
            // 139:7: -> ^( STRING_VALUE $string_value)
            {
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:139:10: ^( STRING_VALUE $string_value)
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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:142:1: numberValue : (interger_value= INTEGER_LITERAL -> ^( NUMBER_VALUE $interger_value) | float_value= FLOAT_LITERAL -> ^( NUMBER_VALUE $float_value) );
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
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:143:3: (interger_value= INTEGER_LITERAL -> ^( NUMBER_VALUE $interger_value) | float_value= FLOAT_LITERAL -> ^( NUMBER_VALUE $float_value) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==INTEGER_LITERAL) ) {
                alt10=1;
            }
            else if ( (LA10_0==FLOAT_LITERAL) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:143:5: interger_value= INTEGER_LITERAL
                    {
                    interger_value=(Token)match(input,INTEGER_LITERAL,FOLLOW_INTEGER_LITERAL_in_numberValue641);  
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
                    // 144:7: -> ^( NUMBER_VALUE $interger_value)
                    {
                        // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:144:10: ^( NUMBER_VALUE $interger_value)
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
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:145:5: float_value= FLOAT_LITERAL
                    {
                    float_value=(Token)match(input,FLOAT_LITERAL,FOLLOW_FLOAT_LITERAL_in_numberValue664);  
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
                    // 146:7: -> ^( NUMBER_VALUE $float_value)
                    {
                        // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:146:10: ^( NUMBER_VALUE $float_value)
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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:149:1: booleanValue : ( TRUE -> ^( BOOLEAN_VALUE TRUE ) | FALSE -> ^( BOOLEAN_VALUE FALSE ) );
    public final AnnotationParser.booleanValue_return booleanValue() throws RecognitionException {
        AnnotationParser.booleanValue_return retval = new AnnotationParser.booleanValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token TRUE25=null;
        Token FALSE26=null;

        AnnotationCommonTree TRUE25_tree=null;
        AnnotationCommonTree FALSE26_tree=null;
        RewriteRuleTokenStream stream_FALSE=new RewriteRuleTokenStream(adaptor,"token FALSE");
        RewriteRuleTokenStream stream_TRUE=new RewriteRuleTokenStream(adaptor,"token TRUE");

        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:150:3: ( TRUE -> ^( BOOLEAN_VALUE TRUE ) | FALSE -> ^( BOOLEAN_VALUE FALSE ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==TRUE) ) {
                alt11=1;
            }
            else if ( (LA11_0==FALSE) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:150:5: TRUE
                    {
                    TRUE25=(Token)match(input,TRUE,FOLLOW_TRUE_in_booleanValue692);  
                    stream_TRUE.add(TRUE25);



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
                    // 151:7: -> ^( BOOLEAN_VALUE TRUE )
                    {
                        // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:151:10: ^( BOOLEAN_VALUE TRUE )
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
                    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:152:5: FALSE
                    {
                    FALSE26=(Token)match(input,FALSE,FOLLOW_FALSE_in_booleanValue712);  
                    stream_FALSE.add(FALSE26);



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
                    // 153:7: -> ^( BOOLEAN_VALUE FALSE )
                    {
                        // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:153:10: ^( BOOLEAN_VALUE FALSE )
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
    // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:156:1: nullValue : NULL -> ^( NULL_VALUE NULL ) ;
    public final AnnotationParser.nullValue_return nullValue() throws RecognitionException {
        AnnotationParser.nullValue_return retval = new AnnotationParser.nullValue_return();
        retval.start = input.LT(1);

        AnnotationCommonTree root_0 = null;

        Token NULL27=null;

        AnnotationCommonTree NULL27_tree=null;
        RewriteRuleTokenStream stream_NULL=new RewriteRuleTokenStream(adaptor,"token NULL");

        try {
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:157:3: ( NULL -> ^( NULL_VALUE NULL ) )
            // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:157:5: NULL
            {
            NULL27=(Token)match(input,NULL,FOLLOW_NULL_in_nullValue739);  
            stream_NULL.add(NULL27);



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
            // 158:7: -> ^( NULL_VALUE NULL )
            {
                // C:\\Dev\\Symfony-2-Eclipse-Plugin\\com.dubture.symfony.annotation\\Resources\\AnnotationParser.g:158:10: ^( NULL_VALUE NULL )
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


    protected DFA5 dfa5 = new DFA5(this);
    static final String DFA5_eotS =
        "\12\uffff";
    static final String DFA5_eofS =
        "\12\uffff";
    static final String DFA5_minS =
        "\1\27\1\uffff\1\26\4\uffff\1\25\2\uffff";
    static final String DFA5_maxS =
        "\1\40\1\uffff\1\40\4\uffff\1\30\2\uffff";
    static final String DFA5_acceptS =
        "\1\uffff\1\1\1\uffff\1\4\1\5\1\6\1\7\1\uffff\1\3\1\2";
    static final String DFA5_specialS =
        "\12\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\2\1\uffff\2\5\1\6\1\1\1\uffff\1\3\2\4",
            "",
            "\7\10\1\uffff\1\7\2\10",
            "",
            "",
            "",
            "",
            "\1\11\1\10\1\uffff\1\10",
            "",
            ""
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "106:1: value : ( annotationValue | objectValue | arrayValue | stringValue | numberValue | booleanValue | nullValue );";
        }
    }
 

    public static final BitSet FOLLOW_ANNOTATION_in_annotation159 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_declaration_in_annotation161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PARAM_START_in_declaration193 = new BitSet(new long[]{0x00000001FED00000L});
    public static final BitSet FOLLOW_statement_in_declaration197 = new BitSet(new long[]{0x0000000000500000L});
    public static final BitSet FOLLOW_COMMA_in_declaration201 = new BitSet(new long[]{0x00000001FE800000L});
    public static final BitSet FOLLOW_statement_in_declaration205 = new BitSet(new long[]{0x0000000000500000L});
    public static final BitSet FOLLOW_PARAM_END_in_declaration209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_argument_in_statement242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_namedArgument_in_statement246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_value_in_argument261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_argumentName_in_namedArgument291 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_EQUAL_in_namedArgument293 = new BitSet(new long[]{0x00000001DE800000L});
    public static final BitSet FOLLOW_argumentValue_in_namedArgument297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_argumentName330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_value_in_argumentValue358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotationValue_in_value385 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objectValue_in_value391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayValue_in_value397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringValue_in_value403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numberValue_in_value409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_booleanValue_in_value415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nullValue_in_value421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANNOTATION_in_annotationValue441 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_declaration_in_annotationValue443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CURLY_START_in_objectValue476 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_pair_in_objectValue480 = new BitSet(new long[]{0x0000000001400000L});
    public static final BitSet FOLLOW_COMMA_in_objectValue483 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_pair_in_objectValue487 = new BitSet(new long[]{0x0000000001400000L});
    public static final BitSet FOLLOW_CURLY_END_in_objectValue491 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringValue_in_pair526 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_EQUAL_in_pair528 = new BitSet(new long[]{0x00000001DE800000L});
    public static final BitSet FOLLOW_value_in_pair530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CURLY_START_in_arrayValue560 = new BitSet(new long[]{0x00000001DFC00000L});
    public static final BitSet FOLLOW_value_in_arrayValue564 = new BitSet(new long[]{0x0000000001400000L});
    public static final BitSet FOLLOW_COMMA_in_arrayValue568 = new BitSet(new long[]{0x00000001DE800000L});
    public static final BitSet FOLLOW_value_in_arrayValue572 = new BitSet(new long[]{0x0000000001400000L});
    public static final BitSet FOLLOW_CURLY_END_in_arrayValue576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_stringValue611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_LITERAL_in_numberValue641 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_LITERAL_in_numberValue664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_booleanValue692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_booleanValue712 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_nullValue739 = new BitSet(new long[]{0x0000000000000002L});

}