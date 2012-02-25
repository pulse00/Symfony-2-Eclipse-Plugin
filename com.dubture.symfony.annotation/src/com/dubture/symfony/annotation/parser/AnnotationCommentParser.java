package com.dubture.symfony.annotation.parser;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import com.dubture.symfony.annotation.model.Annotation;
import com.dubture.symfony.annotation.parser.antlr.AnnotationLexer;
import com.dubture.symfony.annotation.parser.antlr.AnnotationParser;
import com.dubture.symfony.annotation.parser.antlr.error.IAnnotationErrorReporter;
import com.dubture.symfony.annotation.parser.tree.AnnotationCommonTree;
import com.dubture.symfony.annotation.parser.tree.AnnotationCommonTreeAdaptor;
import com.dubture.symfony.annotation.parser.tree.visitor.AnnotationNodeVisitor;

public class AnnotationCommentParser {

    static public List<Annotation> parseFromString(String comment) {
        AnnotationCommentParser parser = new AnnotationCommentParser(comment);

        return parser.parse();
    }

    protected StringBuilder buffer;
    protected int currentOffset;

    public AnnotationCommentParser(String comment) {
        this.buffer = new StringBuilder(comment);
        this.currentOffset = 0;
    }

    public List<Annotation> parse() {
        List<Annotation> annotations = new LinkedList<Annotation>();

        while (hasMoreChunk()) {
            String chunk = getNextChunk();
            Annotation annotation = parseChunk(chunk);
            if (annotation == null ) {
                // If we couldn't get an annotation, increase offset an try at the next @
                currentOffset++;
                continue;
            }

            annotations.add(annotation);
            currentOffset = annotation.getEndPosition().startIndex;
        }

        return annotations;
    }

    protected Annotation parseChunk(String chunk) {
        CharStream content = new ANTLRStringStream(chunk);
        AnnotationLexer lexer = new AnnotationLexer(content, new IAnnotationErrorReporter() {
            @Override
            public void reportError(String header, String message, RecognitionException e) {
                // Do nothing for now
            }
        });

        AnnotationParser parser = new AnnotationParser(new CommonTokenStream(lexer));
        parser.setTreeAdaptor(new AnnotationCommonTreeAdaptor());

        Annotation annotation = null;
        AnnotationParser.annotation_return root;
        try {
            root = parser.annotation();

            AnnotationCommonTree tree = (AnnotationCommonTree) root.getTree();
            AnnotationNodeVisitor visitor = new AnnotationNodeVisitor(currentOffset);
            tree.accept(visitor);

            annotation = visitor.getAnnotation();
        } catch (RecognitionException exception) {
            exception.printStackTrace();
        }

        return annotation;
    }

    protected boolean hasMoreChunk() {
        return currentOffset < buffer.length() && buffer.indexOf("@", currentOffset) != -1;
    }

    protected String getNextChunk() {
        currentOffset = buffer.indexOf("@", currentOffset);

        return buffer.substring(currentOffset);
    }
}
