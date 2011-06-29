package org.eclipse.symfony.core.parser.antlr;

import java.util.HashMap;
import java.util.Map;
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
	
	private Map<String, String> arguments = new HashMap<String, String>();
	
	public AnnotationNodeVisitor(IBuildContext context) {

		this.context = context;
		
	}


	public AnnotationNodeVisitor() {

	}


	@Override
	public void beginVisit(AnnotationCommonTree node) {

		int kind = node.getType();
		
		switch(kind) {
		
		
		case AnnotationParser.NAMED_ARG:

			String key = node.getChild(0).toString();
			arguments.put(key, node.getChild(1).getChild(0).toString());

			break;
		
		
		case AnnotationParser.ARGUMENT_LIST:
			
			
			
			break;
			
			
		case AnnotationParser.ANNOTATION:
			
			break;
		
		
		case AnnotationParser.CLASSNAME:
			
			assert node.getChildCount() == 1;
			className = node.getChild(0).toString();
			break;
			
			
		case AnnotationParser.LITERAL_ARG:
			
			arguments.put(node.getChild(0).toString(), null);
			
			break;
		
		case AnnotationParser.NSPART:
			
			assert node.getChildCount() == 1;
			namespace.push(node.getChild(0).toString());
			
			
			break;
			
		case AnnotationParser.STRING_LITERAL:
			
			break;
			
		default:
			
			break;
				
		}



	}

	@Override
	public void endVisit(AnnotationCommonTree node) {

		
		int kind = node.getType();
		
		switch(kind) {
		
		
		case AnnotationParser.ANNOTATION:
		
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
	
	public Map<String, String> getArguments() {
		
		return arguments;
	}
	
	public String getFirstNamespacePart() {
		
		if (namespace.size() > 0) {
			return namespace.get(0);
		}
		
		return null;
	}
}
