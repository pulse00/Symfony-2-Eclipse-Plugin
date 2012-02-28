/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.test;

import org.antlr.runtime.RecognitionException;

import com.dubture.symfony.annotation.parser.antlr.error.IAnnotationErrorReporter;

public class DebugErrorReporter implements IAnnotationErrorReporter {

private int errorCount = 0;

    public StringBuilder messages = new StringBuilder();

    @Override
    public void reportError(String header, String message, RecognitionException e) {

        errorCount++;

        messages.append("[" + header + "] ");
        messages.append(message);
        messages.append("\n");

    }


    public int getErrorCount() {
        return errorCount;
    }

    public boolean hasErrors() {
        return errorCount > 0;
    }

    public void reset() {
        errorCount = 0;
        messages = new StringBuilder();
    }

    public String getErrorsAsString() {
        return messages.toString();
    }
}
