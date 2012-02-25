package com.dubture.symfony.annotation.parser.antlr;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;

public class AnnotationToken extends CommonToken {
    /**
     * Generated serialVersionUID
     */
    private static final long serialVersionUID = 391767298498973645L;

    public AnnotationToken(CharStream input, int type, int channel, int start, int stop) {
        super(input, type, channel, start, stop);
    }

    public Position getPosition() {
        return new Position(this);
    }

    public Position getPosition(int offset) {
        return new Position(this, offset);
    }
}
