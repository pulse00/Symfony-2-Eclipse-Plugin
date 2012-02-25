package com.dubture.symfony.test;

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
        String comment = "\\**" +
                "           * @Route(\"/path\")" +
                "           */";

        List<Annotation> annotations = getCommentAnnotations(comment);

        assertEquals(1, annotations.size());
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
    }

    protected List<Annotation> getCommentAnnotations(String comment) {
        AnnotationCommentParser parser = new AnnotationCommentParser(comment);

        return parser.parse();
    }
}
