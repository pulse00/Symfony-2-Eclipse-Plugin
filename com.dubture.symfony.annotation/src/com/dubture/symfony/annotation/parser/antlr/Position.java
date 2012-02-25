package com.dubture.symfony.annotation.parser.antlr;

/**
 * This class hold information about position in respect to
 * input. It contains information about the line and the
 * char position in the line.
 *
 * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
 */
public class Position {

    /**
     * The line index relative to the source input
     */
    public int line;

    /**
     * The column relative to the line
     */
    public int column;

    /**
     * The absolute start index relative to the source input
     */
    public int startIndex;

    /**
     * The length of this position, endIndex - startIndex
     */
    public int length;

    /**
     * The absolute end index relative to the source input
     */
    public int endIndex;

    public Position(int line, int column, int startIndex, int endIndex) {
        this.line = line;
        this.column = column;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.length = endIndex - startIndex;
    }

    public Position(AnnotationToken token) {
        this(token.getLine(), token.getCharPositionInLine(), token.getStartIndex(), token.getStopIndex());
    }

    public Position(AnnotationToken token, int offset) {
        this(token.getLine(), token.getCharPositionInLine(), token.getStartIndex() + offset, token.getStopIndex() + offset);
    }

    @Override
    public String toString() {
        return "<" + line + ", " + column + ">(" + startIndex + ", " + endIndex + ")";
    }
}
