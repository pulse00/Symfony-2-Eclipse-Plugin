package org.eclipse.symfony.core.util;

import java.util.Iterator;
import java.util.Map;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.eclipse.symfony.core.parser.antlr.AnnotationCommonTree;
import org.eclipse.symfony.core.parser.antlr.AnnotationCommonTreeAdaptor;
import org.eclipse.symfony.core.parser.antlr.AnnotationLexer;
import org.eclipse.symfony.core.parser.antlr.AnnotationNodeVisitor;
import org.eclipse.symfony.core.parser.antlr.AnnotationParser;
import org.eclipse.symfony.index.dao.Route;

public class AnnotationUtils {
	
	
	@SuppressWarnings("rawtypes")
	public static Route getRoute(String line, String bundle, String controller, String action) {
		
		Route route = null;
		
		try {
			int start = line.indexOf('@');
			int end = line.length()-1;
			
			String annotation = line.substring(start, end+1);
			CharStream content = new ANTLRStringStream(annotation);
			
			AnnotationLexer lexer = new AnnotationLexer(content);
			
			AnnotationParser parser = new AnnotationParser(new CommonTokenStream(lexer));
			
			parser.setTreeAdaptor(new AnnotationCommonTreeAdaptor());
			AnnotationParser.annotation_return root;
			
			root = parser.annotation();
			AnnotationCommonTree tree = (AnnotationCommonTree) root.getTree();
			AnnotationNodeVisitor visitor = new AnnotationNodeVisitor();
			tree.accept(visitor);
			
			Map<String, String> args = visitor.getArguments();
			
			Iterator it = args.keySet().iterator();
			
			String name = null;
			String pattern = null;
			
			while(it.hasNext()) {
				
				String key = (String) it.next();
				String value = args.get(key);
				
				if (key != null && value != null && key.equals("name")) {					
					name = value;
				} else {
					pattern = key;
				}
				
			}
			
			if (name != null & pattern != null)
				route = new Route(bundle, controller, action, name, pattern);
			
		} catch (RecognitionException e) {

			e.printStackTrace();
		}
		return route;
	}
}
