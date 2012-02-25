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
        assertEquals(8, annotation.getSourcePosition().startIndex);
        assertEquals(22, annotation.getSourcePosition().endIndex);
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
        assertEquals(219, annotation.getSourcePosition().startIndex);
        assertEquals(275, annotation.getSourcePosition().endIndex);
    }

    @Test
    public void testSubAnnotations() {
        String comment = "\\**\r\n" +
                "         * @Orm\\JoinTable(\"literal\", name=\"join_table_name\", " +
                "*      joinColumns={@Orm\\JoinColumn(name=\"join_id\", referencedColumnName=\"id_first\")}, " +
                "*      inverseJoinColumns={@Orm\\JoinColumn(name=\"inverse_id\", referencedColumnName=\"id_second\")} " +
                "* )" +
                "           */";

        List<Annotation> annotations = getCommentAnnotations(comment);

        assertEquals(1, annotations.size());

        Annotation annotation = annotations.get(0);
        assertEquals("Orm\\", annotation.getNamespace());
        assertEquals("JoinTable", annotation.getClassName());
        assertEquals("literal", annotation.getArguments().get(0).toString());
        assertEquals("join_table_name", annotation.getArgumentValue("name").getValue());
        assertEquals(2, annotation.getSourcePosition().line);
        assertEquals(12, annotation.getSourcePosition().column);
        assertEquals(16, annotation.getSourcePosition().startIndex);
        assertEquals(252, annotation.getSourcePosition().endIndex);
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

    protected List<Annotation> getCommentAnnotations(String comment) {
        AnnotationCommentParser parser = new AnnotationCommentParser(comment);

        return parser.parse();
    }

    protected List<Annotation> getCommentAnnotations(String comment, boolean excludePhpDocBlock) {
        List<String> excludedClassNames = new LinkedList<String>();
        if (excludePhpDocBlock) {
            // Could add more
            excludedClassNames.add("param");
            excludedClassNames.add("return");
            excludedClassNames.add("author");
        }

        AnnotationCommentParser parser = new AnnotationCommentParser(comment, excludedClassNames);

        return parser.parse();
    }
}
