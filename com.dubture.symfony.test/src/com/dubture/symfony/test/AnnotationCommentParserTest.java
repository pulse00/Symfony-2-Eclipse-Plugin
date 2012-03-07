package com.dubture.symfony.test;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.dubture.symfony.annotation.model.Annotation;
import com.dubture.symfony.annotation.parser.AnnotationCommentParser;

public class AnnotationCommentParserTest extends TestCase {

    @Test
    public void testNoAnnotations() {
        String comment = "\\** A really simple comment without annotation */";

        List<Annotation> annotations = getCommentAnnotations(comment);

        assertEquals(0, annotations.size());
    }

    @Test
    public void testOneAnnotationNoText() {
        String comment = "\\** @Route(\"/path\") */";

        List<Annotation> annotations = getCommentAnnotations(comment);

        assertEquals(1, annotations.size());

        Annotation annotation = annotations.get(0);
        assertEquals("Route", annotation.getClassName());
        assertEquals("/path", annotation.getArguments().get(0).toString());
    }

    @Test
    public void testOneAnnotationColumn() {
        String comment = "\\** \n" +
                        " * @Route(\"/path\") \n" +
                        "*/";

        List<Annotation> annotations = getCommentAnnotations(comment);

        assertEquals(1, annotations.size());
        Annotation annotation = annotations.get(0);
        assertEquals(2, annotation.getSourcePosition().line);
        assertEquals(4, annotation.getSourcePosition().column);
        assertEquals(8, annotation.getSourcePosition().startOffset);
        assertEquals(22, annotation.getSourcePosition().endOffset);
    }

    @Test
    public void testOneAnnotationWithText() {
        String comment = "\\**" +
                "           * This is a test with a comment." +
                "           * We will see if it works." +
                "           * " +
                "           * @Route(\"/path\")" +
                "           * " +
                "           */";

        List<Annotation> annotations = getCommentAnnotations(comment);

        assertEquals(1, annotations.size());

        Annotation annotation = annotations.get(0);
        assertEquals("Route", annotation.getClassName());
        assertEquals("/path", annotation.getArguments().get(0).toString());
    }

    @Test
    public void testFullComment() {
        String comment = "\\**\r\n" +
                "           * This is a test with a comment.\n" +
                "           * We will see if it works. We add a @ just to check how it will be handled\r" +
                "           * \r\n" +
                "           * @Route(\"/path\")\r\n" +
                "           * @Template()\r\n" +
                "           * @Extra\\Secure(\"secured_route\", name = true, other = null) \r" +
                "           */";

        List<Annotation> annotations = getCommentAnnotations(comment);

        assertEquals(3, annotations.size());

        Annotation annotation = annotations.get(2);
        assertEquals("Extra\\", annotation.getNamespace());
        assertEquals("Secure", annotation.getClassName());
        assertEquals("secured_route", annotation.getArguments().get(0).toString());
        assertEquals(true, annotation.getArgumentValue("name").getValue());
        assertEquals(null, annotation.getArgumentValue("other").getValue());
        assertEquals(7, annotation.getSourcePosition().line);
        assertEquals(14, annotation.getSourcePosition().column);
        assertEquals(219, annotation.getSourcePosition().startOffset);
        assertEquals(275, annotation.getSourcePosition().endOffset);
    }

    @Test
    public void testSubAnnotations() {
        String comment = "\\**\r\n" +
                "         * @Orm\\JoinTable(\"literal\", name=\"join_table_name\", " +
                "*      joinColumns={@Orm\\JoinColumn(name=\"join_id\", referencedColumnName=\"id_first\")}, " +
                "*      inverseJoinColumns={@Orm\\JoinColumn(name=\"inverse_id\", referencedColumnName=\"id_second\")} " +
                "* )" +
                "           */";

        int commentOffset = 17;
        List<Annotation> annotations = getCommentAnnotations(comment, commentOffset);

        assertEquals(1, annotations.size());

        Annotation annotation = annotations.get(0);
        assertEquals("Orm\\", annotation.getNamespace());
        assertEquals("JoinTable", annotation.getClassName());
        assertEquals("literal", annotation.getArguments().get(0).toString());
        assertEquals("join_table_name", annotation.getArgumentValue("name").getValue());
        assertEquals(2, annotation.getSourcePosition().line);
        assertEquals(12, annotation.getSourcePosition().column);
        assertEquals(16 + commentOffset, annotation.getSourcePosition().startOffset);
        assertEquals(252 + commentOffset, annotation.getSourcePosition().endOffset);
    }

