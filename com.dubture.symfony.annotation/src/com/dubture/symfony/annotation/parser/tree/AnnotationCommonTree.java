/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.annotation.parser.tree;

import java.util.List;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import com.dubture.symfony.annotation.parser.tree.visitor.IAnnotationNodeVisitor;



/**
 *
 * The {@link AnnotationCommonTree} is used to traverse
 * the Tree created by the {@link SymfonyAnnotationParser}.
 *
 * @see AnnotationVisitor
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class AnnotationCommonTree extends CommonTree {


    public AnnotationCommonTree(Token payload) {
        super(payload);
    }

    public AnnotationCommonTree(TokenStream input, Token start, Token stop) {

    }

    @Override
    public AnnotationCommonTree getChild(int i) {
        if (children == null || i < 0 || i >= children.size()) {
            return null;
        }

        return (AnnotationCommonTree)children.get(i);
    }

    public AnnotationCommonTree getFirstChildFromType(int type) {
        Tree child = getFirstChildWithType(type);

        return (AnnotationCommonTree) child;
    }

    @Override
    public CommonToken getToken() {
        return (CommonToken)token;
    }

    @SuppressWarnings("unchecked")
    public List<AnnotationCommonTree> getChildTrees() {
        return (List<AnnotationCommonTree>) getChildren();
    }

    /**
     * Traverse the annotation tree.
     *
     * @param visitor
     */
    public void accept(IAnnotationNodeVisitor visitor) {
        visitor.visit(this);
    }
}
