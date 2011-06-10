package org.eclipse.symfony.core.parser.antlr;

import java.util.Stack;

import org.eclipse.dltk.core.builder.IBuildContext;


/**
 * 
 * {@link AnnotationNodeVisitor} parses the structured elements
 * from an annotation like namespace classname and parameters.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class AnnotationNodeVisitor implements IAnnotationNodeVisitor {

	private String className = "";
	private Stack<String> namespace = new Stack<String>();
	
	private IBuildContext context;
	
	public AnnotationNodeVisitor(IBuildContext context) {

		this.context = context;
		
	}


	public AnnotationNodeVisitor() {

	}


	@Override
	public void beginVisit(AnnotationCommonTree node) {

		int kind = node.getType();
		
		switch(kind) {
		
		
		case SymfonyAnnotationParser.ANNOTATION:
			
			break;
		
		
		case SymfonyAnnotationParser.CLASSNAME:
			
			assert node.getChildCount() == 1;
			className = node.getChild(0).toString();
			break;
		
		case SymfonyAnnotationParser.NSPART:
			
			assert node.getChildCount() == 1;
			namespace.push(node.getChild(0).toString());
			
			
			break;
			
		default:
			

			break;
				
		}



	}

	@Override
	public void endVisit(AnnotationCommonTree node) {

		
		int kind = node.getType();
		
		switch(kind) {
		
		
		case SymfonyAnnotationParser.ANNOTATION:
		
			break;

		}
	}
	
	public String getClassName() {
			
		return className;
		
	}
	
	public String getNamespace() {

		
		String ns = "";
		
		for (String part : namespace) {				
			ns += part + "\\";				
		}
		
		return ns;
		
	}
	
	public String getFullyQualifiedName() {
		
		return getNamespace() + getClassName();
		
	}
	
	public String getFirstNamespacePart() {
		
		if (namespace.size() > 0) {
			return namespace.get(0);
		}
		
		return null;
	}
}
