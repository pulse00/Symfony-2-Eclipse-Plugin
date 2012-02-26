package com.dubture.symfony.annotation.parser.antlr;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;

public class AnnotationToken extends CommonToken {
    /**
     * Generated serialVersionUID
     */
    private static final long serialVersionUID = 391767298498973645L;

    protected int lineNumber = -1;
    protected int column = -1;
    protected int startOffset = -1;
    protected int endOffset = -1;

    public AnnotationToken(CharStream input, int type, int channel, int start, int stop) {
        super(input, type, channel, start, stop);

        lineNumber = getLine();
        column = getCharPositionInLine();
        startOffset = start;
        endOffset = stop;
    }

    @Override
    public int getLine() {
        return lineNumber;
    }

    public int getColumn() {
        return column;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    @Override
    public void setCharPositionInLine(int charPositionInLine) {
        super.setCharPositionInLine(charPositionInLine);

        column = charPositionInLine;
    }

    @Override
    public void setLine(int line) {
        super.setLine(line);

        lineNumber = line;
    }

    public void adjustOffset(int lineOffset, int columnOffset, int charOffset) {
        this.lineNumber += lineOffset;
        this.column += columnOffset;
        this.startOffset += charOffset;
        this.endOffset += charOffset;
    }
}
