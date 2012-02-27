package com.dubture.symfony.annotation.parser.antlr;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;

public class AnnotationToken extends CommonToken {
    /**
     * Generated serialVersionUID
     */
    private static final long serialVersionUID = 391767298498973645L;

    /**
     * These are offset that needs to be applied to the various getters. They
     * are used to adjust the token position relative to a different
     * referential. For example, if we parse an annotation, we usually receive
     * only the substring starting at the @ sign until the end of the comment.
     * The offset are then used to adjust where they are suppose to appear
     * within the comment.
     */
    protected int lineOffset = 0;
    protected int columnOffset = 0;
    protected int charOffset = 0;

    public AnnotationToken(CharStream input, int type, int channel, int start,
            int stop) {
        super(input, type, channel, start, stop);
    }

    @Override
    public int getLine() {
        return line + lineOffset;
    }

    public int getColumn() {
        return getCharPositionInLine() + columnOffset;
    }

    public int getStartOffset() {
        return getStartIndex() + charOffset;
    }

    public int getEndOffset() {
        return getStopIndex() + charOffset;
    }

    public void adjustOffset(int lineOffset, int columnOffset, int charOffset) {
        this.lineOffset = lineOffset;
        this.columnOffset = columnOffset;
        this.charOffset = charOffset;
    }

    @Override
    public String toString() {
        return getText() + "[" + getLine() + ", " + getColumn() + "]("
                + getStartOffset() + ", " + getEndOffset() + ")";
    }
}
