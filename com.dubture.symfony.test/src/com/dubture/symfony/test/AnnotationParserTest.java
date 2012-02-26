/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.test;


import java.util.Map;

import junit.framework.TestCase;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dubture.symfony.annotation.model.Annotation;
import com.dubture.symfony.annotation.model.AnnotationValue;
import com.dubture.symfony.annotation.model.ArgumentValueType;
import com.dubture.symfony.annotation.model.ArrayValue;
import com.dubture.symfony.annotation.model.ObjectValue;
import com.dubture.symfony.annotation.parser.antlr.AnnotationLexer;
import com.dubture.symfony.annotation.parser.antlr.AnnotationParser;
import com.dubture.symfony.annotation.parser.tree.AnnotationCommonTree;
import com.dubture.symfony.annotation.parser.tree.AnnotationCommonTreeAdaptor;
import com.dubture.symfony.annotation.parser.tree.visitor.AnnotationNodeVisitor;


/**
 *
 * Tests the {@link AnnotationLexer} and {@link AnnotationParser}
 * classes.
 *
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class AnnotationParserTest extends TestCase {


    private DebugErrorReporter reporter = new DebugErrorReporter();
    private AnnotationNodeVisitor root;

    @Before
    public void setUp() throws Exception {
        reporter.reset();
    }

    @After
    public void tearDown() throws Exception {
        reporter.reset();
    }

    @Test
    public void testSyntaxErrors() {
        root = getRootNode("@ManyToManyPersister('shall\". fo)", false);
    }

    @Test
    public void testSimple() {
        root = getRootNode("@O()", false);

        assertEquals("O", root.getClassName());
    }

    @Test
    public void testTemplatePath() {
        root = getRootNode("* @Template(\"DemoBundle:Welcome:index.html.twig\")", false);

        assertEquals("Template", root.getClassName());
    }

    @Test
    public void testRoute() {
        root = getRootNode("* @Route('/blog', name='_blog')", false);

        assertEquals("_blog", root.getAnnotation().getArgumentValue("name").getValue());
    }

    @Test
    public void testRequirementsAdvanced() {
        root = getRootNode("@Route(\"/.{_format}\", " +
                "                  name=\"post\", " +
                "                  double=-10.0," +
                "                  int = 100," +
                "                  defaults={\"_format\" = \"\\s+\"}, " +
                "                  requirements={\"one\", \"two\"})\"", false);

        Annotation annotation = root.getAnnotation();

        assertEquals("post", annotation.getArgumentValue("name").getValue());
        assertEquals(-10.0, annotation.getArgumentValue("double").getValue());
        assertEquals(100.0, annotation.getArgumentValue("int").getValue());

        assertEquals(ArgumentValueType.OBJECT, annotation.getArgumentValue("defaults").getType());
        ObjectValue objectValue = (ObjectValue) annotation.getArgumentValue("defaults");

        assertEquals("\\s+", objectValue.getArgumentValue("_format").getValue());

        assertEquals(ArgumentValueType.ARRAY, annotation.getArgumentValue("requirements").getType());
        ArrayValue arrayValue = (ArrayValue) annotation.getArgumentValue("requirements");

        assertEquals("one", arrayValue.getArgumentValue(0).getValue());
        assertEquals("two", arrayValue.getArgumentValue(1).getValue());
    }

    @Test
    public void testEmpty() {
        root = getRootNode("* @Route()", false);

        assertNotNull(root);
        assertEquals("Route", root.getClassName());
        assertTrue(root.getNamespace().length() == 0);
    }

    @Test
    public void testNoDeclaration() {
        root = getRootNode("* @ORM\\Foo", false);

        assertEquals("Foo", root.getClassName());
        assertEquals("ORM\\", root.getNamespace());
        assertEquals("ORM\\Foo", root.getFullyQualifiedName());
    }

    @Test
    public void testPhpDoc() {
        root = getRootNode("@param Value", false);
        assertEquals("param", root.getClassName());

        root = getRootNode("@inheritdoc Value", false);
        assertEquals("inheritdoc", root.getClassName());

        root = getRootNode("{@inheritdoc}", false);
        assertNull(root.getAnnotation());

        root = getRootNode("@inheritdoc}", false);
        assertEquals("inheritdoc", root.getClassName());
    }

    @Test
    public void testNamespace() {
        root = getRootNode("* @ORM\\Foo()", false);

        assertEquals("Foo", root.getClassName());
        assertEquals("ORM\\", root.getNamespace());
        assertEquals("ORM\\Foo", root.getFullyQualifiedName());
    }

    @Test
    public void testObjectValue() {
        root = getRootNode("* @Sensio\\Route(\"/hello\", " +
                        "        defaults={\"name\"=\"*World*\" " +
                        " *       ,\"isFalse\"=    true,     \"false\"    =    false,  " +
                        "        \"null\" = null})", false);

        assertNotNull(root);
        assertEquals("Route",root.getClassName());
        assertFalse(reporter.hasErrors());

        assertEquals(ArgumentValueType.OBJECT, root.getArgumentValue("defaults").getType());

        ObjectValue objectValue = (ObjectValue) root.getArgumentValue("defaults");
        assertEquals("*World*", objectValue.get("name"));
        assertEquals(true, objectValue.get("isFalse"));
        assertEquals(false, objectValue.get("false"));
        assertEquals(null, objectValue.get("null"));
    }

    @Test
    public void testRoles() {
        root = getRootNode("* @Secure(roles=\"ROLE_ADMIN, ROLE_USER\")", false);

        assertNotNull(root);
        assertEquals("Secure",root.getClassName());
        assertFalse(reporter.hasErrors());
    }

    @Test
    public void testDoctrineParameters() {
        root = getRootNode("* @ORM\\Entity(repositoryClass=\"Acme\\DemoBundle\\Entity\\DoctorRepository\")", false);

        assertNotNull(root);
        assertEquals("Entity",root.getClassName());
        assertFalse(reporter.hasErrors());

        assertEquals("Acme\\DemoBundle\\Entity\\DoctorRepository", root.getArgument("repositoryClass"));
    }

    @Test
    public void testDoctrineSubAnnotations() {
        root = getRootNode("         * @Orm\\JoinTable(\"literal\", name=\"join_table_name\", " +
                "*      joinColumns={@Orm\\JoinColumn(name=\"join_id\", referencedColumnName=\"id_first\")}, " +
                "*      inverseJoinColumns={@Orm\\JoinColumn(name=\"inverse_id\", referencedColumnName=\"id_second\")} " +
                "* )", false);

        assertNotNull(root);
        assertEquals("JoinTable", root.getClassName());
        assertFalse(reporter.hasErrors());

        assertNotNull(root.getLiteralArguments().contains("\"literal\""));
        assertEquals("join_table_name", root.getArgument("name"));
        assertEquals(ArgumentValueType.ARRAY, root.getArgumentValue("joinColumns").getType());
        assertEquals(ArgumentValueType.ARRAY, root.getArgumentValue("inverseJoinColumns").getType());

        ArrayValue joinColumn = (ArrayValue) root.getArgumentValue("joinColumns");
        AnnotationValue joinColumnAnnotation = (AnnotationValue) joinColumn.getArgumentValue(0);
        assertEquals("JoinColumn", joinColumnAnnotation.getClassName());
        assertEquals("Orm\\", joinColumnAnnotation.getNamespace());
        assertEquals("join_id", joinColumnAnnotation.getArgument("name"));
        assertEquals("id_first", joinColumnAnnotation.getArgument("referencedColumnName"));

        ArrayValue inverseJoinColumn = (ArrayValue) root.getArgumentValue("inverseJoinColumns");
        AnnotationValue inverseJoinColumnAnnotation = (AnnotationValue) inverseJoinColumn.getArgumentValue(0);
        assertEquals("JoinColumn", inverseJoinColumnAnnotation.getClassName());
        assertEquals("Orm\\", inverseJoinColumnAnnotation.getNamespace());
        assertEquals("inverse_id", inverseJoinColumnAnnotation.getArgument("name"));
        assertEquals("id_second", inverseJoinColumnAnnotation.getArgument("referencedColumnName"));
    }

    @Test
    public void testSimpleComment() {
        root = getRootNode(
                "\\** " +
                "  * @Orm\\JoinColumn(" +
                "        \"literal\", " +
                "        name=\"join_table_name\"," +
                "        joinColumns = {@Orm\\JoinColumn(name=\"join_id\", referencedColumnName=\"id_first\")}," +
                "        inverseJoinColumns={@Orm\\JoinColumn(name=\"inverse_id\", referencedColumnName=\"id_second\")} " +
                "    " +
                "    ) " +
                "  */", false);

        Annotation annotation = root.getAnnotation();
        assertEquals("JoinColumn", annotation.getClassName());
        assertEquals("Orm\\", annotation.getNamespace());
        assertEquals(4, annotation.getArguments().size());
    }

    public void testWithAttributes() {
        root = getRootNode(" * @Attributes({ " +
        " @Attribute(\"mixed\",                type = \"mixed\"), " +
        " @Attribute(\"boolean\",              type = \"boolean\"), " +
        " @Attribute(\"bool\",                 type = \"bool\"), " +
        " @Attribute(\"float\",                type = \"float\"), " +
        " @Attribute(\"string\",               type = \"string\"), " +
        " @Attribute(\"integer\",              type = \"integer\"), " +
        " @Attribute(\"array\",                type = \"array\"), " +
        " @Attribute(\"arrayOfIntegers\",      type = \"array<integer>\"), " +
        " @Attribute(\"annotation\",           type = \"Doctrine\\Tests\\Common\\Annotations\\Fixtures\\AnnotationTargetAll\"), " +
        " @Attribute(\"arrayOfAnnotations\",   type = \"array<Doctrine\\Tests\\Common\\Annotations\\Fixtures\\AnnotationTargetAll>\")})",
        false);

        assertNotNull(root);
        assertEquals("Attributes", root.getClassName());
        System.out.println(reporter.getErrorsAsString());
        assertFalse(reporter.hasErrors());
    }

    @Test
    public void testRouteVariable() {
        root = getRootNode("* @Route(\"/hello/{name}\", name=\"_demo_secured_hello\")", false);

        assertNotNull(root);
        assertNotNull(reporter);
        assertEquals("Route",root.getClassName());
        System.out.println(reporter.getErrorsAsString());
        assertFalse(reporter.hasErrors());
    }

    @Test
    public void testSyntaxError() {
        root = getRootNode("* @Ro+--ute(name='test\")", true);
        assertNotNull(root);
        assertTrue(reporter.hasErrors());
    }

    @Test
    public void testMissingArgument() {
        root = getRootNode("* @Route(name=)", true);
        assertNotNull(root);
        assertTrue(reporter.hasErrors());
    }

    @Test
    public void testMissingComma() {
        root = getRootNode("* @Template(name='foo' bar)", true);
        assertNotNull(root);
        assertTrue(reporter.hasErrors());
    }

    @Test
    public void testUnclosedQuote() {
        root = getRootNode("* @Template(aho=')", true);
        assertNotNull(root);
        assertTrue(reporter.hasErrors());
    }

    /**
    * Parse an annotation and return the NodeVisitor to test against.
    *
    * @param line
    * @param expectFail if the parsing process is expected to fail
    * @return {@link AnnotationNodeVisitor}
    */
    private AnnotationNodeVisitor getRootNode(String line, boolean expectFail) {
        try {
            CharStream content = new ANTLRStringStream(line);

            AnnotationLexer lexer = new AnnotationLexer(content, reporter);

            AnnotationParser parser = new AnnotationParser(new CommonTokenStream(lexer));
            parser.setErrorReporter(reporter);

            parser.setTreeAdaptor(new AnnotationCommonTreeAdaptor());
            AnnotationParser.annotation_return root;

            root = parser.annotation();

            AnnotationCommonTree tree = (AnnotationCommonTree) root.getTree();
            AnnotationNodeVisitor visitor = new AnnotationNodeVisitor();
            tree.accept(visitor);

            if (expectFail && reporter.hasErrors() == false) {
                fail();
            }

            return visitor;

        } catch (Exception e) {

            e.printStackTrace();
            // we should never be here, if the parser fails it should
            // still finish normally but log syntax errors
            fail();

        }

        if (!expectFail && reporter.hasErrors())
            fail();

        return null;
    }
}
