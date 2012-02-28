package com.dubture.symfony.annotation.parser.antlr;

/**
 * This class hold information about position in respect to
 * input. It contains information about the line and the
 * char position in the line.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class SourcePosition {

    static public SourcePosition fromToken(AnnotationToken token) {
        SourcePosition position = new SourcePosition();
        position.setToken(token);

        return position;
    }

    static public SourcePosition fromStartEndToken(AnnotationToken startToken, AnnotationToken endToken) {
        return new SourcePosition(startToken, endToken);
    }

    /**
     * The start line index relative to the source input
     */
    public int line;

    /**
     * The start column index relative to the start line
     */
    public int column;

    /**
     * The absolute start index relative to the source input
     */
    public int startOffset;

    /**
     * The absolute end index relative to the source input
     */
    public int endOffset;

    /**
     * The length of this source position, endOffset - startOffset
     */
    public int length;

    public SourcePosition() {
        this(-1, -1, -1, -1);
    }

    public SourcePosition(int line, int column, int startOffset) {
        this.line = line;
        this.column = column;
        this.startOffset = startOffset;
    }

    public SourcePosition(int startLine, int startColumn, int startOffset, int endOffset) {
        this.line = startLine;
        this.column = startColumn;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.length = endOffset - startOffset + 1;
    }

    public SourcePosition(AnnotationToken startToken) {
        this(startToken.getLine(),
             startToken.getColumn(),
             startToken.getStartOffset());
    }

    public SourcePosition(AnnotationToken startToken, AnnotationToken endToken) {
        this(startToken.getLine(),
             startToken.getColumn(),
             startToken.getStartOffset(),
             endToken.getEndOffset());
    }

    public void set(int startLine, int startColumn, int startOffset, int endOffset) {
        this.line = startLine;
        this.column = startColumn;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.length = endOffset - startOffset + 1;
    }

    public void set(AnnotationToken startToken, AnnotationToken endToken) {
        set(startToken.getLine(),
            startToken.getColumn(),
            startToken.getStartOffset(),
            endToken.getEndOffset());
    }

    public void setStart(int line, int column, int startOffset) {
        this.line = line;
        this.column = column;
        this.startOffset = startOffset;
    }

    public void setStart(AnnotationToken startToken) {
        setStart(startToken.getLine(),
                 startToken.getColumn(),
                 startToken.getStartOffset());
    }

    public void setEnd(int endOffset) {
        this.endOffset = endOffset;
        this.length = endOffset - startOffset + 1;
    }

    public void setEnd(AnnotationToken endToken) {
        setEnd(endToken.getEndOffset());
    }

    /**
     * This method is useful for source position spanning one token.
     * For example, a string literal will have only one associated token,
     * this method can then be used to set every value of the source position.
     *
     * @param token
     */
    public void setToken(AnnotationToken token) {
        setStart(token);
        setEnd(token);
    }

    @Override
    public String toString() {
        return "<" + line + ", " + column + ">(" + startOffset + ", " + endOffset + ")";
    }
}
