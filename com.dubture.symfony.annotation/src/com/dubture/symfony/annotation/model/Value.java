package com.dubture.symfony.annotation.model;

import com.dubture.symfony.annotation.parser.antlr.SourcePosition;

public class Value implements IValue {

    protected SourcePosition sourcePosition = new SourcePosition();

    @Override
    public SourcePosition getSourcePosition() {
        return sourcePosition;
    }

    @Override
    public void setSourcePosition(SourcePosition position) {
        sourcePosition = position;
    }
}
