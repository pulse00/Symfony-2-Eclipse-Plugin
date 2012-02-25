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
    public int startIndex;

    /**
     * The absolute end index relative to the source input
     */
    public int endIndex;

    /**
     * The length of this source position, endIndex - startIndex
     */
    public int length;

    public SourcePosition() {
        this(-1, -1, -1, -1);
    }

    public SourcePosition(int line, int column, int startIndex) {
        this.line = line;
        this.column = column;
        this.startIndex = startIndex;
    }

    public SourcePosition(int startLine, int startColumn, int startIndex, int endIndex) {
        this.line = startLine;
        this.column = startColumn;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.length = endIndex - startIndex;
    }

    public SourcePosition(AnnotationToken startToken) {
        this(startToken.getLine(),
             startToken.getColumn(),
             startToken.getStartIndex());
    }

    public SourcePosition(AnnotationToken startToken, AnnotationToken endToken) {
        this(startToken.getLine(),
             startToken.getColumn(),
             startToken.getStartIndex(),
             endToken.getStopIndex());
    }

    public void set(int startLine, int startColumn, int startIndex, int endIndex) {
        this.line = startLine;
        this.column = startColumn;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.length = endIndex - startIndex;
    }

    public void set(AnnotationToken startToken, AnnotationToken endToken) {
        set(startToken.getLine(),
            startToken.getColumn(),
            startToken.getStartIndex(),
            endToken.getStopIndex());
    }

    public void setStart(int line, int column, int startIndex) {
        this.line = line;
        this.column = column;
        this.startIndex = startIndex;
    }

    public void setStart(AnnotationToken startToken) {
        setStart(startToken.getLine(),
                 startToken.getColumn(),
                 startToken.getStartIndex());
    }

    public void setEnd(int endIndex) {
        this.endIndex = endIndex;
        this.length = endIndex - startIndex;
    }

    public void setEnd(AnnotationToken endToken) {
        setEnd(endToken.getStopIndex());
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
        return "<" + line + ", " + column + ">(" + startIndex + ", " + endIndex + ")";
    }
}
