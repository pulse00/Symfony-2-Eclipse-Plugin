/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.annotation.parser.tree;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;

/**
 *
 * {@link AnnotationCommonTreeAdaptor} is an adaptor to
 * create {@link AnnotationCommonTree} instances.
 *
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class AnnotationCommonTreeAdaptor extends CommonTreeAdaptor {

    public Object create(Token payload) {
        return new AnnotationCommonTree(payload);
    }


    @Override
    public Object errorNode(TokenStream input, Token start, Token stop,
            RecognitionException e) {

        return new AnnotationCommonTree(input, start, stop);
    }

}
