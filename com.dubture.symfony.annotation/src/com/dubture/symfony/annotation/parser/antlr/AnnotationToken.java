package com.dubture.symfony.annotation.parser.antlr;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;

public class AnnotationToken extends CommonToken {
    /**
     * Generated serialVersionUID
     */
    private static final long serialVersionUID = 391767298498973645L;

    protected int column = -1;

    public AnnotationToken(CharStream input, int type, int channel, int start, int stop) {
        super(input, type, channel, start, stop);
        column = getCharPositionInLine();
    }

    @Override
    public void setCharPositionInLine(int charPositionInLine) {
        super.setCharPositionInLine(charPositionInLine);

        column = charPositionInLine;
    }

    public int getColumn() {
        return column;
    }

    public void adjustOffset(int lineOffset, int columnOffset, int indexOffset) {
        this.line += lineOffset;
        this.column += columnOffset;
        this.start += indexOffset;
        this.stop += indexOffset;
    }
}
