package org.eclipse.symfony.core.visitor;

import java.io.BufferedReader;
import java.io.StringReader;

import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.Route;

/**
 * 
 * {@link ControllerVisitor} parses controller classes
 * for annotations.
 *  
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class ControllerVisitor extends PHPASTVisitor {

	
	private ClassDeclaration currentClass = null;
	private PHPMethodDeclaration currentMethod = null;
	private boolean isAction = false;
	private char[] content;
	private IBuildContext context;
	
	
	public ControllerVisitor(IBuildContext context) {
		
		this(context.getContents());
		this.context = context;
		
	}
	
	public ControllerVisitor(char[] content) {
		
		this.content = content;
	}
	
	
	@Override
	public boolean visit(ClassDeclaration s) throws Exception {

		currentClass = s;
		
		System.out.println("visit controller " + s.getName());
		for (String name : currentClass.getSuperClassNames()) {			
			if (name.equals("Controller")) {
			}
			
		}
		
		return true;
		
	}
	
	@Override
	public boolean endvisit(ClassDeclaration s) throws Exception {

		currentClass = null;
		return true;
	}
	
	
	
	@Override
	public boolean visit(PHPMethodDeclaration method) throws Exception {

		currentMethod = method;
		isAction = currentMethod.getName().endsWith("Action");
		
		if (currentClass == null || isAction == false)
			return true;
		
		
		PHPDocBlock comment = method.getPHPDoc();
		
		if (comment != null) {
			
			int start = comment.sourceStart();
			int end = comment.sourceEnd();
			
			String docBlock = String.valueOf(content).substring(start,end);
			parseAnnotations(docBlock, start);			
			
		}
		
		return true;
	}
	
	
	@Override
	public boolean endvisit(PHPMethodDeclaration s) throws Exception {
		
		currentMethod = null;
		return true;
	}
	
	/**
	 * 
	 * Extract Annotations from Comment string
	 * 
	 * @param comment
	 * @param sourceStart
	 */
	public void parseAnnotations(String comment, int sourceStart) {
		
		
		BufferedReader buffer = new BufferedReader(new StringReader(comment));
		
		try {

			String line;
			
			while((line = buffer.readLine()) != null) {
				
				sourceStart += line.toCharArray().length;
				
				int start = line.indexOf('@');
				int end = line.lastIndexOf(')');
				
				if ((start == -1 || end == -1)) continue;
				
				if(!line.substring(start).startsWith("@Route(") &&
                        !line.substring(start).startsWith("@Template(")) continue;

				
				String annotation = line.substring(start, end+1);
				
				if (annotation.startsWith("@Route")) {
					Route route = Route.fromAnnotation(context.getFile(), annotation);
					ModelManager.getInstance().addRoute(route);
				}
				System.out.println("found annotation: " + annotation);
				
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}		
	}
}