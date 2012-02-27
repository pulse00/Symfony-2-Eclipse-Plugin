/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.test;


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
    private Annotation annotation;

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
        annotation = parseAnnotation("@ManyToManyPersister('shall\". fo)", false);
    }

    @Test
    public void testSimple() {
        annotation = parseAnnotation("@O()", false);

        assertEquals("O", annotation.getClassName());
    }

    @Test
    public void testTemplatePath() {
        annotation = parseAnnotation("* @Template(\"DemoBundle:Welcome:index.html.twig\")", false);

        assertEquals("Template", annotation.getClassName());
    }

    @Test
    public void testRoute() {
        annotation = parseAnnotation("* @Route('/blog', name='_blog')", false);

        assertEquals("_blog", annotation.getArgumentValue("name").getValue());
    }

    @Test
    public void testRequirementsAdvanced() {
        annotation = parseAnnotation("@Route(\"/.{_format}\", " +
                "                  name=\"post\", " +
                "                  double=-10.0," +
                "                  int = 100," +
                "                  defaults={\"_format\" = \"\\s+\"}, " +
                "                  requirements={\"one\", \"two\"})\"", false);

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
        annotation = parseAnnotation("* @Route()", false);

        assertNotNull(annotation);
        assertEquals("Route", annotation.getClassName());
        assertTrue(annotation.getNamespace().length() == 0);
    }

    @Test
    public void testNoDeclaration() {
        annotation = parseAnnotation("* @ORM\\Foo", false);

        assertEquals("Foo", annotation.getClassName());
        assertEquals("ORM\\", annotation.getNamespace());
        assertEquals("ORM\\Foo", annotation.getFullyQualifiedName());
    }

    @Test
    public void testPhpDoc() {
        annotation = parseAnnotation("@param Value", false);
        assertEquals("param", annotation.getClassName());

        annotation = parseAnnotation("@inheritdoc Value", false);
        assertEquals("inheritdoc", annotation.getClassName());

        annotation = parseAnnotation("{@inheritdoc}", false);
        assertNull(annotation);

        annotation = parseAnnotation("@inheritdoc}", false);
        assertEquals("inheritdoc", annotation.getClassName());
    }

    @Test
    public void testNamespace() {
        annotation = parseAnnotation("* @ORM\\Foo()", false);

        assertEquals("Foo", annotation.getClassName());
        assertEquals("ORM\\", annotation.getNamespace());
        assertEquals("ORM\\Foo", annotation.getFullyQualifiedName());
    }

    @Test
    public void testObjectValue() {
        annotation = parseAnnotation("* @Sensio\\Route(\"/hello\", " +
                        "        defaults={\"name\"=\"*World*\" " +
                        " *       ,\"isFalse\"=    true,     \"false\"    =    false,  " +
                        "        \"null\" = null})", false);

        assertNotNull(annotation);
        assertEquals("Route",annotation.getClassName());
        assertFalse(reporter.hasErrors());

        assertEquals(ArgumentValueType.OBJECT, annotation.getArgumentValue("defaults").getType());

        ObjectValue objectValue = (ObjectValue) annotation.getArgumentValue("defaults");
        assertEquals("*World*", objectValue.get("name"));
        assertEquals(true, objectValue.get("isFalse"));
        assertEquals(false, objectValue.get("false"));
        assertEquals(null, objectValue.get("null"));
    }

    @Test
    public void testRoles() {
        annotation = parseAnnotation("* @Secure(roles=\"ROLE_ADMIN, ROLE_USER\")", false);

        assertNotNull(annotation);
        assertEquals("Secure",annotation.getClassName());
        assertFalse(reporter.hasErrors());
    }

    @Test
    public void testDoctrineParameters() {
        annotation = parseAnnotation("* @ORM\\Entity(repositoryClass=\"Acme\\DemoBundle\\Entity\\DoctorRepository\")", false);

        assertNotNull(annotation);
        assertEquals("Entity",annotation.getClassName());
        assertFalse(reporter.hasErrors());

        assertEquals("Acme\\DemoBundle\\Entity\\DoctorRepository", annotation.getArgument("repositoryClass"));
    }

    @Test
    public void testDoctrineSubAnnotations() {
        annotation = parseAnnotation("         * @Orm\\JoinTable(\"literal\", name=\"join_table_name\", " +
                "*      joinColumns={@Orm\\JoinColumn(name=\"join_id\", referencedColumnName=\"id_first\")}, " +
                "*      inverseJoinColumns={@Orm\\JoinColumn(name=\"inverse_id\", referencedColumnName=\"id_second\")} " +
                "* )", false);

        assertNotNull(annotation);
        assertEquals("JoinTable", annotation.getClassName());
        assertFalse(reporter.hasErrors());

        assertNotNull(annotation.getArguments().contains("\"literal\""));
        assertEquals("join_table_name", annotation.getArgument("name"));
        assertEquals(ArgumentValueType.ARRAY, annotation.getArgumentValue("joinColumns").getType());
        assertEquals(ArgumentValueType.ARRAY, annotation.getArgumentValue("inverseJoinColumns").getType());

        ArrayValue joinColumn = (ArrayValue) annotation.getArgumentValue("joinColumns");
        AnnotationValue joinColumnAnnotation = (AnnotationValue) joinColumn.getArgumentValue(0);
        assertEquals("JoinColumn", joinColumnAnnotation.getClassName());
        assertEquals("Orm\\", joinColumnAnnotation.getNamespace());
        assertEquals("join_id", joinColumnAnnotation.getArgument("name"));
        assertEquals("id_first", joinColumnAnnotation.getArgument("referencedColumnName"));

        ArrayValue inverseJoinColumn = (ArrayValue) annotation.getArgumentValue("inverseJoinColumns");
        AnnotationValue inverseJoinColumnAnnotation = (AnnotationValue) inverseJoinColumn.getArgumentValue(0);
        assertEquals("JoinColumn", inverseJoinColumnAnnotation.getClassName());
        assertEquals("Orm\\", inverseJoinColumnAnnotation.getNamespace());
        assertEquals("inverse_id", inverseJoinColumnAnnotation.getArgument("name"));
        assertEquals("id_second", inverseJoinColumnAnnotation.getArgument("referencedColumnName"));
    }

    @Test
    public void testSimpleComment() {
        annotation = parseAnnotation(
                "\\** " +
                "  * @Orm\\JoinColumn(" +
                "        \"literal\", " +
                "        name=\"join_table_name\"," +
                "        joinColumns = {@Orm\\JoinColumn(name=\"join_id\", referencedColumnName=\"id_first\")}," +
                "        inverseJoinColumns={@Orm\\JoinColumn(name=\"inverse_id\", referencedColumnName=\"id_second\")} " +
                "    " +
                "    ) " +
                "  */", false);

        assertEquals("JoinColumn", annotation.getClassName());
        assertEquals("Orm\\", annotation.getNamespace());
        assertEquals(4, annotation.getArguments().size());
    }

    public void testWithAttributes() {
        annotation = parseAnnotation(" * @Attributes({ " +
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

        assertNotNull(annotation);
        assertEquals("Attributes", annotation.getClassName());
        System.out.println(reporter.getErrorsAsString());
        assertFalse(reporter.hasErrors());
    }

    @Test
    public void testRouteVariable() {
        annotation = parseAnnotation("* @Route(\"/hello/{name}\", name=\"_demo_secured_hello\")", false);

        assertNotNull(annotation);
        assertNotNull(reporter);
        assertEquals("Route",annotation.getClassName());
        System.out.println(reporter.getErrorsAsString());
        assertFalse(reporter.hasErrors());
    }

    @Test
    public void testSyntaxError() {
        annotation = parseAnnotation("* @Ro+--ute(name='test\")", true);
        assertNotNull(annotation);
        assertTrue(reporter.hasErrors());
    }

    @Test
    public void testMissingArgument() {
        annotation = parseAnnotation("* @Route(name=)", true);
        assertNotNull(annotation);
        assertTrue(reporter.hasErrors());
    }

    @Test
    public void testMissingComma() {
        annotation = parseAnnotation("* @Template(name='foo' bar)", true);
        assertNotNull(annotation);
        assertTrue(reporter.hasErrors());
    }

    @Test
    public void testUnclosedQuote() {
        annotation = parseAnnotation("* @Template(aho=')", true);
        assertNotNull(annotation);
        assertTrue(reporter.hasErrors());
    }

    /**
    * Parse an annotation and return the NodeVisitor to test against.
    *
    * @param line
    * @param expectFail if the parsing process is expected to fail
    * @return {@link AnnotationNodeVisitor}
    */
    private Annotation parseAnnotation(String line, boolean expectFail) {
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

            return visitor.getAnnotation();

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
