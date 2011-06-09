package org.eclipse.symfony.core.parser.antlr;


/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class AnnotationNodeVisitor implements IAnnotationNodeVisitor {

	@Override
	public void beginVisit(AnnotationCommonTree node) {


	}

	@Override
	public void endVisit(AnnotationCommonTree node) {

		
		int kind = node.getType();
		
		switch(kind) {
		
		
		case SymfonyAnnotationParser.ANNOTATION:
			
			System.err.println("a: " + node.toString());			
			break;
		
		
		default:
			
			System.err.println("d: " + node.toString());
				
		}			
	}
}
