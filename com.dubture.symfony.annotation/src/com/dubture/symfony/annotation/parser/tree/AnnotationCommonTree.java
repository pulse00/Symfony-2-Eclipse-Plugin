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

import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import com.dubture.symfony.annotation.parser.antlr.AnnotationToken;
import com.dubture.symfony.annotation.parser.antlr.Position;
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

    public AnnotationToken getToken() {
        return (AnnotationToken)token;
    }

    public Position getPosition() {
        return getToken().getPosition();
    }

    /**
     * <p>
     * This returns the position of the token. The offset parameter
     * is used to adjust the position of the token. Take for example
     * this string: "*   @test()". Using the AnnotationCommentParser,
     * the parse would be on "@test()" and the position returned here
     * would be relative to this substring. The start index would be
     * 0 and the end index would be 6. However, in respect to
     * the original comment, this is not true.
     * </p>
     *
     * <p>
     * The offset is then used to make the position relative to
     * the start of the comment. So passing an offset of 5, would
     * then results in the good position start index 5 and the good
     * position end index 11.
     * </p>
     *
     * @param offset The offset from which the parse starts
     * @return
     */
    public Position getPosition(int offset) {
        return getToken().getPosition(offset);
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
