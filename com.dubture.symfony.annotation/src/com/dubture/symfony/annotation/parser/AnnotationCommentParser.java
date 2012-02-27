package com.dubture.symfony.annotation.parser;

import java.util.ArrayList;
import java.util.Arrays;
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
        AnnotationCommentParser parser = new AnnotationCommentParser(comment, 0, excludedAnnotationClassNames);

        return parser.parse();
    }

    static public List<Annotation> parseFromString(String comment,
                                                   int commentCharOffset,
                                                   List<String> excludedAnnotationClassNames) {
        AnnotationCommentParser parser = new AnnotationCommentParser(comment,
                                                                     commentCharOffset,
                                                                     excludedAnnotationClassNames);

        return parser.parse();
    }

    protected StringBuffer buffer;
    protected List<String> includedClassNames;
    protected List<String> excludedClassNames;

    protected int lineOffset;
    protected int columnOffset;
    protected int currentCharOffset;
    protected int commentCharOffset;

    public AnnotationCommentParser(String comment) {
        this(comment, 0, new ArrayList<String>());
    }

    public AnnotationCommentParser(String comment, int commentOffset) {
        this(comment, commentOffset, new ArrayList<String>());
    }

    public AnnotationCommentParser(String comment, List<String> excludedClassNames) {
        this(comment, 0, excludedClassNames);
    }

    public AnnotationCommentParser(String comment, int commentCharOffset, List<String> excludedClassNames) {
        this.buffer = new StringBuffer(comment);
        this.excludedClassNames = excludedClassNames;

        this.lineOffset = 0;
        this.columnOffset = 0;
        this.currentCharOffset = 0;
        this.commentCharOffset = commentCharOffset;
    }

    public void setIncludedClassNames(String[] classNames) {
        includedClassNames = Arrays.asList(classNames);
    }

    public void setExcludedClassNames(String[] classNames) {
        excludedClassNames = Arrays.asList(classNames);
    }

    public List<Annotation> parse() {
        List<Annotation> annotations = new LinkedList<Annotation>();

        while (hasMoreChunk()) {
            String chunk = getNextChunk();
            Annotation annotation = parseChunk(chunk);

            if (annotation == null || annotation.getSourcePosition().endOffset == -1) {
                // If we couldn't get an annotation or it is not ended, increase offset an try at the next @
                currentCharOffset++;
                continue;
            }

            annotations.add(annotation);
            currentCharOffset = annotation.getSourcePosition().endOffset + 1 - commentCharOffset;
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
        boolean hasIncludeRestriction = includedClassNames != null && includedClassNames.size() > 0;
        boolean hasExcludeRestriction = excludedClassNames != null && excludedClassNames.size() > 0;

        if (!hasIncludeRestriction && !hasExcludeRestriction) {
            return annotations;
        }

        Iterator<Annotation> iterator = annotations.iterator();
        while (iterator.hasNext()) {
            Annotation annotation = iterator.next();
            String fullyQualifiedName = annotation.getFullyQualifiedName();

            if (hasExcludeRestriction && excludedClassNames.contains(fullyQualifiedName)) {
                iterator.remove();
            }

            if (hasIncludeRestriction && !includedClassNames.contains(fullyQualifiedName)) {
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
            AnnotationNodeVisitor visitor = new AnnotationNodeVisitor(lineOffset,
                                                                      columnOffset,
                                                                      currentCharOffset + commentCharOffset);
            tree.accept(visitor);

            annotation = visitor.getAnnotation();
        } catch (RecognitionException exception) {
            // FIXME: Handle error differently
            exception.printStackTrace();
        }

        return annotation;
    }

    protected boolean hasMoreChunk() {
        if (currentCharOffset >= buffer.length()) {
            // Offset is passed the buffer, no more chunk
            return false;
        }

        int atSignIndex = buffer.indexOf("@", currentCharOffset);

        // No more at sign, or at sign is the last character, no more chunk
        return atSignIndex != -1 && atSignIndex < buffer.length() - 1;
    }

    protected String getNextChunk() {
        int oldOffset = currentCharOffset;
        currentCharOffset = buffer.indexOf("@", currentCharOffset);

        String newChunk = buffer.substring(currentCharOffset);
        String oldChunk = buffer.substring(oldOffset, currentCharOffset);

        adjustOffset(oldChunk);

        return newChunk;
    }

    protected void adjustOffset(String oldChunk) {
        Matcher matcher = Pattern.compile("\r\n|\n|\r").matcher(oldChunk);
        int lastMatchEnd = -1;

        while (matcher.find()) {
            lineOffset++;
            lastMatchEnd = matcher.end() - 1;
        }

        columnOffset = oldChunk.length() - lastMatchEnd;
    }

    protected boolean isIdentifierFirstChar(char character) {
        // This is not entirely ok for our definition of Identifier. However,
        // it is sufficient to eliminate a lot of impossible annotation which
        // is what we seek.
        return Character.isJavaIdentifierStart(character);
    }
}
