package com.dubture.symfony.annotation.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    static public List<Annotation> parseFromString(String comment, List<String> excludedAnnotationClassNames) {
        AnnotationCommentParser parser = new AnnotationCommentParser(comment, excludedAnnotationClassNames);

        return parser.parse();
    }

    protected StringBuffer buffer;
    protected List<String> excludedClassNames;

    protected int lineOffset;
    protected int columnOffset;
    protected int currentOffset;

    public AnnotationCommentParser(String comment) {
        this(comment, new ArrayList<String>());
    }

    public AnnotationCommentParser(String comment, List<String> excludedClassNames) {
        this.buffer = new StringBuffer(comment);
        this.excludedClassNames = excludedClassNames;

        this.lineOffset = 0;
        this.columnOffset = 0;
        this.currentOffset = 0;
    }

    public List<Annotation> parse() {
        List<Annotation> annotations = new LinkedList<Annotation>();

        while (hasMoreChunk()) {
            String chunk = getNextChunk();
            Annotation annotation = parseChunk(chunk);
            if (annotation == null || annotation.getSourcePosition().endIndex == -1) {
                // If we couldn't get an annotation or it is not ended, increase offset an try at the next @
                currentOffset++;
                continue;
            }

            annotations.add(annotation);
            currentOffset = annotation.getSourcePosition().endIndex + 1;
        }

        return postProcess(annotations);
    }

    /**
     * This method will post process the annotations. The post process is used mainly
     * to remove unwanted annotation like {@literal @param} annotation.
     *
     * @return A post-processed list of annotations
     */
    protected List<Annotation> postProcess(List<Annotation> annotations) {
        Iterator<Annotation> iterator = annotations.iterator();
        while (iterator.hasNext()) {
            Annotation annotation = iterator.next();
            if (excludedClassNames.contains(annotation.getFullyQualifiedName())) {
                iterator.remove();
            }
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
            AnnotationNodeVisitor visitor = new AnnotationNodeVisitor(lineOffset, columnOffset, currentOffset);
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
        int oldOffset = currentOffset;
        currentOffset = buffer.indexOf("@", currentOffset);

        String oldChunk = buffer.substring(oldOffset, currentOffset);
        String newChunk = buffer.substring(currentOffset);

        adjustOffset(oldChunk);

        return newChunk;
    }

    protected void adjustOffset(String oldChunk) {
        Matcher matcher = Pattern.compile("\r\n|\n|\r").matcher(oldChunk);
        int lastMatchEnd = 0;

        while (matcher.find()) {
            lineOffset++;
            lastMatchEnd = matcher.end() - 1;
        }

        columnOffset = oldChunk.length() - lastMatchEnd;
    }
}