package com.dubture.symfony.annotation.parser.antlr;

import java.util.List;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;



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
    	
        if (children == null || i >= children.size()) {
            return null;
        }
        return (AnnotationCommonTree)children.get(i);
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

		visitor.beginVisit(this);
		
		for (int i = 0; i < getChildCount(); i++) {
			AnnotationCommonTree child = (AnnotationCommonTree) getChild(i);
			child.accept(visitor);
			
		}
		
		visitor.endVisit(this);
		
	}
}