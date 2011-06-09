package org.eclipse.symfony.core.parser.antlr;



/**
 * 
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public interface IAnnotationNodeVisitor {
	
	
	void beginVisit(AnnotationCommonTree node);
	void endVisit(AnnotationCommonTree node);
}