    @Test
    public void testWithPhpDoc() {
        String comment = "\\**\r\n" +
                "           * This is a test with a comment.\n" +
                "           * We will see if it works. We add a @ just to check how it will be handled\r" +
                "           * \r\n" +
                "           * @Route(\"/path\")\r\n" +
                "           * But there is php doc block after here \n" +
                "           * @param test\r\n" +
                "           * @param (\"that seems to be a correct annotation\")\r" +
                "           */";

        List<Annotation> annotations = getCommentAnnotations(comment, true);

        assertEquals(1, annotations.size());

        Annotation annotation = annotations.get(0);
        assertEquals("", annotation.getNamespace());
        assertEquals("Route", annotation.getClassName());
    }

    @Test
    public void testWithCommentOffsetFarAway() {
        String comment = "/**\r\n" +
"     * @Route(\"/hello/{name}\", name=null, requierements={\"name\" = \"\\+\"})\r\n" +
"     * @Template()\r\n" +
"     */";

        List<Annotation> annotations = getCommentAnnotations(comment, 1568);

        assertEquals(2, annotations.size());

        Annotation annotation = annotations.get(1);
        assertEquals("", annotation.getNamespace());
        assertEquals("Template", annotation.getClassName());
    }

    @Test
    public void testWithIncludedClassNames() {
        String comment = "/**\r\n" +
"     * @Route(\"/hello/{name}\", name=null, requierements={\"name\" = \"\\+\"})\r\n" +
"     * @Template()\r\n" +
"     */";

        AnnotationCommentParser parser = new AnnotationCommentParser();
        parser.setIncludedClassNames(new String[]{"Template"});

        List<Annotation> annotations = parser.parse(comment, 1568);

        assertEquals(1, annotations.size());

        Annotation annotation = annotations.get(0);
        assertEquals("", annotation.getNamespace());
        assertEquals("Template", annotation.getClassName());
    }

    @Test
    public void testAnnotationNoDeclaration() {
        String comment = "/** @A\\B\r\n" +
"     *\r\n" +
"     * @var Test" +
"     * @param author" +
"     */";

        List<Annotation> annotations = getCommentAnnotations(comment, true);

        assertEquals(1, annotations.size());

        Annotation annotation = annotations.get(0);
        assertEquals("A\\", annotation.getNamespace());
        assertEquals("B", annotation.getClassName());
        assertEquals(1, annotation.getSourcePosition().line);
        assertEquals(5, annotation.getSourcePosition().column);
        assertEquals(4, annotation.getSourcePosition().startOffset);
        assertEquals(7, annotation.getSourcePosition().endOffset);
    }

    @Test
    public void testAnnotationEmail() {
        String comment = "/** @A\\B\n" +
"     *\n" +
"     * Licensed to <user@email.com>\n" +
"     */";

        List<Annotation> annotations = getCommentAnnotations(comment, true);

        assertEquals(1, annotations.size());
    }

    @Test
    public void testAnnotationEndsAtEndOfChunk() {
        String comment = "/** @A\\B";

        List<Annotation> annotations = getCommentAnnotations(comment, true);

        assertEquals(1, annotations.size());
    }

    protected List<Annotation> getCommentAnnotations(String comment) {
        return getCommentAnnotations(comment, 0);
    }

    protected List<Annotation> getCommentAnnotations(String comment, int commentOffset) {
        AnnotationCommentParser parser = new AnnotationCommentParser();

        return parser.parse(comment, commentOffset);
    }

    protected List<Annotation> getCommentAnnotations(String comment, boolean excludePhpDocBlock) {
        return getCommentAnnotations(comment, 0, excludePhpDocBlock);
    }

    protected List<Annotation> getCommentAnnotations(String comment, int commentOffset, boolean excludePhpDocBlock) {
        List<String> excludedClassNames = new LinkedList<String>();
        if (excludePhpDocBlock) {
            // Could add more
            excludedClassNames.add("param");
            excludedClassNames.add("return");
            excludedClassNames.add("author");
            excludedClassNames.add("var");
        }

        AnnotationCommentParser parser = new AnnotationCommentParser(excludedClassNames);

        return parser.parse(comment, commentOffset);
    }
}
