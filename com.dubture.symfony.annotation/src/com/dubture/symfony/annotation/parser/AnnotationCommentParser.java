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

/**
 * This is the {@link AnnotationCommentParser}. This is able to parse
 * a comment and returns every annotation found within it.
 *
 * <p>
 * For a better understanding of this class, here a small description
 * of how it works internally. First of all, the class is instantiated.
 * At this point, it is possible for the user of this class to set
 * the excluded class names and also the included class names. The class
 * names is the annotation fully qualified class name. For example, assume
 * we have an annotation {@literal @Orm\Entity(...)}, then to exclude only
 * this annotation, you need to add {@literal "Orm\Entity"} in the exluced
 * class names list. This is the same for includec class names.
 * </p>
 *
 * <p>
 * Excluded class names have priority over included class names. That means
 * that if you have an included class names {@literal "Orm\Entity"} but the
 * you also specified, maybe by mistake, an excluded class name
 * {@literal "Orm\Entity"}, then the returned list will NOT contain
 * {@literal "Orm\Entity"} annotations even if it is found in the included
 * class names.
 * </p>
 *
 * <p>
 * When it has been instantiated, you can now parse a comment by using the
 * method {@link AnnotationCommentParser::parse}. You pass to this method
 * the comment source as a string and a comment start offset value. This
 * start offset is used to adjust source position so positions are relative
 * to the file and not to the comment.
 * </p>
 *
 * <p>
 * Then, the parsing starts. It will begin by finding the first occurrence
 * of the {@literal @} character. It will then create a chunk ranging from
 * the position of the at sign to the end of the comment source. The
 * {@link AnnotationParser} will be run on this chunk.
 * </p>
 *
 * <p>
 * The {@link AnnotationParser} will parse the annotation. If it is valid,
 * the current offset of the parser is set at the end of the parsed annotation.
 * This will enable to parse multiple annotations. If the annotation is
 * invalid, the offset is incremented by one and we restart the process of
 * finding an {@literal @} sign.
 * </p>
 *
 * <p>
 * This process is then repeated until we have read the whole comment.
 * </p>
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class AnnotationCommentParser {

    static public List<Annotation> parseFromString(String comment) {
        return parseFromString(comment, 0);
    }

    static public List<Annotation> parseFromString(String comment, int commentCharOffset) {
        return parseFromString(comment, 0, new ArrayList<String>());
    }

    static public List<Annotation> parseFromString(String comment,
                                                   int commentCharOffset,
                                                   List<String> excludedClassNames) {
        return parseFromString(comment, commentCharOffset, excludedClassNames);
    }

    static public List<Annotation> parseFromString(String comment,
                                                   int commentCharOffset,
                                                   List<String> excludedClassNames,
                                                   List<String> includedClassNames) {
        AnnotationCommentParser parser = new AnnotationCommentParser(excludedClassNames, includedClassNames);

        return parser.parse(comment, commentCharOffset);
    }

    protected StringBuffer buffer;
    protected List<String> includedClassNames;
    protected List<String> excludedClassNames;

    protected String currentChunk;

    protected int lineOffset;
    protected int columnOffset;
    protected int currentCharOffset;
    protected int commentCharOffset;

    public AnnotationCommentParser() {
        this(new ArrayList<String>());
    }


    public AnnotationCommentParser(List<String> excludedClassNames) {
        this(excludedClassNames, new ArrayList<String>());
    }

    public AnnotationCommentParser(List<String> excludedClassNames, List<String> includedClassNames) {
        this.excludedClassNames = excludedClassNames;
        this.includedClassNames = new LinkedList<String>();
    }

    /**
     * Add a class name that should be included when parsing a
     * comment.
     *
     * @param className The class name to include
     */
    public void addIncludedClassName(String className) {
        includedClassNames.add(className);
    }

    /**
     * Add multiple class names that should be included when parsing a
     * comment.
     *
     * @param classNames The class names to include
     */
    public void addIncludedClassNames(String[] classNames) {
        includedClassNames.addAll(Arrays.asList(classNames));
    }

    /**
     * Add a class name that should be excluded when parsing a
     * comment. Excluded class name always has higher priority
     * than included class name.
     *
     * @param className The class name to exclude
     */
    public void addExcludedClassName(String className) {
        excludedClassNames.add(className);
    }

    /**
     * Add multiple class names that should be excluded when parsing a
     * comment. Excluded class names always have higher priority
     * than included class name.
     *
     * @param classNames The class names to exclude
     */
    public void addExcludedClassNames(String[] classNames) {
        excludedClassNames.addAll(Arrays.asList(classNames));
    }

    /**
     * Set the class names that should be included when parsing
     * a comment.
     *
     * @param classNames The new included class names
     */
    public void setIncludedClassNames(String[] classNames) {
        includedClassNames = Arrays.asList(classNames);
    }

    /**
     * Set the class names that should be excluded when parsing
     * a comment. Excluded class names always have higher priority
     * than included class names.
     *
     * @param classNames The new excluded class names
     */
    public void setExcludedClassNames(String[] classNames) {
        excludedClassNames = Arrays.asList(classNames);
    }

    /**
     * This method is used to reset the parser. The mainly purpose
     * for this is to reuse a single parser across many comments. This
     * way, we can cache a parser for an whole file or even an whole
     * project.
     */
    protected void reset(String comment, int commentCharOffset) {
        this.buffer = new StringBuffer(comment);

        this.lineOffset = 0;
        this.columnOffset = 0;
        this.currentCharOffset = 0;
        this.commentCharOffset = commentCharOffset;
    }

    /**
     * See {@link AnnotationCommentParser#parse(String, int)} for
     * a description of the method. This will pass 0 as the
     * comment char offset.
     *
     * @param comment The comment to parse
     *
     * @return A filtered list of annotations.
     */
    public List<Annotation> parse(String comment) {
        return parse(comment, 0);
    }

    /**
     * This method is used to parse a comment and returns a list of
     * annotations that were found in the comment. The annotation
     * that are returned are those considered as valid annotation.
     *
     * <p>
     * The list returned does not contain any excluded class names
     * and contain only included class names.
     * </p>
     *
     * @param comment The comment to parse
     * @param commentCharOffset The offset of the comment within the file
     *
     * @return A filtered list of annotations.
     */
    public List<Annotation> parse(String comment, int commentCharOffset) {
        reset(comment, commentCharOffset);

        List<Annotation> annotations = new LinkedList<Annotation>();

        while (hasMoreChunk()) {
            currentChunk = getNextChunk();
            Annotation annotation = parseChunk(currentChunk);

            if (!isValidAnnotation(annotation)) {
                // The annotation parser is invalid, increase offset an try with the next chunk
                currentCharOffset++;
                continue;
            }

            annotations.add(annotation);
            currentCharOffset = annotation.getSourcePosition().endOffset + 1 - commentCharOffset;
        }

        return postProcess(annotations);
    }

    /**
     * This method determines if the annotation received is a valid annotation.
     * We say that an annotation is valid if it is not null, if it has an
     * endOffset and if the character following the closing parenthesis is
     * a whitespace character.
     *
     * @return true if the annotation is valid, false otherwise
     */
    protected boolean isValidAnnotation(Annotation annotation) {
        if (annotation == null || annotation.getSourcePosition().endOffset == -1) {
            return false;
        }

        int lastCharOffset = annotation.getSourcePosition().endOffset - currentCharOffset - commentCharOffset;
        if (lastCharOffset < 0 || lastCharOffset + 1 > currentChunk.length()) {
            // This is not normal, we should have a valid offset. Assume it's an invalid annotation
            return false;
        }

        if (lastCharOffset + 1 == currentChunk.length()) {
            // The last char is also the last char in the chunk, it's a valid annotation
            return true;
        }

        if (!Character.isWhitespace(currentChunk.codePointAt(lastCharOffset + 1))) {
            // An annotation must be followed by a whitespace character, it is not case here
            return false;
        }

        return true;
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
            boolean excluded = false;

            if (hasExcludeRestriction && excludedClassNames.contains(fullyQualifiedName)) {
                iterator.remove();
                excluded = true;
            }

            if (!excluded && hasIncludeRestriction && !includedClassNames.contains(fullyQualifiedName)) {
                iterator.remove();
                excluded = true;
            }
        }

        return annotations;
    }

    protected Annotation parseChunk(String chunk) {
        CharStream content = new ANTLRStringStream(chunk);

        // FIXME: The error reporter should be passed to the parser
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
            // FIXME: Handle error correctly
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
}
